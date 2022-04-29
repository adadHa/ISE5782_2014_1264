package primitives;

import java.util.List;
import java.util.Objects;
import geometries.Intersectable.GeoPoint;

import static primitives.Util.isZero;

public class Ray {
    /**
     * a constant for moving shade rays' head.
     */
    private static  final double DELTA = 0.1;


    final Point p0;
    final Vector dir;

    public Ray(Point p, Vector v) {
        this.p0 = p;
        if(v.length() == 1)
            this.dir = v;
        else
            this.dir = v.normalize();
    }

    /**
     * Construct ray with delta (according to the normal's vector)
     * @param head
     * @param direction
     * @param normal
     */
    public Ray(Point head, Vector direction, Vector normal){
        this.dir = direction;
        Vector delta =  normal.scale(direction.dotProduct(normal) > 0 ?
                DELTA : - DELTA);
        this.p0 = head.add(delta);
    }

    public Vector getDir() {
        return dir;
    }
    public Point getP0() {
        return p0;
    }

    @Override
    public String toString() {
        return " the Ray: " +
                "point is:" + p0.toString() +
                ", direction vector is: " + dir.toString() +
                '.';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(o == null) return false;
        if (!(o instanceof Ray)) return false;
        Ray ray = (Ray) o;
        return p0.equals(ray.p0) && dir.equals(ray.dir);
    }

    /**
     *
     * @param t
     * @return point on ray scaled by t
     */
    public Point getPoint(double t){
        return p0.add(dir.scale(t));
    }

    /**
     *
     * @param pointsList
     * @return the closest point to the start of the ray from points list.
     */
    public Point findClosestPoint(List<Point> pointsList){
        if(pointsList.size() == 0)
            return null;
        Point closestPoint = pointsList.get(0);
        for (Point p :
                pointsList) {
            if (p.distanceSquared(this.p0) < closestPoint.distanceSquared(this.p0)){
                closestPoint = p;
            }
        }
        return closestPoint;
    }

    /**
     * This method finds the point in the list which is the closest to the start of the ray.
     * @param pointsList the points list
     * @return the closest point to the start of the ray
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> pointsList){
        if(pointsList.size() == 0)
            return null;
        GeoPoint closestPoint = pointsList.get(0);
        double distanceSquared = closestPoint.point.distanceSquared(this.p0);
        for (GeoPoint p : pointsList) {

            if (p.point.distanceSquared(this.p0) < distanceSquared){
                closestPoint = p;
                distanceSquared = closestPoint.point.distanceSquared(this.p0);
            }
        }
        return closestPoint;
    }

    /**
     * check whether the scale operation of ray with t is legal
     * @param t
     * @return
     */
    public boolean isScaleLegal(double t) {
        if(isZero(dir.getX() * t) &&
                isZero(dir.getY() * t) &&
                isZero(dir.getZ() * t))
            return false;
        return true;
    }
}
