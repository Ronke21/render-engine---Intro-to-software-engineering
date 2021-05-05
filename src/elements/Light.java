package elements;

import primitives.Color;

/**
 * this class represents all types of light sources in the scene e.g. spotlight, point light etc.
 *
 * @member color - the color of the light source
 */
abstract class Light {

    private Color intensity = Color.WHITE;

    /**
     * constructor
     * @param color - the color of the light
     */
    protected Light(Color color) {
        this.intensity = color;
    }

    /**
     * getter
     * @return the color of this instance
     */
    public Color getIntensity() {
        return intensity;
    }


}
