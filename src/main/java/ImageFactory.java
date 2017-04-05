
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.omg.CORBA.SystemException;

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
            bufferedImage = ImageIO.read(new File(filePath));
            bufferedImage = new BufferedImage(425, 425, BufferedImage.TYPE_INT_ARGB);

            //this magically resizes the background image to the right size
            Graphics2D graphics2D = (Graphics2D)bufferedImage.getGraphics();
            graphics2D.scale(3.21, 3.21);
            graphics2D.drawImage(image, 0, 0, null);
            graphics2D.dispose();
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println(e);
        }
        return bufferedImage;
    }

}