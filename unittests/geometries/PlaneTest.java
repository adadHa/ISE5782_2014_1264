package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

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
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Plane pl = new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 0, 0));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals(new Vector(0,1,0), pl.getNormal(new Point(0, 0, 0)), "Bad normal to plane");
    }
}