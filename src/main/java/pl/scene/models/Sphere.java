package pl.scene.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.geometry.Point3D;
import javafx.scene.canvas.GraphicsContext;
import pl.scene.primitives.Triangle;

import java.util.ArrayList;

public class Sphere extends Mesh{
    private double r;
    private int m, n;


    public Sphere(@JsonProperty("r") double r, @JsonProperty("m") int m, @JsonProperty("n") int n,
                  @JsonProperty("px") double px, @JsonProperty("py")double py, @JsonProperty("pz")double pz,
                  @JsonProperty("rx")double rx, @JsonProperty("ry")double ry, @JsonProperty("rz")double rz){
        super(px, py, pz, rx, ry, rz);
        this.r = r;
        this.m = m;
        this.n = n;

        v = new ArrayList<>();
        tr = new ArrayList<>();

        initVertices();
    }

    @Override
    protected void initVertices(){
        v.add(new Point3D(0,r,0));
        v.add(new Point3D(0,-r,0));

        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                v.add(new Point3D(r*Math.cos(2*Math.PI*j/m)*Math.sin(Math.PI*(i+1)/(n+1)),
                                    r*Math.cos(Math.PI*(i+1)/(n+1)),
                                    r*Math.sin(2*Math.PI*j/m)*Math.sin(Math.PI*(i+1)/(n+1))));
            }
        }

        globalV = new ArrayList<>(v);
        transform();
    }

    @Override
    protected void initTriangles(){
        tr.clear();
        //upper lid
        for(int i=2;i<=m;i++){
            tr.add(new Triangle(globalV.get(0), globalV.get(i), globalV.get(i+1)));
        }
        tr.add(new Triangle(globalV.get(0), globalV.get(m+1), globalV.get(2)));

        //lower lid
        for(int i=2;i<=m;i++){
            tr.add(new Triangle(globalV.get(1), globalV.get((n-1)*m+i), globalV.get((n-1)*m+i+1)));
        }
        tr.add(new Triangle(globalV.get(1), globalV.get((n-1)*m+m+1), globalV.get((n-1)*m+2)));

        for(int i=0;i<n-1;i++){
            for(int j=1;j<m;j++){
                tr.add(new Triangle(globalV.get(i*m+j+1), globalV.get(i*m+j+2), globalV.get((i+1)*m+j+2)));
                tr.add(new Triangle(globalV.get(i*m+j+1), globalV.get((i+1)*m+j+2), globalV.get((i+1)*m+j+1)));
            }
            tr.add(new Triangle(globalV.get((i+1)*m+1), globalV.get(i*m+2), globalV.get((i+1)*m+2)));
            tr.add(new Triangle(globalV.get((i+1)*m+1), globalV.get((i+1)*m+2), globalV.get((i+2)*m+1)));
        }
    }

    @Override
    public void draw(GraphicsContext gc, int width, int height) {
        ArrayList<Point3D> points;
        initTriangles();
        int i=0;
        for(Triangle t : tr){
            t.clip(width, height);
            if(i<2*m && t.getEdgeP1(0) != null && t.getEdgeP2(0) != null)
                gc.strokeLine(t.getEdgeP1(0).getX(), t.getEdgeP1(0).getY(), t.getEdgeP2(0).getX(), t.getEdgeP2(0).getY());
            if(t.getEdgeP1(1) != null && t.getEdgeP2(1) != null)
                gc.strokeLine(t.getEdgeP1(1).getX(), t.getEdgeP1(1).getY(), t.getEdgeP2(1).getX(), t.getEdgeP2(1).getY());

            i++;
        }
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }
}
