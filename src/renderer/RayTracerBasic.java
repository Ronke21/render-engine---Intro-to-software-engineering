package renderer;

import elements.*;
import geometries.Intersectable.*;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * class used to trace rays for the rendering engine
 */
public class RayTracerBasic extends RayTracerBase {

    /**
     * constructor
     *
     * @param scene - the scene the rays are sent to
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * abstract function to determine the color of a pixel
     *
     * @param ray - the ray sent from the light source to the scene
     * @return the color of the pixel intersects the given ray
     */
    @Override
    public Color traceRay(Ray ray) {

        List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(ray);

        if (intersections == null || intersections.size() == 0) {
            return _scene.background;
        } else {
            GeoPoint closest = ray.findClosestGeoPoint(intersections);
            return calcColor(closest, ray);
        }
    }

    /**
     * function which returns the color of the object the ray is intersecting
     * if no intersection was found, returns the ambient light's color
     *
     * @param point - the point on the 3D model
     * @param ray   - ray to the point
     * @return the color in the point
     */
    private Color calcColor(GeoPoint point, Ray ray) {

        return _scene.ambientLight.getIntensity()
                .add(point.geometry.getEmission())
                .add(calcLocalEffects(point, ray));
    }

    private Color calcLocalEffects(GeoPoint point, Ray ray) {

        Vector v = ray.getDir().normalized();
        Vector n = point.geometry.getNormal(point.point).normalized();

        double nv = alignZero(n.normalized().dotProduct(v.normalized()));
        if (nv == 0) {
            return Color.BLACK;
        }

        Material material = point.geometry.getMaterial();

        int nShininess = material.Shininess;
        double kd = material.Kd;
        double ks = material.Ks;

        Color color = Color.BLACK;

        for (LightSource lightSource : _scene.lights) {

            Vector l = lightSource.getL(point.point).normalized();
            double nl = alignZero(n.dotProduct(l));

            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color lightIntensity = lightSource.getIntensity(point.point);
                color = color.add(
                        calcDiffusive(kd, l, n, lightIntensity),
                        calcSpecular(ks, l, n, v, nShininess, lightIntensity, nl)
                );
            }
        }
        return color;
    }

    /**
     * @param kd
     * @param l
     * @param n
     * @param lightIntensity
     * @return
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        double ln = Math.abs(l.dotProduct(n));
        return lightIntensity.scale(ln * kd);
    }

    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity, double nl) {

        // double nl = l.dotProduct(n);
        Vector r = l.subtract(n.scale(2 * nl));

        double maximal = Math.max(0, v.scale(-1).dotProduct(r));

        var p = Math.pow(maximal, nShininess);

        return lightIntensity.scale(ks * p);
    }
}
