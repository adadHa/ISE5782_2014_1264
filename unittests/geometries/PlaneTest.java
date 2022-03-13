package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Testing Polygons
 *
 * @author Harel Adadi
 *
 */
class PlaneTest {
    /**
     * Test method for {@link geometries.Plane#Plane(Point p0, Point p1, Point p2)}.
     */
    @Test
    void Plane() {
        // ============ Equivalence Partitions Tests ==============
        // TC01 - simple case
        try {
            new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
        }   catch (IllegalArgumentException e) {
            fail("Failed constructing a correct polygon");
        }

        // =============== Boundary Values Tests ==================
        // TC10: co-located vertices
        assertThrows(IllegalArgumentException.class, //
                () -> new Plane( new Point(0, 1, 0), new Point(0, 1, 0), new Point(-1, 1, 1)), //
                "Plane.Constructor TC10 Constructed a polygon with co-located vertices");

        // TC11: all three vertices are on the same ray
        assertThrows(IllegalArgumentException.class, //
                () -> new Plane( new Point(0, 1, 0), new Point(0, 2, 0), new Point(0, 3, 0)), //
                "Plane.Constructor TC11: all three vertices are on the same ray");
    }

    /**
     * Test method for {@link geometries.Plane#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Plane pl = new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 0, 0));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals(new Vector(0,1,0), pl.getNormal(new Point(0, 0, 0)), "Bad normal to plane");
    }

    @Test
    void testFindIntersections() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple intersect ray to the plane (starts before the plane)
        Ray intersectRay = new Ray(new Point(1,0,0) , new Vector(2,1,1));
        Plane plane1 = new Plane(new Point(-3,4,6),new Point(2,2,2), new Point(1,-5,3));
        assertEquals(plane1.findIntersections(intersectRay), new Point(3.16, 1.08, 1.08),  "Plane.findIntersections is not working for simple intersect ray");


        // TC02: There is a simple not intersect ray to the plane (starts after the plane)
        Ray nonIntersectRay = new Ray(new Point(36,50,10) , new Vector(2,1,1));
        assertNull(plane1.findIntersections(nonIntersectRay), "Plane.findIntersections is not returns null for simple non intersect ray");

        // =============== Boundary Values Tests ==================

        // TC03: There is a parallel ray to the plane
        Ray parallelRay = new Ray(new Point(0,0,3), new Vector(-1,-2,0));
        Plane plane2 = new Plane(new Point(-7,-14,0), new Point(20,-30,0), new Point(10,25,0));
        assertNull(plane2.findIntersections(parallelRay), "Plane.findIntersections is not returns null for parallel ray");


        // TC03: co-located vertices


        assertEquals(List.of(new Point()), plane.findIntersections(ray));
    }
}