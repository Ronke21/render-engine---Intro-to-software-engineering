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

//        Vector P_P0 = point.subtract(_axisRay.getP0());
//        double t = _axisRay.getDir().dotProduct(P_P0);
//
//        Point3D O = _axisRay.getP0().add(P_P0.scale(t));
//
//        Vector N = point.subtract(O);
//
//        N.normalize();
//
//        return N;
//        Point3D _point = _axisRay.getP0();
//        //The vector from the point of the cylinder to the given point
//        Vector vector1 = point.subtract(_point);
//
//        Vector _direction = _axisRay.getDir();
//        //We need the projection to multiply the _direction unit vector
//        double projection = vector1.dotProduct(_direction);
//
//        Vector vector2 = _direction.scale(projection);
//
//        //This vector is orthogonal to the _direction vector.
//        Vector check = vector1.subtract(vector2);
//        return check.normalize();

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
}
