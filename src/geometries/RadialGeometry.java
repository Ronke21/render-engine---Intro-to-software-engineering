package geometries;

/**
 * this class represents all kinds of radial shapes - sphere, tube, cylinder
 * contains the radius for the shape
 */
public abstract class RadialGeometry {

    /**
     * @member _radius - the radius of the shape
     */
    final double _radius;

    /**
     * constructor.
     *
     * @param radius - radius of shape
     */
    public  RadialGeometry(double radius) {
        if (radius<=0) {
            throw new IllegalArgumentException("radius can't be negative");
        }
        _radius = radius;
    }

    /**
     * getter
     */
    public double getRadius() {
        return _radius;
    }
}
