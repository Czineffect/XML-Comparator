/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlparser;

import java.util.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.xml.parsers.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Colton Thompson
 * @purpose Takes the content of XML file and store in a LinkedHashMap
 *
 */
public class XmlParser {

    private Map<String, String> nodeContent = new LinkedHashMap<>();
    private JTextArea textarea = new JTextArea();
    private File filepath;
    private final Label diffCount = new Label();

    public XmlParser(File file) {
        try {

            filepath = file;
            Document doc = parseDocument(file);
            try {
                childRecursion(doc.getDocumentElement());
            } catch (Exception ex) {
                Logger.getLogger(XmlParser.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XmlParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XmlParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(XmlParser.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void childRecursion(Node node) {
        // do something with the current node instead of System.out
        if (node.getChildNodes().getLength() < 2) {
            //System.out.println(node.getNodeName() + "--" + node.getTextContent());
            nodeContent.put(node.getParentNode().getNodeName() + "-" + node.getNodeName(), node.getTextContent());
        }
        NodeList nodeList = node.getChildNodes(); //gets the child nodes that you need
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentNode = nodeList.item(i);

            if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                //call the recursion
                childRecursion(currentNode);

            }
        }
    }

    public String toXmlString(File filepath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filepath));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line.trim()).append("\n");
        }
        return sb.toString();
    }

    /*
    @param File filepath
    @return Document 
    @purpose Converts a File object into a Document object
    @throws ParserConfigurationException, IOException, SAXException
    */
    private Document parseDocument(File filename) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringComments(true);
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        Document docFile = docBuilder.parse(filename);
        docFile.getDocumentElement().normalize();
        return docFile;
    }

    /*
    @return Map
    */
    public Map<String, String> getMap() {
        return nodeContent;
    }

    /*
    @return JTextArea
    */
    public JTextArea getTextArea() {
        return textarea;
    }
    /**
     * 
     * @return file Name
     */
    public File getFileName(){
        return filepath; 
    }

   /*
    * Sets the label for each window
    @param int number of differneces in each file
    
    */
    public void setCounterLabel(int num, String filename) {
        diffCount.setText("" + num + " Differences " + this.filepath + "Compared To " + filename);
    }

    /*
    *Sets up all the components that is needed to open a new window
    * with the XML File 
    *
    */
    public void displayFile() throws Exception {
        Stage textWindow = new Stage();
        SwingNode snTextArea = new SwingNode();

        textarea.setLineWrap(true);
        textarea.setWrapStyleWord(true);
        textarea.setEditable(false);
        JScrollPane scroll = new JScrollPane(textarea);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //textarea.setText(XMLString(filepath));
        String sample = "";
        try {
            textarea.setText(toXmlString(filepath));

        } catch (IOException ex) {
            Logger.getLogger(XmlParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        snTextArea.setContent(scroll);

        BorderPane bp = new BorderPane();
        bp.setCenter(snTextArea);
        bp.setBottom(diffCount);
        //bp.getChildren().add(snTextArea);
        Scene scene = new Scene(bp, 600, 400);
        textWindow.setTitle(filepath.getName());
        textWindow.setScene(scene);
        textWindow.show();
    }

}
