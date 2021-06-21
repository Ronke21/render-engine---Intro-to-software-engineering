package elements;

import primitives.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 *
 * Class Camera represent view of the geometric world through the view plane (which represent the picture),
 * Through the view plane the camera captures the geometric world.
 * it produces graphic views of the objects using the view plane and rays and object intersections.
 * The rays converge according to the location of the pixel centers in the view plane.
 * directions of the camera to the right, up and front  (compared to the original x,y,z axis),
 * all vectors orthogonal to each other
 */
public class Camera {



    /**
     * _P0 - the camera location
     */
    private Point3D _P0;
    /**
     * _Vup - Y axis vector
     */
    private Vector _Vup;
    /**
     * X axis vector
     */
    private Vector _Vto;
    /**
     * Z axis vector
     */
    private Vector _Vright;
    /**
     * object's actual width
     */
    private double _width;
    /**
     * object's actual height
     */
    private double _height;
    /**
     * object's actual distance from the camera center
     */
    private double _distance;

    // *************************** params for DOF effect ******************************************
    /**
     * the size of the aperture surface (located in the camera location), the size is relative to a single pixel, example: 1 = one pixel, and so on.
     * the smaller the aperture the greater the width of the sharpness, (similar to blind view without glasses)
     * (smaller = more objects in focus, bigger = less objects in focus, stronger blurring)
     */
    private double _apertureSize;
    /**
     * the distance between the camera's beginning location to the center of focal plane,
     * forward vision becomes sharper in the distance, the more it's close to the focal plane
     * (focal plane, the second imaginary plane, in which the beam rays emitted from the matching pixel in the view plane are passing through)
     */
    private double _focalDistance;
    /**
     * number of rays in the focal plane of the camera (per pixel),
     * the more rays in focal plane the more quality of the blurring
     */
    private int _numberOfRaysInAperture;
    /**
     * boolean value to set whether the camera has depth of field (bokeh) effect of not
     */
    private boolean _DOF;


    // ****************************************** params for AA effect ******************************************
    /**
     * number of rays in a single pixel for active super sampling
     */
    private int _numberOfRaysInPixel;
    /**
     * bolean value to determine anti aliasing
     */
    private boolean _AA;



    /**
     * simple Camera constructor which get as input location point and two orthogonal vectors represent the direction
     *
     * @param p0  - the camera location
     * @param Vto - Y axis vector
     * @param Vup - X axis vector
     */
    public Camera(Point3D p0, Vector Vto, Vector Vup) {

        if (!isZero(Vup.dotProduct(Vto))) {
            throw new IllegalArgumentException("up vector and  to vector arent orthogonal");
        }

        _P0 = p0;
        _Vto = Vto.normalized();
        _Vup = Vup.normalized();

        // _Vright is always the cross product of _Vto and _Vup since they are orthogonal by definition
        _Vright = _Vto.crossProduct(_Vup);
    }


    /**
     * get the location point of camera
     *
     * @return p0
     */
    public Point3D getP0() {
        return _P0;
    }

    /**
     * get the upper vector of the camera (represent the y axis of camera, position of the view)
     *
     * @return Vup
     */
    public Vector getVup() {
        return _Vup;
    }

    /**
     * get the forward vector of the camera (represent the z axis of camera, forward direction of the view)
     *
     * @return Vto
     */
    public Vector getVto() {
        return _Vto;
    }

    /**
     * get the right vector of the camera (represent the x axis of camera, position of the view)
     *
     * @return Vright
     */
    public Vector getVright() {
        return _Vright;
    }

    /**
     * get the aperture surface's (per pixel) size, value relative to a single pixel
     *
     * @return aperture Size
     */
    public double get_apertureSize() {
        return _apertureSize;
    }

    /**
     * get the focal distance from the camera to the focal plane (the area of focus)
     *
     * @return focal distance
     */
    public double get_focalDistance() {
        return _focalDistance;
    }

    /**
     * get the number of rays in the aperture plane per single pixel
     *
     * @return number Of rays
     */
    public int get_numberOfRaysInAperture() {
        return _numberOfRaysInAperture;
    }

    /**
     * get if DOF is activated in camera
     *
     * @return whether the DOF effect is activated or not
     */
    public boolean is_DOF() {
        return _DOF;
    }

    /**
     * get if AA is activated in camera
     *
     * @return whether the AA effect is activated or not
     */
    public boolean is_AA() {
        return _AA;
    }

    /**
     * get width of camera
     *
     * @return the width of the view plane
     */
    public double get_width() {
        return _width;
    }

    /**
     * get height of camera
     *
     * @return the height of the view plane
     */
    public double get_height() {
        return _height;
    }

    /**
     * get distance of camera from scene
     *
     * @return the object's actual distance from the camera center
     */
    public double get_distance() {
        return _distance;
    }

    /**
     * setter - chaining method
     *
     * @param width   - the width of the view plane
     * @param height- the height of the view plane
     * @return the camera with the configured view plane
     */
    public Camera setViewPlaneSize(double width, double height) {
        _height = height;
        _width = width;
        return this;
    }

    /**
     * setter - chaining method
     *
     * @param distance - the object's actual distance from the camera center
     * @return the camera with the configured distance
     */
    public Camera setDistance(double distance) {
        _distance = distance;
        return this;
    }

    /**
     * setter - chaining method
     *
     * @param apertureSize - camera's aperture size
     * @return the camera with the configured aperture
     */
    public Camera setApertureSize(double apertureSize) {
        _apertureSize = apertureSize;
        return this;
    }

