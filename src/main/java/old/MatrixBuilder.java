package old;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class MatrixBuilder {
    public void create(String xml, int n, String type) {
        Document dom;


        // instance of a DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use factory to get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // create instance of DOM
            dom = db.newDocument();

            // create the root element
            Element problem = dom.createElement("travellingSalesmanProblemInstance");

            Element graph = null;
            if (type.equals("ASYMMETRIC")) {
                graph = asymmetric(dom, n);
            }
            else if (type.equals("SYMMETRIC")) {
                graph = symmetric(dom, n);
            }
            problem.appendChild(graph);

            dom.appendChild(problem);

            try {
                Transformer tr = TransformerFactory.newInstance().newTransformer();
                tr.setOutputProperty(OutputKeys.INDENT, "yes");
                tr.setOutputProperty(OutputKeys.METHOD, "xml");
                tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                // send DOM to file
                tr.transform(new DOMSource(dom),
                        new StreamResult(new FileOutputStream(xml)));

            } catch (TransformerException | IOException te) {
                System.out.println(te.getMessage());
            }
        } catch (ParserConfigurationException pce) {
            System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
        }
    }

    private Element asymmetric(Document dom, int n) {
        Element vertex;
        Element graph = dom.createElement("graph");

        Random random = new Random();

        for (int i =0; i<n; i++) {
            vertex = dom.createElement("vertex");
            for (int j = 0; j<n;j++) {
                if (i != j) {
                    Element edge = dom.createElement("edge");
                    double val = random.nextDouble(1000);
                    edge.setAttribute("cost",String.valueOf(val));
                    edge.appendChild(dom.createTextNode(String.valueOf(j)));
                    vertex.appendChild(edge);
                }
            }
            graph.appendChild(vertex);
        }
        return graph;
    }

    private Element symmetric(Document dom, int n) {
        Element vertex;
        Element graph = dom.createElement("graph");

        Random random = new Random();

        for (int i =0; i<n; i++) {
            vertex = dom.createElement("vertex");
            for (int j = 0; j<i;j++) {
                Element edge = dom.createElement("edge");
                double val = random.nextDouble(1000);
                edge.setAttribute("cost",String.valueOf(val));
                edge.appendChild(dom.createTextNode(String.valueOf(j)));
                vertex.appendChild(edge);
            }
            graph.appendChild(vertex);
        }
        return graph;
    }
}
