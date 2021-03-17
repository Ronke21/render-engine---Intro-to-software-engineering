package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Plane class
 */
class PlaneTest {

    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point3D)}.
     */
    @Test
    void getNormal() {

        // ============ Equivalence Partitions Tests ==============
        Plane testP = new Plane(
                new Point3D(1, 0, 0),
                new Point3D(0, 1, 0),
                new Point3D(0, 0, 1)
        );
        double sq = Math.sqrt(1 / 3d);

        // Test that result of getNormal is proper
        assertEquals(testP.getNormal(new Point3D(1, 0, 0)), new Vector(sq, sq, sq));
        assertEquals(testP.getNormal(), new Vector(sq, sq, sq));

    }

    @Test
    void findIntersections() {
        Plane pl = new Plane(
                new Point3D(0, 1, 0),
                new Point3D(1, 0, 0),
                new Point3D(0, 0, 1)
        );

        // ============ Equivalence Partitions Tests ==============
        Point3D intersectionPoint;

        // TC01: Ray intersects the plane (1 points)
        Ray ray1 = new Ray(new Point3D(0, -1, 0), new Vector(1, 3, 1));
        List<Point3D> TC01result = pl.findIntersections(ray1);

        assertEquals(1, TC01result.size(), "Wrong number of intersection points");

        intersectionPoint = new Point3D(0.4, 0.2, 0.4);
        assertEquals(intersectionPoint, TC01result.get(0), "not the correct intersection point");

        // TC02: Ray  doesn't intersects the plane(0 points)
        Ray ray2 = new Ray(new Point3D(3, 4, 2), new Vector(1, 2, 1));
        List<Point3D> TC02result = pl.findIntersections(ray2);

        assertNull(TC02result, "Wrong number of intersection points");


        // =============== Boundary Values Tests ==================

        // TC03 :Ray is parallel to the plane and is included in the plane (0 points)

        Ray ray3 = new Ray(new Point3D(0.5, 0.5, 0), new Vector(1, -1, 0));
        List<Point3D> TC03result = pl.findIntersections(ray3);

        assertNull(TC03result, "Wrong number of intersection points");

        // TC04 :Ray is parallel to the plane and is not included in the plane (0 points)
        Ray ray4 = new Ray(new Point3D(0.5, 0, 0), new Vector(1, -1, 0));
        List<Point3D> TC04result = pl.findIntersections(ray4);

        assertNull(TC04result, "Wrong number of intersection points");

        // TC05 :Ray is orthogonal to the plane and crosses the plane (1 points)
        Ray ray5 = new Ray(new Point3D(1, 1, 1), new Vector(-1, -1, -1));
        List<Point3D> TC05result = pl.findIntersections(ray5);

        assertEquals(1, TC05result.size(), "Wrong number of intersection points");

        intersectionPoint = new Point3D(1 / 3d, 1 / 3d, 1 / 3d);
        assertEquals(intersectionPoint, TC05result.get(0), "not the correct intersection point");

        // TC06 :Ray is orthogonal to the plane and its starting point is on the plane (0 points)
        Ray ray6 = new Ray(new Point3D(0.5, 0.25, 0.25), new Vector(-1, -1, -1));
        List<Point3D> TC06result = pl.findIntersections(ray6);

        assertNull(TC06result, "Wrong number of intersection points");

        // TC07 :Ray is orthogonal to the plane and after the plane (0 points)
        Ray ray7 = new Ray(new Point3D(-1, -1, -1), new Vector(-1, -1, -1));
        List<Point3D> TC07result = pl.findIntersections(ray7);

        assertNull(TC07result, "Wrong number of intersection points");

        // TC08 :Ray is neither orthogonal nor parallel  and begins at the plane (0 points)
        Ray ray8 = new Ray(new Point3D(0.5, 0.25, 0.25), new Vector(-4, 1, 0));
        List<Point3D> TC08result = pl.findIntersections(ray8);

        assertNull(TC08result, "Wrong number of intersection points");

        // TC09 :Ray is neither orthogonal nor parallel  and  begins in
        //the same point which appears as reference point in the plane (0 points)
        Ray ray9 = new Ray(new Point3D(-2, -2, 5), new Vector(2, 10, -5));
        List<Point3D> TC09result = pl.findIntersections(ray9);

        assertNull(TC09result, "Ray is neither orthogonal nor parallel  and  begins in " +
                "the same point which appears as reference point in the plane");
    }

}