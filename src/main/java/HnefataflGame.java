import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

// all of these need implemented
public class HnefataflGame implements GameInterface{

    public int frameHeight = 850;
    public int frameWidth = 850;


    /**
     * Take in a string a name for the game and returns a JFrame
     * named by that string. 
     * @param gameName String to set as name of the JFrame
     * @return JFrame the JFrame needed as a base to the game
     */

	public JFrame createGameFrame(String gameName){

        JFrame _frame = new JFrame(gameName);
        _frame.setSize(frameWidth, frameHeight);
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _frame.setVisible(true);
        return _frame;
    }


    /**
     * Creates the specific toolbar for Hnefatafl
     * @return JFrame the JFrame needed as a base to the game
     */

	public JToolBar createToolBar(){
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);

        JButton newButton = new JButton("New"); //no functions
        // ActionListener newButtonListener = new NewButtonListener();
        // newButton.addActionListener(newButtonListener);
        tools.add(newButton);
        tools.addSeparator();

        JButton saveButton = new JButton("Save"); //no functions
        // ActionListener saveButtonListener = new SaveButtonListener();
        // saveButton.addActionListener(saveButtonListener);
        tools.add(saveButton);
        tools.addSeparator();

        JButton loadButton = new JButton("Load"); //no functions
        // ActionListener loadButtonListener = new LoadButtonListener();
        // loadButton.addActionListener(loadButtonListener);
        tools.add(loadButton);
        tools.addSeparator();

		JButton resignButton = new JButton("Resign");
        // ActionListener resignButtonListener = new resignButtonListener();
		// resignButton.addActionListener(resignButtonListener);
		tools.add(resignButton); //no functions

        tools.addSeparator();
        tools.addSeparator();
        tools.addSeparator();
        tools.addSeparator();
        tools.addSeparator();
        tools.addSeparator();
        tools.addSeparator();
        tools.addSeparator();

        //A JLabel that shows whose turn it is
        JLabel turn = new JLabel("Axe Moves");
        tools.add(turn);
        
        //TODO
        //timer panel to be set up later
        //tools.add(_timerPanel, BorderLayout.PAGE_START);

        return tools;
    }

    /**
     * Take in a BufferedImage and returns a JPanel(BackgroundPanel)
     * with that image painted on it
     * @param backgroundImage BufferedImage to set image for JPanel
     * @return BackgroundImage (JPanel) to be used for the game
     */

    public JPanel createBackground(BufferedImage backgroundImage){

       	//creates new BufferedImage with correctly sized background image
        BufferedImage bImage = new BufferedImage(frameWidth, frameHeight, BufferedImage.TYPE_INT_RGB);
        
        Graphics2D painter = bImage.createGraphics();
        //draw the "image" to the "bufferedImage"
        painter.drawImage(backgroundImage, 0, 0, frameWidth, frameHeight, null);
        

       //Creates a backgroundPanel with bImage painted to it, the image should change size with it.
        BackgroundPanel backgroundPanel = new BackgroundPanel(bImage, 0, 0, 0);

        return backgroundPanel;
    }
}