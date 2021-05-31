package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

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

    /**
     * Test method for
     * {@link geometries.Cylinder#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Cylinder cylinder = new Cylinder(
                new Ray(
                        new Point3D(2, 0, 0), new Vector(0, 0, 1)
                )
                , 1
                , 10);

        Ray ray;
        Point3D geoP1;
        Point3D geoP2;
        // ============ Equivalence Partitions Tests ==============
        // case 01: The ray start under the cylinder and going through 2 bases (2 points)
        ray = new Ray(new Point3D(1.5, 0, -1), new Vector(0.1, 0, 1));
        List<Point3D> result = cylinder.findIntersections(ray);
        assertEquals(2, result.size(), "Wrong number of points");

        if (result.get(0).getZ() > result.get(1).getZ()) {
            result = List.of(result.get(1), result.get(0));
        }

        geoP1 = new Point3D(1.6, 0, 0);
        geoP2 = new Point3D(2.6, 0, 10);

        assertEquals(List.of(geoP1, geoP2), result, "Wrong points");


        // case 02: The ray start before the cylinder and intersect (2 points)
        ray = new Ray(new Point3D(0, 0, 2), new Vector(1, 0, 1));
        result = cylinder.findIntersections(ray);
        assertEquals(2, result.size(), "Wrong number of points");

        if (result.get(0).getZ() > result.get(1).getZ()) {
            result = List.of(result.get(1), result.get(0));
        }

        geoP1 = new Point3D(1, 0, 3);
        geoP2 = new Point3D(3, 0, 5);

        assertEquals(List.of(geoP1, geoP2), result, "Wrong points");


        // case 03: The ray start before the cylinder and going through the top (2 points)
        ray = new Ray(new Point3D(0, 0, 7.5), new Vector(1, 0, 1));
        result = cylinder.findIntersections(ray);
        assertEquals(2, result.size(), "Wrong number of points");

        if (result.get(0).getZ() > result.get(1).getZ()) {
            result = List.of(result.get(1), result.get(0));
        }

        geoP1 = new Point3D(1, 0, 8.5);
        geoP2 = new Point3D(2.5, 0, 10);

        assertEquals(List.of(geoP1, geoP2), result, "Wrong points");


        // case 04: The ray start after the cylinder (0 points)
        ray = new Ray(new Point3D(4, 0, 5), new Vector(1, 0, 1));
        result = cylinder.findIntersections(ray);
        assertNull(result, "should be null");

        // case 05: The ray start above the cylinder (0 points)
        ray = new Ray(new Point3D(0, 0, 11), new Vector(1, 0, 1));
        result = cylinder.findIntersections(ray);
        assertNull(result, "should be null");

        // case 06: Ray's starts within cylinder and going through the top (1 point)
        ray = new Ray(new Point3D(1.5, 0, 3), new Vector(-0.05, 0, 1));
        result = cylinder.findIntersections(ray);
        geoP1 = new Point3D(1.15, 0, 10);

        assertEquals(List.of(geoP1), result, "Wrong points");


        // case 07: Ray's starts within cylinder and going through the bottom (1 point)
        ray = new Ray(new Point3D(1.5, 0, 3), new Vector(-0.1, 0, -1));
        result = cylinder.findIntersections(ray);
        geoP1 = new Point3D(1.2, 0, 0);
        assertEquals(List.of(geoP1), result, "Wrong points");


        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line is parallel to the axis
        // case 11: Ray's starts under cylinder and going through the bases(2 point)
        ray = new Ray(new Point3D(2, 0.5, -1), new Vector(0, 0, 1.05));
        result = cylinder.findIntersections(ray);

        assertEquals(2, result.size(), "Wrong number of points");

        if (result.get(0).getZ() > result.get(1).getZ()) {
            result = List.of(result.get(1), result.get(0));
        }

        geoP1 = new Point3D(2, 0.5, 0);
        geoP2 = new Point3D(2, 0.5, 10);

        assertEquals(List.of(geoP1, geoP2), result, "Wrong points");


        // case 12: Ray's starts within cylinder and going through the top (1 point)
        ray = new Ray(new Point3D(1.5, 0, 3), new Vector(0, 0, 1));
        result = cylinder.findIntersections(ray);
        geoP1 = new Point3D(1.5, 0, 10);

        assertEquals(List.of(geoP1), result, "Wrong point");


        // case 13: Ray's starts within cylinder and going through the bottom (1 point)
        ray = new Ray(new Point3D(1.5, 0, 3), new Vector(0, 0, -1));
        result = cylinder.findIntersections(ray);
        geoP1 = new Point3D(1.5, 0, 0);

        assertEquals(List.of(geoP1), result, "Wrong point");

        // case 14: Ray's starts at cylinder bottom and going through the top (1 point)
        ray = new Ray(new Point3D(1.5, 0, 0), new Vector(0, 0, 1));

        result = cylinder.findIntersections(ray);
        geoP1 = new Point3D(1.5, 0, 10);
        assertEquals(List.of(geoP1), result, "Wrong point");


        // case 15: Ray's starts at cylinder bottom and going outside (0 points)
        ray = new Ray(new Point3D(1.5, 0, 0), new Vector(0, 0, -1));
        result = cylinder.findIntersections(ray);
        assertNull(result, "should be null");

        // case 16: Ray's starts near cylinder center and going through the top (1 point)
        ray = new Ray(new Point3D(2, 0.01, 0), new Vector(0, 0, 1));
        result = cylinder.findIntersections(ray);
        geoP1 = new Point3D(2, 0.01, 10);

        assertEquals(List.of(geoP1), result, "Wrong point");


        // case 17: Ray's starts at cylinder center and going outside (0 points)
        ray = new Ray(new Point3D(2, 0.01, 0), new Vector(0, 0, -1));
        result = cylinder.findIntersections(ray);
        assertNull(result, "should be null");

        // case 18: Ray's starts at cylinder direction and going through the top (1 point)
        ray = new Ray(new Point3D(2, 0.25, 5), new Vector(0, 0, 1));
        result = cylinder.findIntersections(ray);
        geoP1 = new Point3D(2, 0.25, 10);

        assertEquals(List.of(geoP1), result, "Wrong point");

        // **** Group: Ray is orthogonal to the axis
        // case 21: Ray's starts before cylinder and intersect (2 point)
        ray = new Ray(new Point3D(0, 0, 5), new Vector(1, 0, 0));
        result = cylinder.findIntersections(ray);
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX()) {
            result = List.of(result.get(1), result.get(0));
        }

        geoP1 = new Point3D(1, 0, 5);
        geoP2 = new Point3D(3, 0, 5);

        assertEquals(List.of(geoP1, geoP2), result, "Wrong points");


        // case 22: Ray's starts before and above cylinder (0 points)
        ray = new Ray(new Point3D(0, 0, 11), new Vector(1, 0, 0));
        result = cylinder.findIntersections(ray);
        assertNull(result, "should be null");


        // case 23: Ray's starts before and going through the top (0 points)
        ray = new Ray(new Point3D(0, 0, 10), new Vector(1, 0, 0));
        result = cylinder.findIntersections(ray);
        assertNull(result, "should be null");


        // case 24: Ray's starts near the cylinder center (0 points)
        ray = new Ray(new Point3D(2, 0.01, 0), new Vector(1, -1, -9));
        result = cylinder.findIntersections(ray);
        assertNull(result, "should be null");


        // case 25: Ray's tangent to cylinder top (0 points)
        ray = new Ray(new Point3D(1, 4, 10), new Vector(0, -1, 0));
        result = cylinder.findIntersections(ray);
        assertNull(result, "should be null");


        // **** Group: Ray's line is neither parallel nor orthogonal to the axis
        // case 31: Ray's starts within and exits exactly between the base and cylinder (0 points)
        ray = new Ray(new Point3D(2.5, 0, 9), new Vector(0.5, 0, 1));
        result = cylinder.findIntersections(ray);
        assertNull(result, "should be null");


        // case 32: Ray's starts at the cylinder and exits exactly between the base and cylinder (0 points)
        ray = new Ray(new Point3D(1, 0, 8), new Vector(1, 0, 1));
        result = cylinder.findIntersections(ray);
        assertNull(result, "should be null");


        // case 33: Ray's through exactly between the bottom and cylinder exits exactly
        // between the top and cylinder (0 points)
        ray = new Ray(new Point3D(0.8, 0, -1), new Vector(0.2, 0, 1));
        result = cylinder.findIntersections(ray);
        assertNull(result, "should be null");


        // case 34: Ray's start exactly between the bottom and cylinder exits exactly
        // between the top and cylinder (0 points)
        ray = new Ray(new Point3D(1, 0, 0), new Vector(0.2, 0, 1));
        result = cylinder.findIntersections(ray);
        assertNull(result, "should be null");


        // case 35: Ray's starts at cylinder and going through the top (1 point)
        ray = new Ray(new Point3D(1, 0, 8), new Vector(0.1, 0, 1));
        result = cylinder.findIntersections(ray);
        geoP1 = new Point3D(1.2, 0, 10);
        assertEquals(List.of(geoP1), result, "Wrong point");


        // case 36: Ray's tangent the cylinder exactly between the top and cylinder (0 points)
        ray = new Ray(new Point3D(0, 0, 9), new Vector(1, 0, 1));
        result = cylinder.findIntersections(ray);
        assertNull(result, "should be null");
    }
}