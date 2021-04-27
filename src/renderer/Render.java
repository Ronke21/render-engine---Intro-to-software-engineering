package renderer;

import elements.Camera;
import scene.Scene;

/**
 * this class represents the renderer that creates the photo and sets all its properties.
 */
public class Render {

    /**
     * @member _imageWriter - the class that writes the image to bee pictured
     * @member _scene - the scene (containing shapes)
     * @member _camera - the camera in scene that sees the photo
     * @member _rayTracerBase - that sends the ray for camera
     */
    private ImageWriter _imageWriter;
    private Scene _scene;
    private Camera _camera;
    RayTracerBase _rayTracerBase;

    /**
     * setter - chaining method style
     * @param imageWriter - the class that writes the image to bee pictured
     * @return - this instance
     */
    public Render setImageWriter(ImageWriter imageWriter) {
        _imageWriter = imageWriter;
        return this;
    }

    /**
     * setter - chaining method style
     * @param scene - the scene (containing shapes)
     * @return - this instance
     */
    public Render setScene(Scene scene) {
        _scene = scene;
        return this;
    }

    /**
     * setter - chaining method style
     * @param camera - the camera in scene that sees the photo
     * @return - this instance
     */
    public Render setCamera(Camera camera) {
        _camera = camera;
        return this;
    }

    /**
     * setter - chaining method style
     * @param rayTracer - that sends the ray for camera
     * @return - this instance
     */
    public Render setRayTracer(RayTracerBase rayTracer) {
        _rayTracerBase = rayTracer;
        return this;
    }


}