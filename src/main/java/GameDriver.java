import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.omg.CORBA.SystemException;


public class GameDriver{
	//This class's sole purpose is to provide a main method to run the Hnefatafl game

	 /**
     * Main method
     */
    public static void main(String[] args) {
        HnefataflGame hGame = new HnefataflGame();
       
       //hgame - set up game
        JFrame frame = hGame.createGameFrame("Hnefatafl");
        JToolBar tools =  hGame.createToolBar();

        BufferedImage backgroundImage = ImageFactory.createBufferedImage("../../assets/simpleBoard.png");
        JPanel background = hGame.createBackground(backgroundImage);

        Dimension d =  background.getSize();
        System.out.println("Width: " + d.width + "\tHeight: " +  d.height);
        //WARNING
        //All buttons on ToolBar currently do not work - They do not have listeners
        frame.repaint();
        frame.add(tools, BorderLayout.PAGE_START);
        frame.add(background);
        frame.repaint();
        frame.pack();
        frame.setVisible(false);
        frame.setVisible(true);
        // Hnefatafl game = new Hnefatafl();
        // game.drawBoard();
    }
}