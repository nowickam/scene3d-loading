package pl.scene;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.geometry.Point3D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Affine;
import pl.scene.models.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class MeshScene {
    private final Affine toGlobal, toPerspective;
    private Affine toCamera;
    private ArrayList<Mesh> meshes;
    private Camera camera;
    private int width, height;

    public MeshScene(int w, int h){
        width=w;
        height=h;
        meshes = new ArrayList<>();
        camera = new Camera(width, height);

        toGlobal = new Affine(-1,0,0,0,0,-1,0,0,0,0,-1,width/2);
        toCamera = camera.getCameraMatrix(toGlobal);
        double alpha=camera.getAlpha();
        toPerspective = new Affine(-width/2/Math.tan(Math.toRadians(alpha)),0,width/2,0,
                0, width/2/Math.tan(Math.toRadians(alpha)),height/2,0,
                0,0,0,1);
    }

    public void draw(GraphicsContext gc){
        for (Mesh m:meshes) {
            m.draw(gc, width, height);
        }
    }

    public void moveCamera(){
        toCamera = camera.getCameraMatrix(toGlobal);
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

    public void loadMeshesLocal(){
        clearMeshes();

        Mesh m = new Sphere(300,20,20, 700, 200, -500, 0, 0, 0);
        addMesh(m);
        m = new Sphere(100,20,20, -300, 200, -500, 0, 0, 0);
        addMesh(m);
        m = new Cuboid(100,200,100, -200, -200, 0, 0, 0, 60);
        addMesh(m);
        m = new Cylinder(100,150,50, -400, 200, 400, 0, 0, 0);
        addMesh(m);
        m = new Cone(100,150,50, 100, 0, 0, 0, 0, -30);
        addMesh(m);
    }

    public void loadMeshes(File file) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
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
//        Mesh cb = new Cylinder(10,10,10,10,10,10,10,10,10);
//        String s = mapper.writeValueAsString(cb);
//        Cuboid c = mapper.readValue(s, Cuboid.class);
//        String s2 = mapper.writeValueAsString(c);
//        System.out.println(s);
//        System.out.println(s2);

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
        Point3D result = null;
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
