package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * abstract class, represented a geometry that can be intersectable with a ray, all geometries implements it
 */
public abstract class Intersectable {
    /**
     * This method find intersecions points of an Intersectable shape, with a given ray
     * @param ray
     * @return list of intersections points
     */
    public List<Point> findIntersections(Ray ray){
        var geoList = findGeoIntersections(ray, Double.POSITIVE_INFINITY);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * This method find intersecions points of an Intersectable shpae, with a given ray
     * @param ray
     * @return list of intersections points
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * This method find intersecions geopoints of an Intersectable shpae, with a given ray
     * @param ray
     * @return list of intersections geopoints
     */
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance){
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    /**
     * ansract function, every geometry implements it
     * @param ray
     * @param maxDistance
     * @return
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray){
        return findGeoIntersectionsHelper(ray,Double.POSITIVE_INFINITY);
    }

    /**
     * this class enables to represent a point with its geometry.
     */
    public static class GeoPoint {
        /**
         * the geometry that the point is lies on
         */
        public final Geometry geometry;
        /**
         * the point itself
         */
        public final Point point;

        /**
         * Constructor for GeoPint
         *
         * @param geometry
         * @param point
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        /**
         * this is equals function for intersectable
         * @param o
         * @return if this equals o
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null) return false;
            if (!(o instanceof Point)) return false;
            GeoPoint other = (GeoPoint) o;
            return this.point.equals(other.point) && this.geometry.equals(other.geometry);
        }

        /**
         * toString function for intersectable
         * @return
         */
        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }
}



