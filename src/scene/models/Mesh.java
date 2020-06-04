package scene.models;

import javafx.geometry.Point3D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Affine;

import java.awt.*;
import java.util.ArrayList;

public abstract class Mesh {
    private final Affine global, camera, perspective;
    private Affine rotateX, rotateY, rotateZ, translate;
    private int alpha;

    public Mesh(double w, double h, double cx, double cy, double cz){
        alpha=45;
        global = new Affine(-1,0,0,0,0,-1,0,0,0,0,-1,w/2);
        camera = new Affine(1,0,0,cx,0,1,0,cy,0,0,1,cz);
        perspective = new Affine(-w/2/Math.tan(Math.toRadians(alpha)),0,w/2,0,
                                0, w/2/Math.tan(Math.toRadians(alpha)),h/2,0,
                                0,0,0,1);
    }

    public void toGlobal(ArrayList<Point3D> v){
        Point3D result = null;
        for(int i=0;i<v.size();i++){
            result = global.transform(v.get(i));
            v.set(i,result);
        }
    }

    public void toCamera(ArrayList<Point3D> v){
        Point3D result = null;
        for(int i=0;i<v.size();i++){
            result = camera.transform(v.get(i));
            v.set(i,result);
        }
    }

    public void toPerspective(ArrayList<Point3D> v){
        Point3D result = null, point = null;
        double z = 0;
        for(int i=0;i<v.size();i++){
            z = v.get(i).getZ();
            result = perspective.transform(v.get(i));
            result = new Point3D(result.getX()/z, result.getY()/z, result.getZ()/z);
            v.set(i,result);
        }
    }

    public void rotateX(double alpha, ArrayList<Point3D> v) {
        double alphaRad = Math.toRadians(alpha);
        Point3D result = null;
        rotateX = new Affine(1,0,0,0,
                0,Math.cos(alphaRad), -Math.sin(alphaRad),0,
                0, Math.sin(alphaRad),Math.cos(alphaRad),0);
        for(int i=0;i<v.size();i++){
            result = rotateX.transform(v.get(i));
            v.set(i,result);
        }
    }

    public void rotateY(double alpha, ArrayList<Point3D> v) {
        double alphaRad = Math.toRadians(alpha);
        Point3D result = null;
        rotateY = new Affine(Math.cos(alphaRad),0,Math.sin(alphaRad),0,
                0,1, 0,0,
                -Math.sin(alphaRad), 0,Math.cos(alphaRad),0);
        for(int i=0;i<v.size();i++){
            result = rotateY.transform(v.get(i));
            v.set(i,result);
        }
    }

    public void rotateZ(double alpha, ArrayList<Point3D> v) {
        double alphaRad = Math.toRadians(alpha);
        Point3D result = null;
        rotateZ = new Affine(Math.cos(alphaRad),-Math.sin(alphaRad),0,0,
                Math.sin(alphaRad),Math.cos(alphaRad), 0,0,
                0, 0, 1,0);
        for(int i=0;i<v.size();i++){
            result = rotateZ.transform(v.get(i));
            v.set(i,result);
        }
    }

    public void translate(ArrayList<Point3D> v, double tx, double ty, double tz) {
        Point3D result = null;
        translate = new Affine(1,0,0,tx,
                0,1, 0,ty,
                0, 0,1,tz);
        for(int i=0;i<v.size();i++){
            result = translate.transform(v.get(i));
            v.set(i,result);
        }
    }

    public abstract void draw(GraphicsContext gc);
}
