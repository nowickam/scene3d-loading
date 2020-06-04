package scene.models;

import javafx.geometry.Point3D;
import javafx.scene.canvas.GraphicsContext;
import scene.primitives.Triangle;

import java.util.ArrayList;

public class Cylinder extends Mesh{
    private double r, h;
    private int n;

    public Cylinder(double r, double h, int n, double px, double py, double pz, double rx, double ry, double rz){
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

        transform();
    }

    @Override
    protected void initTriangles(){
        //side triangles
        int i=1;
        for(;i<n;i++){
            tr.add(new Triangle(v.get(i), v.get(i+1), v.get(i+2+n)));
            tr.add(new Triangle( v.get((i+2)+n-1), v.get(i+2+n), v.get(i+1)));
        }
        tr.add(new Triangle(v.get(i), v.get(1), v.get(n+2)));
        tr.add(new Triangle(v.get(n+3), v.get(n+2),v.get(1)));

        //upper triangles
        i=1;
        for(;i<n;i++){
            tr.add(new Triangle(v.get(0), v.get(i), v.get(i+1)));
        }
        tr.add(new Triangle(v.get(0), v.get(i), v.get(1)));

        //lower triangles
        i=n+2;
        for(;i<=2*n;i++){
            tr.add(new Triangle(v.get(n+1), v.get(i), v.get(i+1)));
        }
        tr.add(new Triangle(v.get(n+1), v.get(i), v.get(n+2)));
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
}
