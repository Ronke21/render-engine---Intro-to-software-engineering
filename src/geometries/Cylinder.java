package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * this class represents a Cylinder in the space - kind of long tube.
 * has height = length of tube.
 */
public class Cylinder extends Tube {
    /**
     * @member _height - the length of the cylinder from base to base
     */
    final double _height;

    /**
     * constructor for cylinder.
     *
     * @param axisRay - ray for base tube
     * @param radius  = radius of base tube
     * @param height  - length of cylinder
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        if (height <= 0) {
            throw new IllegalArgumentException("height can't be negative");
        }
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
    public Vector getNormal(Point3D P) {

        Point3D P0 = _axisRay.getP0(); //middle of starting base
        Vector v = _axisRay.getDir();
        Point3D P1 = P0.add(v.scale(_height)); //middle of far base

        //if point is on the starting base - if distance from p0 is radius, and orthogonal to ray (so it is not on ray itself)
        if ((P.distance(P0) <= _radius) && (P.subtract(P0).dotProduct(v) == 0)) {
            return getAxisRay().getDir().scale(-1);
        }
        //if point is on the far base - if distance from p1 is radius, and orthogonal to ray (so it is not on ray itself)
        else if ((P.distance(P1) <= _radius) && (P.subtract(P1).dotProduct(v) == 0)) {
            return getAxisRay().getDir();
        }

        // if point is on round surfaces - not based:
        else {

            Vector PMinusP0 = P.subtract(P0);
            double t = v.dotProduct(PMinusP0);

            Point3D O = P0.add(v.scale(t));

            Vector sub = P.subtract(O);

            sub.normalize();

            return sub;
            //copy from tube
        }


    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return super.findIntersections(ray);
    }

}

