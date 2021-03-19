package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Triangle class
 */
class TriangleTest {

    /**
     * Test method for {@link geometries.Triangle#getNormal(primitives.Point3D)}.
     */
    @Test
    void getNormal() {

        // ============ Equivalence Partitions Tests ==============
        Triangle testT = new Triangle(
                new Point3D(1, 0, 0),
                new Point3D(0, 1, 0),
                new Point3D(0, 0, 1)
        );
        double sq = Math.sqrt(1 / 3d);

        // Test that result of getNormal is proper
        assertEquals(testT.getNormal(new Point3D(1, 0, 0)), new Vector(sq, sq, sq));

    }

    @Test
    void findIntersections() {

        // ============ Equivalence Partitions Tests ==============
        Triangle t = new Triangle(
                new Point3D(1, 0, 0),
                new Point3D(0, 1, 0),
                new Point3D(0, 0, 1)
        );

        Point3D intersectionPoint;

        // TC01: Ray intersects the triangle (1 points)
        Ray ray1 = new Ray(new Point3D(0, -1, 0), new Vector(1, 3, 1));
        List<Point3D> TC01result = t.findIntersections(ray1);

        assertEquals(1, TC01result.size(), "Wrong number of intersection points");

        intersectionPoint = new Point3D(0.4, 0.2, 0.4);
        assertEquals(TC01result.get(0), intersectionPoint, "not the correct intersection point");

        // TC02: Ray doesn't intersects the triangle-against the edge (0 points)
        Ray ray2 = new Ray(new Point3D(0, -1, 0), new Vector(2, 2, 1));
        List<Point3D> TC02result = t.findIntersections(ray2);

        assertNull(TC02result, "Wrong number of intersection points");

        // TC03: Ray doesn't intersects the triangle-against the vertices (0 points)
        Ray ray3 = new Ray(new Point3D(0, -1, 0), new Vector(2, 1, 0));
        List<Point3D> TC03result = t.findIntersections(ray3);

        assertNull(TC03result, "Wrong number of intersection points");

        // =============== Boundary Values Tests ==================

        // TC04: Ray  intersects the triangle on the edge (0 points)
        Ray ray4 = new Ray(new Point3D(0, -1, 0), new Vector(1, 2, 1));
        List<Point3D> TC04result = t.findIntersections(ray4);

        assertNull(TC04result, "Wrong number of intersection points");

        // TC05: Ray  intersects the triangle on the vertices (0 points)
        Ray ray5 = new Ray(new Point3D(0, -1, 0), new Vector(1, 1, 0));
        List<Point3D> TC05result = t.findIntersections(ray5);

        assertNull(TC05result, "Wrong number of intersection points");

        // TC06: Ray intersects on edge's continuation (0 points)
        Ray ray6 = new Ray(new Point3D(0, -1, 0), new Vector(1, 0, 0));
        List<Point3D> TC06result = t.findIntersections(ray6);

        assertNull(TC06result, "Wrong number of intersection points");

    }
}