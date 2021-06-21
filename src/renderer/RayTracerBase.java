package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;


/**
 * class used to trace rays for the rendering engine
 */
public abstract class RayTracerBase {

    /**
     * scene to be rendered
     */
    protected Scene _scene;

    /**
     * constructor for the ray tracer
     * @param scene to be intersected
     */
    public RayTracerBase(Scene scene) {
        _scene = scene;
    }

    /**
     * abstract function to determine the color of a pixel
     * @param ray - ray to intersect
     * @return the color of the pixel intersects the given ray
     */
    public abstract Color traceRay(Ray ray);
}