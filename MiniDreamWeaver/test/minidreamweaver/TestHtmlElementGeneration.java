package minidreamweaver;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestHtmlElementGeneration {

    @Test
    public void testSimpleTagGeneration() {
        Br brTag = new Br();
        assertEquals("<br>", brTag.generateHtml());

        Hr hrTag = new Hr();
        hrTag.setAttribute("width", "50%");
        assertEquals("<hr width=\"50%\">", hrTag.generateHtml());
    }

    @Test
    public void testImgTagGeneration() {
        Img imgTag = new Img();
        imgTag.setAttribute("src", "test.jpg");
        imgTag.setAttribute("alt", "Test Image");
        assertEquals("<img src=\"test.jpg\" alt=\"Test Image\">", imgTag.generateHtml());
    }

    @Test
    public void testParagraphWithTextGeneration() {
        P pTag = new P();
        pTag.addChild(new TextNode("Hello World"));
        assertEquals("<p>Hello World</p>", pTag.generateHtml());
    }

    @Test
    public void testFontTagGeneration() {
        FontTag font = new FontTag();
        font.setAttribute("size", "3");
        font.setAttribute("color", "red");
        font.addChild(new TextNode("Important"));
        assertEquals("<font size=\"3\" color=\"red\">Important</font>", font.generateHtml());
    }

    @Test
    public void testNestedElementsGeneration() {
        P pTag = new P();
        pTag.addChild(new TextNode("This is "));
        A aTag = new A();
        aTag.setAttribute("href", "example.com");
        aTag.addChild(new TextNode("a link"));
        pTag.addChild(aTag);
        pTag.addChild(new TextNode("."));
        assertEquals("<p>This is <a href=\"example.com\">a link</a>.</p>", pTag.generateHtml());
    }

    @Test
    public void testTextNodeEscaping() {
        TextNode tn = new TextNode("<\"'&>");
        assertEquals("&lt;&quot;&amp;&apos;&gt;", tn.generateHtml());
    }
}
