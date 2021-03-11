package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Sphere class
 */
class SphereTest {

    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point3D)}.
     */
    @Test
    void getNormal() {

        // ============ Equivalence Partitions Tests ==============
        Sphere sp = new Sphere(new Point3D(0,0,0), 1);
        Vector N = sp.getNormal(new Point3D(2,2,2));
        double sq = Math.sqrt(1/3d);

        // Test that result of getNormal is proper
        assertEquals(N, new Vector(sq,sq,sq));

    }
}