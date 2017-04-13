import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.omg.CORBA.SystemException;


public class GameDriver{
    public static HnefataflGame hGame = new HnefataflGame();
    public static JFrame gameFrame;
	//This class's sole purpose is to provide a main method to run the Hnefatafl game

	 /**
     * Main method
     */
    public static void main(String[] args) {
        
       
       //hgame - set up game
        gameFrame= hGame.createGameFrame("Hnefatafl");
        

        BufferedImage backgroundImage = ImageFactory.createBufferedImage("src/assets/simpleBoard.png");
        JPanel background = hGame.createBackground(backgroundImage);
        Hnefatafl game = new Hnefatafl();
        JToolBar tools =  hGame.createToolBar(game);
        JPanel gamePanel = game.setupGame();
        gamePanel.setOpaque(false);
        Component[] components = tools.getComponents();
        for(int i = 0; i < components.length; i ++){
            System.out.println("Component " + i + " tag is " + components[i]);
        }
        // System.out.println("Toolbar comps: " + tools.getComponents());
        background.add(gamePanel);
        //WARNING
        //All buttons on ToolBar currently do not work - They do not have listeners
        gameFrame.repaint();
        gameFrame.add(tools, BorderLayout.PAGE_START);
        gameFrame.add(background);
        gameFrame.repaint();
        gameFrame.pack();
        gameFrame.setVisible(false);
        gameFrame.setVisible(true);
        
        // game.drawBoard();
    }


    public static void saveGame(Hnefatafl game) {
        try{
            JFileChooser fileChooser = new JFileChooser();
            // Only executes if the user does not cancel. No change to game state regardless.
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);

                oos.writeObject(game);

                oos.close();
                fos.close();
                JOptionPane.showMessageDialog(null, "File saved!");
            } else {
                JOptionPane.showMessageDialog(null, "Operation canceled.");
            }
        } catch(IOException ex) {
            System.out.println("File Writing Error!");
        }
    }

    public static void loadGame(){
        try{
            JFileChooser fileChooser = new JFileChooser();
            // Only executes if the user does not cancel. Otherwise game state is not changed.
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                FileInputStream fip = new FileInputStream(file);
                ObjectInputStream oip = new ObjectInputStream(fip);

                Hnefatafl game = (Hnefatafl)oip.readObject();

                oip.close();
                fip.close();
                JOptionPane.showMessageDialog(null, "File loaded!");
                gameFrame.add(game);

            } else {
                JOptionPane.showMessageDialog(null, "Operation canceled.");
            }

        }catch(IOException ex) {
            System.out.println("File Reading Error!");
        } catch(ClassNotFoundException cex) {
            System.out.println("Class Not Found!");
        }
    }


}