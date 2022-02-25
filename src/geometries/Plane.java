package geometries;

import primitives.*;

public class Plane {
    Point p0;
    Vector normal;

    public Plane(Point p0, Vector normal) {
        this.p0 = p0;
        this.normal = normal;
    }
    public Plane(Point p0, Point p1, Point p2) {
        this.p0 = p0;
        this.normal = null; //temp, will be change
    }


    public Point getP0() {
        return p0;
    }
    public Vector getNormal() {
        return normal;
    }

    @Override
    public Vector getNormal(){
        return null;
    }
}
