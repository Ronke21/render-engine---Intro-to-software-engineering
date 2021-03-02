package primitives;

import java.util.Objects;

import static primitives.Point3D.ZERO;

/**
 * this class represents a vector in the 3d space,
 * from the (0,0,0) to its edge point - called here head
 */
public class Vector {
    Point3D _head;

    /**
     * constructor for vector, receive 3 coordinates and sets as head of the vector from (0,0,0)
     * head can't be (0,0,0) - because the vector doesn't exist.
     *  @param x - the X axis
     * @param y - the Y axis
     * @param z - the Z axis
     */
    public Vector(Coordinate x, Coordinate y, Coordinate z) {
        this(x.coord, y.coord, z.coord);
    }

    /**
     * constructor for vector, receive 3 values and sets as head of the vector from (0,0,0)
     * head can't be (0,0,0) - because the vector doesn't exist.
     *  @param x - the X axis
     * @param y - the Y axis
     * @param z - the Z axis
     */
    public Vector(double x, double y, double z) {
        Point3D head = new Point3D(x, y, z);
        if (head.equals(ZERO)) {
            throw new IllegalArgumentException("Vector head cannot be Point(0,0,0)");
        }
        _head = head;
    }

    /**
     * constructor for vector, receive point and sets as head of the vector from (0,0,0)
     * head can't be (0,0,0) - because the vector doesn't exist.
     *  @param head - point of head for the vector
     */
    public Vector(Point3D head) {
        if (head.equals(ZERO)) {
            throw new IllegalArgumentException("Vector head cannot be Point(0,0,0)");
        }
        _head = head;
    }

    /**
     * adds a vector to current vector and return the sum
     * addition of vectors is addition of the fitting coordinates
     * @param v - vector to be added
     * @return - new vector of the sum
     */
    public Vector add(Vector v) {
        return new Vector(
                _head._x.coord + v._head._x.coord,
                _head._y.coord + v._head._y.coord,
                _head._z.coord + v._head._z.coord
        );
    }

    /**
     * subtracts a vector from current vector and return the difference
     * subtraction of vectors is subtraction of the fitting coordinates
     * @param v - vector to be subtracted
     * @return - new vector of the difference
     */
    public Vector subtract(Vector v) {
        return add(new Vector(
                -1 * v._head._x.coord,
                -1 * v._head._y.coord,
                -1 * v._head._z.coord
        ));
    }


    /**
     * calculates and return the multyplation in scalar of the current vector in a given scalar
     * the way is to multiply every coordinate in the scalar
     * @param mul - the scalar to multiply in
     * @return the multiplied vector
     */
    public Vector scale(double mul) {
        return new Vector(
                _head._x.coord * mul,
                _head._y.coord * mul,
                _head._z.coord * mul
        );
    }

    /**
     * calculates and return the dot product of the current vector in a given vector
     * the way is to multiply fitting coordinates from current vector in coordinates in given vector, and sum all the results
     * @param v - the vector to multiply in
     * @return the double value of the product

     */
    public double dotProduct(Vector v) {
        double u1 = _head._x.coord;
        double u2 = _head._y.coord;
        double u3 = _head._z.coord;

        double v1 = v._head._x.coord;
        double v2 = v._head._y.coord;
        double v3 = v._head._z.coord;

        return (u1 * v1 + u2 * v2 + u3 * v3);
    }

    /**
     * calculates and return the cross product of the current vector in a given vector
     * the way is to create a vector: (y1z2-z1y2, z1x2-x1z2, x1y2-y1x2)
     * @param v - the vector to multiply in
     * @return the vector value of the product
     */
    public Vector crossProduct(Vector v) {
        double u1 = _head._x.coord;
        double u2 = _head._y.coord;
        double u3 = _head._z.coord;

        double v1 = v._head._x.coord;
        double v2 = v._head._y.coord;
        double v3 = v._head._z.coord;

        return new Vector(new Point3D(
                u2 * v3 - u3 * v2,
                u3 * v1 - u1 * v3,
                u1 * v2 - u2 * v1
        ));
    }

    /**
     * calculate the squared length of the vector - it is the squared distance between (0,0,0) and the vector head
     * @return - squared length of vector
     */
    public double lengthSquared() {
        return ZERO.distanceSquared(this._head);
    }

    /**
     * calculate length of vector (norma)
     * @return norma
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * normalizes the vector - so its length will be 1.
     * the way is to divide all its coordinates in its norma.
     * @return - this normalized vector
     */
    public Vector normalize() {
        this._head = new Point3D(
                _head._x.coord / length(),
                _head._y.coord / length(),
                _head._z.coord / length()
        );
        return this;
    }

    /**
     * normalizes the vector - so its length will be 1.
     * the way is to divide all its coordinates in its norma.
     * @return - a new normalized vector
     */
    public Vector normalized() {
        return new Vector(normalize()._head);
    }

    //getter
    public Point3D getHead() {
        return _head;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Objects.equals(_head, vector._head);
    }

    @Override
    public String toString() {
        return "Vector " + _head;
    }
}
