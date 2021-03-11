package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {

    @Test
    void getNormal() {

        // ============ Equivalence Partitions Tests ==============
        Ray ray = new Ray(new Point3D(0,1,0), new Vector(0,1,0));
        Cylinder cl = new Cylinder(ray, 2, 100);

        // Test that result of getNormal is proper
        assertEquals(cl.getNormal(new Point3D(0,0,0)), new Vector(0,-1,0));

    }
}