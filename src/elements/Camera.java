package elements;

import primitives.*;

import static primitives.Util.isZero;

public class Camera {

    final private Point3D _P0;
    final private Vector _vUP;
    final private Vector _vTO;
    final private Vector _vRIGHT;

    private double _width;
    private double _height;
    private double _distance;

    public Camera(Point3D p0, Vector vTO, Vector vUP) {

        if (!isZero(vUP.dotProduct(vTO))) {
            throw new IllegalArgumentException("up vector and  to vector arent orthogonal");
        }

        _P0 = p0;
        _vUP = vUP.normalized();
        _vTO = vTO.normalized();
        _vRIGHT = _vTO.crossProduct(_vUP);
    }

    public Point3D getP0() {
        return _P0;
    }

    public Vector getvUP() {
        return _vUP;
    }

    public Vector getvTO() {
        return _vTO;
    }

    public Vector getvRIGHT() {
        return _vRIGHT;
    }

//    public double getWidth() {
//        return _width;
//    }
//
//    public double getHeight() {
//        return _height;
//    }

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

//    public double getDistance() {
//        return _distance;
//    }

    /**
     * @param nX - amount of rows in view plane (number of pixels)
     * @param nY - amount of columns in view plane (number of pixels)
     * @param j - X's index
     * @param i - Y's index
     * @return - the ray which goes through the pixel
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {

        Point3D Pc = _P0.add(_vTO.scale(_distance));

        double Rx = _width / nX;
        double Ry = _height / nY;

        Point3D Pij = Pc;
        double Yi = -Ry * (i - (nY - 1) / 2d);
        double Xj = Rx * (j - (nX - 1) / 2d);

        if (!isZero(Xj)) {
            Pij = Pij.add(_vRIGHT.scale(Xj));
        }
        if (!isZero(Yi)) {
            Pij = Pij.add(_vUP.scale(Yi));
        }
        Vector Vij = Pij.subtract(_P0);

        return new Ray(_P0, Vij);
    }
}
