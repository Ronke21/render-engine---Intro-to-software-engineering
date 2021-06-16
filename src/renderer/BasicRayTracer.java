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
public class BasicRayTracer extends RayTracerBase {

    /**
     * Constant value defining by how much we need to move the ray's starting point
     */
    // how many reflections we want to calculate
    // USE WITH CAUTION! higher values leads to performance decreasing rapidly
    private static final int MAX_CALC_COLOR_LEVEL = 10; // 10
    // minimal value which is not considered at fully transparent or other effects
    private static final double MIN_CALC_COLOR_K = 0.001; // 0.001
    // TODO : understand the physical meaning of this value.
    //  0 leads to no reflections at all, but no significant difference with any other value
    private static final double INITIAL_K = 1; // 1
    private boolean _bb; // bounding box

    public BasicRayTracer set_bb(boolean _bb) {
        this._bb = _bb;
        return this;
    }

    /**
     * constructor
     *
     * @param scene - the scene the rays are sent to
     */
    public BasicRayTracer(Scene scene) {
        super(scene);
//        scene.getGeometries().BuildTree();
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

        List<GeoPoint> intersections;

        if (!_bb) {
            intersections = _scene.geometries.findGeoIntersections(ray);
        } else {
            intersections = _scene.geometries.findIntersectBoundingRegion(ray);
        }

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
        return calcColor(point, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(_scene.ambientLight.getIntensity());
    }

    /**
     * function which returns the color of the object the ray is intersecting
     * if no intersection was found, returns the ambient light's color
     *
     * @param intersection - closest intersection point on the object
     * @param ray          - ray to the point
     * @param level        - recursion iterations upper limit
     * @param k            - TODO lines 26 - 27
     * @return the color in the point with all the effects
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, double k) {
        if (intersection == null) {
            return _scene.background;
        }
        Color color = intersection.geometry.getEmission();
        // add the diffusive and specular components
        color = color.add(calcLocalEffects(intersection, ray, k));
        // if the limit of recursion has been reached, no more calculations, return the current state
        if (level == 1) {
            return color;
        }
        // else, keep calculating the effects..
        return color.add(calcGlobalEffects(intersection, ray, level, k));
    }

    /**
     * function to calculate transparency and reflections
     *
     * @param geoPoint - the tested point
     * @param ray      - the ray going through the point
     * @param level    - recursion iterations upper limit
     * @param K        - TODO lines 26 - 27
     * @return the Color of the returned light after calculating all the required effects
     */
    private Color calcGlobalEffects(GeoPoint geoPoint, Ray ray, int level, double K) {

        Color color = Color.BLACK;

        Material material = geoPoint.geometry.getMaterial();

        double KKr = K * material.Kr;

        // vector normal
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);

        if (KKr > MIN_CALC_COLOR_K) {
            Ray reflectedRay = constructReflectedRay(n, geoPoint.point, ray);
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, KKr).scale(material.Kr));
        }

        double KKt = K * material.Kt;

        if (KKt > MIN_CALC_COLOR_K) {
            Ray refractedRay = constructRefractedRay(n, geoPoint.point, ray);
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            color = color.add(calcColor(refractedPoint, refractedRay, level - 1, KKt).scale(material.Kt));
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
//        Color color = Color.BLACK;
        Color color = point.geometry.getEmission();

        Vector v = ray.getDir();
        Vector n = point.geometry.getNormal(point.point);

        double nv = alignZero(n.dotProduct(v));
        if (isZero(nv)) {
            return color;
        }

        Material material = point.geometry.getMaterial();

        int nShininess = material.Shininess;
        double kd = material.Kd;
        double ks = material.Ks;

        for (LightSource lightSource : _scene.lights) {

            Vector l = lightSource.getL(point.point);
            double nl = alignZero(n.dotProduct(l));

            if (nl * nv > 0) { // sign(nl) == sing(nv)

                //region unshaded, replaced by transparency
                // if (unshaded(lightSource, l, n, point)) {
                //     Color lightIntensity = lightSource.getIntensity(point.point);
                //     color = color.add(
                //             calcDiffusive(kd, l, n, lightIntensity),
                //             calcSpecular(ks, l, n, v, nShininess, lightIntensity, nl)
                //     );
                // }
                //endregion

                double ktr = transparency(lightSource, l, n, point);
                if (ktr * k > MIN_CALC_COLOR_K) {
                    Color lightIntensity = lightSource.getIntensity(point.point).scale(ktr);
                    color = color.add(
                            calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity, nl)
                    );
                }
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

    //region unshaded - replaced by transparency
//    /**
//     * boolean function to test whether a given point is shaded or not
//     *
//     * @param light    - the light source which we check if the point is shaded from
//     * @param l        - the vector between the light source and the point
//     * @param n        - the normal of l with the body
//     * @param geoPoint - the tested point
//     * @return true if the point is shaded, false otherwise
//     */
//    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geoPoint) {
//
//        Vector lightDirection = l.scale(-1); // from point to light source
//
//        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);
//
//        List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(lightRay);
//
//        if (intersections == null) {
//            return true;
//        }
//
//        double maxDistance = light.getDistance(geoPoint.point);
//
//        for (GeoPoint gp : intersections) {
//            if (alignZero(gp.point.distance(geoPoint.point) - maxDistance) <= 0
//                    && isZero(gp.geometry.getMaterial().Kt)) {
//                return false;
//            }
//        }
//        return true;
//    }
// endregion

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
        return new Ray(point, r, n);
    }

    /**
     * function to determine the level of transparency of the point
     *
     * @param light    - the light source which we measure the distance from
     * @param l        - the vector between the light source and the point
     * @param n        - the normal in the point
     * @param geoPoint - the tested point
     * @return the transparency value
     */
    private double transparency(LightSource light, Vector l, Vector n, GeoPoint geoPoint) {

        Vector lightDirection = l.scale(-1); // from point to light source

        // construct a new ray using the new 3 arguments CTOR
        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);

        // distance between the light source and the point
        double lightDistance = light.getDistance(geoPoint.point);

        List<GeoPoint> intersections;
        if (!_bb) {
            intersections = _scene.geometries.findGeoIntersections(lightRay);
        } else {
            intersections = _scene.geometries.findIntersectBoundingRegion(lightRay);
        }

        // if no intersections, the ray is not blocked at all, meaning full transparency!
        if (intersections == null) {
            return 1.0;
        }

        // else..
        // initialize the transparency coefficient
        double KTr = 1.0;

        // loop through the intersections and multiply the transparency factors to get final results
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0) {
                KTr *= gp.geometry.getMaterial().Kt;
                if (KTr < MIN_CALC_COLOR_K) {
                    return 0.0;
                }
            }
        }
        return KTr;
    }
}
