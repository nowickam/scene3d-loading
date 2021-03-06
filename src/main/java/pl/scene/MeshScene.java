package pl.scene;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.geometry.Point3D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Affine;
import pl.scene.matrices.Matrices;
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

        toGlobal = Matrices.getGlobal();
        toCamera = camera.getCameraMatrix();
        double alpha=camera.getAlpha();
        toPerspective = Matrices.getPerspective(width, height, alpha);
    }

    public void draw(GraphicsContext gc){
        for (Mesh m:meshes) {
            m.draw(gc, width, height);
        }
    }

    public void moveCamera(){
        toCamera = camera.getCameraMatrix();
        for(Mesh m : meshes){
            m.setTransformedV(m.getV());
            transformCoordinates(m);
        }
    }

    private void transformCoordinates(Mesh m){
        Matrices.apply(toGlobal, m.getTransformedV());
        Matrices.apply(toCamera, m.getTransformedV());
        Matrices.applyAndNormalize(toPerspective, m.getTransformedV());
    }

    public void loadMeshesLocal(){
        clearMeshes();

        Mesh m = new Sphere(200,20,20, 700, 200, -500, 0, 0, 0);
        addMesh(m);
        m = new Sphere(100,20,20, -300, 200, -500, 0, 0, 0);
        addMesh(m);
        m = new Cuboid(100,200,100, -200, -200, 0, 0, 0, 60);
        addMesh(m);
        m = new Cylinder(100,150,50, -300, 200, 400, 0, 0, 0);
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

        String mString;
        for(Mesh m : meshes){
            mString = mapper.writeValueAsString(m);
            writer.println(m.getClass().getCanonicalName()+" "+mString);
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
