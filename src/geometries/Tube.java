package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * this class represents a Tube in the space.
 */
public class Tube implements Geometry {

    Ray axisRay;
    double radius;

    /**
     * constructor for a tube.
     * @param axisRay - ray in middle of tube
     * @param radius - radius of tube
     */
    public Tube(Ray axisRay, double radius) {
        this.axisRay = axisRay;
        this.radius = radius;
    }

    /**
     * getter
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    /**
     * getter
     */
    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "(" + axisRay + "," + radius + ")";
    }

    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }
}
