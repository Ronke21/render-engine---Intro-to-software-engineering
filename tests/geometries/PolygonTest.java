package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import primitives.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Testing Polygons
 *
 * @author Dan
 */
public class PolygonTest {

    /**
     * Test method for
     * {@link geometries.Polygon#Polygon(Point3D...)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(-1, 1, 1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct polygon");
        }

        // TC02: Wrong vertices order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(0, 1, 0),
                    new Point3D(1, 0, 0), new Point3D(-1, 1, 1));
            fail("Constructed a polygon with wrong order of vertices");
        } catch (IllegalArgumentException e) {
        }

        // TC03: Not in the same plane
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 2, 2));
            fail("Constructed a polygon with vertices that are not in the same plane");
        } catch (IllegalArgumentException e) {
        }

        // TC04: Concave quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0.5, 0.25, 0.5));
            fail("Constructed a concave polygon");
        } catch (IllegalArgumentException e) {
        }

        // =============== Boundary Values Tests ==================

        // TC10: Vertex on a side of a quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0.5, 0.5));
            fail("Constructed a polygon with vertix on a side");
        } catch (IllegalArgumentException e) {
        }

        // TC11: Last point = first point
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0, 1));
            fail("Constructed a polygon with vertice on a side");
        } catch (IllegalArgumentException e) {
        }

        // TC12: Colocated points
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 1, 0));
            fail("Constructed a polygon with vertice on a side");
        } catch (IllegalArgumentException e) {
        }

    }

    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Polygon pl = new Polygon(
                new Point3D(0, 0, 1),
                new Point3D(1, 0, 0),
                new Point3D(0, 1, 0),
                new Point3D(-1, 1, 1)
        );
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals(new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)), "Bad normal to polygon");
    }


    @Test
    void findIntersections() {
        // ============ Equivalence Partitions Tests ==============
        Polygon p = new Polygon(
                new Point3D(1, 0, 0),
                new Point3D(0, 0, 1),
                new Point3D(-1, 1, 1),
                new Point3D(0, 1, 0)
        );

        Point3D intersectionPoint;

        // TC01: Ray intersects the polygon (1 points)
        Ray ray1 = new Ray(new Point3D(0, -1, 0), new Vector(0, 1.5, 0.5));
        List<Point3D> TC01result = p.findIntersections(ray1);

        assertEquals(1, TC01result.size(), "Wrong number of intersection points");

        intersectionPoint = new Point3D(0, 0.5, 0.5);
        assertEquals(TC01result.get(0), intersectionPoint, "not the correct intersection point");

        // TC02: Ray doesn't intersects the polygon-against the edge (0 points)
        Ray ray2 = new Ray(new Point3D(0, -1, 0), new Vector(2, 2, 1));
        List<Point3D> TC02result = p.findIntersections(ray2);

        assertNull(TC02result, "Wrong number of intersection points");

        // TC03: Ray doesn't intersects the polygon-against the vertices (0 points)
        Ray ray3 = new Ray(new Point3D(0, -1, 0), new Vector(2, 1, 0));
        List<Point3D> TC03result = p.findIntersections(ray3);

        assertNull(TC03result, "Wrong number of intersection points");

        // =============== Boundary Values Tests ==================

        // TC04: Ray  intersects the polygon on the edge (0 points)
        Ray ray4 = new Ray(new Point3D(0, -1, 0), new Vector(1, 2, 1));
        List<Point3D> TC04result = p.findIntersections(ray4);

        assertNull(TC04result, "Wrong number of intersection points");

        // TC05: Ray  intersects the polygon on the vertices (0 points)
        Ray ray5 = new Ray(new Point3D(0, -1, 0), new Vector(1, 1, 0));
        List<Point3D> TC05result = p.findIntersections(ray5);

        assertNull(TC05result, "Wrong number of intersection points");

        // TC06: Ray intersects on edge's continuation (0 points)
        Ray ray6 = new Ray(new Point3D(0, -1, 0), new Vector(1, 0, 0));
        List<Point3D> TC06result = p.findIntersections(ray6);

        assertNull(TC06result, "Wrong number of intersection points");
    }
}
