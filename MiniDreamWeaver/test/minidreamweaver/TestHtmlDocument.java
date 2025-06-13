package minidreamweaver;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestHtmlDocument {

    @Test
    public void testDocumentInitialStructure() {
        HtmlDocument doc = new HtmlDocument();
        String expected = "<html><body></body></html>";
        assertEquals(expected, doc.generateHtml());
    }

    @Test
    public void testAddElementToBody() {
        HtmlDocument doc = new HtmlDocument();
        P p = new P();
        p.addChild(new TextNode("Test"));
        doc.addElementToBody(p);
        String expected = "<html><body><p>Test</p></body></html>";
        assertEquals(expected, doc.generateHtml());
    }

    @Test
    public void testAddElementToSpecificParent() {
        HtmlDocument doc = new HtmlDocument();
        P p = new P();
        doc.addElementToBody(p);

        Img img = new Img();
        img.setAttribute("src", "photo.png");
        // Add img inside p, which is already in body
        doc.addElement(p, img);

        String expected = "<html><body><p><img src=\"photo.png\"></p></body></html>";
        assertEquals(expected, doc.generateHtml());
    }
}
