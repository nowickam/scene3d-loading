package scene;

import javafx.geometry.Point3D;

public class Camera {
    private Point3D position, target, up;

    public Camera(int width, int height){
        position = new Point3D(width/2, height/2, 10);
        target = new Point3D(width/2, height/2+10, 0);
        //vector
        up = new Point3D(0,-1,0);
    }
}
