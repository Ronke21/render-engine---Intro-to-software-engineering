package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;

/**
 * interface for all shapes in the space,
 * objects with material and emission fields, and getNormal method (and has the possibility to be intersected)
 */
public abstract class Geometry extends Container{

    /**
     * emission light
     */
    protected Color emission = Color.BLACK;
    private Material material = new Material();

    /**
     * getself color of the shape
     *
     * @return emission of geometry
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * function that receive a point in a body and return a normal in this point to the body
     *
     * @param point point pointing in the direction of the normal
     * @return normal vector to the Geometry
     */
    public abstract Vector getNormal(Point3D point);

    /**
     * get material type of shape
     *
     * @return the material of the geometry
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * setter of emission light, chaining method design pattern
     *
     * @param emission color of shape
     * @return this instance
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * setter of material, chaining method design pattern
     *
     * @param material type of material
     * @return this instance
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * perform full comparison between a given object and this
     *
     * @param o - object
     * @return - whether the object equals to this or not
     */
    @Override
    public abstract boolean equals(Object o);
}