package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;

/**
 * Interface for Composite Design Pattern The Composite Class - Geometries The
 * Basic Classes - all the specific geometries
 */
public interface Intersectable {
    /**
     * @param ray ray that cross the geometry
     * @return list of intersection points that were found
     */
    List<Point3D> findIntersections(Ray ray);
}