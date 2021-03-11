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
        // TC01: There is a simple single test here
        Tube tube = new Tube(new Ray(new Point3D(0, 0, 1), new Vector(0, 1, 0)), 1.0);
        assertEquals(new Vector(0, 0, 1), tube.getNormal(new Point3D(0, 0.5, 2)), "Bad normal to tube");

        // meaningless change for commit
    }

}