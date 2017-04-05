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
        JToolBar tool =  hGame.createToolBar();
        BufferedImage backgroundImage = ImageFactory.createBufferedImage("src/assets/simpleBoard.png");
        JPanel panel =  hGame.createBackground(backgroundImage);

        frame.add(tool);
        frame.add(panel);


        Hnefatafl game = new Hnefatafl();
        game.drawBoard();
    }
}