package geometries;

import primitives.*;

public class Sphere implements Geometry{

    public Point center;
    public double radius;

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
        return null;
    }
}
