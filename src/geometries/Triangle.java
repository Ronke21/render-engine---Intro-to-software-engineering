package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.*;

/**
 * this class represents a triangle in the space, containing 3 points (in the polygon vertices list)
 */
public class Triangle extends Polygon {

    /**
     * constructor for a triangle
     *
     * @param p1 - first point
     * @param p2 - second point
     * @param p3 - third point
     */
    public Triangle(Point3D p1, Point3D p2, Point3D p3) {
        super(p1, p2, p3);
    }

    /**
     * functions to find the intersection point (if exists) between the ray and the triangle
     *
     * @param ray
     * @return the intersection point
     */
    public List<Point3D> findIntersections(Ray ray) {

        Point3D p0 = ray.getP0();
        Point3D p1 = vertices.get(0);
        Point3D p2 = vertices.get(1);
        Point3D p3 = vertices.get(2);

        Vector v = ray.getDir();
        Vector v1 = p1.subtract(p0);
        Vector v2 = p2.subtract(p0);
        Vector v3 = p3.subtract(p0);

        Vector N1 = (v1.crossProduct(v2)).normalize();
        Vector N2 = (v2.crossProduct(v3)).normalize();
        Vector N3 = (v3.crossProduct(v1)).normalize();

        // make sure we actually have an intersection, otherwise return null..
        double Vn1 = alignZero(N1.dotProduct(v));
        double Vn2 = alignZero(N2.dotProduct(v));
        double Vn3 = alignZero(N3.dotProduct(v));

        if (isZero(Vn1) || isZero(Vn2) || isZero(Vn3)) {
            return null;
        }

        if ((Vn1 > 0 && Vn2 > 0 && Vn3 > 0) ||
                (Vn1 < 0 && Vn2 < 0 && Vn3 < 0)
        ) {

            // after we made sure there is an intersection with the
            // triangle, we can create the plane which contains the triangle
            // and return the intersections with it

            Plane plane = new Plane(p1, p2, p3);
            return plane.findIntersections(ray);
        }

        // if non of the conditions were fulfilled, return null
        return null;
    }
}
