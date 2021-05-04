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

    /**
     * function to read an attribute contains color and extract the color in our primitives.Color format
     *
     * @param node      - the node which we want to extract from
     * @param attribute - the name of the wanted attribute we are looking for
     * @return the color in the attribute in the node in primitives.Color format
     */
    public static Color NodeToColor(Node node, String attribute) {

        // cast the root node to element
        Element elem = (Element) node;

        // read background color attribute from the element
        String[] RGB = (elem.getAttribute(attribute)).split(" ");
        var R = Double.parseDouble(RGB[0]);
        var G = Double.parseDouble(RGB[1]);
        var B = Double.parseDouble(RGB[2]);

        return new Color(R, G, B);
    }

    public static Scene ReadScene(String file) {

        //region Read file safely
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

        Document doc = null;
        if (dBuild != null) {
            try {
                doc = dBuild.parse(xmlDocument);
            } catch (SAXException | IOException e) {
                e.printStackTrace();
            }
        }
        //endregion

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

            // set the background color
            Color BackgroundColor = NodeToColor(node, "background-color");
            scene.setBackground(BackgroundColor);

            // set the ambient light color
            NodeList ambientNL = ((Element) node).getElementsByTagName("ambient-light");
            Node nodeAmbientLight = null;
            if (ambientNL != null) {
                nodeAmbientLight = ambientNL.item(0);
            }
            Color ambientColor = NodeToColor(nodeAmbientLight, "color");
            scene.setAmbientLight(new AmbientLight(ambientColor, 1));
        }

        // create empty geometries list to store all the geometries used in the scene
        Geometries geometriesList = new Geometries();

        // get the child element of scene contains the geometries
        NodeList geometries = null;
        if (node != null) {
            geometries = ((Element) node).getElementsByTagName("geometries");
        }
        Node geo = null;
        if (geometries != null) {
            geo = geometries.item(0);
        }


        // cast geo to Element
        Element geoElem = (Element) geo;

        // read all lists of shapes and store in a node list
        NodeList sphereNL = geoElem.getElementsByTagName("sphere");
        NodeList triangleNL = geoElem.getElementsByTagName("triangle");
        NodeList planeNL = geoElem.getElementsByTagName("plane");

        AddTrianglesToScene(triangleNL);
        AddSpheresToScene(sphereNL);
        AddPlanesToScene(planeNL);

        return scene;

    }
}
