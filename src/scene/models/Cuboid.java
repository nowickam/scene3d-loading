package scene.models;

import javafx.geometry.Point3D;
import javafx.scene.canvas.GraphicsContext;
import scene.primitives.Triangle;

import java.util.ArrayList;

public class Cuboid extends Mesh{
    private int w;
    private int h;
    private int d;
    private ArrayList<Point3D> v;
    private ArrayList<Triangle> tr;

    public Cuboid(int width, int height, int w, int h, int d){
        super(width, height);
        this.w=w;
        this.h=h;
        this.d=d;

        v = new ArrayList<>();
        tr = new ArrayList<>();

        initVertices();
        initTriangles();
    }

    private void initVertices(){
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

        localToGlobal(v);
    }

    private void initTriangles(){
        //side triangles
        for(int i=0;i<4;i++){
            tr.add(new Triangle(v.get(i), v.get((i+1)%4), v.get((i+1)%4+4)));
            tr.add(new Triangle(v.get(i), v.get((i+1)%4+4), v.get((i+1)+3)));
        }

        //upper triangles
        tr.add(new Triangle(v.get(0), v.get(1), v.get(2)));
        tr.add(new Triangle(v.get(0), v.get(2), v.get(3)));

        //lower triangles
        tr.add(new Triangle(v.get(4), v.get(5), v.get(6)));
        tr.add(new Triangle(v.get(4), v.get(6), v.get(7)));

    }

    @Override
    public void draw(GraphicsContext gc) {
        ArrayList<Point3D> points;
        for(Triangle t : tr){
            gc.strokeLine(t.getV1().getX(), t.getV1().getY(), t.getV2().getX(),t.getV2().getY());
            gc.strokeLine(t.getV2().getX(), t.getV2().getY(), t.getV3().getX(),t.getV3().getY());
            gc.strokeLine(t.getV3().getX(), t.getV3().getY(), t.getV1().getX(),t.getV1().getY());
        }
    }
}
