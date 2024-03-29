package geometries;

import primitives.*;
import java.util.List;

/**
 * this class represent a Triangle
 */
public class Triangle extends Polygon {

    /**
     * constructor for Triangle, gets three points
     * @param a
     * @param b
     * @param c
     */
    public Triangle(Point a, Point b, Point c) {
        super(a,b,c);
    }

    /**
     * getter for the vertices list of the triangle
     * @return vertices of the Triangle
     */
    public List<Point> getVertices(){
        return vertices;
    }

    /**
     * toString function to Triangle
     * @return
     */
    @Override
    public String toString() {
        String toReturn = "the Triangle: ";
        int i = 1;
        for(Point p : vertices){
            toReturn += "point " + i + ": " + p.toString();
            i++;
        }
        toReturn += ".";
        return toReturn;
    }

    /**
     * find an intersections between the given ray and the Triangle
     * @param ray
     * @return a list, in geoPoints, of the intersections between the given ray and between the Triangle, if there, else return null
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<Point> intersection = this.plane.findIntersections(ray);
        Vector v = ray.getDir();
        if(intersection != null) { //we check in the beginning if the ray intersect the plane
            Vector v1 = this.vertices.get(0).subtract(ray.getP0());
            Vector v2 = this.vertices.get(1).subtract(ray.getP0());
            Vector v3 = this.vertices.get(2).subtract(ray.getP0());

            Vector v1CrossProdV2 = v1.crossProduct(v2);
            Vector v2CrossProdV3 = v2.crossProduct(v3);
            Vector v3CrossProdV1 = v3.crossProduct(v1);

            Vector n1 = v1CrossProdV2.normalize();
            Vector n2 = v2CrossProdV3.normalize();
            Vector n3 = v3CrossProdV1.normalize();

            double vN1 = v.dotProduct(n1);
            double vN2 = v.dotProduct(n2);
            double vN3 = v.dotProduct(n3);

            if(vN1 > 0 && vN2 > 0 && vN3 > 0 || vN1 < 0 && vN2 < 0 && vN3 < 0) {
                //if we entered to hear, the ray intersect the triangle
                return List.of(new GeoPoint(this,intersection.get(0)));
            }
        }
        return null;
    }
}
