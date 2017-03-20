import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
/**
*	This interface will govern the creation of the three (3) moving parts of a game:
*		A JFrame to hold everything
*		A ToolBar to control non-gameplay operations such as saving, loading and starting a new game
*		A background to be played on and to put the functionality on top of
*
*	How these three methods are called and put together to make a game is up to the developer.  Actual gameplay
*	functionality and buttons should be placed onto another jpanel/class and attached to the background
*	JPanel returned by createBackground(BufferedImage backgroundImage)
*
*/


public interface GameInterface{


	/**
	* Creates the JFrame for the game
	*
	* <p>  Creates a JFrame titled the value of the string passed in for gameName. 
	*
	* @param 	gameName The name of the game will be attributed to the JFrame
	* @return 	JFrame if succesful
	*
	*/
	public JFrame createGameFrame(String gameName);

	/**
	* Creates the ToolBar which can be added to the JFrame
	*
	* <p>  	Creates a ToolBar to be added to the JFrame.  It is expected that at least one tool bar will
	* be needed to handle non-gameplay operations such as saving, loading, restarting.  More may be needed
	*
	* @return 	JToolBar on success
	*
	*/
	public JToolBar createToolBar();

	/**
	* Creates the Background which can be added to the JFrame
	*
	* <p>  	Creates a Background to be added to the JFrame.  The background will not be the game, but will
	* instead encapsulate another JPanel/Class which will be the game and all its functionality
	*
	* @param 	backgroundImage The image to be put behind the game. For instance - a checker board for chess
	* @return 	JPanel on success
	*
	*/
	public JPanel createBackground(BufferedImage backgroundImage);

}