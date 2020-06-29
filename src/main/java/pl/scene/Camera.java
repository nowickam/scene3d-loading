package pl.scene;

import javafx.geometry.Point3D;
import javafx.scene.transform.Affine;
import pl.scene.matrices.Matrices;


public class Camera {

    private Point3D position, target, up;
    private Point3D cx, cy, cz;
    private double alpha;

    public Camera(double width, double height){
        //neutral camera
        position = new Point3D(0, -height/3, 0);
        target = new Point3D(0,0,height);
        //vector
        up = new Point3D(0,1,0);
        alpha = 45;
    }

    private void calculate(){
        cz = (position.subtract(target)).normalize();

        cx = (up.crossProduct(cz)).normalize();

        cy = (cz.crossProduct(cx)).normalize();
    }

    public Affine getCameraMatrix(){
        calculate();

        Affine toCamera = new Affine(-cx.getX(), -cx.getY(), -cx.getZ(), -position.getX(),
                                     cy.getX(), cy.getY(), cy.getZ(), -position.getY(),
                                     -cz.getX(), -cz.getY(), -cz.getZ(), -position.getZ());
        return toCamera;
    }


    public Point3D getPosition() {
        return position;
    }

    public void setPosition(Point3D position) {
        this.position = position;
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

    public double getAlpha() {
        return alpha;
    }
}
