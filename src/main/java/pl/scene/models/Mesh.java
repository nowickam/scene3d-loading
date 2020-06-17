package pl.scene.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.geometry.Point3D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Affine;
import pl.scene.primitives.Triangle;

import java.util.ArrayList;

@JsonIgnoreProperties(value = {"globalV", "tr", "rotateX", "rotateY", "rotateZ", "translate", "v"})
public abstract class Mesh {
    private Affine rotateX, rotateY, rotateZ, translate;
    protected ArrayList<Point3D> v;
    protected ArrayList<Point3D> globalV;
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
        rotateX(rx);
        rotateY(ry);
        rotateZ(rz);
        translate(px, py, pz);
    }


    private void rotateX(double alpha) {
        double alphaRad = Math.toRadians(alpha);
        Point3D result = null;
        rotateX = new Affine(1,0,0,0,
                0,Math.cos(alphaRad), -Math.sin(alphaRad),0,
                0, Math.sin(alphaRad),Math.cos(alphaRad),0);
        for(int i = 0; i< globalV.size(); i++){
            result = rotateX.transform(globalV.get(i));
            globalV.set(i,result);
        }
    }

    private void rotateY(double alpha) {
        double alphaRad = Math.toRadians(alpha);
        Point3D result = null;
        rotateY = new Affine(Math.cos(alphaRad),0,Math.sin(alphaRad),0,
                0,1, 0,0,
                -Math.sin(alphaRad), 0,Math.cos(alphaRad),0);
        for(int i = 0; i< globalV.size(); i++){
            result = rotateY.transform(globalV.get(i));
            globalV.set(i,result);
        }
    }

    private void rotateZ(double alpha) {
        double alphaRad = Math.toRadians(alpha);
        Point3D result = null;
        rotateZ = new Affine(Math.cos(alphaRad),-Math.sin(alphaRad),0,0,
                Math.sin(alphaRad),Math.cos(alphaRad), 0,0,
                0, 0, 1,0);
        for(int i = 0; i< globalV.size(); i++){
            result = rotateZ.transform(globalV.get(i));
            globalV.set(i,result);
        }
    }

    private void translate(double tx, double ty, double tz) {
        Point3D result = null;
        translate = new Affine(1,0,0,tx,
                0,1, 0,ty,
                0, 0,1,tz);
        for(int i = 0; i< globalV.size(); i++){
            result = translate.transform(globalV.get(i));
            globalV.set(i,result);
        }
    }

    public ArrayList<Point3D> getGlobalV() {
        return globalV;
    }

    public ArrayList<Point3D> getV() {
        return v;
    }

    public void setGlobalV(ArrayList<Point3D> v) {
        this.globalV = new ArrayList<Point3D>(v);
        transform();
    }

    protected abstract void initVertices();

    protected abstract void initTriangles();

    public abstract void draw(GraphicsContext gc);
}
