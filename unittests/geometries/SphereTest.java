package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {
    /**
     * Test method for {@link geometries.Sphere#getNormal(Point)}.
     */
    @Test
    void getNormal() {
        Sphere s = new Sphere(new Point(0,0,0), 1);
        assertEquals(s.getNormal(new Point(1,0,0)),
                new Vector(1,0,0),
                "Bad normal to sphere");
    }

    @Test
    void testFindIntersections() {
        Ray r = new Ray(new Point(2,0,0), new Vector(-5,-3,0));
        Sphere sphere = new Sphere(new Point (1, 0, 0), 1d);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))),
                "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Point> result = sphere.findIntersections(new Ray(new Point(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        sphere = new Sphere(new Point(2,0,0), 2);
        r = new Ray(new Point(3,0,0), new Vector(-3,0,2));
        result = sphere.findIntersections(r);
        assertEquals(result.size(), 1, "TC03: Ray starts inside the sphere (1 point). Wrong number of points");
        assertEquals(new Point(0.708876, 0, 1.527415).distanceSquared(new Point(0,0,0)),
                result.get(0).distanceSquared(new Point(0,0,0)),
                0.001,
                "TC03: Ray starts inside the sphere (1 point): points are incorrect");

        // TC04: Ray starts after the sphere (0 points)
        r = new Ray(new Point(-1,0,0), new Vector(-3,0,1));
        assertNull(sphere.findIntersections(r), "TC04: Ray starts after the sphere (0 points). Wrong number of points");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        r = new Ray(new Point(4,0,0), new Vector(-8,0,1));
        result = sphere.findIntersections(r);
        assertEquals(result.size(), 1, "TC11: Ray starts at sphere and goes inside (1 points). Wrong number of points");
        assertEquals(new Point(0.061538461538460876, 0, 0.4923076923076924),
                result.get(0),
                "TC11: Ray starts at sphere and goes inside. points are incorrect");

        // TC12: Ray starts at sphere and goes outside (0 points)
        r = new Ray(new Point(4,0,0), new Vector(8,0,-1));
        assertNull(sphere.findIntersections(r), "TC12: Ray starts at sphere and goes outside (0 points). Wrong number of points");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        sphere = new Sphere(new Point(0,0,3), 1);
        r = new Ray(new Point(0,0,1), new Vector(0,0,1));
        p1 = new Point(0, 0, 4);
        p2 = new Point(0, 0, 2);
        result = sphere.findIntersections(r);
        assertEquals(2, result.size(), "TC13: Ray starts before the sphere (2 points). Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "TC13: Ray starts before the sphere (2 points). Ray crosses sphere");

        // TC14: Ray starts at sphere and goes inside (1 points)
        sphere = new Sphere(new Point(0,0,3), 1);
        r = new Ray(new Point(0,0,2), new Vector(0,0,1));
        p1 = new Point(0, 0, 4);
        result = sphere.findIntersections(r);
        assertEquals(1, result.size(), "TC14: Ray starts at sphere and goes inside (1 points). Wrong number of points");
        assertEquals(p1, result.get(0), "TC14: Ray starts at sphere and goes inside (1 points) Ray crosses sphere");

        // TC15: Ray starts inside (1 points)
        sphere = new Sphere(new Point(0,0,3), 1);
        r = new Ray(new Point(0,0,2.5), new Vector(0,0,1));
        p1 = new Point(0, 0, 4);
        result = sphere.findIntersections(r);
        assertEquals(1, result.size(), "TC15: Ray starts inside (1 points) Wrong number of points");
        assertEquals(p1, result.get(0), "TC15: Ray starts inside (1 points). Ray crosses sphere");

        // TC16: Ray starts at the center (1 points)
        sphere = new Sphere(new Point(0,0,3), 1);
        r = new Ray(new Point(0,0,3), new Vector(0,0,1));
        p1 = new Point(0, 0, 4);
        result = sphere.findIntersections(r);
        assertEquals(1, result.size(), "TC16: Ray starts at the center (1 points). Wrong number of points");
        assertEquals(p1, result.get(0), "TC16: Ray starts at the center (1 points). Ray crosses sphere");

        // TC17: Ray starts at sphere and goes outside (0 points)
        sphere = new Sphere(new Point(0,0,3), 1);
        r = new Ray(new Point(0,0,2), new Vector(0,0,-1));
        result = sphere.findIntersections(r);
        assertNull(result, "TC17: Ray starts at sphere and goes outside (0 points). Wrong number of points");

        // TC18: Ray starts after sphere (0 points)
        sphere = new Sphere(new Point(0,0,3), 1);
        r = new Ray(new Point(0,0,2), new Vector(0,0,-1));
        result = sphere.findIntersections(r);
        assertNull(result, "TC18: Ray starts after sphere (0 points). Wrong number of points");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        sphere = new Sphere(new Point(0,0,3), 1);
        r = new Ray(new Point(2,0,2), new Vector(-1,0,0));
        result = sphere.findIntersections(r);
        assertNull(result, "TC19: Ray starts before the tangent point. Wrong number of points");

        // TC20: Ray starts at the tangent point
        sphere = new Sphere(new Point(0,0,3), 1);
        r = new Ray(new Point(0,0,2), new Vector(-1,0,0));
        result = sphere.findIntersections(r);
        assertNull(result, "TC20: Ray starts at the tangent point. Wrong number of points");

        // TC21: Ray starts after the tangent point
        sphere = new Sphere(new Point(0,0,3), 1);
        r = new Ray(new Point(-1,0,2), new Vector(-1,0,0));
        result = sphere.findIntersections(r);
        assertNull(result, "TC20: Ray starts at the tangent point. Wrong number of points");

        // **** Group: Special cases
        // TC21: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        sphere = new Sphere(new Point(0,0,3), 1);
        r = new Ray(new Point(1,0,1), new Vector(1,0,0));
        result = sphere.findIntersections(r);
        assertNull(result, "TC21: Ray's line is outside, ray is orthogonal to ray start to sphere's center line. Wrong number of points");
    }
}