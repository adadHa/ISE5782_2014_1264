package geometries;

import primitives.*;
import primitives.Vector;

import java.util.*;
import java.util.List;

import static primitives.Util.*;

public class Plane implements Geometry {
    Point p0;
    Vector normal;

    public Plane(Point p0, Point p1, Point p2) {
        this.p0 = p0;
        // check for co-located vertices
        if (p0.equals(p1) || p1.equals(p2) || p0.equals(p2))
            throw new IllegalArgumentException("co-operated vertices");

        // check whether the vertices are on the same ray
        Vector v1 = p1.subtract(p0).normalize();
        Vector v2 = p2.subtract(p0).normalize();
        if(v1 == v2 || v1 == v2.scale(-1))
            throw new IllegalArgumentException("all three vertices are on the same ray");

        // calc normal
        this.normal = v1.crossProduct(v2).normalize();
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
    }


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
    }//todo: 1) there are no difference between 2 get normals.  2) we not use the point we get hear

    @Override
    public List<Point> findIntersections(Ray ray) { //find intersections between the plane and a given ray
        // point in the ray is (point = ray P0 + t * ray dir), now we try to find the t that will return point in our plane
        if(this.p0.equals(ray.getP0()))
            return null;
        Vector planeP0MinusRayP0 = this.p0.subtract(ray.getP0());
        double planeNormalRayDirDotProduct = this.normal.dotProduct(ray.getDir());
        if(!isZero(planeNormalRayDirDotProduct)) {
            double t = alignZero(this.normal.dotProduct(planeP0MinusRayP0)) / planeNormalRayDirDotProduct; //t = (plane normal * vector (plane p0 - ray p0)) / (plane normal * ray dir)
            if (t > 0) {
                Point intersection = ray.getP0().add(ray.getDir().scale(t));//we return the point intersection between the ray and the plane (point = ray P0 + t * ray dir)
                if (intersection != ray.getP0()) { // todo: need we this line? only if temp = 0 intersection = p0 of the ray
                    List<Point> toReturn = new ArrayList<Point>();
                    toReturn.add(intersection);
                    return toReturn;//if the ray is not into the ray (the function will return null), there are maximum one intersection between the ray and the plane
                }
            }
        }
        return null;
           //if the ray starts in the plane, we not say that they have intersection, this should stop big bugs
           //if the temp 0 or lower, it is mean that the ray starts after the plane
           //if the dot product of the plane normal and the ray dir is 0 is mean that the ray parallel or into the plane
    }
}
