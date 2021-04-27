package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for renderer.ImageWriter class
 */
class ImageWriterTest {

    /**
     * Test method for {@link renderer.ImageWriter#writeToImage()}.
     */
    @Test
    void testWriteToImage() {
        ImageWriter imageWriter = new ImageWriter("testblue", 800, 500);
        for (int i = 0; i < 800; i++) {
            for (int j = 0; j < 500; j++) {
                imageWriter.writePixel(i, j, new Color(0, 0, 255));
            }

        }
        imageWriter.writeToImage();


    }

    /**
     * Test method for {@link renderer.ImageWriter#writeToImage()}.
     */
    @Test
    void testWriteToImage2() {
        ImageWriter imageWriter = new ImageWriter("testblue2", 800, 500);
        for (int i = 0; i < 800; i++) {
            for (int j = 0; j < 500; j++) {
                // 800/16 = 50
                if (i % 50 == 0) {
                    imageWriter.writePixel(i, j, Color.BLACK);
                }
                // 500/10 = 50
                else if (j % 50 == 0) {
                    imageWriter.writePixel(i, j, Color.BLACK);
                } else {
                    imageWriter.writePixel(i, j, new Color(java.awt.Color.BLUE));
                }
            }
        }
        imageWriter.writeToImage();
    }

}

