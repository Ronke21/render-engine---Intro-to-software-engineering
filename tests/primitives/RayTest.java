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
        //TC01 -

        List<Point3D> list = new LinkedList<Point3D>();
        list.add(new Point3D(-1000, 90, 100));
        list.add(new Point3D(50, 48, 1000));
        list.add(new Point3D(0,5,1));
        list.add(new Point3D(-20, 60, 50));
        list.add(new Point3D(0, 0, -90));


        assertEquals(list.get(2), ray.findClosestPoint(list), "not enough points!");

        // =============== Boundary Values Tests ==================
        //TC10 - no intersection points
        assertNull(ray.findClosestPoint(null), "supposed to be null!");

    }




}