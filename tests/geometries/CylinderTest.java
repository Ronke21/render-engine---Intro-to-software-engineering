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
        Ray ray = new Ray(new Point3D(0, -2, 0), new Vector(0, 1, 0));
        Cylinder cl = new Cylinder(ray, 2, 3);

        // Test that result of getNormal is proper

        //TC01 - inside first base:
        assertEquals(cl.getNormal(new Point3D(0, -2, 1)), new Vector(0, -1, 0));

        //TC02 - inside far base:
        assertEquals(cl.getNormal(new Point3D(0, 1, 1)), new Vector(0, 1, 0));

        //TC03 - round surface:
        assertEquals(cl.getNormal(new Point3D(0, 0, 2)), new Vector(0, 0, 1));


        // =============== Boundary Values Tests ==================
        //TC10 - corner first base, normal should be like inside base
        assertEquals(cl.getNormal(new Point3D(0, -2, 2)), new Vector(0, -1, 0));

        //TC11 - corner far base - normal should be like inside base
        assertEquals(cl.getNormal(new Point3D(0, 1, 2)), new Vector(0, 1, 0));


    }

    //TODO: add tests
    @Test
    void findIntersections() {
    }
}