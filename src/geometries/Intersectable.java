package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

public interface Intersectable {
    /**
     * This method find intersecions points of an Intersectable shpae, with a given ray
     * @param ray
     * @return
     */
    public List<Point> findIntersections(Ray ray);
}
