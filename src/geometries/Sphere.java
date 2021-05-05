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

    @Override
    public Vector getNormal(Point3D point) {

        Vector N = point.subtract(_center);

        N.normalize();

        return N;
    }

//    /**
//     * function to find all intersections between a given ray and the sphere
//     *
//     * @param ray ray that cross the geometry
//     * @return linked list contains all of the intersection points between the ray and the sphere
//     */
//    @Override
//    public List<Point3D> findIntersections(Ray ray) {
//
//        // redefine all needed variables (copied from the presentation, same names)
//        Point3D O = _center;
//        Point3D p0 = ray.getP0();
//        double r = _radius;
//        Vector v = ray.getDir();
//        Vector u = O.subtract(p0);
//
//        double t_m = alignZero(v.dotProduct(u));
//        double d = alignZero(Math.sqrt(u.lengthSquared() - (t_m * t_m)));
//        double t_h = alignZero(Math.sqrt(r * r - d * d));
//
//        // if d is equal to or bigger than r, there will be no intersections at all
//        if (d >= r) {
//            return null;
//        }
//
//        double t1 = alignZero(t_m + t_h);
//        double t2 = alignZero(t_m - t_h);
//
//        List<Point3D> ans = new LinkedList<Point3D>();
//
//        // t must be positive
//        if (t1 > 0) {
//            ans.add(ray.getPoint(t1));
//        }
//
//        // must bt positive, and significantly different from t1
//        if (t2 > 0 && !isZero(t1 - t2)) {
//            ans.add(ray.getPoint(t2));
//        }
//
//        // if any intersections were found, return them
//        if (ans.size() > 0) {
//            return ans;
//        }
//
//        // else, return null
//        return null;
//    }

    /**
     * @param ray ray that cross the geometry
     * @return list of intersection points that were found
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {

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
        if (t1 > 0) {
            ans.add(new GeoPoint(this, ray.getPoint(t1)));
        }

        // must bt positive, and significantly different from t1
        if (t2 > 0 && !isZero(t1 - t2)) {
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
