package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

/**
 * this class represents a group of shapes in the space that represent a picture.
 */
public class Geometries implements Intersectable {

    /**
     * @member _intersectables - list of all components in the scene
     */
    private List<Intersectable> _intersectables = null;

    /**
     * constructor of class, creats the list and for now it is empty.
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
        for (Intersectable item : geometries) {
            _intersectables.add(item);
        }
    }

    /**
     * a method that receive a ray and find all intersections of this ray with the shapes in this class
     *
     * @param ray - the ray to be checked with the shapes
     * @return list of all intersections
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        List<Point3D> result = null;
        for (Intersectable item : _intersectables) {
            List<Point3D> itemList = item.findIntersections(ray);
            if (itemList != null) {
                if (result == null) {
                    result = new LinkedList<>();
                }
                result.addAll(itemList);
            }
        }
        return result;
    }
}