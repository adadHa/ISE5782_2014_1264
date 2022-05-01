package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Cylinder extends Tube{
    private final double height;

    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        this.height = height;
    }

    @Override
    public Vector getNormal(Point p) {
        // check whether p is on the base which contain p0
        Vector pp0 = p.subtract(getAxisRay().getP0());
        Vector v = getAxisRay().getDir();
        if(pp0.dotProduct(v) == 0)
            return  v.scale(-1);

        // is p on the other base?
        Point p0 = getAxisRay().getP0();
        Point p0OnOtherBase = p0.add(v.scale(height));
        Vector vectorPToP0OnOtherBase = p.subtract(p0OnOtherBase);
        if (vectorPToP0OnOtherBase.dotProduct(v) == 0)
            return  v;

        // otherwise, like tube
        return super.getNormal(p);

    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
