package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Ray;

import java.util.MissingResourceException;

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
    public ImageWriter imageWriter;
//    private Scene _scene;
    public Camera camera;
    public RayTracerBase rayTracer;


    /**
     * setter - chaining method style
     *
     * @param imageWriter - the class that writes the image to bee pictured
     * @return - this instance
     */
    public Render setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

//    /**
//     * setter - chaining method style
//     *
//     * @param scene - the scene (containing shapes)
//     * @return - this instance
//     */
//    public Render setScene(Scene scene) {
//        _scene = scene;
//        return this;
//    }


    /**
     * setter - chaining method style
     *
     * @param camera - the camera in scene that sees the photo
     * @return - this instance
     */
    public Render setCamera(Camera camera) {
        this.camera = camera;
        return this;
    }

    /**
     * setter - chaining method style
     *
     * @param rayTracer - that sends the ray for camera
     * @return - this instance
     */
    public Render setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    public void renderImage() {
        try {
            if (imageWriter == null) {
                throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
            }
//            if (_scene == null) {
//                throw new MissingResourceException("missing resource", Scene.class.getName(), "");
//            }
            if (camera == null) {
                throw new MissingResourceException("missing resource", Camera.class.getName(), "");
            }
            if (rayTracer == null) {
                throw new MissingResourceException("missing resource", RayTracerBase.class.getName(), "");
            }

            //rendering the image
            int nX = imageWriter.getNx();
            int nY = imageWriter.getNy();
            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {
                    Ray ray = camera.constructRayThroughPixel(nX, nY, j, i);
                    Color pixelColor = rayTracer.traceRay(ray);
                    imageWriter.writePixel(j, i, pixelColor);
                }
            }
        } catch (
                MissingResourceException e) {
            throw new UnsupportedOperationException("Not implemented yet" + e.getClassName());
        }

    }

    public void printGrid(int interval, Color color) {

        if (imageWriter == null) {
            throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
        }

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(j, i, color);
                }
            }
        }
    }

    public void writeToImage() {

        if (imageWriter == null) {
            throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
        }

        imageWriter.writeToImage();

    }

}