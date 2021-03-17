package primitives;

import java.util.Objects;

/**
 * This class represents a ray in the space = a vector that doesn't start in (0,0,0).
 * has a starting point p0 and a vector from it to the edge of ray.
 */
public class Ray {
    /**
     * @member _dir - the point the Ray points to from p0
     * @member _p0 - starting point of Ray
     */
    final Point3D _p0;
    final Vector _dir;

    /**
     * constructor for ray. receives starting point and vector and sets them. sets the vector normalized
     *
     * @param p0  - starting point
     * @param dir - direction vector
     */
    public Ray(Point3D p0, Vector dir) {
        _p0 = p0;
        _dir = dir.normalized();
    }

    /**
     * getter
     */
    public Point3D getP0() {
        return _p0;
    }

    /**
     * getter
     */
    public Vector getDir() {
        return _dir;
    }

    /**
     * function to get a point on the ray which its coordinates are scaled by a number (t)
     *
     * @param t (scaler on the direction)
     * @return point on the ray which its coordinates are scaled by a number (t)
     */
    public Point3D getPoint(double t) {
        if (t <= 0) {
            throw new IllegalArgumentException("t must be bigger than 0");
        }
        Vector v = _dir;
        Point3D p = _p0.add(v.scale(t)); // P = P0 + t * v
        return p;
    }

    @Override
    public String toString() {
        return "Ray : (" + _p0 + ") ,(" + _dir._head + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return Objects.equals(_p0, ray._p0) && Objects.equals(_dir, ray._dir);
    }

}
