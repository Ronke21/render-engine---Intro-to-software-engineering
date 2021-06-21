package elements;

import primitives.Color;

/**
 * this Abstract Class represents a basic element which is defined by the intensity of colors of the lighting,
 * Refers to the light propagation model.
 * represents all types of light sources in the scene e.g. spotlight, point light etc.
 * its function is to illuminate the objects with colors in different ways.
 */
abstract class Light {

    /**
     * the color of the light, the intensity of RGB colors
     */
    private Color intensity = Color.WHITE;

    /**
     * Light constructor accepting the intensity of colors value
     * creates the light's intensity
     *
     * @param color - the color of the light
     */
    protected Light(Color color) {
        this.intensity = color;
    }

    /**
     * getter for Light intensity
     *
     * @return the color of this instance
     */
    public Color getIntensity() {
        return intensity;
    }
}
