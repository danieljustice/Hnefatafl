import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.sun.crypto.provider.RSACipher;


// all of these need implemented
public class HnefataflGame implements GameInterface{

    public int frameHeight = 850;
    public int frameWidth = 850;
    public ClockTimer axeTimer = new ClockTimer();
    public ClockTimer shieldTimer = new ClockTimer();

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

	public JToolBar createToolBar(JPanel game){
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);

        JButton newButton = new JButton("New"); //no functions
        Hnefatafl.NewButtonListener newButtonListener = ((Hnefatafl)game).new NewButtonListener();
        newButton.addActionListener(newButtonListener);
        newButton.setName("New Button");
        tools.add(newButton);
        tools.addSeparator();

        JButton saveButton = new JButton("Save"); //no functions
        Hnefatafl.SaveButtonListener saveButtonListener = ((Hnefatafl)game).new SaveButtonListener();
        saveButton.addActionListener(saveButtonListener);
        saveButton.setName("Save Button");
        tools.add(saveButton);
        tools.addSeparator();

        JButton loadButton = new JButton("Load"); //no functions
        Hnefatafl.LoadButtonListener loadButtonListener = ((Hnefatafl)game).new LoadButtonListener();
        loadButton.addActionListener(loadButtonListener);
        loadButton.setName("Load Button");
        tools.add(loadButton);
        tools.addSeparator();

		JButton resignButton = new JButton("Resign");
        Hnefatafl.ResignButtonListener resignButtonListener = ((Hnefatafl)game).new ResignButtonListener();
		resignButton.addActionListener(resignButtonListener);
        resignButton.setName("Resign Button");
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
        turn.setName("Turn Text");
        tools.add(turn);
        

        //timer panel to be set up later
        JPanel _timerPanel = drawClock();
        _timerPanel.setName("Timer Panel");
        tools.add(_timerPanel, BorderLayout.PAGE_START);

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

        /**
    *
    */
	public JPanel drawClock(){

		JPanel _timerPanel = new JPanel();

		_timerPanel.setLayout(new GridLayout(1, 1));
		_timerPanel.add(axeTimer);
		_timerPanel.add(shieldTimer);
		_timerPanel.setVisible(true);
	    return _timerPanel;
	}
}