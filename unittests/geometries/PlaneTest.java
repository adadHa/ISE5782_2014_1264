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
        assertEquals(new Point(3.1632, 1.08163, 1.08163).distanceSquared(new Point(0,0,0)),
                plane1.findIntersections(intersectRay).get(0).distanceSquared(new Point(0,0,0)),
                0.001,
                "Plane.findIntersections is not working for simple intersect ray");

        // TC02: There is a simple not intersect ray to the plane (starts after the plane)
        Ray nonIntersectRay = new Ray(new Point(36,50,10) , new Vector(2,1,1));
        assertNull(plane1.findIntersections(nonIntersectRay), "Plane.findIntersections is not returns null for simple non intersect ray");

        // =============== Boundary Values Tests ==================

        // TC03: There is a parallel ray to the plane
        Ray parallelRay = new Ray(new Point(0,0,3), new Vector(-1,-2,0));
        Plane plane2 = new Plane(new Point(-7,-14,0), new Point(20,-30,0), new Point(10,25,0));
        assertNull(plane2.findIntersections(parallelRay), "Plane.findIntersections is not returns null for parallel ray");

        // TC04: The plane contains the ray
        Ray containedRay = new Ray(new Point(1,2,0), new Vector(2,-8,0));
        assertNull(plane2.findIntersections(containedRay), "Plane.findIntersections is not returns null for contained ray");//todo check if it the right option to do in contained ray

        // TC06: The ray is orthogonal to the plane, and starts before the plane
        Ray orthogonalBeforeRay = new Ray(new Point(3,30,12), new Vector(0,-1310,0));
        Plane plane3 = new Plane(new Point(-7,0,-4), new Point(20,0, -35), new Point(10,0,25));
        assertEquals(new Point(3, 0,12), plane3.findIntersections(orthogonalBeforeRay).get(0),"Plane.findIntersections is wrong for orthogonal ray that starts before the plane");

        // TC07: The ray is orthogonal to the plane, and stars in the plane
        Ray orthogonalInRay = new Ray(new Point(4,0,-12), new Vector(0,-1310,0));
        //assertEquals((List<Point>)new Point(4, 0,-12), plane3.findIntersections(orthogonalInRay), "Plane.findIntersections is wrong for orthogonal ray that starts in the plane");
        assertNull(plane3.findIntersections(orthogonalInRay), "Plane.findIntersections is not returns null for orthogonal ray that starts in the plane");

        // TC08: The ray is orthogonal to the plane, and stars after the plane
        Ray orthogonalAfterRay = new Ray(new Point(3,30,12), new Vector(0,1310,0));
        assertNull(plane3.findIntersections(orthogonalAfterRay), "Plane.findIntersections is not returns null for orthogonal ray that starts after the plane");

        // TC09: The ray starts in the plane, neither orthogonal nor parallel
        Ray startsInRay = new Ray(new Point(2,2,-2), new Vector(0,5,0));
        Plane plane4 = new Plane(new Point(0,5,0), new Point(0,0,-5),new Point(10,0,0));
        //assertEquals((List<Point>)new Point(2,2,-2), plane4.findIntersections(startsInRay),"Plane.findIntersections is wrong for a ray that starts in the plane (neither parallel nor orthogonal)");
        assertNull(plane4.findIntersections(startsInRay),"Plane.findIntersections is not returns null for a ray that starts in the plane (neither parallel nor orthogonal)");

        //TC10: The Ray starts in the plane`s q0, neither orthogonal nor parallel
        Ray startsInPointRay = new Ray(new Point(0,5,0), new Vector(0,5,0));
        //assertEquals((List<Point>)new Point(0,5,0), plane4.findIntersections(startsInPointRay),"Plane.findIntersections is wrong for a ray that starts in the p0 of the plane (neither parallel nor orthogonal)");
        assertNull(plane4.findIntersections(startsInPointRay),"Plane.findIntersections is not returns null for a ray that starts in the p0 of the plane (neither parallel nor orthogonal)");

        //assertEquals(List.of(new Point()), plane.findIntersections(ray));
    }
}