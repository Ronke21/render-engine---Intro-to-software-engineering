package geometries;

import primitives.Ray;

import java.util.List;

public abstract class Container implements Intersectable {
//******

    /**
     * every Intersectable composite have his bounding volume, which represented by a bounding box
     */
    public BoundingBox _boundingBox; // = null as default

    /**
     * method of checking if bounding region exists and if the ray intersections it,
     * only ray value input.
     *
     * @param ray the ray which we about to check for intersection with it and some geometries which in her way
     * @return list of intersection points with the ray and the geometries,
     * calls origin function of for calculating the points
     */
    public List<GeoPoint> findIntersectBoundingRegion(Ray ray) {
        if (_boundingBox == null || _boundingBox.intersectBV(ray)) {
            return findGeoIntersections(ray);
        }
        return null;
    }

    /**
     * method of checking if bounding region exists and if the ray intersections it,
     * only ray value and distance inputs
     *
     * @param ray         the ray which we about to check for intersection with it and some geometries which in her way
     * @param maxDistance the maximum distance we will like to calculate the intersections in it
     * @return list of intersection points with the ray and the geometries,
     * calls origin function of for calculating the points
     */
    public List<Intersectable.GeoPoint> findIntersectBoundingRegion(Ray ray, double maxDistance) {
        if (_boundingBox == null || _boundingBox.intersectBV(ray)) {
            return findGeoIntersections(ray, maxDistance);
        }
        return null;
    }

    /**
     * method sets the values of the bounding volume of the intersectable component
     * this implementation is for constructing new bounding box if necessary/needed
     */
    public void setBoundingBox() {
        if (_boundingBox == null) {
            _boundingBox = new BoundingBox();
        }
    }
}
