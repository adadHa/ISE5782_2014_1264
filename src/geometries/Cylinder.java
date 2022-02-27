package geometries;

import primitives.Point;
import primitives.Vector;

public class Cylinder extends Tube{
    private final double height;

    @Overrides
    public Vector getNormal(Point p) {
        return null;
    }
}
