package minidreamweaver;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestContentModelValidation {

    @Test(expected = IllegalArgumentException.class)
    public void testUlCannotAddP() {
        Ul ul = new Ul();
        ul.addChild(new P());
    }

    @Test
    public void testUlCanAddLi() {
        Ul ul = new Ul();
        ul.addChild(new Li());
        assertEquals(1, ul.getChildren().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTrCannotAddP() {
        Tr tr = new Tr();
        tr.addChild(new P());
    }

    @Test
    public void testTrCanAddTd() {
        Tr tr = new Tr();
        tr.addChild(new Td());
        assertEquals(1, tr.getChildren().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTableCannotAddP() {
        Table table = new Table();
        table.addChild(new P());
    }

    @Test
    public void testTableCanAddTr() {
        Table table = new Table();
        table.addChild(new Tr());
        assertEquals(1, table.getChildren().size());
    }
}
