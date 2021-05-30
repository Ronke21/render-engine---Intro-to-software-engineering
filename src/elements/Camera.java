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
     * @member _width - object's actual width
     * @member _height - object's actual height
     * @member _distance - object's actual distance from the camera center
     */

    private Point3D _P0;
    private Vector _Vup;
    private Vector _Vto;
    private Vector _Vright;

    private double _apertureSize;
    private double _focalDistance;
    private int _numberOfRays;
    private boolean DOF;

    private double _width;
    private double _height;
    private double _distance;

    /**
     * constructor for basic ray tracer
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
     * constructor for advanced ray tracer
     *
     * @param p0           - the camera location
     * @param Vto          - Y axis vector
     * @param Vup          - X axis vector
     * @param apertureSize - the radius of the aperture
     * @param focalLength  - the distance between the view plane and the focal plane
     * @param numberOfRays - number of rays
     */
    public Camera(Point3D p0, Vector Vto, Vector Vup, double apertureSize, double focalLength, int numberOfRays) {

        if (!isZero(Vup.dotProduct(Vto))) {
            throw new IllegalArgumentException("up vector and  to vector arent orthogonal");
        }

        _P0 = p0;
        _Vto = Vto.normalized();
        _Vup = Vup.normalized();
        _apertureSize = apertureSize;
        _focalDistance = focalLength;
        _numberOfRays = numberOfRays;

        // _Vright is always the cross product of _Vto and _Vup since they are orthogonal by definition
        _Vright = _Vto.crossProduct(_Vup);
    }

    public Point3D getP0() {
        return _P0;
    }

    public Vector getVup() {
        return _Vup;
    }

    public Vector getVto() {
        return _Vto;
    }

    public Vector getVright() {
        return _Vright;
    }


    //chaining methods
    public Camera setViewPlaneSize(double width, double height) {
        _height = height;
        _width = width;
        return this;
    }

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
     * and return the ray from the camera which intersects this pixel
     *
     * @param nX - amount of rows in view plane (number of pixels)
     * @param nY - amount of columns in view plane (number of pixels)
     * @param j  - X's index
     * @param i  - Y's index
     * @return - the ray which goes through the pixel
     */
    public List<Ray> constructRaysThroughPixel(int nX, int nY, int j, int i, double distanceFromScreen, double screenWidth, double screenHeight) {

        List<Ray> _rays = new ArrayList<>();

        Ray center_ray = constructRayThroughPixel(nX, nY, j, i);
        if (_numberOfRays == 1) {
            _rays.add(center_ray);
        }
        double rY = alignZero(screenHeight / nY);   //  Ry = h/Ny
        double rX = alignZero(screenWidth / nX);   //  Rx = w/Nx

        if (_numberOfRays != 1) {
            List<Ray> temp_rays = new LinkedList<>();
            // apertureSize is the value of how many pixels it spreads on
            double apertureRadius = Math.sqrt(_apertureSize * (rY * rX)) / 2d;
            for (Ray ray : _rays) {
                // creating list of focal rays (from the aperture plane towards the focal point)
                temp_rays.addAll(ray.randomRaysInCircle(ray.getP0(), _Vup, _Vright, apertureRadius, _numberOfRays, _focalDistance));
                _rays = temp_rays; // the original rays included in the temp rays
            }
        }
        return _rays;
    }



    /**
     * function to set new camera location
     *
     * @param x -the new x coordinate
     * @param y -
     * @param z -
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
