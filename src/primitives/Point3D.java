package primitives;

import java.util.Objects;

/**
 * basic Point for RayTracing project in 3D
 * contains coordinates in 3 planes
 */
public class Point3D {
    final Coordinate _x;
    final Coordinate _y;
    final Coordinate _z;

    // (0,0,0)
    public final static Point3D ZERO = new Point3D(0d, 0d, 0d);

    /**
     * constructor for Point3D
     *
     * @param x coordinate for X axis
     * @param y coordinate for Y axis
     * @param z coordinate for Z axis
     */
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        this(x.coord, y.coord, z.coord);
    }

    /**
     * constructor for Point3D
     *
     * @param x double value for X axis
     * @param y double value for Y axis
     * @param z double value for Z axis
     */
    public Point3D(double x, double y, double z) {
        _x = new Coordinate(x);
        _y = new Coordinate(y);
        _z = new Coordinate(z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return _x.equals(point3D._x) && _y.equals(point3D._y) && _z.equals(point3D._z);
    }

    @Override
    public String toString() {
        return "(" + _x + "," + _y + "," + _z + ")";
    }

    /**
     * calculates square of the distance of current point from a given point
     * @param other - the point to calculate distance from
     * @return (x2 - x1)^2 + (y2-y1)^2 + (z2-z1)^2 - according to distance formula
     */
    public double distanceSquared(Point3D other) {
        final double x1 = _x.coord;
        final double y1 = _y.coord;
        final double z1 = _z.coord;

        final double x2 = other._x.coord;
        final double y2 = other._y.coord;
        final double z2 = other._z.coord;

        final double dx = x2 - x1;
        final double dy = y2 - y1;
        final double dz = z2 - z1;

        return (dx * dx) + (dy * dy) + (dz * dz);
    }

    /**
     * calculates the distance of current point from a given point.
     * uses the function of squared distance (DRY)
     * @param point3D - the point to calculate distance from
     * @return (x2 - x1)^2 + (y2-y1)^2 + (z2-z1)^2 - according to distance formula
     */
    public double distance(Point3D point3D) {
        return Math.sqrt(distanceSquared(point3D));
    }

    /**
     * calculates a vector to a given point
     * @param pt2 - second point
     * @return the subtraction between points
     */
    public Vector subtract(Point3D pt2) {
        if (pt2.equals(this)) {
            throw new IllegalArgumentException("cannot create Vector to Point(0,0,0)");
        }
        return new Vector(new Point3D(
                _x.coord - pt2._x.coord,
                _y.coord - pt2._y.coord,
                _z.coord - pt2._z.coord
        ));
    }

    /**
     * adds a vector to current point and return its edge
     * @param vector - to be added
     * @return new vector end
     */
    public Point3D add(Vector vector) {
        return new Point3D(
                _x.coord + vector._head._x.coord,
                _y.coord + vector._head._y.coord,
                _z.coord + vector._head._z.coord
        );
    }
}


