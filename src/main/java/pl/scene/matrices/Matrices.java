package pl.scene.matrices;

import javafx.geometry.Point3D;
import javafx.scene.transform.Affine;

import java.util.ArrayList;

public class Matrices {
    private static final double depth = 1000;

    public static Affine getRotationX(double alpha){
        double alphaRad = Math.toRadians(alpha);
        return new Affine(1,0,0,0,
                0,Math.cos(alphaRad), -Math.sin(alphaRad),0,
                0, Math.sin(alphaRad),Math.cos(alphaRad),0);
    }

    public static Affine getRotationY(double alpha){
        double alphaRad = Math.toRadians(alpha);
        return new Affine(Math.cos(alphaRad),0,Math.sin(alphaRad),0,
                0,1, 0,0,
                -Math.sin(alphaRad), 0,Math.cos(alphaRad),0);
    }

    public static Affine getRotationZ(double alpha){
        double alphaRad = Math.toRadians(alpha);
        return new Affine(Math.cos(alphaRad),-Math.sin(alphaRad),0,0,
                Math.sin(alphaRad),Math.cos(alphaRad), 0,0,
                0, 0, 1,0);
    }


    public static Affine getTranslation(double tx, double ty, double tz) {
        return new Affine(1, 0, 0, tx,
                0, 1, 0, ty,
                0, 0, 1, tz);
    }

    public static Affine getPerspective(double width, double height, double alpha){
        return  new Affine(-width/2/Math.tan(Math.toRadians(alpha)),0,width/2,0,
                0, width/2/Math.tan(Math.toRadians(alpha)),height/2,0,
                0,0,0,1);
    }

    public static Affine getGlobal(){
        return new Affine(-1,0,0,0,
                        0,-1,0,0,
                        0,0,-1, depth);
    }

    public static void apply(Affine matrix, ArrayList<Point3D> transformedV){
        Point3D result = null;
        for(int i = 0; i< transformedV.size(); i++){
            result = matrix.transform(transformedV.get(i));
            transformedV.set(i,result);
        }
    }

    public static void applyAndNormalize(Affine matrix, ArrayList<Point3D> transformedV){
        Point3D result = null;
        double z = 0;
        for(int i=0;i<transformedV.size();i++){
            z = transformedV.get(i).getZ();
            result = matrix.transform(transformedV.get(i));
            result = new Point3D(result.getX()/z, result.getY()/z, result.getZ()/z);
            transformedV.set(i,result);
        }
    }
}
