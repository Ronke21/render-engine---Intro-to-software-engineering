package renderer;

import elements.*;
import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import java.util.Arrays;
import java.util.List;

public class MP1 {
    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void TreeTestMP1() {

        Scene scene = new Scene("Test scene");

        // need to aim the camera
        Camera camera = new Camera(
                new Point3D(70, 60, 10),
                new Vector(-5, -5, -1),
                new Vector(-2.52, -2.52, 25.2),
                1.5, 65, 13, true)
                .setViewPlaneSize(250, 250)
                .setDistance(300);

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

        Tube trunk = new Tube(new Ray(new Point3D(0, 0, 0), new Vector(0, 0, -10)), 2.5);

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
                sun,
                new Sphere(new Point3D(-100, -75, 0), 30).setEmission(Color.RED.reduce(3))
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
                .setImageWriter(new ImageWriter("TreeTestDOF_MP1", pixels, pixels)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();
    }


    @Test
    public void MP1() {
        Scene scene = new Scene("Test scene");

        Camera camera = new Camera(
                new Point3D(0, 0, 1000),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0),
                25, 200, 36, true
        ) //
                .setViewPlaneSize(150, 150) //
                .setDistance(1000);

        Camera camera2 = new Camera(
                new Point3D(0, 0, 1000),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0)
        ) //
                .setViewPlaneSize(150, 150) //
                .setDistance(1000);

        scene.geometries.add(new Sphere(new Point3D(-30, 0, -50), 30) //
                .setEmission(Color.BLUE.reduce(2)) //
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));

        scene.geometries.add(new Sphere(new Point3D(-5, 0, 750), 6) //
                .setEmission(Color.RED.reduce(2)) //
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));

        scene.geometries.add(new Sphere(new Point3D(0, 0, 950), 1) //
                .setEmission(Color.GREEN.reduce(2)) //
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));


        scene.lights.add(
                new DirectionalLight(
                        Color.YELLOW,
                        new Vector(1, 1.5, -1.5)));

        int PIXELS = 500;

        ImageWriter imageWriter = new ImageWriter("MP1TestDOF", PIXELS, PIXELS);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera.setDistance(750)) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();

        imageWriter = new ImageWriter("MP1TestNODOF", PIXELS, PIXELS);
        render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera2.setDistance(750)) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();
    }
}
