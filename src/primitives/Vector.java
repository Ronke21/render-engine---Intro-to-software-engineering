package primitives;

import java.util.Objects;

import static primitives.Point3D.ZERO;

public class Vector {
    Point3D _head;

    public Vector(Coordinate x, Coordinate y, Coordinate z) {
        this(x.coord, y.coord, z.coord);
    }

    public Vector(double x, double y, double z) {
        Point3D head = new Point3D(x, y, z);
        if (head.equals(ZERO)) {
            throw new IllegalArgumentException("Vector head cannot be Point(0,0,0)");
        }
        _head = head;
    }

    public Vector(Point3D head) {
        if (head.equals(ZERO)) {
            throw new IllegalArgumentException("Vector head cannot be Point(0,0,0)");
        }
        _head = head;
    }

    public Vector add(Vector v) {
        return new Vector(
                _head._x.coord + v._head._x.coord,
                _head._y.coord + v._head._y.coord,
                _head._z.coord + v._head._z.coord
        );
    }

    public Vector subtract(Vector v) {
        return add(new Vector(
                -1 * v._head._x.coord,
                -1 * v._head._y.coord,
                -1 * v._head._z.coord
        ));
    }

    public Vector scale(double mul) {
        return new Vector(
                _head._x.coord * mul,
                _head._y.coord * mul,
                _head._z.coord * mul
        );
    }

    public double dotProduct(Vector v) {
        double u1 = _head._x.coord;
        double u2 = _head._y.coord;
        double u3 = _head._z.coord;

        double v1 = v._head._x.coord;
        double v2 = v._head._y.coord;
        double v3 = v._head._z.coord;

        return (u1 * v1 + u2 * v2 + u3 * v3);

    }

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

    public double lengthSquared() {
        return ZERO.distanceSquared(this._head);
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public Vector normalize() {
        this._head = new Point3D(
                _head._x.coord / length(),
                _head._y.coord / length(),
                _head._z.coord / length()
        );
        return this;
    }

    public Vector normalized() {
        return new Vector(normalize()._head);
    }

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
