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
     * @member _intersectables - list of all components in the scene
     */
    private List<Intersectable> _intersectables = null;

    /**
     * constructor of class, creates the list and for now it is empty.
     * implements as a linked list that allows to delete members if necessary.
     */
    public Geometries() {
        _intersectables = new LinkedList<>();
    }

    /**
     * constructor of class, creats the list and for now it is empty.
     * implements as a linked list that allows to delete members if necessary.
     * after initializing, it adds shapes to the list, by using the add method.
     *
     * @param geometries - shapes to be added to the constructed instance
     */
    public Geometries(Intersectable... geometries) {
        _intersectables = new LinkedList<>();
        add(geometries);
    }

    /**
     * a method that receive one or more shape and adds to this list.
     *
     * @param geometries - shapes to be added to this instance
     */
    public void add(Intersectable... geometries) {
        _intersectables.addAll(Arrays.asList(geometries));
    }

    /**
     * remove method allow to remove (even zero) geometries from the composite class
     *
     * @param geometries which we want to add to the composite class
     * @return the geometries after the remove
     */
    public Geometries remove(Intersectable... geometries) {
        _intersectables.removeAll(Arrays.asList(geometries));
        return this;
    }

    public void addAll(List<Geometry> geometries) {
        _intersectables.addAll(geometries);
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
    //TODO: implement findIntersectBoundingRegion and boundingVolumeHierarchy
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = new LinkedList<>();

        for (Intersectable geometry : _intersectables) {
            List<Intersectable.GeoPoint> geoIntersections = geometry.findGeoIntersections(ray, maxDistance);
            if (geoIntersections != null) {
                if (geoIntersections.size() > 0) {
                    intersections.addAll(geoIntersections);
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
                "_intersectables=" + _intersectables +
                '}';
    }


    /**
     * method sets the values of the bounding volume for each component
     */
    @Override
    public void setBoundingBox() {
        super.setBoundingBox();                      // first, create a default bounding region if necessary
        for (Intersectable geo : _intersectables) {  // in a recursive call set bounding region for all the
            ((Container) geo).setBoundingBox();      // components and composites inside
        }

        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double minZ = Double.POSITIVE_INFINITY;

        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        double maxZ = Double.NEGATIVE_INFINITY;

        for (Intersectable inter : _intersectables) {
            Container geo = (Container) inter; // casting

            // get minimal & maximal x value for the containing box
            minX = Math.min(geo._boundingBox.getMinX(), minX);
            maxX = Math.max(geo._boundingBox.getMaxX(), maxX);

            // get minimal & maximal y value for the containing box
            minY = Math.min(geo._boundingBox.getMinY(), minY);
            maxY = Math.max(geo._boundingBox.getMaxY(), maxY);

            // get minimal & maximal z value for the containing box
            minZ = Math.min(geo._boundingBox.getMinZ(), minZ);
            maxZ = Math.max(geo._boundingBox.getMaxZ(), maxZ);
        }

        // set the minimum and maximum values in 3 axes for this bounding region of the component
        _boundingBox.setBoundingBox(minX, maxX, minY, maxY, minZ, maxZ);
    }

    /**
     * method which creates hierarchy with the volumes of the components,
     * by grouping the closest components to single component. this implemintation makes the components having
     * only two objects as maximum.
     *
     * @return the new structure of the geometries inside the current instance
     */
    public Geometries boundingVolumeHierarchy() {
        Geometries component = disassemblyGeometries(_intersectables, new Geometries());
        Intersectable left = null, right = null;
        while (component._intersectables.size() > 1) {
            double Best = Double.POSITIVE_INFINITY;
            for (Intersectable componentI : component._intersectables) {
                for (Intersectable componentJ : component._intersectables) {
                    if (!((Container) componentI)._boundingBox.equals(((Container) componentJ)._boundingBox) &&
                            ((Container) componentI)._boundingBox.getMaxDistance(((Container) componentJ)._boundingBox) < Best) {
                        Best = ((Container) componentI)._boundingBox.getMaxDistance(((Container) componentJ)._boundingBox);
                        left = componentI;
                        right = componentJ;
                    }
                }
            }
            Geometries componentTag = new Geometries(left, right);
            componentTag.setBoundingBox();
            component.remove(left, right).add(componentTag);
            ((Container) component).setBoundingBox();
        }
        this._intersectables = component._intersectables;
        return this;
    }

    /**
     * method which flatten all the geometries inside some geometries so that all components/composites will have the
     * same depth of hierarchy tree which is 1,
     * the function is working in a recursive way to get to all the composites in the original tree
     *
     * @param geometries      the geometries we're about to disassembly
     * @param geometriesFinal the result of the disassembled geometries
     * @return the flatten geometries, the result
     */
    private Geometries disassemblyGeometries(List<Intersectable> geometries, Geometries geometriesFinal) {
        for (Intersectable geo : geometries) {
            if (geo instanceof Geometries) {
                disassemblyGeometries(((Geometries) geo)._intersectables, geometriesFinal);
            } else {
                if (!geometriesFinal._intersectables.contains(geo))
                    geometriesFinal.add(geo);
            }
        }
        return geometriesFinal;
    }


    /**
     * method which creates a string of all the hierarchy of the components and the composites in the tree
     *
     * @return a string of all the hierarchy tree
     */
    public String hierarchyTree() {
        return hierarchyTree(this, " ");
    }

    /**
     * method which creates a string of all the hierarchy of the components and the composites in the tree.
     * works in a recursive way
     *
     * @param geometries current geometries we want to show it's objects
     * @param tabs       number of tabs whose for express the relationship between parent and sons
     * @return string of current 'geometries' and (in a recursive call) his sons
     */
    private String hierarchyTree(Geometries geometries, String tabs) {
        String str = "";
        for (Intersectable geo : geometries._intersectables) {
            if (geo instanceof Geometries) {
                str += tabs + "Geometries: {\n";
                str += hierarchyTree((Geometries) geo, tabs + " ");
                str += tabs + "   }\n";
            } else {
                str += tabs + "   " + geo.getClass() + "\n";
            }
        }
        return str;
    }

}