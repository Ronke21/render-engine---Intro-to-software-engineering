package renderer;

import elements.*;
import geometries.Intersectable.*;
import primitives.*;

import scene.*;

import java.util.List;

import static primitives.Util.*;

/**
 * class used to trace rays for the rendering engine
 */
public class RayTracerBasic extends RayTracerBase {

    /**
     * Constant value defining by how much we need to move the ray's starting point
     */
//    private static final double DELTA = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10; // 10
    private static final double MIN_CALC_COLOR_K = 0.001;// 1.0

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

        GeoPoint closestPoint = findClosestIntersection(ray);
        if (closestPoint == null) {
            return _scene.background;
        }
        return calcColor(closestPoint, ray);
    }

    /**
     * function to find geo point closest to starting point of the ray
     * from all of the intersections with an object
     *
     * @param ray - the tested ray
     * @return the point closest to the ray's starting point
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(ray);
        if (intersections == null || intersections.size() == 0) {
            return null;
        } else {
            return ray.findClosestGeoPoint(intersections);
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
        return calcColor(point, ray, MAX_CALC_COLOR_LEVEL, MIN_CALC_COLOR_K)
                .add(_scene.ambientLight.getIntensity());
    }

    /**
     * function which returns the color of the object the ray is intersecting
     * if no intersection was found, returns the ambient light's color
     *
     * @param intersection - closest intersection point on the object
     * @param ray          - ray to the point
     * @param level        - recursion iterations upper limit
     * @param k            - TODO
     * @return the color in the point with all the effects
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, double k) {
        Color color = intersection.geometry.getEmission();
        color = color.add(calcLocalEffects(intersection, ray, k));
        if (level == 1) {
            return color;
        }
        return color.add(calcGlobalEffects(intersection, ray, level, k));
    }

    private Color calcGlobalEffects(GeoPoint geopoint, Ray ray, int level, double K) {

        Color color = Color.BLACK;

        Material material = geopoint.geometry.getMaterial();

        double Kr = material.Kr;
        double KKr = K * Kr;

        Vector n = geopoint.geometry.getNormal(geopoint.point);

        if (KKr > MIN_CALC_COLOR_K) {
            Ray reflectedRay = constructReflectedRay(n, geopoint.point, ray);
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, KKr).scale(Kr));
        }

        double Kt = material.Kt;
        double KKt = K * Kt;

        if (KKt > MIN_CALC_COLOR_K) {
            Ray refractedRay = constructRefractedRay(n, geopoint.point, ray);
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            color = color.add(calcColor(refractedPoint, refractedRay, level - 1, KKt).scale(Kt));
        }
        return color;
    }

    /**
     * function which returns the color of the object the ray is intersecting after adding the required effects
     *
     * @param point - the point on the object
     * @param ray   - ray to the point
     * @return the color in the point with local effects
     */
    private Color calcLocalEffects(GeoPoint point, Ray ray, double k) {

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

                if (unshaded(lightSource, l, n, point)) {
                    Color lightIntensity = lightSource.getIntensity(point.point);
                    color = color.add(
                            calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity, nl)
                    );
                }

//                if (nl * nv > 0) { // sign(nl) == sing(nv)
//                    double ktr = transparency(lightSource, l, n, point);
//                    if (ktr * k > MIN_CALC_COLOR_K) {
//                        Color lightIntensity = lightSource.getIntensity(point.point).scale(ktr);
//                        color = color.add(
//                                calcDiffusive(kd, l, n, lightIntensity),
//                                calcSpecular(ks, l, n, v, nShininess, lightIntensity, nl)
//                        );
//                    }
//
//                }

            }
        }
        return color;
    }

    /**
     * function to calculate the diffusive component of the light
     *
     * @param kd             - diffuse attenuation factor
     * @param l              - the vector between the light source and the point
     * @param n              - the normal of l with the body
     * @param lightIntensity - the color of the light
     * @return the light after the calculated effects
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        double ln = Math.abs(l.dotProduct(n));
        return lightIntensity.scale(ln * kd);
    }

    /**
     * function to calculate the specular component of the light
     *
     * @param ks             - specular attenuation factor
     * @param l              - the vector between the light source and the point
     * @param n              - the normal of l with the body
     * @param v              - the camera vector
     * @param nShininess     - how shiny the object is
     * @param lightIntensity - the color of the light
     * @param nl             - n.dotProduct(l), received as a param to save unnecessary calculations
     * @return the light after the calculated effects
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity, double nl) {

        // double nl = l.dotProduct(n);
        Vector r = l.add(n.scale(-2 * nl));

        double maximal = Math.max(0, v.scale(-1).dotProduct(r));

        var p = Math.pow(maximal, nShininess);

        return lightIntensity.scale(ks * p);
    }

    /**
     * boolean function to test whether a given point is shaded or not
     *
     * @param light    - the light source which we check if the point is shaded from
     * @param l        - the vector between the light source and the point
     * @param n        - the normal of l with the body
     * @param geopoint - the tested point
     * @return true if the point is shaded, false otherwise
     */
    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geopoint) {

        Vector lightDirection = l.scale(-1); // from point to light source

        Ray lightRay = new Ray(geopoint.point, lightDirection, n);

        List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(lightRay);

        if (intersections == null) {
            return true;
        }

        double maxDistance = light.getDistance(geopoint.point);

        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(geopoint.point) - maxDistance) <= 0
                    && isZero(gp.geometry.getMaterial().Kt)) {
                return false;
            }
        }
        return true;
    }

    /**
     * function to construct the new ray reflected
     * from a point where another ray hits
     *
     * @param n     - the normal in point
     * @param point - the reflection point
     * @param ray   - the original ray
     * @return the reflected ray
     */
    private Ray constructRefractedRay(Vector n, Point3D point, Ray ray) {
        return new Ray(point, ray.getDir(), n);
    }

    /**
     * function to construct the new ray reflected
     * from a point where another ray hits
     *
     * @param n     - the normal in point
     * @param point - the reflection point
     * @param ray   - the original ray
     * @return the reflected ray
     */
    private Ray constructReflectedRay(Vector n, Point3D point, Ray ray) {
        Vector v = ray.getDir();
        double vn = v.dotProduct(n);
        Vector vnn = n.scale(-2 * vn);
        Vector r = v.add(vnn);
        // use the constructor with 3 arguments to move the head if needed
        return new Ray(point, ray.getDir(), n);
    }

    /**
     * boolean function to test whether a given point is shaded or not
     *
     * @param light    - the light source which we check if the point is shaded from
     * @param l        - the vector between the light source and the point
     * @param n        - the normal of l with the body
     * @param geopoint - the tested point
     * @return true if the point is shaded, false otherwise
     */
    private double transparency(LightSource light, Vector l, Vector n, GeoPoint geopoint) {

        Vector lightDirection = l.scale(-1); // from point to light source

        Ray lightRay = new Ray(geopoint.point, lightDirection, n);

        double maxDistance = light.getDistance(geopoint.point);

        List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(lightRay);

        if (intersections == null) {
            return 1.0;
        }

        double ktr = 1.0;

        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(geopoint.point) - maxDistance) <= 0) {
                ktr *= geopoint.geometry.getMaterial().Kt;
                if (ktr < MIN_CALC_COLOR_K) {
                    return 0.0;
                }
            }
        }
        return ktr;
    }
}
