package renderer;

import elements.*;
import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
                10, 85, 1, true)
                .setViewPlaneSize(250, 250)
                .setDistance(300);

        int pixels = 250;

        scene.setBackground(Color.BLUE.add(Color.GREEN.reduce(2)).reduce(5));
        scene.setAmbientLight(new AmbientLight(Color.WHITE.reduce(5), 0.1));

        Color naturalGreen = new Color(Color.GREEN.add(Color.RED.reduce(10)).reduce(4));
        Color naturalGreen2 = naturalGreen.add(Color.GREEN.reduce(5));

        //region Tree1 points
        // create all relevant points
        double xMoveTree = 0;
        double yMoveTree = -5;
        double zMoveTree = 0;

        Point3D a = new Point3D(-14 + xMoveTree, 1 + yMoveTree, 1 + zMoveTree);
        Point3D b = new Point3D(1 + xMoveTree, -14 + yMoveTree, 1 + zMoveTree);
        Point3D c = new Point3D(14 + xMoveTree, 1 + yMoveTree, 1 + zMoveTree);
        Point3D d = new Point3D(1 + xMoveTree, 14 + yMoveTree, 1 + zMoveTree);
        Point3D f = new Point3D(-12 + xMoveTree, 1 + yMoveTree, 10 + zMoveTree);
        Point3D g = new Point3D(1 + xMoveTree, -12 + yMoveTree, 10 + zMoveTree);
        Point3D h = new Point3D(12 + xMoveTree, 1 + yMoveTree, 10 + zMoveTree);
        Point3D i = new Point3D(1 + xMoveTree, 12 + yMoveTree, 10 + zMoveTree);
        Point3D j = new Point3D(1 + xMoveTree, 1 + yMoveTree, 30 + zMoveTree);
        Point3D k = new Point3D(-10 + xMoveTree, 1 + yMoveTree, 20 + zMoveTree);
        Point3D l = new Point3D(1 + xMoveTree, -10 + yMoveTree, 20 + zMoveTree);
        Point3D m = new Point3D(10 + xMoveTree, 1 + yMoveTree, 20 + zMoveTree);
        Point3D n = new Point3D(1 + xMoveTree, 10 + yMoveTree, 20 + zMoveTree);
        Point3D o = new Point3D(1 + xMoveTree, 1 + yMoveTree, 40 + zMoveTree);
        Point3D p = new Point3D(1 + xMoveTree, 1 + yMoveTree, 20 + zMoveTree);
        //endregion

        //region Tree1 polygons
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
        //endregion

        //region color Tree1 polygons and add to scene's geometries
        // add all of them to a list to apply settings (e.g. color) to all of them easily
        List<Geometry> greenTriangles = Arrays.asList(
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

        for (Geometry geo : greenTriangles) {
            geo.setEmission(naturalGreen)
                    .setMaterial(new Material()
                            .setKd(0.01)
                            .setKs(0.0001)
                            .setShininess(2)
                            .setKr(0.01));
        }

        scene.geometries.addAll(greenTriangles);
        //endregion

        //region Tree2 points
        // create all relevant points
        xMoveTree = -40;
        yMoveTree = 5;
        zMoveTree = 0;

        a = new Point3D(-14 + xMoveTree, 1 + yMoveTree, 1 + zMoveTree);
        b = new Point3D(1 + xMoveTree, -14 + yMoveTree, 1 + zMoveTree);
        c = new Point3D(14 + xMoveTree, 1 + yMoveTree, 1 + zMoveTree);
        d = new Point3D(1 + xMoveTree, 14 + yMoveTree, 1 + zMoveTree);
        f = new Point3D(-12 + xMoveTree, 1 + yMoveTree, 10 + zMoveTree);
        g = new Point3D(1 + xMoveTree, -12 + yMoveTree, 10 + zMoveTree);
        h = new Point3D(12 + xMoveTree, 1 + yMoveTree, 10 + zMoveTree);
        i = new Point3D(1 + xMoveTree, 12 + yMoveTree, 10 + zMoveTree);
        j = new Point3D(1 + xMoveTree, 1 + yMoveTree, 30 + zMoveTree);
        k = new Point3D(-10 + xMoveTree, 1 + yMoveTree, 20 + zMoveTree);
        l = new Point3D(1 + xMoveTree, -10 + yMoveTree, 20 + zMoveTree);
        m = new Point3D(10 + xMoveTree, 1 + yMoveTree, 20 + zMoveTree);
        n = new Point3D(1 + xMoveTree, 10 + yMoveTree, 20 + zMoveTree);
        o = new Point3D(1 + xMoveTree, 1 + yMoveTree, 40 + zMoveTree);
        p = new Point3D(1 + xMoveTree, 1 + yMoveTree, 20 + zMoveTree);
        //endregion

        //region Tree2 polygons
        BottomPyramidBottom = new Polygon(a, b, c, d);
        // tree's triangles
        BottomPyramid1 = new Triangle(a, b, p);
        BottomPyramid2 = new Triangle(b, c, p);
        BottomPyramid3 = new Triangle(c, d, p);
        BottomPyramid4 = new Triangle(a, d, p);

        MiddlePyramid1 = new Triangle(f, g, j);
        MiddlePyramid2 = new Triangle(g, h, j);
        MiddlePyramid3 = new Triangle(h, i, j);
        MiddlePyramid4 = new Triangle(i, f, j);

        TopPyramid1 = new Triangle(k, l, o);
        TopPyramid2 = new Triangle(l, m, o);
        TopPyramid3 = new Triangle(m, n, o);
        TopPyramid4 = new Triangle(n, k, o);
        //endregion

        //region color Tree2 polygons and add to scene's geometries
        greenTriangles = Arrays.asList(
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

        for (Geometry geo : greenTriangles) {
            geo.setEmission(naturalGreen)
                    .setMaterial(new Material()
                            .setKd(0.01)
                            .setKs(0.0001)
                            .setShininess(2)
                            .setKr(0.01));
        }

        scene.geometries.addAll(greenTriangles);
        //endregion

        //region Tree3 points
        // create all relevant points
        xMoveTree = -40;
        yMoveTree = -20;
        zMoveTree = 0;

        a = new Point3D(-14 + xMoveTree, 1 + yMoveTree, 1 + zMoveTree);
        b = new Point3D(1 + xMoveTree, -14 + yMoveTree, 1 + zMoveTree);
        c = new Point3D(14 + xMoveTree, 1 + yMoveTree, 1 + zMoveTree);
        d = new Point3D(1 + xMoveTree, 14 + yMoveTree, 1 + zMoveTree);
        f = new Point3D(-12 + xMoveTree, 1 + yMoveTree, 10 + zMoveTree);
        g = new Point3D(1 + xMoveTree, -12 + yMoveTree, 10 + zMoveTree);
        h = new Point3D(12 + xMoveTree, 1 + yMoveTree, 10 + zMoveTree);
        i = new Point3D(1 + xMoveTree, 12 + yMoveTree, 10 + zMoveTree);
        j = new Point3D(1 + xMoveTree, 1 + yMoveTree, 30 + zMoveTree);
        k = new Point3D(-10 + xMoveTree, 1 + yMoveTree, 20 + zMoveTree);
        l = new Point3D(1 + xMoveTree, -10 + yMoveTree, 20 + zMoveTree);
        m = new Point3D(10 + xMoveTree, 1 + yMoveTree, 20 + zMoveTree);
        n = new Point3D(1 + xMoveTree, 10 + yMoveTree, 20 + zMoveTree);
        o = new Point3D(1 + xMoveTree, 1 + yMoveTree, 40 + zMoveTree);
        p = new Point3D(1 + xMoveTree, 1 + yMoveTree, 20 + zMoveTree);
        //endregion

        //region Tree3 polygons
        BottomPyramidBottom = new Polygon(a, b, c, d);
        // tree's triangles
        BottomPyramid1 = new Triangle(a, b, p);
        BottomPyramid2 = new Triangle(b, c, p);
        BottomPyramid3 = new Triangle(c, d, p);
        BottomPyramid4 = new Triangle(a, d, p);

        MiddlePyramid1 = new Triangle(f, g, j);
        MiddlePyramid2 = new Triangle(g, h, j);
        MiddlePyramid3 = new Triangle(h, i, j);
        MiddlePyramid4 = new Triangle(i, f, j);

        TopPyramid1 = new Triangle(k, l, o);
        TopPyramid2 = new Triangle(l, m, o);
        TopPyramid3 = new Triangle(m, n, o);
        TopPyramid4 = new Triangle(n, k, o);
        //endregion

        //region color Tree3 polygons and add to scene's geometries
        greenTriangles = Arrays.asList(
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

        for (Geometry geo : greenTriangles) {
            geo.setEmission(naturalGreen)
                    .setMaterial(new Material()
                            .setKd(0.01)
                            .setKs(0.0001)
                            .setShininess(2)
                            .setKr(0.01));
        }

        scene.geometries.addAll(greenTriangles);
        //endregion

        //region Tree4 points
        // create all relevant points
        xMoveTree = 5;
        yMoveTree = -20;
        zMoveTree = 0;

        a = new Point3D(-14 + xMoveTree, 1 + yMoveTree, 1 + zMoveTree);
        b = new Point3D(1 + xMoveTree, -14 + yMoveTree, 1 + zMoveTree);
        c = new Point3D(14 + xMoveTree, 1 + yMoveTree, 1 + zMoveTree);
        d = new Point3D(1 + xMoveTree, 14 + yMoveTree, 1 + zMoveTree);
        f = new Point3D(-12 + xMoveTree, 1 + yMoveTree, 10 + zMoveTree);
        g = new Point3D(1 + xMoveTree, -12 + yMoveTree, 10 + zMoveTree);
        h = new Point3D(12 + xMoveTree, 1 + yMoveTree, 10 + zMoveTree);
        i = new Point3D(1 + xMoveTree, 12 + yMoveTree, 10 + zMoveTree);
        j = new Point3D(1 + xMoveTree, 1 + yMoveTree, 30 + zMoveTree);
        k = new Point3D(-10 + xMoveTree, 1 + yMoveTree, 20 + zMoveTree);
        l = new Point3D(1 + xMoveTree, -10 + yMoveTree, 20 + zMoveTree);
        m = new Point3D(10 + xMoveTree, 1 + yMoveTree, 20 + zMoveTree);
        n = new Point3D(1 + xMoveTree, 10 + yMoveTree, 20 + zMoveTree);
        o = new Point3D(1 + xMoveTree, 1 + yMoveTree, 40 + zMoveTree);
        p = new Point3D(1 + xMoveTree, 1 + yMoveTree, 20 + zMoveTree);
        //endregion

        //region Tree4 polygons
        BottomPyramidBottom = new Polygon(a, b, c, d);
        // tree's triangles
        BottomPyramid1 = new Triangle(a, b, p);
        BottomPyramid2 = new Triangle(b, c, p);
        BottomPyramid3 = new Triangle(c, d, p);
        BottomPyramid4 = new Triangle(a, d, p);

        MiddlePyramid1 = new Triangle(f, g, j);
        MiddlePyramid2 = new Triangle(g, h, j);
        MiddlePyramid3 = new Triangle(h, i, j);
        MiddlePyramid4 = new Triangle(i, f, j);

        TopPyramid1 = new Triangle(k, l, o);
        TopPyramid2 = new Triangle(l, m, o);
        TopPyramid3 = new Triangle(m, n, o);
        TopPyramid4 = new Triangle(n, k, o);
        //endregion

        //region color Tree4 polygons and add to scene's geometries
        greenTriangles = Arrays.asList(
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

        for (Geometry geo : greenTriangles) {
            geo.setEmission(naturalGreen)
                    .setMaterial(new Material()
                            .setKd(0.01)
                            .setKs(0.0001)
                            .setShininess(2)
                            .setKr(0.01));
        }

        scene.geometries.addAll(greenTriangles);
        //endregion


        //region Tree5 points
        // create all relevant points
        xMoveTree = -15;
        yMoveTree = 10;;
        zMoveTree = 0;

        a = new Point3D(-14 + xMoveTree, 1 + yMoveTree, 1 + zMoveTree);
        b = new Point3D(1 + xMoveTree, -14 + yMoveTree, 1 + zMoveTree);
        c = new Point3D(14 + xMoveTree, 1 + yMoveTree, 1 + zMoveTree);
        d = new Point3D(1 + xMoveTree, 14 + yMoveTree, 1 + zMoveTree);
        f = new Point3D(-12 + xMoveTree, 1 + yMoveTree, 10 + zMoveTree);
        g = new Point3D(1 + xMoveTree, -12 + yMoveTree, 10 + zMoveTree);
        h = new Point3D(12 + xMoveTree, 1 + yMoveTree, 10 + zMoveTree);
        i = new Point3D(1 + xMoveTree, 12 + yMoveTree, 10 + zMoveTree);
        j = new Point3D(1 + xMoveTree, 1 + yMoveTree, 30 + zMoveTree);
        k = new Point3D(-10 + xMoveTree, 1 + yMoveTree, 20 + zMoveTree);
        l = new Point3D(1 + xMoveTree, -10 + yMoveTree, 20 + zMoveTree);
        m = new Point3D(10 + xMoveTree, 1 + yMoveTree, 20 + zMoveTree);
        n = new Point3D(1 + xMoveTree, 10 + yMoveTree, 20 + zMoveTree);
        o = new Point3D(1 + xMoveTree, 1 + yMoveTree, 40 + zMoveTree);
        p = new Point3D(1 + xMoveTree, 1 + yMoveTree, 20 + zMoveTree);
        //endregion

        //region Tree5 polygons
        BottomPyramidBottom = new Polygon(a, b, c, d);
        // tree's triangles
        BottomPyramid1 = new Triangle(a, b, p);
        BottomPyramid2 = new Triangle(b, c, p);
        BottomPyramid3 = new Triangle(c, d, p);
        BottomPyramid4 = new Triangle(a, d, p);

        MiddlePyramid1 = new Triangle(f, g, j);
        MiddlePyramid2 = new Triangle(g, h, j);
        MiddlePyramid3 = new Triangle(h, i, j);
        MiddlePyramid4 = new Triangle(i, f, j);

        TopPyramid1 = new Triangle(k, l, o);
        TopPyramid2 = new Triangle(l, m, o);
        TopPyramid3 = new Triangle(m, n, o);
        TopPyramid4 = new Triangle(n, k, o);
        //endregion

        //region color Tree5 polygons and add to scene's geometries
        // add all of them to a list to apply settings (e.g. color) to all of them easily
        greenTriangles = Arrays.asList(
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

        for (Geometry geo : greenTriangles) {
            geo.setEmission(naturalGreen)
                    .setMaterial(new Material()
                            .setKd(0.01)
                            .setKs(0.0001)
                            .setShininess(2)
                            .setKr(0.01));
        }

        scene.geometries.addAll(greenTriangles);
        //endregion

        //region create the trees' trunks and add them to the scene's geometries

        //region trunk for Tree1
        double xMoveTrunk = 0;
        double yMoveTrunk = -5;
        double zMoveTrunk = 0;

        Tube trunk = new Cylinder(
                new Ray(
                        new Point3D(0 + xMoveTrunk, 0 + yMoveTrunk, -10 + zMoveTrunk),
                        new Vector(0, 0, 1))
                , 2.5,
                15);
        trunk.setEmission(new Color(180, 83, 0).reduce(5));
        scene.geometries.add(trunk);
        //endregion

        //region trunk for Tree2
        xMoveTrunk = -40;
        yMoveTrunk = 5;
        zMoveTrunk = 0;
        trunk = new Cylinder(
                new Ray(
                        new Point3D(0 + xMoveTrunk, 0 + yMoveTrunk, -10 + zMoveTrunk),
                        new Vector(0, 0, 1))
                , 2.5,
                15);
        trunk.setEmission(new Color(180, 83, 0).reduce(5));
        scene.geometries.add(trunk);
        //endregion

        //region trunk for Tree3
        xMoveTrunk = -40;
        yMoveTrunk = -20;
        zMoveTrunk = 0;
        trunk = new Cylinder(
                new Ray(
                        new Point3D(0 + xMoveTrunk, 0 + yMoveTrunk, -10 + zMoveTrunk),
                        new Vector(0, 0, 1))
                , 2.5,
                15);
        trunk.setEmission(new Color(180, 83, 0).reduce(5));
        scene.geometries.add(trunk);
        //endregion

        //region trunk for Tree4
        xMoveTrunk = 5;
        yMoveTrunk = -20;
        zMoveTrunk = 0;
        trunk = new Cylinder(
                new Ray(
                        new Point3D(0 + xMoveTrunk, 0 + yMoveTrunk, -10 + zMoveTrunk),
                        new Vector(0, 0, 1))
                , 2.5,
                15);
        trunk.setEmission(new Color(180, 83, 0).reduce(5));
        scene.geometries.add(trunk);
        //endregion

        //region trunk for Tree5
        xMoveTrunk = -15;
        yMoveTrunk = 10;
        zMoveTrunk = 0;

        trunk = new Cylinder(
                new Ray(
                        new Point3D(0 + xMoveTrunk, 0 + yMoveTrunk, -10 + zMoveTrunk),
                        new Vector(0, 0, 1))
                , 2.5,
                15);
        trunk.setEmission(new Color(180, 83, 0).reduce(5));
        scene.geometries.add(trunk);
        //endregion


        //endregion

        //region add the ground plane
        Plane ground = new Plane(
                new Point3D(5, 10, -10),
                new Point3D(2, 3, -10),
                new Point3D(7, -3, -10)
        );

        ground.setEmission(Color.BLUE.add(Color.GREEN.reduce(3)).reduce(10))
                .setMaterial(new Material().setKd(0.025).setKs(0.00005).setKr(0.8));

        scene.geometries.add(ground);
        //endregion

        //region moon
        Point3D moonPoint = new Point3D(-10, -20, 30);
        Sphere sun = new Sphere(moonPoint, 10);
        sun.setEmission(Color.YELLOW.add(Color.RED.reduce(6)))
                .setMaterial(
                        new Material()
                                .setKd(0.005)
                                .setKs(0.00005)
                                .setShininess(50)
                                .setKt(0.9)
                );
        scene.geometries.add(sun);

        scene.lights.add(
                new PointLight(
                        Color.WHITE.reduce(2).add(Color.YELLOW).scale(3),
                        moonPoint)
        );
        //endregion

        Point3D starPoint = new Point3D(-40, -100, 30);
        sun = new Sphere(moonPoint, 10);
        sun.setEmission(Color.YELLOW)
                .setMaterial(
                        new Material()
                                .setKd(0.005)
                                .setKs(0.00005)
                                .setShininess(50)
                                .setKt(0.9)
                );
        scene.geometries.add(sun);


        scene.lights.add(
                new SpotLight(
                        new Color(Color.WHITE.scale(10)),
                        new Point3D(70, 60, 20),
                        new Vector(-5, -5, -1))
                        .setkL(0.0004)
                        .setkQ(0.0000006));

        scene.lights.add(
                new PointLight(
                        Color.WHITE.reduce(2).add(Color.YELLOW).scale(3),
                        moonPoint)
        );

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