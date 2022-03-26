package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {

    @Test
    void writeToImage() {
        Camera camera = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVPSize(16, 10)
                .setVPDistance(10);
        ImageWriter imageWriter = new ImageWriter("images", 800, 500);
        Color background = Color.BLACK;
        Color grid = new Color(255, 23, 43);
        double squreLength = imageWriter.getNx()/16;
        for (int i = 0; i < imageWriter.getNy(); i++) {
            for (int j = 0; j < imageWriter.getNx(); j++) {
                if (j % squreLength == 0 ||
                        i % squreLength == 0)
                    imageWriter.writePixel(j, i, grid);
                else
                    imageWriter.writePixel(j, i, background);
            }
        }
        imageWriter.writeToImage();
    }
}