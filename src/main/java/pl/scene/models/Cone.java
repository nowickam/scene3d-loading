package pl.scene.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.geometry.Point3D;
import javafx.scene.canvas.GraphicsContext;
import pl.scene.primitives.Triangle;

import java.util.ArrayList;

public class Cone extends Mesh{
    private double r, h;
    private int n;


    public Cone(@JsonProperty("r") double r, @JsonProperty("h") double h, @JsonProperty("n") int n,
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

        //lower center
        v.add(new Point3D(0,0,0));

        //lower base
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
        int i=2;
        for(;i<=n;i++){
            tr.add(new Triangle(globalV.get(0), globalV.get(i), globalV.get(i+1)));
        }
        tr.add(new Triangle(globalV.get(0), globalV.get(2), globalV.get(n+1)));

        //lower triangles
        i=2;
        for(;i<n;i++){
            tr.add(new Triangle(globalV.get(i), globalV.get(i+1), globalV.get(1)));
        }
        tr.add(new Triangle(globalV.get(2), globalV.get(n), globalV.get(1)));

    }

    @Override
    public void draw(GraphicsContext gc) {
        ArrayList<Point3D> points;
        initTriangles();
        for(Triangle t : tr){
            gc.strokeLine(t.getV1().getX(), t.getV1().getY(), t.getV2().getX(),t.getV2().getY());
//            gc.strokeLine(t.getV2().getX(), t.getV2().getY(), t.getV3().getX(),t.getV3().getY());
//            gc.strokeLine(t.getV3().getX(), t.getV3().getY(), t.getV1().getX(),t.getV1().getY());
        }
    }
}
