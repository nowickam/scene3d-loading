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

        transformedV = new ArrayList<>(v);
        transform();
    }

    @Override
    protected void initTriangles(){
        tr.clear();
        //side triangles
        int i=1;
        for(;i<n;i++){
            tr.add(new Triangle(transformedV.get(i), transformedV.get(i+1), transformedV.get(i+2+n)));
            tr.add(new Triangle( transformedV.get((i+2)+n-1), transformedV.get(i+2+n), transformedV.get(i+1)));
        }
        tr.add(new Triangle(transformedV.get(i), transformedV.get(1), transformedV.get(n+2)));
        tr.add(new Triangle(transformedV.get(n+3), transformedV.get(n+2), transformedV.get(1)));

        //upper triangles
        i=1;
        for(;i<n;i++){
            tr.add(new Triangle(transformedV.get(0), transformedV.get(i), transformedV.get(i+1)));
        }
        tr.add(new Triangle(transformedV.get(0), transformedV.get(i), transformedV.get(1)));

        //lower triangles
        i=n+2;
        for(;i<=2*n;i++){
            tr.add(new Triangle(transformedV.get(n+1), transformedV.get(i), transformedV.get(i+1)));
        }
        tr.add(new Triangle(transformedV.get(n+1), transformedV.get(i), transformedV.get(n+2)));
    }

    @Override
    public void draw(GraphicsContext gc, int width, int height) {
        ArrayList<Point3D> points;
        initTriangles();
        for(Triangle t : tr){
            t.clip(width, height);
            if(t.getEdgeP1(1) != null && t.getEdgeP2(1) != null)
                gc.strokeLine(t.getEdgeP1(1).getX(), t.getEdgeP1(1).getY(), t.getEdgeP2(1).getX(), t.getEdgeP2(1).getY());
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
