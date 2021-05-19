package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * this class represents a sphere in the space,
 * containing the center point of the sphere and its radius.
 */
public class Sphere extends RadialGeometry {

    /**
     * @member _center - the center point of the sphere
     */
    final Point3D _center;


    /**
     * constructor.
     *
     * @param center - center of sphere
     * @param radius - radius of sphere
     */
    public Sphere(Point3D center, double radius) {
        super(radius);
        _center = center;
    }

    /**
     * getter
     */
    public Point3D getCenter() {
        return _center;
    }

    @Override
    public String toString() {
        return "(" + _center + ", " + _radius + ")";
    }

    /**
     * function to calculate the normal of the sphere
     *
     * @param point point pointing in the direction of the normal
     * @return the normal vector
     */
    @Override
    public Vector getNormal(Point3D point) {
        return (point.subtract(_center)).normalized();
    }


    /**
     * @param ray         ray that cross the geometry
     * @param maxDistance - the upper bound of distance, any point which
     *                    its distance is greater than this bound will not be returned
     * @return list of intersection points that were found
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {

        // redefine all needed variables (copied from the presentation, same names)
        Point3D O = _center;
        Point3D p0 = ray.getP0();
        double r = _radius;
        Vector v = ray.getDir();
        Vector u = O.subtract(p0);

        double t_m = alignZero(v.dotProduct(u));
        double d = alignZero(Math.sqrt(u.lengthSquared() - (t_m * t_m)));
        double t_h = alignZero(Math.sqrt(r * r - d * d));

        // if d is equal to or bigger than r, there will be no intersections at all
        if (d >= r) {
            return null;
        }

        double t1 = alignZero(t_m + t_h);
        double t2 = alignZero(t_m - t_h);

        List<GeoPoint> ans = new LinkedList<>();

        // t must be positive
        if (t1 > 0 && ((alignZero(t1 - maxDistance) <= 0))) {
            ans.add(new GeoPoint(this, ray.getPoint(t1)));
        }

        // t2 must be positive, and significantly different from t1
        if (t2 > 0 && (alignZero(t2 - maxDistance) <= 0) && !isZero(t1 - t2)) {
            ans.add(new GeoPoint(this, ray.getPoint(t2)));
        }

        // if any intersections were found, return them
        if (ans.size() > 0) {
            return ans;
        }

        // else, return null
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
        Sphere sphere = (Sphere) o;
        if (sphere._radius != this._radius) return false;
        return _center.equals(sphere._center);
    }
}
