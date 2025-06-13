package minidreamweaver.ui;

import javax.swing.*;
import java.awt.*;
import minidreamweaver.HtmlDocument;
import minidreamweaver.*; // For all tag classes
import javax.swing.JOptionPane; // If not already there
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EditorFrame extends JFrame {

    private JToolBar toolBar;
    private JTextArea htmlDisplayArea;
    private HtmlDocument htmlDocument;
    private ContainerTag currentParent;

    private static final String[] TAG_NAMES = {
        "HTML", "BODY", "P", "IMG", "BR", "HR", "UL", "LI",
        "FONT", "TABLE", "TR", "TD", "A"
        // Note: HTML and BODY are structural and usually not added repeatedly via toolbar.
        // Consider how their addition/management is handled. For now, they are buttons.
    };

    public EditorFrame() {
        super("MiniDreamWeaver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Center the frame

        htmlDocument = new HtmlDocument();
        currentParent = htmlDocument.getBody(); // Default parent is the body

        initComponents();
        updateHtmlDisplay();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // ---- Menu Bar ----
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveMenuItem = new JMenuItem("Save...");
        saveMenuItem.addActionListener(e -> saveHtmlToFile());
        fileMenu.add(saveMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar); // Add menu bar to the JFrame

        // Initialize Toolbar
        toolBar = new JToolBar("HTML Tags");
        for (String tagName : TAG_NAMES) {
            JButton button = new JButton(tagName);
            button.addActionListener(e -> handleTagButtonClick(button.getText())); // Pass the tag name from button's text
            toolBar.add(button);
        }
        add(toolBar, BorderLayout.NORTH);

        // Initialize HTML Display Area
        htmlDisplayArea = new JTextArea();
        htmlDisplayArea.setEditable(false);
        htmlDisplayArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(htmlDisplayArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void updateHtmlDisplay() {
        if (htmlDocument != null && htmlDisplayArea != null) {
            htmlDisplayArea.setText(htmlDocument.generateHtml());
        }
    }

    // Placeholder for handling tag button clicks - to be implemented
    // private void handleTagButtonClick(String tagName) {
    //     System.out.println(tagName + " button clicked.");
    //     // Logic to create and add tag, then update display
    // }

    private void handleTagButtonClick(String tagName) {
        HtmlElement newElement = null;
        boolean updateCurrentParent = false;

        try {
            switch (tagName.toUpperCase()) {
                case "P":
                    P pTag = new P();
                    String pText = JOptionPane.showInputDialog(this, "Enter text for <p>:", "Paragraph Text", JOptionPane.PLAIN_MESSAGE);
                    if (pText != null) { // Add text only if user provided some
                        pTag.addChild(new minidreamweaver.TextNode(pText));
                    }
                    newElement = pTag;
                    break;
                case "IMG":
                    Img imgTag = new Img();
                    String src = JOptionPane.showInputDialog(this, "Enter src for <img>:", "Image Source", JOptionPane.PLAIN_MESSAGE);
                    if (src != null && !src.trim().isEmpty()) {
                        imgTag.setAttribute("src", src);
                    }
                    String alt = JOptionPane.showInputDialog(this, "Enter alt text for <img>:", "Alt Text", JOptionPane.PLAIN_MESSAGE);
                    if (alt != null && !alt.trim().isEmpty()) {
                        imgTag.setAttribute("alt", alt);
                    }
                    // width/height can be added similarly if desired
                    newElement = imgTag;
                    break;
                case "BR":
                    newElement = new minidreamweaver.Br();
                    break;
                case "HR":
                    newElement = new minidreamweaver.Hr();
                    // Example for attribute:
                    String widthHr = JOptionPane.showInputDialog(this, "Enter width for <hr> (e.g., '50%' or '100'):", "HR Width", JOptionPane.PLAIN_MESSAGE);
                    if (widthHr != null && !widthHr.trim().isEmpty()) {
                        ((minidreamweaver.Hr) newElement).setAttribute("width", widthHr);
                    }
                    break;
                case "UL":
                    newElement = new minidreamweaver.Ul();
                    updateCurrentParent = true;
                    break;
                case "LI":
                    Li liTag = new Li();
                    String liText = JOptionPane.showInputDialog(this, "Enter text for <li> (optional):", "List Item Text", JOptionPane.PLAIN_MESSAGE);
                    if (liText != null && !liText.trim().isEmpty()) {
                       liTag.addChild(new minidreamweaver.TextNode(liText));
                    }
                    // Could also ask if user wants to add other tags inside LI here, or rely on changing currentParent
                    newElement = liTag;
                    break;
                case "FONT":
                    FontTag fontTag = new FontTag();
                    String fontText = JOptionPane.showInputDialog(this, "Enter text for <font>:", "Font Text", JOptionPane.PLAIN_MESSAGE);
                     if (fontText != null && !fontText.trim().isEmpty()) {
                        fontTag.addChild(new minidreamweaver.TextNode(fontText));
                    }
                    String size = JOptionPane.showInputDialog(this, "Enter size for <font>:", "Font Size", JOptionPane.PLAIN_MESSAGE);
                    if (size != null && !size.trim().isEmpty()) {
                        fontTag.setAttribute("size", size);
                    }
                    String color = JOptionPane.showInputDialog(this, "Enter color for <font>:", "Font Color", JOptionPane.PLAIN_MESSAGE);
                    if (color != null && !color.trim().isEmpty()) {
                        fontTag.setAttribute("color", color);
                    }
                    newElement = fontTag;
                    updateCurrentParent = true; // Font can contain other elements potentially
                    break;
                case "TABLE":
                    newElement = new minidreamweaver.Table();
                    updateCurrentParent = true;
                    break;
                case "TR":
                    newElement = new minidreamweaver.Tr();
                    updateCurrentParent = true;
                    break;
                case "TD":
                    Td tdTag = new Td();
                    String tdText = JOptionPane.showInputDialog(this, "Enter text for <td> (optional):", "Table Cell Text", JOptionPane.PLAIN_MESSAGE);
                    if (tdText != null && !tdText.trim().isEmpty()) {
                        tdTag.addChild(new minidreamweaver.TextNode(tdText));
                    }
                    newElement = tdTag;
                    updateCurrentParent = true; // TD can contain other elements
                    break;
                case "A":
                    A aTag = new A();
                    String href = JOptionPane.showInputDialog(this, "Enter href for <a>:", "Link URL", JOptionPane.PLAIN_MESSAGE);
                    if (href != null && !href.trim().isEmpty()) {
                        aTag.setAttribute("href", href);
                    }
                    String linkText = JOptionPane.showInputDialog(this, "Enter link text for <a>:", "Link Text", JOptionPane.PLAIN_MESSAGE);
                    if (linkText != null && !linkText.trim().isEmpty()) {
                        aTag.addChild(new minidreamweaver.TextNode(linkText));
                    }
                    newElement = aTag;
                    // A tag can also contain other elements, so consider updateCurrentParent = true;
                    updateCurrentParent = true;
                    break;
                // HTML and BODY are special, usually not added this way after initial document setup.
                // For now, clicking them won't do much or could reset currentParent to body.
                case "HTML":
                    JOptionPane.showMessageDialog(this, "The <html> tag is the root of the document.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    return; // Don't add, just inform
                case "BODY":
                    currentParent = htmlDocument.getBody();
                    JOptionPane.showMessageDialog(this, "Current parent set to <body>.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    updateHtmlDisplay(); // Show current state
                    return; // Don't add a new body, just change context
                default:
                    JOptionPane.showMessageDialog(this, "Tag <" + tagName + "> handling not yet implemented.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
            }

            if (newElement != null) {
                // Ensure currentParent is a ContainerTag, though it should be by logic
                if (currentParent instanceof ContainerTag) {
                    ((ContainerTag)currentParent).addChild(newElement);
                    if (updateCurrentParent && newElement instanceof ContainerTag) {
                        currentParent = (ContainerTag) newElement;
                    }
                } else {
                     // Should not happen if currentParent is managed correctly (e.g. always body or a ContainerTag)
                    htmlDocument.addElementToBody(newElement);
                    if (updateCurrentParent && newElement instanceof ContainerTag) {
                        currentParent = (ContainerTag) newElement;
                    }
                }
            }
            updateHtmlDisplay();

        } catch (IllegalArgumentException iae) {
            JOptionPane.showMessageDialog(this, "Error adding element: " + iae.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(); // For developer console
        }
    }

    private void saveHtmlToFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save HTML File");
        // Suggest .html extension
        fileChooser.setSelectedFile(new File("untitled.html"));
        // Add a filter for .html files (optional but good practice)
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("HTML Files (*.html, *.htm)", "html", "htm"));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            // Ensure it has a .html extension if the filter is not strictly enforced or user overrides
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".html") && !filePath.toLowerCase().endsWith(".htm")) {
                fileToSave = new File(filePath + ".html");
            }

            try (FileWriter writer = new FileWriter(fileToSave)) {
                String htmlContent = htmlDocument.generateHtml();
                writer.write(htmlContent);
                JOptionPane.showMessageDialog(this, "File saved successfully to " + fileToSave.getAbsolutePath(), "File Saved", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage(), "Save Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace(); // For developer console
            }
        }
    }
}
