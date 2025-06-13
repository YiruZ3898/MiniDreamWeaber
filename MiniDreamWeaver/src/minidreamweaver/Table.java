package minidreamweaver;

public class Table extends ContainerTag {
    public Table() {
        super("table");
    }

    @Override
    public boolean canAddChild(HtmlElement element) {
        // Simplified: only TR. Could be extended for THEAD, TBODY, TFOOT, CAPTION
        return element instanceof Tr;
    }
}
