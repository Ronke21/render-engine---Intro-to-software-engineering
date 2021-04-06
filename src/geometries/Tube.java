package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.Determinant;
import static primitives.Util.*;

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
     *
     * @param axisRay - ray in middle of tube
     * @param radius  - radius of tube
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

        // , is dotProduct
        // () is scale

        Vector Va = _axisRay.getDir();
        Point3D Pa = _axisRay.getP0();

        double r = getRadius();
        Point3D P = ray.getP0();
        Vector V = ray.getDir();

        double VVa = alignZero(V.dotProduct(Va));

        Vector V_VVaVa;

        // if the ray is orthogonal to the tube's ray
        if (VVa == 0) {
            V_VVaVa = V;
        } else {

            Vector VVaVa = Va.scale(VVa);

            try {
                V_VVaVa = V.subtract(VVaVa);
            } catch (IllegalArgumentException ex) {
                return null;
            }
        }

        Vector DP;
        try {
            DP = P.subtract(Pa);
        } catch (IllegalArgumentException ex) {
            if (VVa == 0) {
                return List.of(ray.getPoint(r));
            }

            double num = alignZero(Math.sqrt(r * r / V_VVaVa.lengthSquared()));

            if (num == 0) {
                return null;
            } else {
                return List.of(ray.getPoint(num));
            }
        }


        // A = (V - (V * Va) * Va) ^ 2
        double A = (V_VVaVa).lengthSquared();

        double DPVa = alignZero(DP.dotProduct(Va));
        Vector DPVaVa;
        Vector DP_PVaVa;

        if (DPVa == 0) {
            DP_PVaVa = DP;
        } else {
            DPVaVa = Va.scale(DPVa);
            try {
                DP_PVaVa = DP.subtract(DPVaVa);
            } catch (IllegalArgumentException ex) {
                double num = alignZero(Math.sqrt(r * r / A));
                if (num == 0) {
                    return null;
                } else {
                    return List.of(ray.getPoint(num));
                }
            }
        }

        // B = 2 * ((V - ((V * Va) * Va)) * (ΔP - (ΔP * Va) * Va))
        double B = 2 * alignZero((V_VVaVa).dotProduct(DP_PVaVa));

        // C = (ΔP - (ΔP * Va) * Va) ^ 2 - r ^ 2
        double C = (DP_PVaVa.lengthSquared()) - (r * r);

        double det = Determinant(A, B, C);

        // no intersections
        if (det <= 0) {
            return null;
        }

        // ray is tangent to the tube
        double sqrtDet = alignZero(Math.sqrt(det));
        if (sqrtDet == 0) {
            return null;
        }

        double X1 = alignZero(((-1 * B) + sqrtDet) / (2 * A));

        // if the + has no intersections, we can determine that the - will not have
        if (X1 <= 0) {
            return null;
        }

        double X2 = alignZero(((-1 * B) - sqrtDet) / (2 * A));

        if (X2 <= 0) {
            return List.of(ray.getPoint(X1));
        } else return List.of(ray.getPoint(X1), ray.getPoint(X2));

    }
}


