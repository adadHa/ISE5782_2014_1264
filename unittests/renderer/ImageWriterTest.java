package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {

    @Test
    void writeToImage(){
        Camera camera = new Camera(new Point(0,0,0), new Vector(0,0,-1), new Vector(0,1,0))
                .setVPSize(800,500)
                .setVPDistance(10);
        ImageWriter imageWriter = new ImageWriter("images", 16, 10);
        Color background = Color.BLACK;
        Color grid = new Color(255,23,43);
        for (int i = 0; i < 16; i++){
            for (int j = 0; j < 10; j ++){
                imageWriter.writePixel(i, j, grid);
            }
        }

    }
}