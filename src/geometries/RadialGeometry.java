package geometries;

/**
 * this class represents all kinds of radial shapes - sphere, tube, cylinder
 * contains the radius for the shape
 */
public abstract class RadialGeometry extends Geometry{

    /**
     * @member _radius - the radius of the shape
     */
    final double _radius;

    /**
     * constructor to set radius
     *
     * @param radius - radius of shape
     */
    public RadialGeometry(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("radius can't be negative");
        }
        _radius = radius;
    }

    /**
     * getter to radius of geometry
     * @return  radius
     */
    public double getRadius() {
        return _radius;
    }
}
