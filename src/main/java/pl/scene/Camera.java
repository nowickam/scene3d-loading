package pl.scene;

import javafx.geometry.Point3D;
import javafx.scene.transform.Affine;


public class Camera {

    private Point3D position, target, up;
    private Point3D cx, cy, cz;

    public Camera(double width, double height){
        //neutral camera
        position = new Point3D(0, 100, 0);
        target = new Point3D(0,0,1000);
        //vector
        up = new Point3D(0,1,0);
        calculate();
    }

    private void calculate(){
        Point3D nom;
        double den;

        nom = position.subtract(target);
        den = nom.magnitude();
        if(den!=0)den=1/den;
        cz = nom.multiply(den);

        nom = up.crossProduct(cz);
        den = nom.magnitude();
        if(den!=0)den=1/den;
        cx = nom.multiply(den);

        nom = cz.crossProduct(cx);
        den = nom.magnitude();
        if(den!=0)den=1/den;
        cy = nom.multiply(den);
    }

    public Affine getCameraMatrix(){
        Affine toCamera = new Affine(1,0,0,position.getX(),0,1,0,position.getY(),0,0,1,position.getZ());
        Affine toCamera2 = new Affine(1,0,0,cx.dotProduct(position),0,1,0,cy.dotProduct(position),0,0,1,cz.dotProduct(position));
        return toCamera2;
    }


    public Point3D getPosition() {
        return position;
    }

    public void setPosition(Point3D position) {
        this.position = position;
        calculate();
    }

    public Point3D getTarget() {
        return target;
    }

    public void setTarget(Point3D target) {
        this.target = target;
    }

    public Point3D getUp() {
        return up;
    }

    public void setUp(Point3D up) {
        this.up = up;
    }
}
