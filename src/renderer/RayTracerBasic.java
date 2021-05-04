package renderer;

import geometries.Intersectable.*;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * class used to trace rays for the rendering engine
 */
public class RayTracerBasic extends RayTracerBase {

    /**
     * constructor
     *
     * @param scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * abstract function to determine the color of a pixel
     *
     * @param ray
     * @return the color of the pixel intersects the given ray
     */
    @Override
    public Color traceRay(Ray ray) {

        List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(ray);

        if (intersections == null || intersections.size() == 0) {
            return _scene.background;
        } else {
            GeoPoint closest = ray.findClosestGeoPoint(intersections);
            return calcColor(closest);
        }
    }

    /**
     * function which returns the color of the object the ray is intersecting
     * if no intersection was found, returns the ambient light's color
     *
     * @param point - the point on the 3D model
     * @return the color in the point
     */
    private Color calcColor(GeoPoint point) {

        return _scene.ambientLight.getIntensity().add(point.geometry.getEmission());

    }

}
