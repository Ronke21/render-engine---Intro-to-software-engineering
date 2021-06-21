package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import java.util.Objects;

import static primitives.Util.Determinant;
import static primitives.Util.*;

/**
 * Class Tube represent a smooth infinite surface, curvature at every point on its face fixed.
 * That is the cylinder which does not have a length.
 * Represented by ray and radius, (direction, point which belong to infinite line, radius)
 */
public class Tube extends RadialGeometry {

    /**
     * @member _axisRay - direction ray of tube
     */
    final Ray _axisRay;

    /**
     * Main Constructor for class Tube, inputs are ray and radius
     *
     * @param axisRay - ray in middle of tube
     * @param radius  - radius of tube
     */
    public Tube(Ray axisRay, double radius) {
        super(radius);
        _axisRay = axisRay;
    }

    /**
     * getter to the ray (values of direction and place) which represent the tube
     * @return axis ray
     */
    public Ray getAxisRay() {
        return _axisRay;
    }


    @Override
    public String toString() {
        return "(" + _axisRay + "," + _radius + ")";
    }

    /**
     * function that receive a point in a body and return a normal in this point to the body
     *
     * @param point point pointing in the direction of the normal
     * @return normal vector to the Geometry
     */
    @Override
    public Vector getNormal(Point3D point) {


        Vector v = _axisRay.getDir();
        Point3D P0 = _axisRay.getP0();
        Vector PMinusP0 = point.subtract(P0);
        double t = v.dotProduct(PMinusP0);

        Point3D O = P0.add(v.scale(t));

        return (point.subtract(O)).normalized();
    }

    /**
     * this function finds the intersection points of a given ray with the tube
     *
     * @param ray         - ray that cross the geometry
     * @param maxDistance - the upper bound of distance, any point which
     *                    its distance is greater than this bound will not be returned
     * @return list of intersection points that were found
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance, boolean bb) {

        // in the formulas from the presentation :
        // , is dotProduct
        // () is scale

        Vector Va = _axisRay.getDir(); // tube's vector
        Point3D Pa = _axisRay.getP0(); // tube's ray starting point

        double r = getRadius();
        Point3D P = ray.getP0();        // ray's starting point
        Vector V = ray.getDir();        // ray's vector

        double VVa = alignZero(V.dotProduct(Va)); // dot product off the tube's ray and ray

        Vector V_VVaVa;

        // if the ray is orthogonal to the tube's ray
        if (VVa == 0) {
            V_VVaVa = V; // since VVaVa is 0
        } else {
            Vector VVaVa = Va.scale(VVa);
            try {
                V_VVaVa = V.subtract(VVaVa); // might be equal to (0,0,0)
            } catch (IllegalArgumentException ex) {
                return null;
            }
        }

        Vector DP;
        try {
            DP = P.subtract(Pa);    // ΔP = P - Pa = (0, 0, 0) => P and Pa starts at the same point
        } catch (IllegalArgumentException ex) {
            // if orthogonal, the intersection point will have distance r from P
            if (VVa == 0) {
                return List.of(new GeoPoint(this, ray.getPoint(r)));
            }

            // else, calculate the distance ray <-> intersection
            double multiplier = alignZero(Math.sqrt(r * r / V_VVaVa.lengthSquared()));

            if (multiplier == 0) {
                return null;
            } else {
                return List.of(new GeoPoint(this, ray.getPoint(multiplier)));
            }
        }


        // A = (V - (V * Va) * Va) ^ 2
        double A = (V_VVaVa).lengthSquared();

        double DPVa = alignZero(DP.dotProduct(Va));
        Vector DPVaVa;
        Vector DP_DPVaVa;

        // if orthogonal, the intersection point will have distance r from P
        if (DPVa == 0) {
            DP_DPVaVa = DP;
        } else {
            // the part of Va from Pa to the intersection with the ray
            DPVaVa = Va.scale(DPVa);
            try {
                // the part of ray from P to the intersection with the tube's ray
                DP_DPVaVa = DP.subtract(DPVaVa);
            } catch (IllegalArgumentException ex) {
                // else, calculate the distance ray <-> intersection
                double multiplier = alignZero(Math.sqrt(r * r / A));
                if (multiplier == 0) {
                    return null;
                } else {
                    return List.of(new GeoPoint(this, ray.getPoint(multiplier)));
                }
            }
        }

        // B = 2 * ((V - ((V * Va) * Va)) * (ΔP - (ΔP * Va) * Va))
        double B = 2 * alignZero((V_VVaVa).dotProduct(DP_DPVaVa));

        // C = (ΔP - (ΔP * Va) * Va) ^ 2 - r ^ 2
        double C = (DP_DPVaVa.lengthSquared()) - (r * r);

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

        // intersection point of the ray and the tube
        double X1 = alignZero(((-1 * B) + sqrtDet) / (2 * A));
        double X2 = alignZero(((-1 * B) - sqrtDet) / (2 * A));

        double checkMaxX1 = alignZero(X1 - maxDistance);
        double checkMaxX2 = alignZero(X2 - maxDistance);

        if (X1 > 0 && checkMaxX1 <= 0 && X2 > 0 && checkMaxX2 <= 0) {
            return List.of(new GeoPoint(this, ray.getPoint(X1)), new GeoPoint(this, ray.getPoint(X2)));
        }
        if (X1 > 0 && checkMaxX1 <= 0) {
            return List.of(new GeoPoint(this, ray.getPoint(X1)));
        }
        if (X2 > 0 && checkMaxX2 <= 0) {
            return List.of(new GeoPoint(this, ray.getPoint(X2)));
        }

        //  if ((X2 <= 0) && (alignZero(X1 - maxDistance) <= 0)) {
        ///       return List.of(new GeoPoint(this, ray.getPoint(X1)));
        //   } else return List.of(new GeoPoint(this, ray.getPoint(X1)), new GeoPoint(this,  ray.getPoint(X2)));
        return null;
    }


    /**
     * perform full comparison between a given object and this
     *
     * @param o - object
     * @return - whether the object equals to this or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tube tube = (Tube) o;
        if (tube._radius != this._radius) return false;
        return _axisRay.equals(tube._axisRay);
    }

}


