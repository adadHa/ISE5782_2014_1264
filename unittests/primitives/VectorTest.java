/**
 * Unit tests for primitives.Vector class
 * @author Harel Adadi
 */

package primitives;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;


class VectorTest {
    /**
     * Test method for {@link primitives.Vector#add(primitives.Vector)}.
     */
    @Test
    void add() {
    }

    @Test
    void subtract() {
    }

    @Test
    void scale() {
    }

    @Test
    void dotProduct() {
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v2);

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        ////assertEquals("crossProduct() wrong result length", (Double)(v1.length() * v2.length()), (Double)(vr.length()), (Double)0.00001);

        // TC02: Test cross-product result orthogonality to its operands
        //assertTrue("crossProduct() result is not orthogonal to 1st operand", isZero(vr.dotProduct(v1)));
        //assertTrue("crossProduct() result is not orthogonal to 2nd operand", isZero(vr.dotProduct(v2)));

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-productof co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
        //assertThrows("crossProduct() for parallel vectors does not throw an exception",
          //      IllegalArgumentException.class, () -> v1.crossProduct(v3));

    }

    @Test
    void crossProduct() {
    }

    @Test
    void lengthSquared() {
    }

    @Test
    void length() {
    }

    @Test
    void normalize() {
    }
}