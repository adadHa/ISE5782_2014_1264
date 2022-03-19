package geometries;

import primitives.*;
import java.util.List;

public class Triangle extends Polygon {

    public Triangle(Point a, Point b, Point c) {
        super(a,b,c);
    }

    public List<Point> getVertices(){
        return vertices;
    }

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

    @Override
    public List<Point> findIntersections(Ray ray) {

        List<Point> intersection = this.plane.findIntersections(ray);
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

            if((v1.dotProduct(n1) > 0 && v2.dotProduct(n2) > 0 && v3.dotProduct(n3) > 0) || (v1.dotProduct(n1) < 0 && v2.dotProduct(n2) < 0 && v3.dotProduct(n3) < 0)) {
                //if we entered to hear, the ray intersect the triangle
                return intersection;
            }
        }
        return null;
    }
}
