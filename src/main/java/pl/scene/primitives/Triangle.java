package pl.scene.primitives;

import javafx.geometry.Point3D;

import java.util.ArrayList;
import java.util.List;

public class Triangle {
    Point3D[] v;
    Point3D[] clippedEdges;
    int idx;

    public Triangle(Point3D v1, Point3D v2, Point3D v3) {
        v = new Point3D[3];
        clippedEdges = new Point3D[6];
        idx = -1;

        v[0] = v1;
        v[1] = v2;
        v[2] = v3;
    }

    public Point3D[] getVertices() {
        return v;
    }

    public void clip(int width, int height) {
        idx = -1;
        for (int i = 0; i < v.length; i++) {
            liangBarsky(v[i], v[(i + 1) % v.length], width, height);
        }
    }

    private void liangBarsky(Point3D p1, Point3D p2, int width, int height) {
        double p1x = p1.getX(), p2x = p2.getX(), p1y = p1.getY(), p2y = p2.getY(), p1z = p1.getZ(), p2z = p2.getZ();
        double dx = p2x - p1x, dy = p2y - p1y, dz = p2z - p1z;
        double tE[] = new double[1];
        double tL[] = new double[1];

        double left = 0;
        double right = width;

        double top = height;
        double bottom = 0;

        double front = (double) 1 / 50;
        double back = (double) 1 / (height * width);

        tE[0] = 0;
        tL[0] = 1;

        if (clipLine(-dx, p1x - right, tE, tL))
            if (clipLine(dx, left - p1x, tE, tL))
                if (clipLine(dy, bottom - p1y, tE, tL))
                    if (clipLine(-dy, p1y - top, tE, tL))
                        if (clipLine(-dz, p2z - front, tE, tL))
                            if (clipLine(dz, back - p2z, tE, tL)) {
                                if (tL[0] < 1) {
                                    p2x = (int) (p1x + dx * tL[0]);
                                    p2y = (int) (p1y + dy * tL[0]);
                                    p2z = (int) (p1z + dz * tL[0]);
                                }
                                if (tE[0] > 0) {
                                    p1x += (int) (dx * tE[0]);
                                    p1y += (int) (dy * tE[0]);
                                    p1z += (int) (dz * tE[0]);
                                }
                                clippedEdges[++idx] = new Point3D(p1x, p1y, p1z);
                                clippedEdges[++idx] = new Point3D(p2x, p2y, p2z);
                                return;
                            }
        clippedEdges[++idx] = null;
        clippedEdges[++idx] = null;
    }

    boolean clipLine(double denom, double numer, double[] tE, double[] tL) {
        if (denom == 0) { //Paralel line
            if (numer > 0)
                return false; // outside - discard
            return true; //skip to next edge
        }
        double t = numer / denom;
        if (denom > 0) { //PE
            if (t > tL[0]) //tE > tL - discard
                return false;
            if (t > tE[0])
                tE[0] = t;
        } else { //PL
            if (t < tE[0]) //tL < tE - discard
                return false;
            if (t < tL[0])
                tL[0] = t;
        }
        return true;
    }


    public Point3D getEdgeP1(int i) {
        return clippedEdges[2 * i];
    }

    public Point3D getEdgeP2(int i) {
        return clippedEdges[(2 * i + 1) % clippedEdges.length];
    }

}
