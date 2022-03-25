package primitives;

import java.util.List;
import java.util.Objects;

public class Ray {
    final Point p0;
    final Vector dir;

    public Ray(Point p, Vector v) {
        this.p0 = p;
        if(v.length() == 1)
            this.dir = v;
        else
            this.dir = v.normalize();
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
}
