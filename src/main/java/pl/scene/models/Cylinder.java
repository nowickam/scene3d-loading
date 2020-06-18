package pl.scene.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.geometry.Point3D;
import javafx.scene.canvas.GraphicsContext;
import pl.scene.primitives.Triangle;

import java.util.ArrayList;

public class Cylinder extends Mesh{
    private double r, h;
    private int n;

    public Cylinder(@JsonProperty("r") double r, @JsonProperty("h") double h, @JsonProperty("n") int n,
                    @JsonProperty("px") double px, @JsonProperty("py")double py, @JsonProperty("pz")double pz,
                    @JsonProperty("rx")double rx, @JsonProperty("ry")double ry, @JsonProperty("rz")double rz){
        super(px, py, pz, rx, ry, rz);
        this.r=r;
        this.h=h;
        this.n =n;

        v = new ArrayList<>();
        tr = new ArrayList<>();

        initVertices();
    }

    @Override
    protected void initVertices(){
        //upper center
        v.add(new Point3D(0,h,0));
        //upper base
        for(int i = 0; i< n; i++){
            v.add(new Point3D(r*Math.cos(2*Math.PI*i/ n), h, r*Math.sin(2*Math.PI*i/n)));
        }

        //lower center
        v.add(new Point3D(0,0,0));
        //upper base
        for(int i = 0; i< n; i++){
            v.add(new Point3D(r*Math.cos(2*Math.PI*i/ n), 0, r*Math.sin(2*Math.PI*i/n)));
        }

        globalV = new ArrayList<>(v);
        transform();
    }

    @Override
    protected void initTriangles(){
        tr.clear();
        //side triangles
        int i=1;
        for(;i<n;i++){
            tr.add(new Triangle(globalV.get(i), globalV.get(i+1), globalV.get(i+2+n)));
            tr.add(new Triangle( globalV.get((i+2)+n-1), globalV.get(i+2+n), globalV.get(i+1)));
        }
        tr.add(new Triangle(globalV.get(i), globalV.get(1), globalV.get(n+2)));
        tr.add(new Triangle(globalV.get(n+3), globalV.get(n+2), globalV.get(1)));

        //upper triangles
        i=1;
        for(;i<n;i++){
            tr.add(new Triangle(globalV.get(0), globalV.get(i), globalV.get(i+1)));
        }
        tr.add(new Triangle(globalV.get(0), globalV.get(i), globalV.get(1)));

        //lower triangles
        i=n+2;
        for(;i<=2*n;i++){
            tr.add(new Triangle(globalV.get(n+1), globalV.get(i), globalV.get(i+1)));
        }
        tr.add(new Triangle(globalV.get(n+1), globalV.get(i), globalV.get(n+2)));
    }

    @Override
    public void draw(GraphicsContext gc) {
        ArrayList<Point3D> points;
        initTriangles();
        for(Triangle t : tr){
            //gc.strokeLine(t.getV1().getX(), t.getV1().getY(), t.getV2().getX(),t.getV2().getY());
            gc.strokeLine(t.getV2().getX(), t.getV2().getY(), t.getV3().getX(),t.getV3().getY());
            //gc.strokeLine(t.getV3().getX(), t.getV3().getY(), t.getV1().getX(),t.getV1().getY());
        }
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }
}
