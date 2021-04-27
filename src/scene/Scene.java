package scene;

import elements.AmbientLight;
import geometries.Geometries;
import primitives.Color;

/**
 *this class represents a scene in the real life - containing different geometries to be seen by camera
 */
public class Scene {

    /**
     * @member _name - the point the vector points to from (0,0,0)
     * @member background -  color of background in photo
     * @member ambientlight -  the surrounding light in the room
     * @member geometries - the shapes in scene
     */
    private final String _name;
    public Color background = Color.BLACK;
    public AmbientLight ambientlight= new AmbientLight(new Color(192, 192, 192),1.d); ;
    public Geometries geometries = null;

    /**
     * constructor
     * @param name - name of scene
     */
    public Scene(String name) {
        _name = name;
    }

    //chaining methods

    /**
     * setter - chaining method style
     * @param background - color of background
     * @return this instance
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * setter - chaining method style
     * @param ambientlight - the surroundong light in room
     * @return this instance
     */
    public Scene setAmbientLight(AmbientLight ambientlight) {
        this.ambientlight = ambientlight;
        return this;
    }

    /**
     * setter - chaining method style
     * @param geometries - shapes in photo
     * @return this instance
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

}