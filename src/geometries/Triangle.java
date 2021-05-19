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
}
