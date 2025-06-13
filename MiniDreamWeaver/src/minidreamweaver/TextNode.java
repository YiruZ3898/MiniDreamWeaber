package minidreamweaver;

// Basic text node, for now, does not escape HTML.
// This might need enhancement later for proper HTML escaping.
public class TextNode extends HtmlElement {
    private String textContent;

    public TextNode(String text) {
        super("#text"); // Special tag name for text nodes
        this.textContent = text != null ? text : "";
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent != null ? textContent : "";
    }

    @Override
    public String generateHtml() {
        // Basic HTML escaping for common characters
        return textContent.replace("&", "&amp;")
                          .replace("<", "&lt;")
                          .replace(">", "&gt;")
                          .replace("\"", "&quot;")
                          .replace("'", "&#39;");
    }
}
