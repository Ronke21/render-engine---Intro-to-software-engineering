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
     * @param vertex - first point
     * @param vertex1 - second point
     * @param vertex2 - third point
     */
    public Plane(Point3D vertex, Point3D vertex1, Point3D vertex2) {
        _q0 = vertex;
        _normal = null;
    }

    public Vector getNormal() {
        //tODO
        return _normal;
    }

    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }
}
