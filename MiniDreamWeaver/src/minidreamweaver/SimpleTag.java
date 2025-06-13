package minidreamweaver;

public class SimpleTag extends HtmlElement {
    public SimpleTag(String tagName) {
        super(tagName);
    }

    @Override
    public String generateHtml() {
        return "<" + getTagName() + generateAttributesHtml() + ">";
    }
}
