package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

/**
 * this class represents a sphere in the space,
 * containing the center point of the sphere and its radius.
 */
public class Sphere extends RadialGeometry implements Geometry {

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

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        Point3D O = _center;
        Point3D p0 = ray.getP0();
        double r = _radius;
        Vector v = ray.getDir();
        Vector u = O.subtract(p0);

        double t_m = v.dotProduct(u);
        double d = Math.sqrt(u.lengthSquared() - (t_m * t_m));
        double t_h = Math.sqrt(r * r - d * d);

        if (d >= r) {
            return null;
        }

        double t1 = t_m + t_h;
        double t2 = t_m - t_h;

        List<Point3D> ans = new LinkedList<Point3D>();

        if (t1 > 0) {
            ans.add(ray.getPoint(t1));
        }
        if (t2 > 0) {
            ans.add(ray.getPoint(t2));
        }

        if (ans.size() > 0) {
            return ans;
        }

        return null;
    }
}
