package renderer;

import org.junit.jupiter.api.Test;

import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 *
 * @author dzilb
 */
public class ReflectionRefractionTests {
    private Scene scene = new Scene("Test scene");

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheres() {
        Camera camera = new Camera(
                new Point3D(0, 0, 1000),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0)) //
                .setViewPlaneSize(150, 150)
                .setDistance(1000);

        scene.geometries.add( //
                new Sphere(
                        new Point3D(0, 0, -50), 50) //
                        .setEmission(Color.BLUE) //
                        .setMaterial(
                                new Material()
                                        .setKd(0.4)
                                        .setKs(0.3)
                                        .setShininess(100)
                                        .setKt(0.3)
                        ),

                new Sphere(
                        new Point3D(0, 0, -50), 25) //
                        .setEmission(Color.RED) //
                        .setMaterial(
                                new Material()
                                        .setKd(0.5)
                                        .setKs(0.5)
                                        .setShininess(100)
                        )
        );

        scene.lights.add( //
                new SpotLight(
                        new Color(1000, 600, 0),
                        new Point3D(-100, -100, 500),
                        new Vector(-1, -1, -2)) //
                        .setkL(0.0004)
                        .setkQ(0.0000006));

        Render render = new Render() //
                .setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
                .setCamera(camera) //
                .setRayTracer(new RayTracerBasic(scene));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheresOnMirrors() {
        Camera camera = new Camera(
                new Point3D(0, 0, 10000),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0)) //
                .setViewPlaneSize(2500, 2500)
                .setDistance(10000); //

        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

        scene.geometries.add( //
                new Sphere(new Point3D(-950, -900, -1000), 400) //
                        .setEmission(new Color(0, 0, 100)) //
                        .setMaterial(
                                new Material()
                                        .setKd(0.25)
                                        .setKs(0.25)
                                        .setShininess(20)
                                        .setKt(0.5)
                        ),

                new Sphere(new Point3D(-950, -900, -1000), 200) //
                        .setEmission(new Color(100, 20, 20)) //
                        .setMaterial(
                                new Material()
                                        .setKd(0.25)
                                        .setKs(0.25)
                                        .setShininess(20)
                        ),

                new Triangle(
                        new Point3D(1500, -1500, -1500),
                        new Point3D(-1500, 1500, -1500),
                        new Point3D(670, 670, 3000)
                ) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKr(1)),

                new Triangle(
                        new Point3D(1500, -1500, -1500),
                        new Point3D(-1500, 1500, -1500),
                        new Point3D(-1500, -1500, -2000)
                ) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKr(0.5)));

        scene.lights.add(
                new SpotLight(new Color(1020, 400, 400),
                        new Point3D(-750, -750, -150),
                        new Vector(-1, -1, -4)) //
                        .setkL(0.00001).setkQ(0.000005));

        ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new RayTracerBasic(scene));

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a partially
     * transparent Sphere producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() {
        Camera camera = new Camera(
                new Point3D(0, 0, 1000),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0)) //
                .setViewPlaneSize(200, 200)
                .setDistance(1000);

        scene.setAmbientLight(new AmbientLight(Color.WHITE, 0.15));

        scene.geometries.add( //
                new Triangle(
                        new Point3D(-150, -150, -115),
                        new Point3D(150, -150, -135),
                        new Point3D(75, 75, -150)
                ) //
                        .setMaterial(
                                new Material()
                                        .setKd(0.5)
                                        .setKs(0.5)
                                        .setShininess(60)
                        ), //

                new Triangle(
                        new Point3D(-150, -150, -115),
                        new Point3D(-70, 70, -140),
                        new Point3D(75, 75, -150)
                ) //
                        .setMaterial(
                                new Material()
                                        .setKd(0.5)
                                        .setKs(0.5)
                                        .setShininess(60)
                        ), //

                new Sphere(new Point3D(60, 50, -50), 30) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(
                                new Material()
                                        .setKd(0.2)
                                        .setKs(0.2)
                                        .setShininess(30)
                                        .setKt(0.6)
                        )
        );

        scene.lights.add(
                new SpotLight(
                        new Color(700, 400, 400),
                        new Point3D(60, 50, 0),
                        new Vector(0, 0, -1)
                ) //
                        .setkL(4E-5).setkQ(2E-7));

        ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new RayTracerBasic(scene));

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void MyTest() {

        Scene scene = new Scene("Test scene");

        Camera camera = new Camera(
                new Point3D(10, -20, 60),
                new Vector(-20, -10, -90),
                new Vector(0, 1, -1d / 9d))
                .setViewPlaneSize(40, 40)
                .setDistance(50);

        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        // Geometries
        //walls and mirror
        scene.geometries.add(

                new Plane(new Point3D(100, 0, 0), new Vector(1, 0, 0))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(0).setKt(0).setKr(0))
                        .setEmission((Color.GRAY.add(Color.RED)).reduce(20)),

                new Plane(new Point3D(-100, 0, 0), new Vector(0, 1, 0))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(0).setKt(0).setKr(0))
                        .setEmission(Color.GRAY.reduce(2)));

