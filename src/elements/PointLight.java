package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * class represents a light source with a known position, emits light to all directions (e.g. light bulb)
 */
public class PointLight extends Light implements LightSource {

    private Point3D position;
    private double kC = 1;
    private double kL = 0;
    private double kQ = 0;

    /**
     * constructor
     *
     * @param color    - the color of the light source
     * @param position - the point which the light is being emitted from
     * @param kC       - attenuation factor
     * @param kL       - attenuation factor
     * @param kQ       - attenuation factor
     */
    public PointLight(Color color, Point3D position, double kC, double kL, double kQ) {
        super(color);
        this.position = position;
        this.kC = kC;
        this.kL = kL;
        this.kQ = kQ;
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
        return getIntensity().reduce(kC + kL * dist + kQ * dist * dist);
    }

    /**
     * function to get the ray from the light source to the given point
     *
     * @param p - the ray's destination point
     * @return the ray - the normalized(p - pL)
     */
    @Override
    public Vector getL(Point3D p) {
        return (p.subtract(position)).normalized();
    }
}
