package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Cylinder class
 */
class CylinderTest {

    /**
     * Test method for {@link geometries.Cylinder#getNormal(primitives.Point3D)}.
     */
    @Test
    void getNormal() {

        // ============ Equivalence Partitions Tests ==============
        Ray ray = new Ray(new Point3D(0,1,0), new Vector(0,1,0));
        Cylinder cl = new Cylinder(ray, 2, 100);

        // Test that result of getNormal is proper
        assertEquals(cl.getNormal(null), new Vector(0,1,0));

    }
}