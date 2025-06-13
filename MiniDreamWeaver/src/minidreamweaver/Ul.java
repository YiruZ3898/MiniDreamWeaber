package minidreamweaver;

public class Ul extends ContainerTag {
    public Ul() {
        super("ul");
    }

    @Override
    public boolean canAddChild(HtmlElement element) {
        return element instanceof Li;
    }
}
