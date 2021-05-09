package elements;

import primitives.Color;

/**
 * Ambient Light Color
 */
public class AmbientLight extends Light {

    /**
     * Constructor
     *
     * @param Ia intensity color
     * @param Ka constant for intensity
     */
    public AmbientLight(Color Ia, double Ka) {
        super(Ia.scale(Ka));
    }

    public AmbientLight() {
        super(Color.BLACK);
    }

    @Override
    public String toString() {
        return "AmbientLight{" +
                "_intensity=" + getIntensity() +
                '}';
    }
}