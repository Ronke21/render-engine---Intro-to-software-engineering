package primitives;

import java.util.List;
import java.util.Objects;
import geometries.Intersectable.GeoPoint;

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

        Vector V = _dir;
        Point3D p = _p0.add(V.scale(t)); // P = P0 + Vt
        return p;
    }

    /**
     * find the closest Point to Ray
     *
     * @param points3DList List of intersections point
     * @return the closest point
     */
    public Point3D findClosestPoint(List<Point3D> points3DList) {
        double distance = Double.POSITIVE_INFINITY;
        Point3D nearPoint = null;

        if (points3DList == null) {
            return null;
        }

        for (Point3D p : points3DList) {
            double dis = p.distance(_p0); // distance from the starting point of the ray
            if (dis < distance) {
                distance = dis;
                nearPoint = p;
            }
        }

        return nearPoint;
    }

    /**
     * find the closest GeoPoint to Ray
     *
     * @param GeoPointList List of intersections point
     * @return the closest point
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> GeoPointList) {
        double distance = Double.POSITIVE_INFINITY;
        GeoPoint nearPoint = null;

        if (GeoPointList == null) {
            return null;
        }

        for (GeoPoint p : GeoPointList) {
            double dis = p.point.distance(_p0); // distance from the starting point of the ray
            if (dis < distance) {
                distance = dis;
                nearPoint = p;
            }
        }

        return nearPoint;
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
