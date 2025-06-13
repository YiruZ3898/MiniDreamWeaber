package minidreamweaver;

public class HtmlDocument {
    private Html rootElement;
    private Body bodyElement; // Convenience reference to the body

    public HtmlDocument() {
        this.rootElement = new Html();
        this.bodyElement = new Body();
        this.rootElement.addChild(this.bodyElement);
    }

    public Html getRootElement() {
        return rootElement;
    }

    public Body getBody() {
        return bodyElement;
    }

    /**
     * Adds an element as a direct child of the BODY tag.
     * @param element The HtmlElement to add.
     */
    public void addElementToBody(HtmlElement element) {
        if (this.bodyElement != null && element != null) {
            this.bodyElement.addChild(element);
        }
    }

    /**
     * Adds a child element to a specified parent element within the document.
     * Note: This is a generic adder. Specific validation (e.g., UL only takes LI)
     * will be handled in a later validation step or by more specific methods.
     * @param parent The parent ContainerTag to add the child to.
     * @param child The HtmlElement to add as a child.
     */
    public void addElement(ContainerTag parent, HtmlElement child) {
        if (parent != null && child != null) {
            parent.addChild(child);
            // Later: Could add logic here to ensure 'parent' is actually part of this document.
        }
    }

    /**
     * Generates the full HTML content of the document.
     * @return A string containing the HTML document.
     */
    public String generateHtml() {
        if (rootElement != null) {
            return rootElement.generateHtml();
        }
        return ""; // Should not happen with current constructor
    }

    // Placeholder for a main method for very basic testing
    public static void main(String[] args) {
        HtmlDocument doc = new HtmlDocument();

        P paragraph = new P();
        paragraph.addChild(new TextNode("This is a simple paragraph."));
        doc.addElementToBody(paragraph);

        Img image = new Img();
        image.setAttribute("src", "image.jpg");
        image.setAttribute("alt", "A test image");
        doc.addElementToBody(image);

        System.out.println(doc.generateHtml());
        // Expected output: <html><body><p>This is a simple paragraph.</p><img src="image.jpg" alt="A test image"></body></html>
    }
}
