package scene;

import javafx.geometry.Point3D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Affine;
import scene.models.Cuboid;
import scene.models.Cylinder;
import scene.models.Mesh;

import java.util.ArrayList;

public class MeshScene {
    private final Affine toGlobal, toCamera, toPerspective;
    private ArrayList<Mesh> meshes;
    private Camera camera;
    private double width, height;

    public MeshScene(int width, int height){
        this.height=height;
        this.width=width;
        meshes = new ArrayList<>();
        camera = new Camera(width, height);

        double alpha=45;
        toGlobal = new Affine(-1,0,0,0,0,-1,0,0,0,0,-1,width/2);
        toCamera = new Affine(1,0,0,camera.getPosition().getX(),0,1,0,camera.getPosition().getY(),0,0,1,camera.getPosition().getZ());
        toPerspective = new Affine(-width/2/Math.tan(Math.toRadians(alpha)),0,width/2,0,
                0, width/2/Math.tan(Math.toRadians(alpha)),height/2,0,
                0,0,0,1);
    }

    public void draw(GraphicsContext gc){
        for (Mesh m:meshes) {
            m.draw(gc);
        }
    }

    private void transformCoordinates(Mesh m){
        toGlobal(m.getV());
        toCamera(m.getV());
        toPerspective(m.getV());
    }

    public void loadMeshes(){
//        Cuboid c = new Cuboid(100,100,100, 150, -150, 0, 0, 0, 0);
//        meshes.add(c);

        Cylinder c = new Cylinder(100,100,12, 150, -150, 0, 0, 0, 0);
        addMesh(c);
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

    public ArrayList<Mesh> getMeshes() {
        return meshes;
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
