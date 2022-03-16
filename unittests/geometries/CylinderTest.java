package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Testing Polygons
 *
 * @author Harel Adadi
 *
 */
class CylinderTest {
    /**
     * Test method for {@link geometries.Cylinder#Cylinder(Ray axisRay, double radius, double height)}.
     */
    @Test
    void testConstractor() {
    }
    /**
     * Test method for {@link geometries.Cylinder#getNormal(Point)}.
     */
    @Test
    void getNormal() {

        // ============ Equivalence Partitions Tests ==============
        // TC01 - simple case, like tube
        Cylinder c = new Cylinder(new Ray(new Point(0,0,0),
                new Vector(1,0,0)),
                1,
                10);
        Point p = new Point(0.5,0,1);
        assertEquals( new Vector(0,0,1), c.getNormal(p));

        // TC02 - on base 1 (which contain p0)
        c = new Cylinder(new Ray(new Point(1,0,0),
                        new Vector(0,0,1)),
                1,
                10);
        p = new Point(1.5,0,0);
        assertEquals( new Vector(0,0,-1), c.getNormal(p));
        // TC03 - on base 2
        p = new Point(1.5,0,10);
        assertEquals( new Vector(0,0,1), c.getNormal(p));
    }

}