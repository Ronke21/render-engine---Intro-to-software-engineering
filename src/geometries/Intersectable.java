package geometries;

import primitives.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * An Interface for Composite Design Pattern The Composite Class - Geometries The
 * Basic Classes - all the specific geometries
 */
public interface Intersectable {

    /**
     * PDS
     * in order to find the correct color in a point, we need to
     * take into consideration th
     * shape which the light is bouncing from
     */

    /** Helper class representing a point on a geometry surface
     * @author Dan
     */
    public static class GeoPoint {
        /** the body of the point */
        public Geometry geometry;
        /** the point itself */
        public Point3D point;

        /** Constructor to initialize both fields of this helper class
         *
         * @param geo geometry (basic)
         * @param p   point on the surface of the geometry
         */
        public GeoPoint(Geometry geo, Point3D p) {
            geometry = geo;
            point = p;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null)
                return false;
            if (this == obj)
                return true;
            if (!(obj instanceof GeoPoint))
                return false;
            GeoPoint oth = (GeoPoint) obj;
            return this.geometry == oth.geometry && this.point.equals(oth.point);
        }

        @Override
        public String toString() {
            return "" + geometry + ": " + point;
        }

        /** Ray direction from point of view to the point*/
        public Vector v;
        /** Normal vector to the geometry at the point*/
        public Vector n;
        /** n*v - dotProduct*/
        public double nv;
        /** Point color (IP) being calculated (including intermediate results)*/
        public Color c;
        /** Geometry's material */
        public Material m;

        /** Initialize geo point cache data according to the direction of the ray
         * that produced the point.
         * GeoPoint now will include all the info for any functions using it thus avoiding
         * re-calculations
         * @param v ray direction
         */
        public void initCache(Vector v) {
            this.v = v;
            n = geometry.getNormal(point);
            nv = v.dotProduct(n);
            c = geometry.getEmission();
            m = geometry.getMaterial();
        }
    }

    /**
     * @param ray ray that cross the geometry
     * @return list of intersection points that were found
     */
    default List<Point3D> findIntersections(Ray ray) {
        List<GeoPoint> geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).collect(Collectors.toList());
    }


    /**
     * @param ray ray that cross the geometry
     * @return list of intersection points that were found
     */
    default List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY, false);
    }

    /**
     * @param ray         - ray that cross the geometry
     * @param maxDistance - the upper bound of distance, any point which
     *                    its distance is greater than this bound will not be returned
     * @param bb boolean for bounding box
     * @return list of intersection points that were found and has valid distance value
     */
    List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance, boolean bb);
}