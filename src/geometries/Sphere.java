package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * this class represents a sphere in the space,
 * containing the center point of the sphere and its radius.
 */
public class Sphere implements Geometry {

    Point3D center;
    double radius;

    /**
     * constructor.
     * @param center - center of sphere
     * @param radius - radius of sphere
     */
    public Sphere(Point3D center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    /**
     * getter
     */
    public Point3D getCenter() {
        return center;
    }

    /**
     * getter
     */
    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "(" + center + ", " + radius + ")";
    }

    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }
}
