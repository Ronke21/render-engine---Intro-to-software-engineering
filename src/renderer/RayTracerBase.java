package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;


/**
 * class used to trace rays for the rendering engine
 */
public abstract class RayTracerBase {

    protected Scene _scene;

    /**
     * constructor
     * @param scene
     */
    public RayTracerBase(Scene scene) {
        _scene = scene;
    }

    /**
     * abstract function to determine the color of a pixel
     * @param ray
     * @return the color of the pixel intersects the given ray
     */
    public abstract Color traceRay(Ray ray);

}