package primitives;

import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * tests for primitives.Vector class and its methods.
 *
 */
class VectorTest {
    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-2, -4, -6);

    @Test
    void testZeroPoint(){
        try { // test zero vector
            new Vector(0, 0, 0);
            fail("ERROR: zero vector does not throw an exception");
        } catch (IllegalArgumentException e) {
            out.println("good: zero vector cannot exist");
        }

    }

    @Test
    void add() {
        // ============ Equivalence Partitions Tests ==============
        Vector v3 = new Vector(-1, -2, -3);

        // Test that sum of add is proper
        assertEquals(v3, v1.add(v2), "ERROR: add() wrong value");
    }

    @Test
    void subtract() {
        // ============ Equivalence Partitions Tests ==============
        Vector v3 = new Vector(3, 6, 9);

        // Test that difference of subtract is proper
        assertEquals(v3, v1.subtract(v2), "ERROR: subtract() wrong value");
    }

    @Test
    void scale() {
        // ============ Equivalence Partitions Tests ==============
        Vector v3 = new Vector(3, 6, 9);

        // Test that result of scale is proper
        assertEquals(v3, v1.scale(3), "ERROR: scale() wrong value");
     }

    @Test
    void dotProduct() {
        // ============ Equivalence Partitions Tests ==============
        int v3 = -28; //-2-8-18;

        // Test that result of dotProduct is proper
        assertEquals(v3, v1.dotProduct(v2), "ERROR: dotProduct() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
        @Test
    void crossProduct() {

            // ============ Equivalence Partitions Tests ==============
            Vector v3 = new Vector(0, 3, -2);
            Vector vr = v1.crossProduct(v3);

            // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
            assertEquals( v1.length() * v3.length(), vr.length(), 0.00001,"crossProduct() wrong result length");

            // Test cross-product result orthogonality to its operands
            assertTrue( isZero(vr.dotProduct(v1)),"crossProduct() result is not orthogonal to 1st operand");
            assertTrue( isZero(vr.dotProduct(v3)),"crossProduct() result is not orthogonal to 2nd operand");

            // =============== Boundary Values Tests ==================
            // test zero vector from cross-product of co-lined vectors
            try {
                v1.crossProduct(v2);
                fail("crossProduct() for parallel vectors does not throw an exception");
            } catch (Exception e) {}

        }

    @Test
    void lengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        int length = 56;

        // Test that length squared is proper
        assertEquals(length, v2.lengthSquared(), "ERROR: lengthSquared() wrong value");

    }

    @Test
    void length() {
        // ============ Equivalence Partitions Tests ==============
        int length = 56;

        // Test that length is proper
        assertEquals(length, v2.lengthSquared(), "ERROR: length() wrong value");

    }

    @Test
    void normalize() {

        // ============ Equivalence Partitions Tests ==============
        double len = v1.length();
        Vector v3 = v1.scale(1/len);
        v1.normalize();

        // Test that result of normalize is proper
        assertEquals(v3, v1, "ERROR: normalize() wrong value");

    }

    @Test
    void normalized() {

        // ============ Equivalence Partitions Tests ==============
        double len = v1.length();
        Vector v3 = v1.scale(1/len);

        // Test that result of normalized is proper
        assertEquals(v3, v1.normalized(), "ERROR: normalized() wrong value");

    }
}