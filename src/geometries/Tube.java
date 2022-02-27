package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;
public class Tube implements Geometry {
    private final Ray axisRay;
    private final double radius;

    @Overrides
    public Vector getNormal(Point p) {
        return null;
    }
}
