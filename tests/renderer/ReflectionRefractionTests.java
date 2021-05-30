package renderer;

import org.junit.jupiter.api.Test;

import elements.*;
import geometries.*;
import primitives.*;
import scene.Scene;

import java.util.Arrays;
import java.util.List;

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
                .setRayTracer(new BasicRayTracer(scene));
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
                .setRayTracer(new BasicRayTracer(scene));

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
                .setRayTracer(new BasicRayTracer(scene));

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
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();

        camera.turnCamera(new Vector(0, 5, 5), -2);
        camera.moveCamera(3, 20, 3);

        Render render2 = new Render() //
                .setImageWriter(new ImageWriter("MyTransparencyTestAfter1", 250, 250)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render2.renderImage();
        render2.writeToImage();

        camera.turnCamera(new Vector(0, 5, 5), 5);
        camera.moveCamera(-3, 0, -3);

        Render render3 = new Render() //
                .setImageWriter(new ImageWriter("MyTransparencyTestAfter2", 250, 250)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render3.renderImage();
        render3.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void TreeTest() {

        Scene scene = new Scene("Test scene");

        // need to aim the camera
        Camera camera = new Camera(
                new Point3D(70, 60, 10),
                new Vector(-5, -5, -1),
                new Vector(-2.52, -2.52, 25.2))
                .setViewPlaneSize(250, 250)
                .setDistance(300)
                ;

        scene.setBackground(Color.BLUE.add(Color.GREEN.reduce(2)).reduce(1.2));
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));


        // create all relevant points
        Point3D a = new Point3D(-14, 1, 1);
        Point3D b = new Point3D(1, -14, 1);
        Point3D c = new Point3D(14, 1, 1);
        Point3D d = new Point3D(1, 14, 1);
        Point3D f = new Point3D(-12, 1, 10);
        Point3D g = new Point3D(1, -12, 10);
        Point3D h = new Point3D(12, 1, 10);
        Point3D i = new Point3D(1, 12, 10);
        Point3D j = new Point3D(1, 1, 30);
        Point3D k = new Point3D(-10, 1, 20);
        Point3D l = new Point3D(1, -10, 20);
        Point3D m = new Point3D(10, 1, 20);
        Point3D n = new Point3D(1, 10, 20);
        Point3D o = new Point3D(1, 1, 40);
        Point3D p = new Point3D(1, 1, 20);

        Polygon BottomPyramidBottom = new Polygon(a, b, c, d);
        // tree's triangles
        Triangle BottomPyramid1 = new Triangle(a, b, p);
        Triangle BottomPyramid2 = new Triangle(b, c, p);
        Triangle BottomPyramid3 = new Triangle(c, d, p);
        Triangle BottomPyramid4 = new Triangle(a, d, p);

        Triangle MiddlePyramid1 = new Triangle(f, g, j);
        Triangle MiddlePyramid2 = new Triangle(g, h, j);
        Triangle MiddlePyramid3 = new Triangle(h, i, j);
        Triangle MiddlePyramid4 = new Triangle(i, f, j);

        Triangle TopPyramid1 = new Triangle(k, l, o);
        Triangle TopPyramid2 = new Triangle(l, m, o);
        Triangle TopPyramid3 = new Triangle(m, n, o);
        Triangle TopPyramid4 = new Triangle(n, k, o);

        // add all of them to a list to apply settings (e.g. color) to all of them easily
        List<Polygon> greenTriangles = Arrays.asList(
                BottomPyramidBottom,
                BottomPyramid1,
                BottomPyramid2,
                BottomPyramid3,
                BottomPyramid4,
                MiddlePyramid1,
                MiddlePyramid2,
                MiddlePyramid3,
                MiddlePyramid4,
                TopPyramid1,
                TopPyramid2,
                TopPyramid3,
                TopPyramid4
        );

        Color naturalGreen = new Color(Color.GREEN.add(Color.RED.reduce(10)).reduce(4));
        Color naturalGreen2 = naturalGreen.add(Color.GREEN.reduce(5));

        for (Polygon polygon : greenTriangles) {
            polygon.setEmission(naturalGreen)
                    .setMaterial(new Material()
                            .setKd(0.01)
                            .setKs(0.0001)
                            .setShininess(2)
                            .setKr(0.01));
        }

        Tube trunk = new Cylinder(
                new Ray(
                        new Point3D(0, 0, -10),
                        new Vector(0, 0, 1))
                , 2.5,
                15);

        trunk.setEmission(new Color(180, 83, 0).reduce(5));

        Plane ground = new Plane(
                new Point3D(5, 10, -10),
                new Point3D(2, 3, -10),
                new Point3D(7, -3, -10)
        );

        ground.setEmission(Color.BLUE.add(Color.GREEN.reduce(3)).reduce(10))
                .setMaterial(new Material().setKd(0.03).setKs(0.00005).setKr(0.8));

//        BottomPyramidBottom.setEmission(Color.RED)
//                .setMaterial(new Material()
//                        .setKd(0.01)
//                        .setKs(0.0001)
//                        .setShininess(2)
//                        .setKr(0.01));

        Sphere sun = new Sphere(new Point3D(10, -40, 30), 10);
        sun.setEmission(Color.YELLOW.add(Color.RED.reduce(6)))
                .setMaterial(
                        new Material()
                                .setKd(0.005)
                                .setKs(0.00005)
                                .setShininess(50)
                                .setKt(0.9)
                );

        scene.geometries.add(
                BottomPyramidBottom,
                BottomPyramid1,
                BottomPyramid2,
                BottomPyramid3,
                BottomPyramid4,
                MiddlePyramid1,
                MiddlePyramid2,
                MiddlePyramid3,
                MiddlePyramid4,
                TopPyramid1,
                TopPyramid2,
                TopPyramid3,
                TopPyramid4,
                trunk,
                ground,
                sun
        );

        scene.lights.add(
                new SpotLight(
                        new Color(Color.WHITE.scale(10)),
                        new Point3D(70, 60, 20),
                        new Vector(-5, -5, -1))
                        .setkL(0.0004)
                        .setkQ(0.0000006));

        scene.lights.add(
                new PointLight(Color.WHITE.reduce(2).add(Color.YELLOW).scale(3), new Point3D(10, -40, 30)));

        int pixels = 500;

        //70, 60, 10
        Render render = new Render() //
                .setImageWriter(new ImageWriter("TreeTest", pixels, pixels)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();

        camera.moveCamera(0, 0, -10);
        // 300
        camera.setDistance(400);
        // 70, 60, 0
        camera.turnCamera(new Vector(-70, -60, 18), new Vector(8, 20, 1760d / 18d));

        render = new Render() //
                .setImageWriter(new ImageWriter("TreeTest2", pixels, pixels)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();

        camera.moveCamera(90, 10, 5);
        // -20, 70, 5
        camera.setDistance(250);
        camera.turnCamera(new Vector(0, -14, -1), new Vector(0, -15d / 14, 15));

        render = new Render() //
                .setImageWriter(new ImageWriter("TreeTest3", pixels, pixels)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();


        camera.moveCamera(-20, -10, -5);

        camera.setDistance(150);
        camera.turnCamera(new Vector(0, 0, 1), -40);

        render = new Render() //
                .setImageWriter(new ImageWriter("TreeTest4", pixels, pixels)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();

        camera = new Camera(
                new Point3D(-70, -60, 10),
                new Vector(5, 5, 1),
                new Vector(-2.52, -2.52, 25.2))
                .setViewPlaneSize(250, 250)
                .setDistance(150);

        render = new Render() //
                .setImageWriter(new ImageWriter("TreeTest5", pixels, pixels)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();

    }
}


