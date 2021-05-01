package scene;

import elements.AmbientLight;
import geometries.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;


// based on the guide in :
// https://www.w3schools.com/xml/dom_nodes.asp

public class XMLtoScene {

    static Scene scene;

    /**
     * get a node list contains planes,them to be in the geometries list of scene
     *
     * @param planeNL - node list from the XML file contains planes as nodes
     */
    public static void AddPlanesToScene(NodeList planeNL) {

        Geometries geometriesList = scene.geometries;

        for (int i = 0; i < planeNL.getLength(); i++) {

            Node planeN = planeNL.item(i);

            Element planeElem = (Element) planeN;

            String[] q0Str = (planeElem.getAttribute("q0")).split(" ");
            String[] normalStr = (planeElem.getAttribute("normal")).split(" ");

            Point3D q0 = new Point3D(
                    Double.parseDouble(q0Str[0]),
                    Double.parseDouble(q0Str[1]),
                    Double.parseDouble(q0Str[2])
            );

            Vector normal = new Vector(
                    Double.parseDouble(normalStr[0]),
                    Double.parseDouble(normalStr[1]),
                    Double.parseDouble(normalStr[2])
            );

            geometriesList.add(new Plane(q0, normal));
        }
    }

    /**
     * get a node list contains spheres,them to be in the geometries list of scene
     *
     * @param sphereNL - node list from the XML file contains spheres as nodes
     */
    public static void AddSpheresToScene(NodeList sphereNL) {

        Geometries geometriesList = scene.geometries;

        for (int i = 0; i < sphereNL.getLength(); i++) {

            Node sphereN = sphereNL.item(i);

            Element sphereElem = (Element) sphereN;

            String[] centerStr = (sphereElem.getAttribute("center")).split(" ");
            String radiusStr = sphereElem.getAttribute("radius");

            Point3D center = new Point3D(
                    Double.parseDouble(centerStr[0]),
                    Double.parseDouble(centerStr[1]),
                    Double.parseDouble(centerStr[2])
            );

            double radius = Double.parseDouble(radiusStr);

            geometriesList.add(new Sphere(center, radius));
        }
    }

    /**
     * get a node list contains triangles,them to be in the geometries list of scene
     *
     * @param triangleNL - node list from the XML file contains triangles as nodes
     */
    public static void AddTrianglesToScene(NodeList triangleNL) {

        Geometries geometriesList = scene.geometries;

        for (int i = 0; i < triangleNL.getLength(); i++) {

            Node triangleN = triangleNL.item(i);
            Element triangleElem = (Element) triangleN;

            String[] P0 = (triangleElem.getAttribute("p0")).split(" ");
            String[] P1 = (triangleElem.getAttribute("p1")).split(" ");
            String[] P2 = (triangleElem.getAttribute("p2")).split(" ");

            Triangle triangle = new Triangle(
                    new Point3D(
                            Double.parseDouble(P0[0]),
                            Double.parseDouble(P0[1]),
                            Double.parseDouble(P0[2])
                    ),
                    new Point3D(
                            Double.parseDouble(P1[0]),
                            Double.parseDouble(P1[1]),
                            Double.parseDouble(P1[2])
                    ),
                    new Point3D(
                            Double.parseDouble(P2[0]),
                            Double.parseDouble(P2[1]),
                            Double.parseDouble(P2[2])
                    )
            );

            geometriesList.add(triangle);
        }
    }


    public static Scene ReadScene(String file) {

        // read file using DOM

        File xmlDocument = new File(file);

        scene = new Scene(xmlDocument.getName());

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuild = null;
        try {
            dBuild = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        // read the root element
        try {
            Document doc = null;
            if (dBuild != null) {
                doc = dBuild.parse(xmlDocument);
            }

            // read nodes
            NodeList nodes = null;
            if (doc != null) {
                nodes = doc.getElementsByTagName("scene");
            }

            // the root element
            Node node = null;
            if (nodes != null) {
                node = nodes.item(0);
            }

            if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {

                // cast the root node to element
                Element elem = (Element) node;

                // read background color attribute from the element
                String[] backgroundRGB = (elem.getAttribute("background-color")).split(" ");
                var backgroundR = Double.parseDouble(backgroundRGB[0]);
                var backgroundG = Double.parseDouble(backgroundRGB[1]);
                var backgroundB = Double.parseDouble(backgroundRGB[2]);

                // set the background color
                scene.setBackground(new Color(backgroundR, backgroundG, backgroundB));


                // get the child element of scene contains the ambient light data
                NodeList amb = elem.getElementsByTagName("ambient-light");
                Node ambient = amb.item(0);

                if (ambient != null && ambient.getNodeType() == Node.ELEMENT_NODE) {

                    // cast the node to element
                    Element elem2 = (Element) ambient;

                    // read ambient light color attribute from the element
                    String[] ambientRGB = (elem2.getAttribute("color")).split(" ");

                    double ambientR = Double.parseDouble(ambientRGB[0]);
                    double ambientG = Double.parseDouble(ambientRGB[1]);
                    double ambientB = Double.parseDouble(ambientRGB[2]);

                    // set the ambient light color
                    scene.setAmbientLight(new AmbientLight(new Color(ambientR, ambientG, ambientB), 1));
                }

                // create empty geometries list to store all the geometries used in the scene
                Geometries geometriesList = new Geometries();

                // get the child element of scene contains the geometries
                NodeList geometries = elem.getElementsByTagName("geometries");
                Node geo = geometries.item(0);


                // cast geo to Element
                Element geoElem = (Element) geo;

                // read all lists of shapes and store in a node list
                NodeList sphereNL = geoElem.getElementsByTagName("sphere");
                NodeList triangleNL = geoElem.getElementsByTagName("triangle");
                NodeList planeNL = geoElem.getElementsByTagName("plane");

                AddTrianglesToScene(triangleNL);
                AddSpheresToScene(sphereNL);
                AddPlanesToScene(planeNL);

            }


        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }

//        System.out.println(scene);
        return scene;

    }
}
