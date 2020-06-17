package pl.scene;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.geometry.Point3D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Affine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.scene.models.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class MeshScene {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private final Affine toGlobal, toPerspective;
    private Affine toCamera;
    private ArrayList<Mesh> meshes;
    private Camera camera;
    private double width, height;

    public MeshScene(int width, int height){
        this.height=height;
        this.width=width;
        meshes = new ArrayList<>();
        camera = new Camera(width, height);

        double alpha=40;
        toGlobal = new Affine(-1,0,0,0,0,-1,0,0,0,0,-1,width/2);
        toCamera = camera.getCameraMatrix();
        toPerspective = new Affine(-width/2/Math.tan(Math.toRadians(alpha)),0,width/2,0,
                0, width/2/Math.tan(Math.toRadians(alpha)),height/2,0,
                0,0,0,1);
    }

    public void draw(GraphicsContext gc){
        for (Mesh m:meshes) {
            m.draw(gc);
        }
    }

    public void moveCamera(){
        toCamera = camera.getCameraMatrix();
        for(Mesh m : meshes){
            m.setGlobalV(m.getV());
            transformCoordinates(m);
        }
    }

    private void transformCoordinates(Mesh m){
        toGlobal(m.getGlobalV());
        toCamera(m.getGlobalV());
        toPerspective(m.getGlobalV());
    }

    public void loadMeshes(File file) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
//        Sphere s = new Sphere(100,10,5, 100, -100, -500, 0, 0, 0);
//        addMesh(s);
//        s = new Sphere(100,10,5, 100, -100, 0, 0, 0, 0);
//        addMesh(s);
//        s = new Sphere(100,10,5, 100, -100, 400, 0, 0, 0);
//        addMesh(s);

        clearMeshes();
        ObjectMapper mapper = new ObjectMapper();
        Scanner reader = new Scanner(file);
        String mString[];
        while(reader.hasNextLine()){
            mString = reader.nextLine().split(" ");
            Class c = Class.forName(mString[0]);
            Mesh m = (Mesh) mapper.readValue(mString[1], Class.forName(mString[0]));
            addMesh(m);
        }

    }

    public void saveMeshes(PrintWriter writer) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(meshes.get(0));
        Cuboid c = mapper.readValue(s, Cuboid.class);
        String s2 = mapper.writeValueAsString(c);
        System.out.println(s);
        System.out.println(s2);

        String mString;
        for(Mesh m : meshes){
            mString = mapper.writeValueAsString(m);
            writer.println(m.getClass().getCanonicalName()+" "+mString);
        }
    }

    public void toGlobal(ArrayList<Point3D> v){
        Point3D result = null;
        for(int i=0;i<v.size();i++){
            result = toGlobal.transform(v.get(i));
            v.set(i,result);
        }
    }

    public void toCamera(ArrayList<Point3D> v){
        Point3D result = null;
        for(int i=0;i<v.size();i++){
            result = toCamera.transform(v.get(i));
            v.set(i,result);
        }
    }

    public void toPerspective(ArrayList<Point3D> v){
        Point3D result = null, point = null;
        double z = 0;
        for(int i=0;i<v.size();i++){
            z = v.get(i).getZ();
            result = toPerspective.transform(v.get(i));
            result = new Point3D(result.getX()/z, result.getY()/z, result.getZ()/z);
            v.set(i,result);
        }
    }

    public void clearMeshes() {
        meshes = new ArrayList<>();

    }

    public void addMesh(Mesh mesh) {
        transformCoordinates(mesh);
        meshes.add(mesh);
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }
}
