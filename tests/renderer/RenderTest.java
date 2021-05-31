package renderer;

import elements.*;
import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;
import scene.XMLtoScene;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */
class RenderTest {
    private Camera camera = new Camera(
            Point3D.ZERO,
            new Vector(0, 0, -1),
            new Vector(0, 1, 0)) //
            .setDistance(100) //
            .setViewPlaneSize(500, 500);

    /**
     * Produce a scene with basic 3D model and render it into a jpeg image with a
     * grid
     */

    @Test
    public void basicRenderTwoColorTest() {
        Scene scene = new Scene("Test scene")//
                .setAmbientLight(new AmbientLight(new Color(255, 191, 191), 1)) //
                .setBackground(new Color(75, 127, 90));

        scene.geometries.add(new Sphere(new Point3D(0, 0, -100), 50),
                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, 100, -100), new Point3D(-100, 100, -100)), // up
                // left
                new Triangle(new Point3D(100, 0, -100), new Point3D(0, 100, -100), new Point3D(100, 100, -100)), // up
                // right
                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, -100, -100), new Point3D(-100, -100, -100)), // down
                // left
                new Triangle(new Point3D(100, 0, -100), new Point3D(0, -100, -100), new Point3D(100, -100, -100))); // down
        // right

        ImageWriter imageWriter = new ImageWriter("base render test", 1000, 1000);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage();
        render.printGrid(100, new Color(java.awt.Color.YELLOW));
        render.writeToImage();
    }


    /**
     * Test for XML based scene - for bonus
     */
    @Test
    public void basicRenderXml() {
//        Scene scene = new Scene("XML Test scene");

        Scene scene = XMLtoScene.ReadScene("C:\\Users\\amiha\\IdeaProjects\\IntroToSE_Project\\basicRenderTestTwoColors.xml");
//        Scene scene = XMLtoScene.ReadScene("C:\\Users\\ronke\\IdeaProjects\\basicRenderTestTwoColors.xml");
        // enter XML file name and parse from XML file into scene object
        // ...

        ImageWriter imageWriter = new ImageWriter("xml render test", 1000, 1000);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage();
        render.printGrid(100, new Color(java.awt.Color.YELLOW));
        render.writeToImage();
    }

    @Test
    public void basicRenderMultiColorTest() {
        Scene scene = new Scene("Test scene")//
                .setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.2)); //

        scene.geometries.add(new Sphere(new Point3D(0, 0, -100), 50) //
                        .setEmission(new Color(java.awt.Color.CYAN)), //
                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, 100, -100), new Point3D(-100, 100, -100)) // up left
                        .setEmission(new Color(java.awt.Color.GREEN)),
                new Triangle(new Point3D(100, 0, -100), new Point3D(0, 100, -100), new Point3D(100, 100, -100)), // up right
                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, -100, -100), new Point3D(-100, -100, -100)) // down left
                        .setEmission(new Color(java.awt.Color.RED)),
                new Triangle(new Point3D(100, 0, -100), new Point3D(0, -100, -100), new Point3D(100, -100, -100)) // down right
                        .setEmission(new Color(java.awt.Color.BLUE)));

        ImageWriter imageWriter = new ImageWriter("color render test", 1000, 1000);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage();
        render.printGrid(100, new Color(java.awt.Color.WHITE));
        render.writeToImage();
    }


    /**
     * Testing basic shadows
     *
     * @author Dan
     */
    public static class ShadowTests {
        private Scene scene = new Scene("Test scene");
        private Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(200, 200).setDistance(1000);

        /**
         * Produce a picture of a sphere and triangle with point light and shade
         */
        @org.testng.annotations.Test
        public void sphereTriangleInitial() {
            scene.geometries.add( //
                    new Sphere(new Point3D(0, 0, -200), 60) //
                            .setEmission(new Color(java.awt.Color.BLUE)) //
                            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)), //
                    new Triangle(new Point3D(-70, -40, 0), new Point3D(-40, -70, 0), new Point3D(-68, -68, -4)) //
                            .setEmission(new Color(java.awt.Color.BLUE)) //
                            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)) //
            );
            scene.lights.add( //
                    new SpotLight(new Color(400, 240, 0), new Point3D(-100, -100, 200), new Vector(1, 1, -3)) //
                            .setkL(1E-5).setkQ(1.5E-7));

            Render render = new Render(). //
                    setImageWriter(new ImageWriter("shadowSphereTriangleInitial", 400, 400)) //
                    .setCamera(camera) //
                    .setRayTracer(new BasicRayTracer(scene));
            render.renderImage();
            render.writeToImage();
        }

        /**
         * Produce a picture of a two triangles lighted by a spot light with a Sphere
         * producing a shading
         */
        @org.testng.annotations.Test
        public void trianglesSphere() {
            scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

            scene.geometries.add( //
                    new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //
                            .setMaterial(new Material().setKs(0.8).setShininess(60)), //
                    new Triangle(new Point3D(-150, -150, -115), new Point3D(-70, 70, -140), new Point3D(75, 75, -150)) //
                            .setMaterial(new Material().setKs(0.8).setShininess(60)), //
                    new Sphere(new Point3D(0, 0, -115), 30) //
                            .setEmission(new Color(java.awt.Color.BLUE)) //
                            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)) //
            );
            scene.lights.add( //
                    new SpotLight(new Color(700, 400, 400), new Point3D(40, 40, 115), new Vector(-1, -1, -4)) //
                            .setkL(4E-4).setkQ(2E-5));

            Render render = new Render() //
                    .setImageWriter(new ImageWriter("shadowTrianglesSphere", 600, 600)) //
                    .setCamera(camera) //
                    .setRayTracer(new BasicRayTracer(scene));
            render.renderImage();
            render.writeToImage();
        }
    }
}