//                new Plane(new Point3D(0, 300, 0), new Vector(0, 1, 0))
//                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(0).setKt(0).setKr(0))
//                        .setEmission(Color.BLUE)


        scene.lights.add(
                new SpotLight(
                        new Color(0, 0, 0),
                        new Point3D(0, 40, 0),
                        new Vector(0, -1, 0)
                ).setFocus(1)
                        .setkL(0.0005)
                        .setkQ(0.000005));

        scene.lights.add(
                new PointLight(
                        new Color(255, 250, 0),
                        new Point3D(0, 40, 0))
                        .setkL(0.001).setkQ(0.0002));

        scene.lights.add(
                new DirectionalLight(
                        new Color(50, 50, 50),
                        new Vector(1, -1, -1)));

        scene.geometries.add(
                new Sphere(new Point3D(-20, -40, -50), 5)
                        .setEmission(Color.WHITE.reduce(6))
                        .setMaterial(new Material().setKd(1).setKs(1).setShininess(100).setKt(0).setKr(0.3)));

        scene.geometries.add(
                new Sphere(new Point3D(-10, -40, -50), 5)
                        .setEmission(Color.GREEN.reduce(6))
                        .setMaterial(new Material().setKd(1).setKs(1).setShininess(100).setKt(0).setKr(0.3)));

        scene.geometries.add(
                new Sphere(new Point3D(-15, -30, -50), 5)
                        .setEmission(Color.BLUE.reduce(6))
                        .setMaterial(new Material().setKd(1).setKs(1).setShininess(100).setKt(0).setKr(0.3)));

        scene.geometries.add(
                new Sphere(new Point3D(-30, -40, -50), 5)
                        .setEmission(Color.WHITE.reduce(6))
                        .setMaterial(new Material().setKd(1).setKs(1).setShininess(100).setKt(0).setKr(0.3)));

        scene.geometries.add(
                new Sphere(new Point3D(-25, -30, -50), 5)
                        .setEmission(Color.GREEN.reduce(6))
                        .setMaterial(new Material().setKd(1).setKs(1).setShininess(100).setKt(0).setKr(0.3)));

        scene.geometries.add(
                new Triangle(
                        new Point3D(-12, -25, -50),
                        new Point3D(-28, -25, -50),
                        new Point3D(-20, -15, -50))
                        .setEmission(Color.BLUE)
                        .setMaterial(new Material().setKd(1).setKs(1).setShininess(100).setKt(1).setKr(0.3))
        );

        scene.geometries.add(
                new Polygon(
                        new Point3D(-120, -90, -149),
                        new Point3D(-120, 150, -149),
                        new Point3D(120, 150, -149),
                        new Point3D(120, -90, -149)
                )
                        .setEmission(new Color(40, 40, 40))
                        .setMaterial(new Material().setKd(0).setKs(0).setShininess(0).setKt(0).setKr(0.3)));

        scene.geometries.add(
                new Tube(new Ray(
                        new Point3D(-35, -47, -50),
                        new Vector(1, 0, 0)), 3)
                        .setEmission(Color.RED.reduce(5))
                        .setMaterial(new Material().setKd(0).setKs(0).setShininess(0).setKt(0).setKr(0.3)
                        ));

        Render render = new Render() //
                .setImageWriter(new ImageWriter("MyTransparencyTest", 250, 250)) //
                .setCamera(camera) //
                .setRayTracer(new RayTracerBasic(scene));
        render.renderImage();
        render.writeToImage();

        camera.turnCamera(new Vector(0, 5, 5), -2);
        camera.moveCamera(3, 20, 3);

        Render render2 = new Render() //
                .setImageWriter(new ImageWriter("MyTransparencyTestAfter1", 250, 250)) //
                .setCamera(camera) //
                .setRayTracer(new RayTracerBasic(scene));
        render2.renderImage();
        render2.writeToImage();

        camera.turnCamera(new Vector(0, 5, 5), 5);
        camera.moveCamera(-3, 0, -3);

        Render render3 = new Render() //
                .setImageWriter(new ImageWriter("MyTransparencyTestAfter2", 250, 250)) //
                .setCamera(camera) //
                .setRayTracer(new RayTracerBasic(scene));
        render3.renderImage();
        render3.writeToImage();
    }
}


