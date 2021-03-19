package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {

    @Test
    void add() {
    }

    @Test
    void findIntersections() {

        Plane plane = new Plane(
                new Point3D(0, 1, 0),
                new Point3D(1, 0, 0),
                new Point3D(0, 0, 1)
        );

        Sphere sphere = new Sphere(new Point3D(0, 0, 1), 1.0);

        Triangle triangle = new Triangle(
                new Point3D(0, 1, 0),
                new Point3D(1, 0, 0),
                new Point3D(0, 0, 1)
        );

        Geometries geometries = new Geometries(plane, sphere, triangle);
        Geometries empty = new Geometries();

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray intersects 2 out of 3 of the shapes (2 points)
        Ray ray1 = new Ray(new Point3D(-1, -0.5, 1), new Vector(1, -0.25, -0.75));
        List<Point3D> TC01result = geometries.findIntersections(ray1);

        assertEquals(2, TC01result.size(), "Wrong number of intersection points");


        // =============== Boundary Values Tests ==================

        // TC02: empty list of shapes (0 points)
        Ray ray2 = new Ray(new Point3D(1, 1, 0), new Vector(1, 0, 0));
        List<Point3D> TC02result = empty.findIntersections(ray2);

        assertNull(TC02result, "Wrong number of intersection points");

        // TC03: Ray doesn't intersect the shapes (0 points)
        Ray ray3 = new Ray(new Point3D(2, 1, 0), new Vector(1, 2, 0));
        List<Point3D> TC03result = geometries.findIntersections(ray3);

        assertNull(TC03result, "Wrong number of intersection points");

        // TC04: Ray intersects only one of the shapes (1 points)
        Ray ray4 = new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0));
        List<Point3D> TC04result = geometries.findIntersections(ray4);

        assertEquals(1, TC04result.size(), "Wrong number of intersection points");

        // TC05: Ray intersects all the shapes (3 points)
        Ray ray5 = new Ray(new Point3D(-1, -1, 1), new Vector(1, 1, -1));
        List<Point3D> TC05result = geometries.findIntersections(ray5);

        assertEquals(3, TC05result.size(), "Wrong number of intersection points");
    }
}
