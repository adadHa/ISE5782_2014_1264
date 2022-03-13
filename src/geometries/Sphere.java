package geometries;

import primitives.*;

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
        return null;
    }
}
