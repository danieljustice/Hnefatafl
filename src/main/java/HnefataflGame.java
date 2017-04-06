import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

// all of these need implemented
public class HnefataflGame implements GameInterface{


    /**
     * Take in a string a name for the game and returns a JFrame
     * named by that string.  Currently the size of the JFrame is 
     * locked to 850 X 850
     * @param gameName String to set as name of the JFrame
     * @return JFrame the JFrame needed as a base to the game
     */

	public JFrame createGameFrame(String gameName){

        JFrame _frame = new JFrame(gameName);
        int frameWidth = 850;
        int frameHeight = 850;
        _frame.setSize(frameWidth, frameHeight);
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _frame.setVisible(true);
        return _frame;
    }

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

    public JPanel createBackground(BufferedImage backgroundImage){

        return null;
    }
}