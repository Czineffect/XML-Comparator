package xmlparser;

import java.awt.Color;
import java.util.Map;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

/**
 *
 * @author Colton Thompson
 */
public class XMLParserMain {

    /**
     * @param args the command line arguments
     */
    static int f1Counter = 0, f2Counter = 0;

    /**
     *
     * @param File f1
     * @param File f2
     * @throws Exception
     */
    public static void compareXML(File f1, File f2) throws Exception {
        XmlParser xmlF1 = new XmlParser(f1);
        XmlParser xmlF2 = new XmlParser(f2);
        xmlF1.displayFile();
        xmlF2.displayFile();
        for (String k : xmlF1.getMap().keySet()) {
            if (!xmlF1.getMap().get(k).equals(xmlF2.getMap().get(k))) {
                //Split String -- Node has current Element and Parent as the key 
                String[] splitNode = k.split("-");
                highlightText(splitNode[1], xmlF1);
                f1Counter++;

            }
        }

        for (String y : xmlF2.getMap().keySet()) {
            if (!xmlF2.getMap().get(y).equals(xmlF1.getMap().get(y))) {
                String[] splitNode = y.split("-");
                highlightText(splitNode[1], xmlF2);

                f2Counter++;
            }
        }

        xmlF1.setCounterLabel(f1Counter, xmlF2.getFileName().getName());

        xmlF2.setCounterLabel(f2Counter, xmlF1.getFileName().getName());

    }

    /**
     *
     * @param String text
     * @param XMLParser obj
     * @throws BadLocationException Purpose: Highlights a specific line of text in a JTextArea
     */
    public static void highlightText(String text, XmlParser obj) throws BadLocationException {
        Highlighter highlighter = obj.getTextArea().getHighlighter();
        HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
        int p0 = obj.getTextArea().getText().indexOf(text);
        int p1 = p0 + text.length();
        int value = p1 + 1;
        int p2 = obj.getTextArea().getText().indexOf("</"+text);
        int endValue = p2 - 1;
        System.out.println(p0 + " " );

        highlighter.addHighlight(p0, p1, painter); 
        
        if (value >= 0 && endValue >= 0) {
            highlighter.addHighlight(value, endValue, painter);
            highlighter.addHighlight(endValue, (endValue + text.length()), painter);
            highlighter.addHighlight((endValue+text.length()+1), (endValue+text.length()+text.length()), painter);
        }
    }

}
