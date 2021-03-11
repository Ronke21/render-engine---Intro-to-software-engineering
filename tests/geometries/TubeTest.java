package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Tube class
 */
class TubeTest {

    /**
     * Test method for {@link geometries.Tube#getNormal(primitives.Point3D)}.
     */
    @Test
    void getNormal() {

        // ============ Equivalence Partitions Tests ==============
        Ray ray = new Ray(new Point3D(0,1,0), new Vector(0,1,0));
        Tube tb = new Tube(ray, 2);

        // Test that result of getNormal is proper
        assertEquals(tb.getNormal(new Point3D(0,0,0)), new Vector(0,-1,0));

    }
}