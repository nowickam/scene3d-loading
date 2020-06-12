package pl.scene;

import javafx.geometry.Point3D;

public class Camera {
    private Point3D position, target, up;

    public Camera(double width, double height){
        //neutral camera
        position = new Point3D(0, 0, 0);
        target = new Point3D(0, 0, 0);
        //vector
        up = new Point3D(0,-1,0);
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
}
