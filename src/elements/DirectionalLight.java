package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * class represents a light source with a known  direction, no position! (e.g. the sun)
 */
public class DirectionalLight extends Light implements LightSource {

    private Vector direction;

    /**
     * constructor
     *
     * @param color     - the color of the light
     * @param direction - the direction of the light beam
     */
    public DirectionalLight(Color color, Vector direction) {
        super(color);
        this.direction = direction;
    }

    /**
     * function calculates the color of the light in a given point in the 3D space
     *
     * @param p - the point which we want to know what the color is in
     * @return the light color in p
     */
    @Override
    public Color getIntensity(Point3D p) {
        return getIntensity(); // No attenuation with distance
    }

    /**
     * function to get the ray from the light source to the given point
     *
     * @param p - the ray's destination point
     * @return the ray - the normalized(p - pL)
     */
    //todo : make sure we understood correctly (since distance is infinite)
    @Override
    public Vector getL(Point3D p) {
        return direction.normalized();
    }
}
