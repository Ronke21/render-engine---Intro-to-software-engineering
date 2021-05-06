package elements;

import primitives.*;

import static primitives.Util.isZero;

public class Camera {

    /**
     * @member _P0 - the camera location
     * @member _Vup - Y axis vector
     * @member _Vto - X axis vector
     * @member _Vright - Z axis vector
     *
     * @member _width - object's actual width
     * @member _height - object's actual height
     * @member _distance - object's actual distance from the camera center
     */

    private Point3D _P0;
    private Vector _Vup;
    private Vector _Vto;
    private Vector _Vright;

    private double _width;
    private double _height;
    private double _distance;

    /**
     * constructor
     *
     * @param p0 - the camera location
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
     *
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
     * function to set new camera location
     *
     * @param up    add to _vUp vector
     * @param to    add to _vTo vector
     * @param right add to _vRight vector
     * @return the camera in its new position
     */

    public Camera moveCamera(double up, double to, double right) {

        if (!isZero(up)) {
            this._P0.add(_Vup.scale(up));
        }

        if (!isZero(to)) {
            this._P0.add(_Vto.scale(to));
        }

        if (!isZero(right)) {
            this._P0.add(_Vright.scale(right));
        }

        return this;
    }


    // TODO: check if vector needs to be rotated by angle
    // TODO: vector rotation : https://stackoverflow.com/questions/31225062/rotating-a-vector-by-angle-and-axis-in-java\

    /**
     * function to set new direction vectors to the camera
     *
     * @param Vup    - new Vup vector
     * @param Vto    - n
     * @param Vright - new Vright vector
     * @return the camera after the rotation
     */
    public Camera turnCamera(Vector Vup, Vector Vto, Vector Vright) {

        this._Vup = Vup;
        this._Vto = Vto;
        this._Vright = Vright;

        return this;
    }
}