    /**
     * setter - chaining method
     *
     * @param focalDistance -  the distance between the view plane and the focal plane
     * @return the camera with the configured distance
     */
    public Camera setFocalDistance(double focalDistance) {
        _focalDistance = focalDistance;
        return this;
    }

    /**
     * setter - chaining method
     *
     * @param numberOfRays - number of rays in the aperture
     * @return the camera with the configured number of rays
     */
    public Camera setNumberOfRaysInAperture(int numberOfRays) {
        _numberOfRaysInAperture = numberOfRays;
        return this;
    }

    /**
     * setter - chaining method
     *
     * @param DOF - does the picture has depth of field
     * @return the camera with the configured DOF
     */
    public Camera set_DOF(boolean DOF) {
        _DOF = DOF;
        return this;
    }

    /**
     * setter - chaining method
     *
     * @param numberOfRaysInPixel - number of rays in a pixel
     * @return the camera with the configured number of rays
     */
    public Camera setNumberOfRaysInPixel(int numberOfRaysInPixel) {
        _numberOfRaysInPixel = numberOfRaysInPixel;
        return this;
    }

    /**
     * setter - chaining method
     *
     * @param AA - does the picture has Anti Aliasing
     * @return the camera with the configured AA
     */
    public Camera setAA(boolean AA) {
        _AA = AA;
        return this;
    }

    /**
     * this function gets the view plane size and a selected pixel,
     * and return the ray from the camera which intersects this pixel
     *
     * @param nX - amount of rows in view plane (number of pixels)
     * @param nY - amount of columns in view plane (number of pixels)
     * @param j  - X's index
     * @param i  - Y's index
     * @return - the ray which goes through the pixel
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {

        Point3D Pc = _P0.add(_Vto.scale(_distance)); // image center

        // ratio (pixel width and height)
        double Rx = _width / nX;
        double Ry = _height / nY;

        Point3D Pij = Pc;
        double Yi = -Ry * (i - (nY - 1) / 2d);
        double Xj = Rx * (j - (nX - 1) / 2d);

        if (!isZero(Xj)) {
            Pij = Pij.add(_Vright.scale(Xj));
        }
        if (!isZero(Yi)) {
            Pij = Pij.add(_Vup.scale(Yi));
        }

        Vector Vij = Pij.subtract(_P0); // vector to pixel center

        return new Ray(_P0, Vij);
    }

    /**
     * this function gets the view plane size and a selected pixel,
     * and return the rays from the view plane which intersects the focal plane
     *
     * @param nX - amount of columns in view plane (number of pixels)
     * @param nY - amount of rows in view plane (number of pixels)
     * @param j  - X's index
     * @param i  - Y's index
     * @return - the list of rays which goes from the pixel through the focal plane
     */
    public List<Ray> constructRaysThroughPixel(int nX, int nY, int j, int i) {

        // the returned list of rays
        List<Ray> rays = new ArrayList<>();

        // add the center ray to the list
        Ray centerRay = constructRayThroughPixel(nX, nY, j, i);
        rays.add(centerRay);

        // calculate the actual size of a pixel
        // pixel height is the division of the view plane height in the number of rows of pixels
        double pixelHeight = alignZero(_height / nY);   //  Ry = h/Ny
        // pixel width is the division of the view plane width in the number of columns of pixels
        double pixelWidth = alignZero(_width / nX);   //  Rx = w/Nx

        if (_numberOfRaysInPixel != 1) {
            rays.addAll(centerRay.randomRaysInGrid(
                    _Vup,
                    _Vright,
                    _numberOfRaysInPixel,
                    _distance,
                    pixelWidth,
                    pixelHeight)
            );
        }

        // if more then one ray is emitted (DOF effect)
        if (_numberOfRaysInAperture != 1) {
            List<Ray> temp_rays = new LinkedList<>();
            // apertureSize is the value of how many pixels it spreads on
            double apertureRadius = Math.sqrt(_apertureSize * (pixelHeight * pixelWidth)) / 2d;
            for (Ray ray : rays) {
                // creating list of focal rays (from the aperture on the view plane to the point on the focal plane)
                temp_rays.addAll(ray.randomRaysInCircle(ray.getP0(), _Vup, _Vright, apertureRadius, _numberOfRaysInAperture, _focalDistance));
            }
            // the original rays included in the temp rays
            rays = temp_rays;
        }

        return rays;
    }


    /**
     * function to set new camera location
     *
     * @param x - added amount to the x coordinates
     * @param y - added amount to the y coordinates
     * @param z - added amount to the z coordinates
     * @return the camera in its new position
     */

    public Camera moveCamera(double x, double y, double z) {

        this._P0 = new Point3D(_P0.getX() + x, _P0.getY() + y, _P0.getZ() + z);

        return this;
    }


    /**
     * function to set new direction vectors to the camera
     *
     * @param Vup - new Vup vector
     * @param Vto - n
     * @return the camera after the rotation
     */
    public Camera turnCamera(Vector Vto, Vector Vup) {
        Camera newcam = new Camera(this._P0, Vto, Vup);
        return newcam;
    }

    /**
     * function to set new direction vectors to the
     * camera according to a rotating axis
     *
     * @param axis  - turning axis
     * @param theta - angle to turn the camera
     * @return this instance
     */
    public Camera turnCamera(Vector axis, double theta) {
        if (theta == 0) return this;
        this._Vup.rotateVector(axis, theta);
        this._Vright.rotateVector(axis, theta);
        this._Vto.rotateVector(axis, theta);
        return this;
    }
}
