package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

public abstract class Intersectable {
    /**
     * This method find intersecions points of an Intersectable shpae, with a given ray
     * @param ray
     * @return
     */
    public List<Point> findIntersections(Ray ray){
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).toList();
    }

    public List<GeoPoint> findGeoIntersections(Ray ray){
        return findGeoIntersectionsHelper(ray);
    }

    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null) return false;
            if (!(o instanceof Point)) return false;
            GeoPoint other = (GeoPoint) o;
            return this.point.equals(other.point) && this.geometry.equals(other.geometry);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }
}



