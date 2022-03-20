package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {

    @Test
    void findIntersections() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: empty list
        Geometries geometries = new Geometries(
        );
        Ray r = new Ray(new Point(0,0, 1), new Vector(1,0,0));
        assertNull(geometries.findIntersections(r), "TC01. Wrong number of points");

        // TC02: no intersections
        geometries = new Geometries(
                new Sphere(new Point(0,0,4), 1),
                new Sphere(new Point(0,0 ,7), 1)
        );
        r = new Ray(new Point(0,0, 1), new Vector(1,0,0));
        assertNull(geometries.findIntersections(r), "TC02: Wrong number of points");

        // TC02: one shape is intersected
        geometries = new Geometries(
                new Sphere(new Point(0,0,4), 1),
                new Sphere(new Point(0,0 ,7), 1)
        );
        r = new Ray(new Point(0,0, 4.5), new Vector(0,0,-1));
        assertEquals(1, countIntersections(geometries, r), "TC03: Wrong number of points");

        // TC03: some of the shapes are intersected
        geometries = new Geometries(
                new Sphere(new Point(0,0,4), 1),
                new Sphere(new Point(0,0 ,7), 1),
                new Sphere(new Point(100, 40, 30), 1)
        );
        r = new Ray(new Point(0,0, 0), new Vector(0,0,1));
        assertEquals(4, countIntersections(geometries, r), "TC03: Wrong number of points");

        // TC04: all shapes are intersected
        geometries = new Geometries(
                new Sphere(new Point(0,0,4), 1),
                new Sphere(new Point(0,0 ,7), 1)
        );
        r = new Ray(new Point(0,0, 0), new Vector(0,0,1));
        assertEquals(4, countIntersections(geometries, r), "TC03: Wrong number of points");
    }

    private int countIntersections(Geometries geometries, Ray r) {
        List<Point> intersections = geometries.findIntersections(r);
        if(intersections != null)
            return intersections.size();
        return 0;
    }
}