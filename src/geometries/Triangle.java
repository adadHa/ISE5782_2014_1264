package geometries;

import primitives.Point;

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
        }
        toReturn += ".";
        return toReturn;
    }
}
