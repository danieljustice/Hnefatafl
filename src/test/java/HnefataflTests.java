import org.junit.Test;
import org.junit.Before;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import static junit.framework.Assert.*;

public class HnefataflTests {
    private Hnefatafl game;

    @Before
    public void beforeTests(){
    	game = new Hnefatafl();
    }
    @Test
    public void testInstanceOfHnefatafl(){
    	assertTrue(game != null);
    	assertTrue(game instanceof Hnefatafl);
    }

    @Test
    public void testImageIcons(){
    	assertTrue(game.defenseIcon != null);
    	assertTrue(game.axeIcon != null);
    	assertTrue(game.emptyImageIcon != null);
    }


    @Test
    public void testBoardDraws(){
        // Test that the board draws without error
        assertTrue(game.drawBoard());
    }
    @Test
    public void testForEmptyButton(){
    	//must set up the game on the drawnboard
    	game.setupGame(); 
    	JButton testButton = game._buttons[1][1];
    	assertTrue(testButton != null);
    	ImageIcon testImageIcon = (ImageIcon)testButton.getIcon();
    	String testDescription = testImageIcon.getDescription();
    	assertTrue(testDescription.equals("empty"));
    }
    @Test
    //EXPECT THIS TO BECOME OBSOLETE AT ONE POINT
    public void testClickCycle(){
    	//this test will click an empty tile three (3) times.  The tile should turn
    	//into an axe, then turn into an empty and then into a shield
    	
    	game.setupGame(); 
    	JButton testButton = game._buttons[1][1];
    	assertTrue(testButton != null);
    	ImageIcon testImageIcon = (ImageIcon)testButton.getIcon();
    	String testDescription = testImageIcon.getDescription();
    	assertTrue(testDescription.equals("empty"));
    	//first click changes it to an axe
    	testButton.doClick();
    	testImageIcon = (ImageIcon)testButton.getIcon();
    	testDescription = testImageIcon.getDescription();
    	assertTrue(testDescription.equals("axe"));
    	//second click turns it empty
    	testButton.doClick();
    	testImageIcon = (ImageIcon)testButton.getIcon();
    	testDescription = testImageIcon.getDescription();
    	assertTrue(testDescription.equals("empty"));
 		//third click turns it into a shield
    	testButton.doClick();
    	testImageIcon = (ImageIcon)testButton.getIcon();
    	testDescription = testImageIcon.getDescription();
    	assertTrue(testDescription.equals("shield"));

    }
}
