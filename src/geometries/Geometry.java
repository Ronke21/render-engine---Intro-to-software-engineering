package geometries;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * interface for all shapes in the space.
 */
public abstract class Geometry implements Intersectable {

    /**
     * emission light
     */
    protected Color emission = Color.BLACK;

    /**
     * getter
     *
     * @return emission of geometry
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * setter of emission light, chaining method design pattern
     *
     * @param emission
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * function that receive a point in a body and return a normal in this point to the body
     *
     * @param point point pointing in the direction of the normal
     * @return normal vector to the Geometry
     */
    public abstract Vector getNormal(Point3D point);

    /**
     * perform full comparison between a given object and this
     * @param o - object
     * @return - whether the object equals to this or not
     */
    @Override
    public abstract boolean equals(Object o);
}
