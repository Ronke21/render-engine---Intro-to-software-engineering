package renderer;

import org.junit.jupiter.api.Test;

import elements.*;
import geometries.*;
import primitives.*;
import scene.Scene;

/**
 * Testing basic shadows
 *
 * @author Dan
 */
public class ShadowTests {
    private Scene scene = new Scene("Test scene");
    private Camera camera = new Camera(
            new Point3D(0, 0, 1000),
            new Vector(0, 0, -1),
            new Vector(0, 1, 0)) //
            .setViewPlaneSize(200, 200).setDistance(1000);

    /**
     * Produce a picture of a sphere and triangle with point light and shade
     */
    @Test
    public void sphereTriangleInitial() {
        scene.geometries.add( //
                new Sphere(new Point3D(0, 0, -200), 60) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)), //
                new Triangle(
                        new Point3D(-70, -40, 0),
                        new Point3D(-40, -70, 0),
                        new Point3D(-68, -68, -4)
                ) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)) //
        );

        scene.lights.add( //
                new SpotLight(
                        new Color(400, 240, 0),
                        new Point3D(-100, -100, 200),
                        new Vector(1, 1, -3)
                )
                        .setkL(1E-5).setkQ(1.5E-7)
        );

        Render render = new Render(). //
                setImageWriter(new ImageWriter("shadowSphereTriangleInitial", 400, 400)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Sphere-Triangle shading - move triangle up
     */
    @Test
    public void SphereTriangleMoveTriangleUp() {
        scene.geometries.add( //
                new Sphere(new Point3D(0, 0, -200), 60) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)), //

                new Triangle(
                        new Point3D(-63, -33, 0),
                        new Point3D(-33, -63, 0),
                        new Point3D(-61, -61, -4)
                ) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)) //
        );

        scene.lights.add( //
                new SpotLight(
                        new Color(400, 240, 0),
                        new Point3D(-100, -100, 200),
                        new Vector(1, 1, -3)
                )
                        .setkL(1E-5).setkQ(1.5E-7)
        );

        Render render = new Render(). //
                setImageWriter(new ImageWriter("shadowSphereTriangleTriUp", 400, 400)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Sphere-Triangle shading - move triangle down
     */
    @Test
    public void SphereTriangleMoveTriangleDown() {
        scene.geometries.add( //
                new Sphere(new Point3D(0, 0, -200), 60) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)), //

                new Triangle(
                        new Point3D(-50, -20, 0),
                        new Point3D(-20, -50, 0),
                        new Point3D(-48, -48, -4)
                ) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)) //
        );

        scene.lights.add( //
                new SpotLight(
                        new Color(400, 240, 0),
                        new Point3D(-100, -100, 200),
                        new Vector(1, 1, -3)
                )
                        .setkL(1E-5).setkQ(1.5E-7)
        );

        Render render = new Render(). //
                setImageWriter(new ImageWriter("shadowSphereTriangleTriDown", 400, 400)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Sphere-Triangle shading - move triangle close
     */
    @Test
    public void SphereTriangleMoveTriangleCloseLittle() {
        scene.geometries.add( //
                new Sphere(new Point3D(0, 0, -200), 60) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)), //

                new Triangle(
                        new Point3D(-70, -40, 0),
                        new Point3D(-40, -70, 0),
                        new Point3D(-68, -68, -4)
                ) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)) //
        );

        scene.lights.add( //
                new SpotLight(
                        new Color(400, 240, 0),
                        new Point3D(-90, -90, 163),
                        new Vector(1, 1, -3)
                )
                        .setkL(1E-5).setkQ(1.5E-7)
        );

        Render render = new Render(). //
                setImageWriter(new ImageWriter("shadowSphereTriangleCloseLittle", 400, 400)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Sphere-Triangle shading - move triangle close
     */
    @Test
    public void SphereTriangleMoveTriangleCloseBig() {
        scene.geometries.add( //
                new Sphere(new Point3D(0, 0, -200), 60) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)), //

                new Triangle(
                        new Point3D(-70, -40, 0),
                        new Point3D(-40, -70, 0),
                        new Point3D(-68, -68, -4)
                ) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)) //
        );

        scene.lights.add( //
                new SpotLight(
                        new Color(400, 240, 0),
                        new Point3D(-80, -80, 75),
                        new Vector(1, 1, -3)
                )
                        .setkL(1E-5).setkQ(1.5E-7)
        );

        Render render = new Render(). //
                setImageWriter(new ImageWriter("shadowSphereTriangleCloseBig", 400, 400)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();
    }


    /**
     * Produce a picture of a two triangles lighted by a spot light with a Sphere
     * producing a shading
     */
    @Test
    public void trianglesSphere() {
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.geometries.add( //
                new Triangle(
                        new Point3D(-150, -150, -115),
                        new Point3D(150, -150, -135),
                        new Point3D(75, 75, -150)
                ) //
                        .setMaterial(new Material().setKs(0.8).setShininess(60)),
                //
                new Triangle(
                        new Point3D(-150, -150, -115),
                        new Point3D(-70, 70, -140),
                        new Point3D(75, 75, -150)
                ) //
                        .setMaterial(new Material().setKs(0.8).setShininess(60)),
                //
                new Sphere(new Point3D(0, 0, -115), 30) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)) //
        );

        scene.lights.add( //
                new SpotLight(
                        new Color(700, 400, 400),
                        new Point3D(40, 40, 115),
                        new Vector(-1, -1, -4)
                ) //
                        .setkL(4E-4).setkQ(2E-5));

        Render render = new Render() //
                .setImageWriter(new ImageWriter("shadowTrianglesSphere", 600, 600)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();
    }
}