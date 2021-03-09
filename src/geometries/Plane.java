package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * this class represents a plane in the space. contains a point in the plane and normal to the plane.
 */
public class Plane implements Geometry{

    final Point3D _q0;
    final Vector _normal;

    /**
     * constructor for plane.
     * @param q0 - random point in the plane.
     * @param normal - normal to plane in the q0 point.
     */
    public Plane(Point3D q0, Vector normal) {
        _q0 = q0;
        _normal = normal;
    }

    /**
     *      * constructor for plane. receives 3 points.
     * @param p1 - first point
     * @param p2 - second point
     * @param p3 - third point
     */
    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        _q0 = p1;

        Vector U = p2.subtract(p1);
        Vector V = p3.subtract(p1);

        Vector N = U.crossProduct(V);

        N.normalize();

        _normal = N;
    }

    @Override
    public Vector getNormal(Point3D point) {
        return _normal;
    }
}
