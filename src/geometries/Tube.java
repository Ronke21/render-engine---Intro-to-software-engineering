package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * this class represents a Tube in the space.
 */
public class Tube extends RadialGeometry implements Geometry {

    /**
     * @member _axisRay - direction ray of tube
     */
    final Ray _axisRay;

    /**
     * constructor for a tube.
     * @param axisRay - ray in middle of tube
     * @param radius - radius of tube
     */
    public Tube(Ray axisRay, double radius) {
        super(radius);
        _axisRay = axisRay;
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


        Vector v = _axisRay.getDir();
        Point3D P0 = _axisRay.getP0();
        Point3D P = point;
        Vector PMinusP0 = P.subtract(P0);
        double t = v.dotProduct(PMinusP0);

        Point3D O = P0.add(v.scale(t));

        Vector sub = P.subtract(O);

        sub.normalize();

        return sub;
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }
}
