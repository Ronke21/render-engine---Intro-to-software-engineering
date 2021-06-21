package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * class represents a light source in a shape  of spotlight
 * in order to get the same light intensity, we need to increase the initial intensity of the light source
 * class Spot Light defined by position, direction and light intensity.
 * it's a kind of point light so we choose to extend it from pointLight.
 */
public class SpotLight extends PointLight {
    /**
     *  direction - the direction of the light
     *  focus - the narrowing factor, the beam gets narrower and weaker as it gets bigger.
     */
    private final Vector _direction;
    /**
     * focus size
     */
    private double _focus = 1;

    /**
     * constructor
     *
     * @param color    - the color of the light source
     * @param position - the point which the light is being emitted from
     * @param direction direction of light
     */
    public SpotLight(Color color, Point3D position, Vector direction) {
        super(color, position);
        _direction = direction.normalized();
    }

    /**
     * setter - chaining method design pattern
     *
     * uses Math.max to make sure we get a valid value.
     * a value lower than 1 is not valid since it will increase the intensity instead of decreasing it!
     *
     * @param focus - the opening angle of the beam
     * @return the updated SpotLight
     */
    public SpotLight setFocus(double focus) {
        _focus = Math.max(focus, 1);
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
        Vector L = getL(p);

        // we use pow because in order to make the light intensity lower,
        // we want to increase the attenuation in any direction, to make sure that as far as we get
        // from the center of the beam, the light will get weaker, which will create the beam effect
        double distance = Math.pow(_direction.dotProduct(L), _focus);

        return super.getIntensity(p).scale(Math.max(0, distance));
    }
}
