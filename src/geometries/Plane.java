package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static primitives.Util.*;

/**
 * this class represents a plane in the space. contains a point in the plane and normal to the plane.
 */
public class Plane extends FlatGeometry {
    /**
     * @member _q0 - random point on plane
     * @member _normal - normal to plane on q0
     */
    final Point3D _q0;
    final Vector _normal;


    /**
     * constructor for plane.
     *
     * @param q0     - random point in the plane.
     * @param normal - normal to plane in the q0 point.
     */
    public Plane(Point3D q0, Vector normal) {
        _q0 = q0;
        _normal = normal.normalized();
    }

    /**
     * * constructor for plane. receives 3 points.
     *
     * @param p1 - first point
     * @param p2 - second point
     * @param p3 - third point
     */
    public Plane(Point3D p1, Point3D p2, Point3D p3) {

        _q0 = p1;

        Vector U = p2.subtract(p1);
        Vector V = p3.subtract(p1);

        _normal = U.crossProduct(V).normalized();
    }

    /**
     * getter
     */
    public Vector getNormal() {
        return _normal;
    }

    /**
     * getter
     */
    @Override
    public Vector getNormal(Point3D point) {
        return getNormal();
    }

    /**
     * @param ray         - ray that cross the geometry
     * @param maxDistance - the upper bound of distance, any point which
     *                    its distance is greater than this bound will not be returned
     * @return list of intersection points that were found
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance, boolean bb) {

        Point3D P0 = ray.getP0();
        Vector v = ray.getDir();
        Point3D Q0 = _q0;
        Vector N = _normal;

        double t = alignZero((N.dotProduct(Q0.subtract(P0))) / (N.dotProduct(v)));

        if ((t > 0) && alignZero(t - maxDistance) <= 0) {
            List<Point3D> ans = new LinkedList<Point3D>();
            return List.of(new GeoPoint(this, ray.getPoint(t)));
        }

        return null;
    }

    /**
     * perform full comparison between a given object and this
     *
     * @param o - object
     * @return - whether the object equals to this or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plane plane = (Plane) o;
        return _q0.equals(plane._q0) && _normal.equals(plane._normal);
    }
}
