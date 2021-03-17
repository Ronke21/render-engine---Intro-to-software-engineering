package primitives;

/**
 * basic Point for RayTracing project in 3D
 * contains coordinates in 3 planes
 */
public class Point3D {
    /**
     * @member _x - the coordinate on x pivot
     * @member _y - the coordinate on y pivot
     * @member _z - the coordinate on z pivot
     */
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
//        this(x._coord, y._coord, z._coord);
        //for performance improvement
        _x = x;
        _y = y;
        _z = z;
    }

    /**
     * primary constructor for Point3D
     *
     * @param x double value for X axis
     * @param y double value for Y axis
     * @param z double value for Z axis
     */
    public Point3D(double x, double y, double z) {
//        this(new Coordinate(x),new Coordinate(y),new Coordinate(z));
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

//    @Override
//    public String toString() {
//        return "(" + _x + "," + _y + "," + _z + ")";
//    }

    @Override
    public String toString() {
        return "(" + getX() + ", " + getY() + ", " + getZ() + ")";
    }

    /**
     * calculates square of the distance of current point from a given point
     *
     * @param other - the point to calculate distance from
     * @return (x2 - x1)^2 + (y2-y1)^2 + (z2-z1)^2 - according to distance formula
     */
    public double distanceSquared(Point3D other) {
        final double x1 = _x._coord;
        final double y1 = _y._coord;
        final double z1 = _z._coord;

        final double x2 = other._x._coord;
        final double y2 = other._y._coord;
        final double z2 = other._z._coord;

        final double dx = x2 - x1;
        final double dy = y2 - y1;
        final double dz = z2 - z1;

        return (dx * dx) + (dy * dy) + (dz * dz);
    }

    /**
     * calculates the distance of current point from a given point.
     * uses the function of squared distance (DRY)
     *
     * @param point3D - the point to calculate distance from
     * @return (x2 - x1)^2 + (y2-y1)^2 + (z2-z1)^2 - according to distance formula
     */
    public double distance(Point3D point3D) {
        return Math.sqrt(distanceSquared(point3D));
    }

    /**
     * calculates a vector to a given point
     *
     * @param pt2 - second point
     * @return the subtraction between points
     */
    public Vector subtract(Point3D pt2) {
        if (pt2.equals(this)) {
            throw new IllegalArgumentException("cannot create Vector to Point(0,0,0)");
        }
        return new Vector(new Point3D(
                _x._coord - pt2._x._coord,
                _y._coord - pt2._y._coord,
                _z._coord - pt2._z._coord
        ));
    }

    /**
     * adds a vector to current point and return its edge
     *
     * @param vector - to be added
     * @return new vector end
     */
    public Point3D add(Vector vector) {
        return new Point3D(
                _x._coord + vector._head._x._coord,
                _y._coord + vector._head._y._coord,
                _z._coord + vector._head._z._coord
        );
    }

    /**
     * getters for x coordinate double value
     *
     * @return _x
     */
    public double getX() {
        return _x._coord;
    }

    /**
     * getters for y coordinate double value
     *
     * @return _y
     */
    public double getY() {
        return _y._coord;
    }

    /**
     * getters for z coordinate double value
     *
     * @return _z
     */
    public double getZ() {
        return _z._coord;
    }
}