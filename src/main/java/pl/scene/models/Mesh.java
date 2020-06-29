package pl.scene.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.geometry.Point3D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Affine;
import pl.scene.primitives.Triangle;
import pl.scene.matrices.Matrices;

import java.util.ArrayList;

@JsonIgnoreProperties(value = {"tr", "rotateX", "rotateY", "rotateZ", "translate", "v", "transformedV"})
public abstract class Mesh {
    private Affine rotateX, rotateY, rotateZ, translate;
    protected ArrayList<Point3D> v;
    protected ArrayList<Point3D> transformedV;
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
        rotateX = Matrices.getRotationX(alpha);
        Matrices.apply(rotateX, transformedV);
    }

    private void rotateY(double alpha) {
        rotateY = Matrices.getRotationY(alpha);
        Matrices.apply(rotateY, transformedV);
    }

    private void rotateZ(double alpha) {
        rotateZ = Matrices.getRotationZ(alpha);
        Matrices.apply(rotateZ, transformedV);
    }

    private void translate(double tx, double ty, double tz) {
        translate = Matrices.getTranslation(tx, ty, tz);
        Matrices.apply(translate, transformedV);
    }

    public ArrayList<Point3D> getTransformedV() {
        return transformedV;
    }

    public ArrayList<Point3D> getV() {
        return v;
    }

    public void setTransformedV(ArrayList<Point3D> v) {
        this.transformedV = new ArrayList<Point3D>(v);
        transform();
    }

    protected abstract void initVertices();

    protected abstract void initTriangles();

    public abstract void draw(GraphicsContext gc, int width, int height);

    public double getPy() {
        return py;
    }

    public void setPy(double py) {
        this.py = py;
    }

    public double getPx() {
        return px;
    }

    public void setPx(double px) {
        this.px = px;
    }

    public double getPz() {
        return pz;
    }

    public void setPz(double pz) {
        this.pz = pz;
    }

    public double getRz() {
        return rz;
    }

    public void setRz(double rz) {
        this.rz = rz;
    }

    public double getRy() {
        return ry;
    }

    public void setRy(double ry) {
        this.ry = ry;
    }

    public double getRx() {
        return rx;
    }

    public void setRx(double rx) {
        this.rx = rx;
    }
}
