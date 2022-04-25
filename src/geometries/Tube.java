package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;

import java.util.List;

public class Tube extends Geometry {
    private final Ray axisRay;
    private final double radius;

    public Tube(Ray axisRay, double radius) {
        this.axisRay = axisRay;
        this.radius = radius;
    }

    public Ray getAxisRay() {
        return axisRay;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public Vector getNormal(Point p) {
        Vector v = axisRay.getDir();
        Point p0 = axisRay.getP0();
        double t = v.dotProduct(p.subtract(p0));
        Point o = null;
        Vector n = null;
        if(t != 0)
        {
             o = p0.add(v.scale(t));
             n = p.subtract(o).normalize();
        }
        else
            n = p.subtract(p0);
        return n;
    }


    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        return null;
    }
}
