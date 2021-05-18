package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Aמ Interface for Composite Design Pattern The Composite Class - Geometries The
 * Basic Classes - all the specific geometries
 */
public interface Intersectable {


    /**
     * PDS
     * in order to find the correct color in a point, we need to
     * take into consideration the shape which the light is bouncing from
     */
    public static class GeoPoint {

        public Geometry geometry;
        public Point3D point;

        /**
         * constructor of inner geo Point
         *
         * @param geometry - reference to current geometry
         * @param point    - reference to point on current geometry
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GeoPoint)) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return geometry.equals(geoPoint.geometry) && point.equals(geoPoint.point);
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
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }
    List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance);



}