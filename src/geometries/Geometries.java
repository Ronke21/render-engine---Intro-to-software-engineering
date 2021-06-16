package geometries;

import primitives.Ray;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * this class represents a group of shapes in the space that represent a picture.
 */
public class Geometries extends Container {

    /**
     * @member _containers - list of all components in the scene
     */
    private List<Container> _containers = null;

    /**
     * constructor of class, creates the list and for now it is empty.
     * implements as a linked list that allows to delete members if necessary.
     */
    public Geometries() {
        _containers = new LinkedList<>();
        this.setBoundingBox();
    }

    /**
     * constructor of class, creats the list and for now it is empty.
     * implements as a linked list that allows to delete members if necessary.
     * after initializing, it adds shapes to the list, by using the add method.
     *
     * @param geometries - shapes to be added to the constructed instance
     */
    public Geometries(Container... geometries) {
        _containers = new LinkedList<>();
        add(geometries);
        this.setBoundingBox();
    }

    /**
     * a method that receive one or more shape and adds to this list.
     *
     * @param geometries - shapes to be added to this instance
     */
    public void add(Container... geometries) {
        _containers.addAll(Arrays.asList(geometries));
    }

    /**
     * remove method allow to remove (even zero) geometries from the composite class
     *
     * @param geometries which we want to add to the composite class
     * @return the geometries after the remove
     */
    public Geometries remove(Container... geometries) {
        _containers.removeAll(Arrays.asList(geometries));
        return this;
    }

    public void addAll(List<Geometry> geometries) {
        _containers.addAll(geometries);
    }

    /**
     * a method that receive a ray and find all intersections of this ray with the shapes in this class
     *
     * @param ray         - the ray to be checked with the shapes
     * @param maxDistance - the upper bound of distance, any point which
     *                    its distance is greater than this bound will not be returned
     * @return list of all intersections in a form of GeoPoint
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance, boolean bb) {
        List<GeoPoint> intersections = new LinkedList<>();
        for (Container geometry : _containers) {
            if (bb) {
                if (geometry._boundingBox.intersectBV(ray)) {
                    List<Intersectable.GeoPoint> geoIntersections = geometry.findGeoIntersections(ray, maxDistance, true);
                    if (geoIntersections != null) {
                        if (geoIntersections.size() > 0) {
                            intersections.addAll(geoIntersections);
                        }
                    }
                }
            } else {
                List<Intersectable.GeoPoint> geoIntersections = geometry.findGeoIntersections(ray, maxDistance, false);
                if (geoIntersections != null) {
                    if (geoIntersections.size() > 0) {
                        intersections.addAll(geoIntersections);
                    }
                }
            }
        }
        if (intersections.size() > 0) {
            return intersections;
        }
        return null;
    }


    @Override
    public String toString() {
        return "Geometries{" +
                "_intersectables=" + _containers +
                '}';
    }


    /**
     * method sets the values of the bounding volume for each component
     */
    @Override
    public void setBoundingBox() {
        super.setBoundingBox();                 // first, create a default bounding region if necessary
        for (Container geo : _containers) {     // in a recursive call set bounding region for all the
            geo.setBoundingBox();               // components and composites inside
        }

        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double minZ = Double.POSITIVE_INFINITY;

        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        double maxZ = Double.NEGATIVE_INFINITY;

        for (Container inter : _containers) {

            // get minimal & maximal x value for the containing box
            minX = Math.min(inter._boundingBox.getMinX(), minX);
            maxX = Math.max(inter._boundingBox.getMaxX(), maxX);

            // get minimal & maximal y value for the containing box
            minY = Math.min(inter._boundingBox.getMinY(), minY);
            maxY = Math.max(inter._boundingBox.getMaxY(), maxY);

            // get minimal & maximal z value for the containing box
            minZ = Math.min(inter._boundingBox.getMinZ(), minZ);
            maxZ = Math.max(inter._boundingBox.getMaxZ(), maxZ);
        }

        // set the minimum and maximum values in 3 axes for this bounding region of the component
        _boundingBox.setBoundingBox(minX, maxX, minY, maxY, minZ, maxZ);
    }

    /**
     * build bvh tree
     */
    public void BuildTree() {

        this.flatten();
        double distance;
        Container bestGeometry1 = null;
        Container bestGeometry2 = null;

        while (_containers.size() > 1) {
            double best = Double.MAX_VALUE;
            for (Container geometry1 : _containers) {
                for (Container geometry2 : _containers) {
                    if (geometry1._boundingBox == null || geometry2._boundingBox == null) {
                        System.out.println("hello bug");
                    }
                    distance = geometry1._boundingBox.BoundingBoxDistance(geometry2._boundingBox);
                    if (!geometry1.equals(geometry2) && distance < best) {
                        best = distance;
                        bestGeometry1 = geometry1;
                        bestGeometry2 = geometry2;
                    }
                }
            }
            _containers.add(new Geometries(bestGeometry1, bestGeometry2));
            _containers.remove(bestGeometry1);
            _containers.remove(bestGeometry2);
        }
    }

    /**
     * recursive func to flatten the geometries list
     *
     * @param new_geometries current geometries
     */
    private void flatten(Geometries new_geometries) {
        for (Container container : new_geometries._containers) {
            if (container instanceof Geometry)
                _containers.add(container);
            else
                flatten((Geometries) container);
        }
    }

    /**
     * flatten the geometries list
     */
    public void flatten() {
        Geometries new_geometries = new Geometries(_containers.toArray(new Container[_containers.size()]));
        _containers.clear();
        flatten(new_geometries);
    }
}