package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * class represents a light source with a known position, emits light to all directions (e.g. light bulb)
 */
public class PointLight extends Light implements LightSource {

    protected Point3D position;
    protected double kC = 1;
    protected double kL = 0;
    protected double kQ = 0;

    /**
     * constructor
     *
     * @param color    - the color of the light source
     * @param position - the point which the light is being emitted from
     */
    public PointLight(Color color, Point3D position) {
        super(color);
        this.position = position;
    }

    /**
     * setter - chaining method design pattern
     *
     * @param kC - attenuation factor
     * @return the light source after the change
     */
    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;

    }

    /**
     * setter - chaining method design pattern
     *
     * @param kL - attenuation factor
     * @return the light source after the change
     */
    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * setter - chaining method design pattern
     *
     * @param kQ - attenuation factor
     * @return the light source after the change
     */
    public PointLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;
    }

    /**
     * function calculates the color of the light in a given point in the 3D space
     *
     * @param p - the point which we want to know what the color is in
     * @return the light color in p
     */
    @Override
    public Color getIntensity(Point3D p) {
        double dist = p.distance(position);
        double distSquared = p.distanceSquared(position);
        return getIntensity().scale(1 / (kC + kL * dist + kQ * distSquared));
    }

    /**
     * function to get the ray from the light source to the given point
     *
     * @param p - the ray's destination point
     * @return the ray - the normalized(p - pL)
     */
    @Override
    public Vector getL(Point3D p) {
        return p.subtract(position).normalized();
    }
}
