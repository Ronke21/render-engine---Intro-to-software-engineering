package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

public abstract class RayTracerBase {

    protected Scene _scene;

    public RayTracerBase(Scene scene) {
        _scene = scene;
    }
///////////////////////////////לתעד.........................
    public abstract Color traceRay(Ray ray);

}