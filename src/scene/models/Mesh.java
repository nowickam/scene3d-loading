package scene.models;

import javafx.geometry.Point3D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Affine;
import scene.primitives.Triangle;

import java.util.ArrayList;

public abstract class Mesh {
    private Affine rotateX, rotateY, rotateZ, translate;
    protected ArrayList<Point3D> v;
    protected ArrayList<Triangle> tr;
    protected double px, py, pz, rx, ry, rz;


    public Mesh(double px, double py, double pz, double rx, double ry, double rz){
        this.px = px;
        this.py = py;
        this.pz = pz;
        this.rx = rx;
        this.ry = ry;
        this.rz = rz;
    }

    protected void transform(){
        translate(px, py, pz);
        rotateX(rx);
        rotateY(ry);
        rotateZ(rz);
    }


    private void rotateX(double alpha) {
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

    private void rotateY(double alpha) {
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

    private void rotateZ(double alpha) {
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

    private void translate(double tx, double ty, double tz) {
        Point3D result = null;
        translate = new Affine(1,0,0,tx,
                0,1, 0,ty,
                0, 0,1,tz);
        for(int i=0;i<v.size();i++){
            result = translate.transform(v.get(i));
            v.set(i,result);
        }
    }

    public ArrayList<Point3D> getV() {
        return v;
    }

    public void setV(ArrayList<Point3D> v) {
        this.v = new ArrayList<Point3D>(v);
    }

    protected abstract void initVertices();

    protected abstract void initTriangles();

    public abstract void draw(GraphicsContext gc);
}
