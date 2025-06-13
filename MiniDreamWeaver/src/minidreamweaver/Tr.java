package minidreamweaver;

public class Tr extends ContainerTag {
    public Tr() {
        super("tr");
    }

    @Override
    public boolean canAddChild(HtmlElement element) {
        return element instanceof Td;
    }
}
