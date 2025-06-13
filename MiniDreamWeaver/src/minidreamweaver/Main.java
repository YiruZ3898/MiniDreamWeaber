package minidreamweaver;

import javax.swing.SwingUtilities;
import minidreamweaver.ui.EditorFrame; // Import the new UI class

public class Main {
    public static void main(String[] args) {
        System.out.println("MiniDreamWeaver Application Starting...");
        // testValidation(); // Can be commented out or kept for console validation checks

        // Initialize and show GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            EditorFrame editorFrame = new EditorFrame();
            editorFrame.setVisible(true);
        });
    }

    // testValidation() method remains unchanged from the previous step
    public static void testValidation() {
        System.out.println("\n--- Testing Content Model Validation ---");

        Ul ul = new Ul();
        Li li = new Li();
        P p = new P();
        ul.addChild(li);
        System.out.println("Successfully added <li> to <ul>.");
        try {
            ul.addChild(p);
            System.err.println("Error: Allowed adding <p> to <ul>!");
        } catch (IllegalArgumentException e) {
            System.out.println("Correctly blocked adding <p> to <ul>: " + e.getMessage());
        }

        Tr tr = new Tr();
        Td td = new Td();
        tr.addChild(td);
        System.out.println("Successfully added <td> to <tr>.");
        try {
            tr.addChild(new P());
            System.err.println("Error: Allowed adding <p> to <tr>!");
        } catch (IllegalArgumentException e) {
            System.out.println("Correctly blocked adding <p> to <tr>: " + e.getMessage());
        }

        Table table = new Table();
        table.addChild(new Tr());
        System.out.println("Successfully added <tr> to <table>.");
        try {
            table.addChild(new Td());
            System.err.println("Error: Allowed adding <td> to <table>!");
        } catch (IllegalArgumentException e) {
            System.out.println("Correctly blocked adding <td> to <table>: " + e.getMessage());
        }

        System.out.println("\n--- Validation Test Complete ---");

        HtmlDocument doc = new HtmlDocument();
        P para1 = new P();
        para1.addChild(new TextNode("Hello World!"));
        doc.addElementToBody(para1);

        Table myTable = new Table();
        Tr row1 = new Tr();
        Td cell1 = new Td();
        cell1.addChild(new TextNode("Cell 1"));
        row1.addChild(cell1);
        myTable.addChild(row1);
        doc.addElementToBody(myTable);

        System.out.println("\nGenerated HTML from Document:");
        System.out.println(doc.generateHtml());
    }
}
