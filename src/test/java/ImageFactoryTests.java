import static org.junit.Assert.*;
import javax.swing.JButton;
import org.junit.Test;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageFactoryTests{

    //Tests that given a valid path createBufferedImage() will not return a null value.
    @Test
    public void createBufferedImageGoodPathTest(){
        assertTrue(ImageFactory.createBufferedImage("src/assets/simpleBoard.png") != null);
    }

    //Tests that given an invalid path createBufferedImage() will return a null value.
    @Test
    public void createBufferedImageBadPathTest(){
        assertTrue(ImageFactory.createBufferedImage("src/assts") == null);
    }
    
    
}