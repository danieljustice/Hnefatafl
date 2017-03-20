import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
/**
*
*	
*
*
*
*
*
*/


public interface BackgroundInterface{

	/**
	* Loads in game data and compiles it into a JPanel 
	*
	* <p>  	Loads game data from a saved file.  The data will be 
	* compiled and represented on a JPanel that can be added to the Background JPanel
	*
	* @return 	JPanel on succesfully loaded game data.  Null on failure.
	*/
	public JPanel loadGameData();

	/**
	* Saves current game data to a file
	*
	* <p>  	Saves current game data to a file so that it can be retrieved at a later date.
	*
	* @return 	True on succes; False on Failure
	*/
	public boolean saveGameData();

	/**
	* Removes any JPanels attached to this Background JPanel
	*
	* <p>  	Removes any JPanels attached to this JPanel. The purpose is to remove the existing
	* game JPanel, potentially to replace it with a new one.
	*
	* @return  An arraty of JPanels on succesfully removing (a) JPanel(s).  Null on failure or lack of JPanels.
	*/
	public JPanel[] removeGamePanel();

	/**
	* Adds to the Background JPanel a JPanel containing the game 
	*
	* <p>  	Takes in a JPanel and adds it to the Background JPanel
	*
	* @param gamePanel The game to be placed on the background
	* @return 	True on succes; False on Failure
	*/
	public boolean addGamePanel(JPanel gamePanel);





}