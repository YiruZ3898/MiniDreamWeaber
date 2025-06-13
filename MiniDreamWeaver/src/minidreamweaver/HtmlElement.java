package minidreamweaver;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class HtmlElement {
    protected String tagName;
    protected Map<String, String> attributes;

    public HtmlElement(String tagName) {
        this.tagName = tagName;
        this.attributes = new HashMap<>();
    }

    public String getTagName() {
        return tagName;
    }

    public void setAttribute(String name, String value) {
        this.attributes.put(name, value);
    }

    public String getAttribute(String name) {
        return this.attributes.get(name);
    }

    public Map<String, String> getAttributes() {
        return this.attributes;
    }

    protected String generateAttributesHtml() {
        if (attributes.isEmpty()) {
            return "";
        }
        return " " + attributes.entrySet().stream()
                .map(entry -> entry.getKey() + "=\"" + entry.getValue() + "\"")
                .collect(Collectors.joining(" "));
    }

    public abstract String generateHtml();
}
