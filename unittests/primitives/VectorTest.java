/**
 * Unit tests for primitives.Vector class
 * @author Harel Adadi
 */

package primitives;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static primitives.Util.isZero;


class VectorTest {
    /**
     * Test method for {@link primitives.Vector#add(primitives.Vector)}.
     */
    @Test
    void testAdd() {
        Vector v1 = new Vector(1,2,1);
        Vector v2 = new Vector(2,1,2);

        // ============ Equivalence Partitions Tests ==============
        //zavit chada
        assertEquals(v1.add(v2), new Vector(3,3,3), "Vector.add() return wrong value for simple case");
        //zavit keha
        v2 = new Vector(-2, -1, -2);
        assertEquals(v1.add(v2), new Vector(-1,1,-1), "Vector.add() return wrong value for simple case");

        // ============ Boundary Partitions Tests ==============
        //TC01 v1 + v2 = 0
        Vector v3 = new Vector(-1,-2,-1);
        assertThrows( IllegalArgumentException.class,
                    () -> v1.add(v3),
                    "Vector.add() not throws exception for vector 0");

        //TC02 zavit yeshara, 90 gradua
        assertEquals(v1.add(new Vector(1,1,-3)),
                    new Vector(2,3,-2),
                    "Vector.add() return wrong value for zavit yeshara, 90 gradua");

        //TC03 |v1| = |v2|
        assertEquals(v1.add(new Vector(1,2,1)),
                    new Vector(2,4,2),
                "Vector.add() return wrong value for |v1| = |v2|");

        //TC04 same direction
        assertEquals(v1.add(new Vector(2,4,2)),
                    new Vector(3,6,3),
                "Vector.add() return wrong value for same direction");

        //TC05 opposite direction
        assertEquals(v1.add(new Vector(-2,-4,-2)),
                new Vector(-1,-2,-1),
                "Vector.add() return wrong value for opposite direction");
    }

    @Test
    void subtract() {
        Vector v1 = new Vector(1,2,1);

        // ============ Equivalence Partitions Tests ==============
        //zavit chada
        assertEquals(v1.subtract(new Vector(3,3,3)),
                new Vector(-2,-1,-2),
                "Vector.subtract() return wrong value for zavit chada");
        //zavit keha
        assertEquals(v1.subtract(new Vector(3,-3,-1)),
                new Vector(-2,5,2),
                "Vector.subtract() return wrong value for zavit keha");

        // ============ Boundary Partitions Tests ==============
        //TC01 v1 - v2 = 0
        Vector v3 = new Vector(1,2,1);
        assertThrows( IllegalArgumentException.class,
                () -> v1.subtract(v3),
                "Vector.subtract() not throws exception for vector 0");

        //TC02 same direction
        assertEquals(v1.subtract(new Vector(2,4,2)),
                new Vector(-1,-2,-1),
                "Vector.subtract() return wrong value for TC02 same direction");

        // TC03 opposite direction
        assertEquals(v1.subtract(new Vector(-2,-4,-2)),
                new Vector(3,6,3),
                "Vector.subtract() return wrong value for TC03 opposite direction");
    }

    @Test
    void scale() {
        Vector v1 = new Vector(1,2,1);

        // ============ Equivalence Partitions Tests ==============
        //simple case
        assertEquals(v1.scale(2),
                new Vector(2,4,2),
                "Vector.subtract() return wrong value for simple case");

        // ============ Boundary Partitions Tests ==============
        //TC01 - scalar 0
        assertThrows( IllegalArgumentException.class,
                () -> v1.scale(0),
                "Vector.scale() not throws exception for vector 0");
        //TC02 - opposite direction
        assertEquals(v1.scale(-1),
                new Vector(-1,-2,-1),
                "Vector.scale() return wrong value for opposite direction");
    }

    @Test
    void dotProduct() {
        Vector v1 = new Vector(1,2,1);

        // ============ Equivalence Partitions Tests ==============
        //EP01 - acute angle
        assertEquals(v1.dotProduct(new Vector(1,2,2)),
                7,
                "Vector.dotProduct() return wrong value for EP01 - acute angle");
        //EP02 - obtuse angle
        assertEquals(v1.dotProduct(new Vector(-1,-2,2)),
                -3,
                "Vector.dotProduct() return wrong value for EP02 - obtuse angle");

        // ============ Boundary Partitions Tests ==============
        // TC01 - vertical
        assertEquals(v1.dotProduct(new Vector(-1,-2,5)),
                0,
                "Vector.dotProduct() return wrong value for TC01 - orthogonal vectors (value is not zero)");
        // TC02 - same direction
        assertEquals(v1.dotProduct(new Vector(2,4,2)),
                12,
                "Vector.dotProduct() return wrong value for TC02 - same direction");
        // TC03 - opposite direction
        assertEquals(v1.dotProduct(new Vector(-1,-2,-1)),
                -6,
                "Vector.dotProduct() return wrong value for TC03 - opposite direction");
    }

    @Test
    void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v2);

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals((Double)(v1.length() * v2.length()), (Double)(vr.length()), (Double)0.00001, "crossProduct() wrong result length");

        // TC02: Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v2)), "crossProduct() result is not orthogonal to 2nd operand");

        // TC03: Test acute angle case
        assertEquals(v1.crossProduct(new Vector(1,2,2)),
                new Vector(-2,1,0),
                "crossProduct() wrong result for TC03: Test acute angle case");

        // TC04: Test acute angle case
        assertEquals(v1.crossProduct(new Vector(1,2,2)),
                new Vector(-2,1,0),
                "crossProduct() wrong result for TC03: Test acute angle case");

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-product of co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v3), "crossProduct() for parallel vectors does not throw an exception");
    }

    @Test
    void lengthSquared() {
        Vector v1 = new Vector(2, 2, 1);

        // ============ Equivalence Partitions Tests ==============
        //simple case
        assertEquals(v1.lengthSquared(), 9, "Vector.lengthSquared() return wrong value for simple case");
    }

    @Test
    void length() {
        Vector v1 = new Vector(2, 2, 1);

        // ============ Equivalence Partitions Tests ==============
        //simple case
        assertEquals(v1.lengthSquared(), 9, "Vector.length() return wrong value for simple case");
    }

    @Test
    void normalize() {
        Vector v1 = new Vector(2, 2, 1);

        // ============ Equivalence Partitions Tests ==============
        //simple case
        assertEquals(v1.normalize().length(),
                new Vector(2/3f,2/3f,1/3f).length(),
                0.00001,
                "Vector.normalize() return wrong value for simple case");

        // =============== Boundary Values Tests ==================
        assertThrows( IllegalArgumentException.class,
                () -> new Vector(0,0,0).normalize(),
                "Vector.subtract() not throws exception for vector 0");
    }
}