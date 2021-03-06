package scene;

import elements.AmbientLight;
import elements.LightSource;
import geometries.Geometries;
import primitives.Color;

// imports for the xml bonus
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;
import java.util.LinkedList;
import java.util.List;


/**
 * this class represents a scene in the real life - containing different geometries to be seen by camera
 */
public class Scene {

    /**
     * _name - the point the vector points to from (0,0,0)
     */
    public final String _name;
    /**
     * background -  color of background in photo
     */
    public Color background = Color.BLACK;
    /**
     * ambientLight -  the surrounding light in the room
     */
    public AmbientLight ambientLight = new AmbientLight(Color.BLACK, 1.d);
    /**
     * geometries - the shapes in scene
     */
    public Geometries geometries = null;
    /**
     * lights - the lights in scene
     */
    public List<LightSource> lights = new LinkedList<LightSource>();


    /**
     * constructor for scene
     *
     * @param name - name of scene
     */
    public Scene(String name) {
        _name = name;
        geometries = new Geometries();
    }


//chaining methods

    /**
     * setter - chaining method style
     *
     * @param background - color of background
     * @return this instance
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * setter - chaining method style
     *
     * @param ambientLight - the surroundong light in room
     * @return this instance
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * setter - chaining method style
     *
     * @param geometries - shapes in photo
     * @return this instance
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     * setter - chaining method style
     *
     * @param lights - lights in photo
     * @return this instance
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }

    /**
     * get the geometries in scene
     * @return geometries in scene
     */
    public Geometries getGeometries() {
        return geometries;
    }

    @Override
    public String toString() {
        return "Scene{" +
                "_name='" + _name + '\'' +
                ", background=" + background +
                ", ambientLight=" + ambientLight +
                ", geometries=" + geometries +
                '}';
    }
}