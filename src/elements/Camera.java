package elements;

import primitives.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Camera {

    /**
     * @member _P0 - the camera location
     * @member _Vup - Y axis vector
     * @member _Vto - X axis vector
     * @member _Vright - Z axis vector
     * @member _apertureSize - determines how much the aperture will be open in our camera
     * (smaller = more objects in focus, bigger = less objects in focus, stronger blurring)
     * @member _focalDistance - the distance between the view plane and the focal plane
     * (focal plane, the second imaginary plane, in which the beam rays emitted from the matching pixel in the view plane are passing through)
     * @member _numberOfRays - the number of rays emitted from the view plane towards the focal plane
     * @member _DOF - boolean value to set whether the camera has depth of field (bokeh) effect of not
     * @member _width - object's actual width
     * @member _height - object's actual height
     * @member _distance - object's actual distance from the camera center
     */

    private Point3D _P0;
    private Vector _Vup;
    private Vector _Vto;
    private Vector _Vright;

    // params for DOF effect
    private double _apertureSize;
    private double _focalDistance;
    private int _numberOfRays;
    private boolean _DOF;

    private double _width;
    private double _height;
    private double _distance;

    /**
     * simple constructor
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
     * more complex constructor for DOF
     *
     * @param p0           - the camera location
     * @param Vto          - Y axis vector
     * @param Vup          - X axis vector
     * @param apertureSize - the radius of the aperture
     * @param focalLength  - the distance between the view plane and the focal plane
     * @param numberOfRays - number of rays
     */
    public Camera(Point3D p0, Vector Vto, Vector Vup, double apertureSize, double focalLength, int numberOfRays, boolean dof) {

        this(p0, Vto, Vup);

        _apertureSize = apertureSize;
        _focalDistance = focalLength;
        _numberOfRays = numberOfRays;
        _DOF = dof;
    }

    /**
     * getter
     *
     * @return p0
     */
    public Point3D getP0() {
        return _P0;
    }

    /**
     * getter
     *
     * @return Vup
     */
    public Vector getVup() {
        return _Vup;
    }

    /**
     * getter
     *
     * @return Vto
     */
    public Vector getVto() {
        return _Vto;
    }

    /**
     * getter
     *
     * @return Vright
     */
    public Vector getVright() {
        return _Vright;
    }

    /**
     * getter
     *
     * @return aperture Size
     */
    public double get_apertureSize() {
        return _apertureSize;
    }

    /**
     * getter
     *
     * @return focal distance
     */
    public double get_focalDistance() {
        return _focalDistance;
    }

    /**
     * getter
     *
     * @return number Of rays
     */
    public int get_numberOfRays() {
        return _numberOfRays;
    }

    /**
     * getter
     *
     * @return whether the DOF effect is activated or not
     */
    public boolean is_DOF() {
        return _DOF;
    }

    /**
     * getter
     *
     * @return the width of the view plane
     */
    public double get_width() {
        return _width;
    }

    /**
     * getter
     *
     * @return the height of the view plane
     */
    public double get_height() {
        return _height;
    }

    /**
     * getter
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
     * @param nX - amount of rows in view plane (number of pixels)
     * @param nY - amount of columns in view plane (number of pixels)
     * @param j  - X's index
     * @param i  - Y's index
     * @return - the list of rays which goes from the pixel through the focal plane
     */
    public List<Ray> constructRaysThroughPixel(int nX, int nY, int j, int i) {

        // the returned list of rays
        List<Ray> _rays = new ArrayList<>();

        // add the center ray to the list
        _rays.add(constructRayThroughPixel(nX, nY, j, i));

        // calculate the actual size of a pixel
        // pixel height is the division of the view plane height in the number of rows of pixels
        double pixelHeight = alignZero(_height / nY);   //  Ry = h/Ny
        // pixel width is the division of the view plane width in the number of columns of pixels
        double pixelWidth = alignZero(_width / nX);   //  Rx = w/Nx

        // if more then one ray is emitted (DOF effect)
        if (_numberOfRays != 1) {
            List<Ray> temp_rays = new LinkedList<>();
            // apertureSize is the value of how many pixels it spreads on
            double apertureRadius = Math.sqrt(_apertureSize * (pixelHeight * pixelWidth)) / 2d;
            for (Ray ray : _rays) {
                // creating list of focal rays (from the aperture on the view plane to the point on the focal plane)
                temp_rays.addAll(ray.randomRaysInCircle(ray.getP0(), _Vup, _Vright, apertureRadius, _numberOfRays, _focalDistance));
            }
            // the original rays included in the temp rays
            _rays = temp_rays;
        }
        return _rays;
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
     * @return
     */
    public Camera turnCamera(Vector axis, double theta) {
        if (theta == 0) return this;
        this._Vup.rotateVector(axis, theta);
        this._Vright.rotateVector(axis, theta);
        this._Vto.rotateVector(axis, theta);
        return this;
    }
}
