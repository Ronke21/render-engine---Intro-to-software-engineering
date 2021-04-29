package scene;

import elements.AmbientLight;
import geometries.Geometries;
import geometries.Geometry;
import geometries.Sphere;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import primitives.Color;
import primitives.Point3D;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;


// based on the guide in :
// https://www.w3schools.com/xml/dom_nodes.asp

public class XMLtoScene {

    public static Scene ReadScene(String file) {

        // read file using DOM

        File xmlDocument = new File(file);

        Scene scene = new Scene(xmlDocument.getName());

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
                System.out.println(backgroundR);
                System.out.println(backgroundG);
                System.out.println(backgroundB);

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
                    System.out.println(ambientR);
                    System.out.println(ambientG);
                    System.out.println(ambientB);

                    // set the ambient light color
                    scene.setAmbientLight(new AmbientLight(new Color(backgroundR, backgroundG, backgroundB), 1));
                }


                // get the child element of scene contains the geometries
                NodeList geometries = elem.getElementsByTagName("geometries");
                Node geo = geometries.item(0);

                // cast geo to Element
                Element geoElem = (Element) geo;

                NodeList geoElemChildren = geoElem.getChildNodes();

                // create empty geometries list to store all the geometries used in the scene
                Geometries geometriesList = new Geometries();

                Node tempNode;
                Element tempElem;

                // loop on all the elements and add them to the list
                for (int i = 0; i < geoElemChildren.getLength(); i++) {

                    tempNode = geoElemChildren.item(i);
                    tempElem = (Element) tempNode;

                    switch (tempElem.getTagName()) {

                        case "sphere": {

                            double rad = Double.parseDouble(tempElem.getAttribute("radius"));

                            String[] coordinates = tempElem.getAttribute("center").split(" ");

                            Point3D center = new Point3D(
                                    Double.parseDouble(coordinates[0]),
                                    Double.parseDouble(coordinates[1]),
                                    Double.parseDouble(coordinates[2])
                            );

                            Sphere sphere = new Sphere(center, rad);

                            geometriesList.add(sphere);

                            break;
                        }

                    }

                }

                scene.setGeometries(geometriesList);
            }



        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }

        System.out.println(scene);
        return scene;

    }
}
