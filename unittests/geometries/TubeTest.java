package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {
    /**
     * Test method for {@link geometries.Tube#getNormal(Point)}.
     */
    @Test
    void getNormal() {
        Point p = new Point(0,1,0);
        Tube x = new Tube(
                new Ray(new Point(0,0,0), new Vector(1,0,0)),
                1
                );
        assertEquals( new Vector(0,1,0), x.getNormal(p));
    }
}