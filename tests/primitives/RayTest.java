package primitives;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    @Test
    void testFindClosestPoint() {
        Ray ray = new Ray(Point3D.ZERO, new Vector(1, 0, 0));


        assertNull(ray.findClosestPoint(null));

    }




}