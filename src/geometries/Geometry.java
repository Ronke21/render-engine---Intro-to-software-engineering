package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * interface for all shapes in the space.
 */
public interface Geometry extends Intersectable{
    /**
     * function that receive a point in a body and return a normal in this point to the body
     * @param point point pointing in the direction of the normal
     * @return normal vector to the Geometry
     */
    Vector getNormal(Point3D point);
}
