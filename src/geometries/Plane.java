package geometries;

import primitives.*;

public class Plane implements Geometry {
    Point p0;
    Vector normal;

    public Plane(Point p0, Point p1, Point p2) {
        this.p0 = p0;
        this.normal = null; //temp, will be change

        // check for co-located vertices
        if (p0.equals(p1) || p1.equals(p2) || p0.equals(p2))
            throw new IllegalArgumentException("co-operated vertices");

        // check whether the vertices are on the same ray
        Vector v1 = p1.subtract(p0).normalize();
        Vector v2 = p2.subtract(p0).normalize();
        if(v1 == v2 || v1 == v2.scale(-1))
            throw new IllegalArgumentException("all three vertices are on the same ray");

        // calc normal
        normal = v1.crossProduct(v2).normalize();
    }
    public Plane(Point p0, Vector normal) {
        this.p0 = p0;
        if(normal.length() == 1){//todo: change to lengthSquared? (smaller run time without sqrt) here and ray
            this.normal = normal;
        }
        else{
            this.normal = normal.normalize();
        }
    }



    public Point getP0() {
        return p0;
    }
    public Vector getNormal() {
        return normal;
    } // clear?


    @Override
    public String toString() {
        return " the Plane:" +
                "point is" + p0 +
                ", normal vector is" + normal +
                '.';
    }

    @Override
    public Vector getNormal(Point p){
        return normal;
    }
}
