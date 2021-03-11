package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * this class represents a sphere in the space,
 * containing the center point of the sphere and its radius.
 */
public class Sphere implements Geometry {

    /**
     * @member _center - the center point of the sphere
     * @member _radius - the radius of the sphere
     */
    final Point3D _center;
    final double _radius;

    /**
     * constructor.
     *
     * @param center - center of sphere
     * @param radius - radius of sphere
     */
    public Sphere(Point3D center, double radius) {
        _center = center;
        _radius = radius;
    }

    /**
     * getter
     */
    public Point3D getCenter() {
        return _center;
    }

    /**
     * getter
     */
    public double getRadius() {
        return _radius;
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
}
