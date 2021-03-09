package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * this class represents a Tube in the space.
 */
public class Tube implements Geometry {

    final Ray _axisRay;
    final double _radius;

    /**
     * constructor for a tube.
     * @param axisRay - ray in middle of tube
     * @param radius - radius of tube
     */
    public Tube(Ray axisRay, double radius) {
        _axisRay = axisRay;
        _radius = radius;
    }

    /**
     * getter
     */
    public Ray getAxisRay() {
        return _axisRay;
    }

    /**
     * getter
     */
    public double getRadius() {
        return _radius;
    }

    @Override
    public String toString() {
        return "(" + _axisRay + "," + _radius + ")";
    }

    @Override
    public Vector getNormal(Point3D point) {

        Vector P_P0 = point.subtract(_axisRay.getP0());
        double t = _axisRay.getDir().dotProduct(P_P0);

        Point3D O = _axisRay.getP0().add(P_P0.scale(t));

        Vector N = point.subtract(O);

        N.normalize();

        return N;
    }
}
