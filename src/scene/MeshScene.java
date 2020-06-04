package scene;

import javafx.scene.canvas.GraphicsContext;
import scene.models.Cuboid;
import scene.models.Mesh;

import java.util.ArrayList;

public class MeshScene {
    private ArrayList<Mesh> meshes;
    private Camera camera;
    private double width, height;

    public MeshScene(int width, int height){
        this.height=height;
        this.width=width;
        meshes = new ArrayList<>();
        camera = new Camera(width, height);
    }

    public void draw(GraphicsContext gc){
        for (Mesh m:meshes) {
            m.draw(gc);
        }
    }

    public void loadMeshes(){
        Cuboid c = new Cuboid(width, height, camera, 100,100,100);
        meshes.add(c);
    }

    public ArrayList<Mesh> getMeshes() {
        return meshes;
    }

    public void addMesh(Mesh mesh) {
        meshes.add(mesh);
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }
}
