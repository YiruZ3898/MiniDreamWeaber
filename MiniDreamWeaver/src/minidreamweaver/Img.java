package minidreamweaver;

public class Img extends SimpleTag {
    public Img() {
        super("img");
        // Common attributes: src, width, height, alt
        // User will set them via setAttribute("src", "path/to/image.jpg")
    }
}
