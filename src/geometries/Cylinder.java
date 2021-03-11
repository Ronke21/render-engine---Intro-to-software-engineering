package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * this class represents a Cylinder in the space - kind of long tube.
 * has height = length of tube.
 */
public class Cylinder extends Tube {

    final double _height;

    /**
     * constructor for cylinder.
     * @param axisRay - ray for base tube
     * @param radius = radius of base tube
     * @param height - length of cylinder
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        _height = height;
    }

    /**
     * getter
     */
    public double getHeight() {
        return _height;
    }

    @Override
    public String toString() {
        return "(" + _axisRay + "," + _radius + "," + _height + ")";
    }

    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }

}

