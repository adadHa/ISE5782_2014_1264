package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * this class represents a Sphere
 */
public class Sphere extends Geometry{

    final Point center;
    final double radius;

    /**
     * getter for the center
     * @return center point of sphere
     */
    public Point getCenter() {
        return center;
    }

    /**
     * getter for the radius
     * @return radius of the sphere
     */
    public double getRadius(){
        return radius;
    }

    /**
     * constructor to the Sphere, gets central point and the radius
     * @param center
     * @param radius
     */
    public Sphere(Point center, double radius){
        this.center = center;
        this.radius = radius;
    }

    /**
     * toString function to Sphere
     * @return
     */
    @Override
    public String toString() {
        return " the Sphere:" +
                "center is " + center +
                ", radius is " + radius +
                '.';
    }

    /**
     *
     * @param p
     * @return a normal vector to the sphere in the point p, if there
     */
    @Override
    public Vector getNormal(Point p){
        Vector n = p.subtract(center).normalize();
        return n;
    }

    /**
     * find an intersections between the given ray and the Sphere
     * @param ray
     * @param maxDistance
     * @return a list, in geoPoints, of the intersections of the given ray with the Sphere, if there, else return null
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> resultList = new ArrayList<GeoPoint>();
        Vector v = ray.getDir();
        Point p0 = ray.getP0();
        if(center.equals(p0)){ // p0 is the center
            resultList.add(new GeoPoint(this,ray.getPoint(radius)));
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

            if (t1 > 0 && alignZero(t1 - maxDistance) <= 0) {
                Point p1 = ray.getPoint(t1);
                resultList.add(new GeoPoint(this,p1));
            }
            if (t2 > 0 && alignZero(t2 - maxDistance) <= 0) {
                Point p2 = ray.getPoint(t2);
                resultList.add(new GeoPoint(this,p2));
            }
            if(resultList.size() > 0)
                return resultList;
            return  null;
        }
    }

}
