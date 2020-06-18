package pl.scene.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.geometry.Point3D;
import javafx.scene.canvas.GraphicsContext;
import pl.scene.primitives.Triangle;

import java.util.ArrayList;

public class Cuboid extends Mesh{
    private double w, h, d;


    @JsonCreator
    public Cuboid(@JsonProperty("w") double w, @JsonProperty("h") double h,@JsonProperty("d") double d,
                  @JsonProperty("px") double px, @JsonProperty("py")double py, @JsonProperty("pz")double pz,
                  @JsonProperty("rx")double rx, @JsonProperty("ry")double ry, @JsonProperty("rz")double rz){
        super(px, py, pz, rx, ry, rz);

        this.w=w;
        this.h=h;
        this.d=d;

        v = new ArrayList<>();
        tr = new ArrayList<>();

        initVertices();
    }


    @Override
    protected void initVertices(){
        //upper base
        v.add(new Point3D(0,0,d));
        v.add(new Point3D(w,0,d));
        v.add(new Point3D(w,0,0));
        v.add(new Point3D(0,0,0));

        //lower base
        v.add(new Point3D(0,h,d));
        v.add(new Point3D(w,h,d));
        v.add(new Point3D(w,h,0));
        v.add(new Point3D(0,h,0));

        globalV = new ArrayList<>(v);
        transform();
    }

    @Override
    protected void initTriangles(){
        tr.clear();
        //side triangles
        for(int i=0;i<4;i++){
            tr.add(new Triangle(globalV.get(i), globalV.get((i+1)%4), globalV.get((i+1)%4+4)));
            tr.add(new Triangle(globalV.get(i),  globalV.get((i+1)+3), globalV.get((i+1)%4+4)));
        }

        //upper triangles
        tr.add(new Triangle(globalV.get(0), globalV.get(1), globalV.get(2)));
        tr.add(new Triangle(globalV.get(0), globalV.get(3), globalV.get(2)));

        //lower triangles
        tr.add(new Triangle(globalV.get(4), globalV.get(5), globalV.get(6)));
        tr.add(new Triangle(globalV.get(4), globalV.get(7), globalV.get(6)));

    }

    @Override
    public void draw(GraphicsContext gc) {
        ArrayList<Point3D> points;
        initTriangles();
        for(Triangle t : tr){
            gc.strokeLine(t.getV1().getX(), t.getV1().getY(), t.getV2().getX(),t.getV2().getY());
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

    public double getW() {
        return w;
    }

    public void setW(double w) {
        this.w = w;
    }

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = d;
    }
}
