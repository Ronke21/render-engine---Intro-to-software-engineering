package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

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
        return null;
    }
}
