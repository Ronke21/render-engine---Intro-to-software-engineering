package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * interface for all shapes in the space.
 */
public interface Geometry {
    /**
     * function that receive a point in a body and return a normal in this point to the body
     * @param point
     * @return
     */
    Vector getNormal(Point3D point);
}
