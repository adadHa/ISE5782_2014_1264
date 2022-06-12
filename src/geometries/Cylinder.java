package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * this class represent a Cylinder
 */
public class Cylinder extends Tube{
    private final double height;

    /**
     * constructor to Cylinder
     * @param axisRay central cylinder ray
     * @param radius
     * @param height
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        this.height = height;
    }

    /**
     *
     * @param p
     * @return normal vector to the cylinder, in point p, if there
     */
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

    /**
     *
     * @param ray
     * @return
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }


    /**
     *
     * @param ray The ray that we check if it intersects the tube.
     * @param maxDistance
     * @return list of intersections of the ray with the cylinder, if there, else return null
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> toReturn = new ArrayList<>();
        List<GeoPoint> optionalGPoints = super.findGeoIntersectionsHelper(ray, maxDistance);  //take the intersection from the tube
        if (optionalGPoints != null)
            for (GeoPoint geoPoint : optionalGPoints) {
                double distance = alignZero(geoPoint.point.subtract(this.getAxisRay().getP0()).dotProduct(this.getAxisRay().getDir()));
                if (distance > 0 && distance <= this.height)
                    toReturn.add(geoPoint);
            }

        if (toReturn.size() == 0)
            return null;
        return toReturn;
    }
}
