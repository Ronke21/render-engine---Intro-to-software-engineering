package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;

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

            return (P.subtract(O)).normalized();
//            Vector sub = P.subtract(O);
//
//            sub.normalize();
//
//            return sub;
            //copy from tube
        }


    }


    /**
     * findIntersections method will group all the points which the ray intersect with the class Tube
     * Cases are: ray cross cylinder (2 points) ,ray launch cylinder(1 points), ray doesn't intersect cylinder at all (0 points)
     * or that the distance is beyond the maximum (0 points).
     *
     * @param ray         - which could intersect the cylinder
     * @param maxDistance - is the maximum distance to find intersections in
     * @return list of points
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {

        List<GeoPoint> intersections = super.findGeoIntersections(ray, maxDistance);
        if (intersections == null) return null;
        // Step 2: intersect is between caps
        Vector va = _axisRay.getDir();
        Point3D A = _axisRay.getP0();
        Point3D B = _axisRay.getPoint(_height);
        double lowerBound, upperBound;
        List<GeoPoint> intersectionsCylinder = new ArrayList<>();
        for (GeoPoint gPoint : intersections) {
            lowerBound = va.dotProduct(gPoint.point.subtract(A));
            upperBound = va.dotProduct(gPoint.point.subtract(B));
            if (lowerBound > 0 && upperBound < 0) {
                // the check for distance, if the intersection point is beyond the distance
                if (alignZero(gPoint.point.distance(ray.getP0()) - maxDistance) <= 0)
                    intersectionsCylinder.add(gPoint);
            }
        }
        // Step 3: intersect with each plane which belongs to the caps
        Plane planeA = new Plane(A, va);
        Plane planeB = new Plane(B, va);
        List<GeoPoint> intersectionPlaneA = planeA.findGeoIntersections(ray, maxDistance);
        List<GeoPoint> intersectionPlaneB = planeB.findGeoIntersections(ray, maxDistance);
        if (intersectionPlaneA == null && intersectionPlaneB == null)
            return intersectionsCylinder;
        // Step 4: intersect inside caps
        Point3D q3, q4;
        if (intersectionPlaneA != null) {
            q3 = intersectionPlaneA.get(0).point;
            if (q3.subtract(A).lengthSquared() < _radius * _radius) {
                intersectionsCylinder.add(intersectionPlaneA.get(0));
            }
        }
        if (intersectionPlaneB != null) {
            q4 = intersectionPlaneB.get(0).point;
            if (q4.subtract(B).lengthSquared() < _radius * _radius) {
                intersectionsCylinder.add(intersectionPlaneB.get(0));
            }
        }
        if (intersectionsCylinder.isEmpty()) return null;
        return intersectionsCylinder;
    }
}


