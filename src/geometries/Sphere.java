package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

public class Sphere implements Geometry{

    final Point center;
    final double radius;

    public Point getCenter() {
        return center;
    }
    public double getRadius(){
        return radius;
    }

    public Sphere(Point center, double radius){
        this.center = center;
        this.radius = radius;
    }

    @Override
    public String toString() {
        return " the Sphere:" +
                "center is " + center +
                ", radius is " + radius +
                '.';
    }

    @Override
    public Vector getNormal(Point p){
        Vector n = p.subtract(center).normalize();
        return n;
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> resultList = new ArrayList<Point>();
        Vector v = ray.getDir();
        Point p0 = ray.getP0();
        if(center.equals(p0)){ // p0 is the center
            resultList.add(ray.getPoint(radius));
            return resultList;
        }
        Vector u = this.center.subtract(p0);
        double tm = v.dotProduct(u);
        double dSquare = u.lengthSquared() - tm * tm;
        double th = Math.sqrt(radius * radius - dSquare);
        double t1 = tm + th;
        double t2 = tm - th;
        if (v.normalize().equals(u.normalize())){ // v parallel to u <=> ray goes through the center of the sphere
            tm = u.length();
            t1 = tm + radius;
            t2 = tm + radius*-1;
        }
        if (dSquare >= this.radius * this.radius) // if d >= r there are no intersections.
            return  null;
        else { // simple case

            if (t1 > 0) {
                Point p1 = p0.add(v.scale(t1));
                resultList.add(p1);
            }
            if (t2 > 0) {
                Point p2 = p0.add(v.scale(t2));
                resultList.add(p2);
            }
            if(resultList.size() > 0)
                return resultList;
            return  null;
        }
    }
}
