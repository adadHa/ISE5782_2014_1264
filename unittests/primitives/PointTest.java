package primitives;

import org.junit.jupiter.api.Test;


import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for primitives.Point class
 * @author Harel Adadi
 */

public class PointTest {
    /**
     * Test method for {@link primitives.Point#subtract(Point)}.
     */
    @Test
    void testSubtract() {
        //simple scalar test
        Point p1 = new Point(1,2,3);
        Point p2 = new Point(3,2,2);
        assertEquals(p1.subtract(p2), new Vector(-2,0,1), "Point.subtract() return wrong vector for simple case");
        //boundary value test
        assertThrows(IllegalArgumentException.class,() -> p1.subtract(p1), "point.subtract() not throw exception for zero vector");
    }

    @org.junit.jupiter.api.Test
    void testAdd() {
        //simple scalar test
        Point p1 = new Point(1,2,3);
        Vector v1 = new Vector(2,3,4);
        assertEquals( p1.add(v1), new Point(3,5,7) ,"Point.add() return wrong point for simple case");
    }

    @org.junit.jupiter.api.Test
    void testDistanceSquared() {
        // ================Equivalence Partitions Tests ==============
        //simple scalar test
        Point p1 = new Point(1,2,3);
        Point p2 = new Point(3,3,1);
        assertEquals(p1.distanceSquared(p2), 9, 0.0001, "point.distanceSquared() return wrong for simple case");
        assertEquals(p2.distanceSquared(p1), 9, 0.0001, "point.distanceSquared() return wrong for simple case");

        assertEquals(p1.distanceSquared(p1), 0, 0.0001, "point.distanceSquared() return wrong for distance 0 case");
        //boundary cases tests

        // two coordinates equals 0
        p1 = new Point(2,0,0);
        p2 = new Point(4,0,0);
        assertEquals(p1.distanceSquared(p2), 4, 0.0001, "point.distanceSquared() return wrong for y,z = 0 case");
        p1 = new Point(0,2,0);
        p2 = new Point(0,4,0);
        assertEquals(p1.distanceSquared(p2), 4, 0.0001, "point.distanceSquared() return wrong for x,z = 0 case");
        p1 = new Point(0,0,2);
        p2 = new Point(0,0,4);
        assertEquals(p1.distanceSquared(p2), 4, 0.0001, "point.distanceSquared() return wrong for x,y = 0 case");

        //one coordinate equals 0
        p1 = new Point(0,1,2);
        p2 = new Point(0,1,4);
        assertEquals(p1.distanceSquared(p2), 4, 0.0001, "point.distanceSquared() return wrong for x = 0 case");
        p1 = new Point(1,0,2);
        p2 = new Point(1,0,4);
        assertEquals(p1.distanceSquared(p2), 4, 0.0001, "point.distanceSquared() return wrong for y = 0 case");
        p1 = new Point(1,2,0);
        p2 = new Point(1,4,0);
        assertEquals(p1.distanceSquared(p2), 4, 0.0001, "point.distanceSquared() return wrong for z = 0 case");
    }

    @org.junit.jupiter.api.Test
    void testDistance() {
        // ================Equivalence Partitions Tests ==============
        //simple scalar test
        Point p1 = new Point(1,2,3);
        Point p2 = new Point(3,3,1);
        assertEquals(p1.distance(p2), 3, 0.0001, "point.distance() return wrong for simple case");
        assertEquals(p2.distance(p1), 3, 0.0001, "point.distance() return wrong for simple case");

        assertEquals(p1.distance(p1), 0, 0.0001, "point.distance() return wrong for distance 0 case");
        //boundary cases tests

        // two coordinates equals 0
        p1 = new Point(2,0,0);
        p2 = new Point(4,0,0);
        assertEquals(p1.distance(p2), 2, 0.0001, "point.distance() return wrong for y,z = 0 case");
        p1 = new Point(0,2,0);
        p2 = new Point(0,4,0);
        assertEquals(p1.distance(p2), 2, 0.0001, "point.distance() return wrong for x,z = 0 case");
        p1 = new Point(0,0,2);
        p2 = new Point(0,0,4);
        assertEquals(p1.distance(p2), 2, 0.0001, "point.distance() return wrong for x,y = 0 case");

        //one coordinate equals 0
        p1 = new Point(0,1,2);
        p2 = new Point(0,1,4);
        assertEquals(p1.distance(p2), 2, 0.0001, "point.distance() return wrong for x = 0 case");
        p1 = new Point(1,0,2);
        p2 = new Point(1,0,4);
        assertEquals(p1.distance(p2), 2, 0.0001, "point.distance() return wrong for y = 0 case");
        p1 = new Point(1,2,0);
        p2 = new Point(1,4,0);
        assertEquals(p1.distance(p2), 2, 0.0001, "point.distance() return wrong for z = 0 case");
    }
}