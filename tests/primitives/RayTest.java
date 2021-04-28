package primitives;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Ray class
 */
class RayTest {

    /**
     * Test method for {@link primitives.Ray#findClosestPoint(List<Point3D>)}.
     */
    @Test
    void testFindClosestPoint() {

        Ray ray = new Ray(Point3D.ZERO, new Vector(0, 0, 1));

        // ============ Equivalence Partitions Tests ==============
        // TC01 - the closest point is in the middle of the list

        List<Point3D> list = new LinkedList<>() {{
            add(new Point3D(-1000, 90, 100));
            add(new Point3D(50, 48, 1000));
            add(new Point3D(0, 5, 1));
            add(new Point3D(-20, 60, 50));
            add(new Point3D(0, 0, -90));
        }};

        assertEquals(list.get(2), ray.findClosestPoint(list), "wrong point!");

        // =============== Boundary Values Tests ==================
        //TC11 - no points
        assertNull(ray.findClosestPoint(null), "supposed to be null!");

        //TC21 - the closest point is at the end of the list
        list.add(new Point3D(0, 0, 3));
        assertEquals(list.get(list.size() - 1), ray.findClosestPoint(list), "wrong point!");

        //TC22 - the closest point is at the beginning of the list
        list.add(0, Point3D.ZERO);
        assertEquals(list.get(0), ray.findClosestPoint(list), "wrong point!");

    }


}