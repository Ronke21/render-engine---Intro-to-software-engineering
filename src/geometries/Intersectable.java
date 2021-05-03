package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;
import java.util.Objects;

/**
 * Aמ Interface for Composite Design Pattern The Composite Class - Geometries The
 * Basic Classes - all the specific geometries
 */
public interface Intersectable {


    /**
     * PDS class
     * class to represent the geo point - containing point3D and the its geometry.
     * used for the @TODO
     */
    public static class GeoPoint {

        public Geometry geometry;
        public Point3D point;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GeoPoint)) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return geometry.equals(geoPoint.geometry) && point.equals(geoPoint.point);
        }


        /**
         * constructor of inner geo Point
         * @param geometry - reference to current geometry
         * @param point - reference to point on current geometry
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = point;


        }
    }

    /**
     * @param ray ray that cross the geometry
     * @return list of intersection points that were found
     */
    List<Point3D> findIntersections(Ray ray);

    /**
     * @param ray ray that cross the geometry
     * @return list of intersection points that were found
     */
    List<GeoPoint> findGeoIntersections(Ray ray);

}