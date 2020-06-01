package scene.models;

import javafx.geometry.Point3D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Affine;

import java.util.ArrayList;

public abstract class Mesh {
    Affine global, camera;

    public Mesh(int width, int height){
        global = new Affine(1,0,0,width/2,0,1,0,height/2,0,0,1,0);
        camera = new Affine();
    }

    public void localToGlobal(ArrayList<Point3D> v){
        Point3D result = null;
        for(int i=0;i<v.size();i++){
            result = global.transform(v.get(i).getX(), v.get(i).getY(), v.get(i).getZ());
            v.set(i,result);
        }
    }

    public abstract void draw(GraphicsContext gc);
}
