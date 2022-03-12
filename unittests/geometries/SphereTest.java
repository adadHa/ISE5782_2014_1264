package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

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
}