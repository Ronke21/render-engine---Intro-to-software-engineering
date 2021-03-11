package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {

    @Test
    void getNormal() {

        // ============ Equivalence Partitions Tests ==============
        Plane testP = new Plane(new Point3D(1,0,0), new Point3D(0,1,0),new Point3D(0,0,1));
        double sq = Math.sqrt(1/3d);

        // Test that result of getNormal is proper
        assertEquals(testP.getNormal(new Point3D(1,0,0)), new Vector(sq,sq,sq));

    }
}