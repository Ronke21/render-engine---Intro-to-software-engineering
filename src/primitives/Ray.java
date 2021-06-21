package primitives;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import geometries.Intersectable.GeoPoint;

import static primitives.Util.*;

/**
 * This class represents a ray in the space = a vector that doesn't start in (0,0,0).
 * has a starting point p0 and a vector from it to the edge of ray.
 */
public class Ray {
    /**
     * @member _dir - the point the Ray points to from p0
     */
    Point3D _p0;
    /**
     * @member _p0 - starting point of Ray
     */
    final Vector _dir;
    /**
     * DELTA - Constant value defining by how much we need to move the ray's starting point
     */
    private static final double DELTA = 0.1;

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
     * constructor for ray.
     * creates a new ray and moves its head in the
     * normal direction by the normal scaled by DELTA
     *
     * @param p0     - starting point
     * @param dir    - direction vector
     * @param normal - the normal defining the plane
     */
    public Ray(Point3D p0, Vector dir, Vector normal) {
        this(p0, dir); // activate the current instance constructor

        // make sure the normal and the direction are not orthogonal
        double nv = alignZero(normal.dotProduct(_dir));

        // if not orthogonal
        if (!isZero(nv)) {
            // create new vector to help move the head of
            // the vector to the correct position
            Vector fixVector = normal.scale(nv > 0 ? DELTA : -DELTA);
            // move the head of the vector in the right direction
            _p0 = p0.add(fixVector);
        }
    }

    /**
     * get starting point
     * @return starting point3d
     */
    public Point3D getP0() {
        return _p0;
    }

    /**
     * get direction of ray
     * @return direction
     */
    public Vector getDir() {
        return _dir;
    }

    /**
     * function to get a point on the ray which its coordinates are scaled by a number (t)
     *
     * @param t (scalar on the direction)
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

        // distance => distanceSquared
        // no need to activate the Math.sqrt function
        // distance is always a positive value,
        for (GeoPoint p : GeoPointList) {
            double dis = p.point.distanceSquared(_p0); // distance from the starting point of the ray
            if (dis < distance) {
                distance = dis;
                nearPoint = p;
            }
        }

        return nearPoint;
    }

    /**
     * auxiliary function to randomly scatter points within a circular surface.
     * returns a list of rays which related to the surface.
     *
     * @param center  - center point of the circular surface.
     * @param vUp     - upper vector of circular surface.
     * @param vRight  - right vector of circular surface.
     * @param radius  - radius of circular surface. (mostly aperture)
     * @param numRays - number of rays we create in the circular surface.
     * @param dist    - distance between the view plane and the focal plane
     * @return list of rays from the area of the aperture to the focal point
     */
    public List<Ray> randomRaysInCircle(Point3D center, Vector vUp, Vector vRight, double radius, int numRays, double dist) {
        List<Ray> rays = new LinkedList<>();

        rays.add(this); // including the original ray
        if (radius == 0) {
            // radius input zero means there's no circular surface.
            return rays;
        }

        Point3D focalPoint = getPoint(dist);

        for (int i = 1; i < numRays; ++i) {

            // min = -1, max = 1
            // which means the degree of the line which the points located (between 0 to Pie)
            double cosTheta = -1 + (Math.random() * 2);

            // using Pythagoras theorem, define the complement to cosTheta (the 'mashlim' in Hebrew)
            double sinTheta = Math.sqrt(1 - cosTheta * cosTheta);

            // min = -radius, max = +radius
            // if we get extreme value, it means the new p0 will be located on the diameter,
            // otherwise, somewhere in the middle of the circle
            double d = -radius + (Math.random() * (2 * radius));

            // Move from polar to Cartesian system:
            double x_move = d * cosTheta;
            double y_move = d * sinTheta;

            // define a new starting point for the new ray
            // start from the center of the circle
            Point3D newP0 = center;

            // if the x and y steps are not 0, move the point
            if (!isZero(x_move)) {
                newP0 = newP0.add(vRight.scale(x_move));
            }
            if (!isZero(y_move)) {
                newP0 = newP0.add(vUp.scale(y_move));
            }

            // the vectors normalized inside Ray constructor
//            if (_p0.equals(center)) {

            // add the ray from the new starting point to the focal point
            rays.add(new Ray(newP0, (focalPoint.subtract(newP0)))); // from surface


//            } else {
//                rays.add(new Ray(_p0, (newP0.subtract(_p0)))); // toward surface
//            }
        }
        return rays;
    }

    /**
     * auxiliary function to randomly scatter points within a rectangular surface.
     * returns a list of rays which relates to the surface.
     *
     * @param numRays     - number of rays we create in the rectangular surface.
     * @param vUp         - upper vector of rectangular surface.
     * @param vRight      - right vector of rectangular surface.
     * @param dist        - of the camera from the view plane
     * @param pixelWidth  - the width of a single pixel in view plane
     * @param pixelHeight - the height of a single pixel in view plane
     * @return list of rays from the area of the pixel to the scene
     */
    public List<Ray> randomRaysInGrid(Vector vUp, Vector vRight, int numRays, double dist, double pixelWidth, double pixelHeight) {
        List<Ray> rays = new LinkedList<>();

        // the starting point of the original vector
        Point3D p0 = _p0;

        // the center of the pixel
        Point3D pixelCenter = _p0.add(_dir.scale(dist));

        // calculate the maximal number of rays which we can compress in one dimension
        // if we don't make this calculation, we might add too many rays, which will decrease performance
        // if the sqrt is not an integer, it will be the floor value
        // (mostly, unless the number is x.99...99 with 15 9s or more, which will probably never happen)
        int raysInDimension = (int) Math.sqrt(numRays);

        // the size of one step from one ray to another in each dimension
        double xMove = pixelWidth / raysInDimension;
        double yMove = pixelHeight / raysInDimension;

        // the starting point is the right bottom corner of the pixel
        Point3D cornerOfPixel = pixelCenter
                .add(vRight.scale(pixelWidth / 2d))
                .add(vUp.scale(pixelHeight / 2d));

        Point3D newRayStartPoint;

        int sign = 1;
        Random rand = new Random();

        for (int i = 0; i < raysInDimension; ++i) {
            for (int j = 0; j < raysInDimension; ++j, sign *= -1) {

                newRayStartPoint = cornerOfPixel;

                // determine by how much we move the point in each iteration
                double randomMoveX = xMove / 2d + xMove * rand.nextDouble();
                double randomMoveY = yMove / 2d + yMove * rand.nextDouble();

                // move in the x dimension
                if (!isZero(i * randomMoveX)) {
                    newRayStartPoint = newRayStartPoint.add(vRight.scale((i) * randomMoveX));
                }

                // move in the y dimension
                if (!isZero(j * randomMoveY)) {
                    newRayStartPoint = newRayStartPoint.add(vUp.scale((j) * randomMoveY));
                }

//                // if we have reach the center point of the pixel
//                if (i == raysInDimension / 2 && j == i) {
//                    newRayStartPoint = pixelCenter;
//                }

                // make sure we do not add the canter ray more than once
                if (!newRayStartPoint.equals(pixelCenter)) {
                    rays.add(new Ray(p0, (newRayStartPoint.subtract(p0)))); // normalized inside Ray constructor
                }

            }
        }
        return rays;
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
