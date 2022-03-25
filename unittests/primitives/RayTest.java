package primitives;

import org.junit.jupiter.api.Test;

import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    /**
     * Test method for {@link primitives.Ray#findClosestPoint(List)}.
     */
    @Test
    void findClosestPoint() {
        // ================Equivalence Partitions Tests ==============
        //TC01 - simple case, the closest point is in the middle of the list (not the first or the last element).
        Ray ray = new Ray(new Point(0,0,1), new Vector(1,0,0));
        ArrayList<Point> list = new ArrayList<Point>();
        list.add(new Point(2,3,4));
        list.add(new Point(1,1,1));
        list.add(new Point(6,7,8));

        assertEquals(new Point(1,1,1), ray.findClosestPoint(list), "TC01 - simple case. wrong closest point");

        // ============ Boundary Partitions Tests ==============
        //TC11 - empty list
        list = new ArrayList<Point>();
        assertNull(ray.findClosestPoint(list), "TC11 - empty list");

        //TC12 - first point of the list is the closest point
        list = new ArrayList<Point>();
        list.add(new Point(1,1,1));
        list.add(new Point(2,3,4));
        list.add(new Point(6,7,8));

        assertEquals(new Point(1,1,1), ray.findClosestPoint(list), "TC12 - first point of the list is the closest point. wrong closest point");

        //TC13 - last point of the list is the closest point
        list = new ArrayList<Point>();
        list.add(new Point(2,3,4));
        list.add(new Point(6,7,8));
        list.add(new Point(1,1,1));

        assertEquals(new Point(1,1,1), ray.findClosestPoint(list), "TC13 - last point of the list is the closest point. wrong closest point");

    }
}