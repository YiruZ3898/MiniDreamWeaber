package minidreamweaver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ContainerTag extends HtmlElement {
    protected List<HtmlElement> children;

    public ContainerTag(String tagName) {
        super(tagName);
        this.children = new ArrayList<>();
    }

    /**
     * Checks if a child element can be legally added to this container.
     * Subclasses should override this to enforce specific content models.
     * @param element The element to check.
     * @return true if the element can be added, false otherwise.
     */
    public boolean canAddChild(HtmlElement element) {
        return true; // Default: any child is allowed
    }

    public void addChild(HtmlElement element) {
        if (element != null) {
            if (!canAddChild(element)) {
                throw new IllegalArgumentException("Element <" + element.getTagName() + "> cannot be added to <" + this.getTagName() + ">.");
            }
            this.children.add(element);
        }
    }

    public List<HtmlElement> getChildren() {
        return this.children;
    }

    @Override
    public String generateHtml() {
        StringBuilder contentBuilder = new StringBuilder();
        for (HtmlElement child : children) {
            contentBuilder.append(child.generateHtml());
        }
        return "<" + getTagName() + generateAttributesHtml() + ">" +
               contentBuilder.toString() +
               "</" + getTagName() + ">";
    }
}
