package elements;

import primitives.Color;

/**
 * Ambient Light Color
 */
public class AmbientLight extends Light {
    /**
     * intensity of ambient light color
     */
//    final private Color _intensity;

    /**
     * Constructor
     *
     * @param Ia intensity color
     * @param Ka constant for intensity
     */
    public AmbientLight(Color Ia, double Ka) {
        super(Ia.scale(Ka));
    }

    /**
     * get intensity color
     *
     * @return intensity
     */
//    public Color getIntensity() {
//        return _intensity;
//    }

    @Override
    public String toString() {
        return "AmbientLight{" +
                "_intensity=" + getIntensity() +
                '}';
    }
}