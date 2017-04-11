
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.swing.ImageIcon;
import org.omg.CORBA.SystemException;
import java.util.*;
import javax.imageio.ImageReadParam;
import javax.imageio.stream.ImageInputStream;

public class ImageFactory{

    private ImageFactory(){

    }

    public static ImageIcon createImageIcon(String filePath, String description){
        ImageIcon imageIcon = null;
        try {
            imageIcon = new ImageIcon(ImageIO.read(new File(filePath)));
            imageIcon.setDescription(description);
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println(description + e);
        }
        return imageIcon;
    }

    public static BufferedImage createBufferedImage(String filePath){
        Image image = null;
        BufferedImage bufferedImage = null; 

        try {
            File file = new File(filePath);

            //Create a stream to read the image
            ImageInputStream iis = ImageIO.createImageInputStream(file);

            Iterator<ImageReader> iter = ImageIO.getImageReadersByFormatName("png");
            ImageReader imageReader = iter.next();
            imageReader.setInput(iis, true);
            ImageReadParam param = imageReader.getDefaultReadParam();
            image = imageReader.read(0, param);
            bufferedImage = new BufferedImage(image.getWidth(null),
                image.getHeight(null), BufferedImage.TYPE_INT_RGB);

             //using "painter" we can draw in to "bufferedImage"
             Graphics2D painter = bufferedImage.createGraphics();

            //draw the "image" to the "bufferedImage"
            painter.drawImage(image, null, null);

            ((ImageInputStream)imageReader.getInput()).close();
            imageReader.dispose();
        } catch (Exception e) {
            //TODO: handle exception
            bufferedImage = null;
            System.err.println("Error at BufferedImage: " + e);
        }
        return bufferedImage;
    }

}