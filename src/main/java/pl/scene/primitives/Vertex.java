package pl.scene.primitives;

public class Vertex {
    private int x,y,z,f;

    public Vertex(int x, int y, int z, int f){
        this.x=x;
        this.y=y;
        this.z=z;
        this.f=f;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }
}
