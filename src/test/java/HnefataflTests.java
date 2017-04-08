import static org.junit.Assert.*;
import javax.swing.JButton;
import org.junit.Test;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class HnefataflTests extends Hnefatafl{
    private Hnefatafl game = new Hnefatafl();
	private GameLogic gamelogic = new GameLogic();

    //Basic Test to verify that we can create an instance of Hnefatafl
	@Test
    public void testInstanceOfHnefatafl(){
    	assertTrue(game != null);
    	assertTrue(game instanceof Hnefatafl);
    }
    //Test that we are correctly bringing in ImageIcons for player pieces
    @Test
    public void testImageIcons(){
    	assertTrue(game.defenseIcon != null);
    	assertTrue(game.axeIcon != null);
    	assertTrue(game.emptyImageIcon != null);
    }
    //Test that a board successfully renders itself
    @Test
    public void testBoardDraws(){
        // Test that the board draws without error
        assertTrue(game.drawBoard());
    }
    
    //Test that there is an empty button where it is expected to be
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
    
    /*
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
    */


    /**
     * Tests that a non-King piece is destroyed when surrounded
     */
	@Test
	public void attackPiecesTest() {
		Hnefatafl h = new Hnefatafl();
		GameLogic g = new GameLogic();
		JButton jbutton = h._buttons[2][2];
		assertEquals(h._buttons, g.attackPieces(jbutton, h.emptyImageIcon, h.kingIcon, h.axeIcon, h.defenseIcon, h.gameWidth, h.gameHeight, h._buttons));
    
	}
	
	/*
	 * Tests that piecesLeft() accurately recognizes when a team has no more pieces
	 */
	@Test
	public void piecesLeftTest() {
		Hnefatafl h = new Hnefatafl();
		GameLogic g = new GameLogic();
		assertEquals(0, g.piecesLeft(h.gameWidth, h.gameHeight, h.axeIcon, h.kingIcon, h._buttons));;
	}

	/**
	 * Test that getXandY() returns the expected values for a jbutton
	 */
	@Test
	public void GetXYTest() {
		Hnefatafl h = new Hnefatafl();
		GameLogic g = new GameLogic();
		JButton jbutton = h._buttons[0][0];
		int[] xy = g.getXandY(jbutton, h.gameWidth, h.gameHeight, h._buttons);
		int[] testvalues = new int[]{ 0, 0 };
		assertEquals(xy[0],testvalues[0]);
		assertEquals(xy[1],testvalues[1]);
	}
	
	/**
	 * This test verifies that the space is occupied
	 */
	@Test
	public void IsSpaceOccupiedTestOccupied() {
		Hnefatafl h = new Hnefatafl();
		GameLogic g = new GameLogic();
		h.setupGame();
		h._buttons[0][2] = new JButton(axeIcon);
		assertTrue(g.isSpaceOccupied(new int[] {0,2}, h._buttons ));
		
    
	}
	
	/**
	 * This test verifies that the space is not occupied
	 */
	@Test
	public void IsSpaceOccupiedTestNotOccupied() {
		Hnefatafl h = new Hnefatafl();
		GameLogic g = new GameLogic();
		h.setupGame();
		h._buttons[0][0] = new JButton(emptyImageIcon);
		assertFalse(g.isSpaceOccupied(new int[] {0,0}, h._buttons ));
	}
	
	/**
	 * Tests that isValidMove() does not allow a piece to move to its current spot
	 */
	@Test
	public void IsValidMoveCurrentLocationTest(){
		Hnefatafl h = new Hnefatafl();
		GameLogic g = new GameLogic();
		int[] start = new int[] { 1, 1 };
		int[] dest = new int[] { 1, 1 };
		assertFalse(g.isValidMove(start, dest, false, h._buttons));
	}
	
	/**
	 * Tests that isValidMove() does not allow a piece to move diagonally
	 */
	@Test
	public void IsValidMoveDiagonalMoveTest(){
		Hnefatafl h = new Hnefatafl();
		GameLogic g = new GameLogic();
		int[] start = new int[] { 0, 0 };
		int[] dest = new int[] { 1, 1 };
		assertFalse(g.isValidMove(start, dest, false, h._buttons));
	}
	
	/**
	 * Tests that isValidMove() does not allow a piece to move left if it is not valid
	 */
	@Test
	public void IsValidMoveLeftMoveTest(){
		Hnefatafl h = new Hnefatafl();
		GameLogic g = new GameLogic();
		int[] start = new int[] { 0, 0 };
		int[] dest = new int[] { 1, 0 };
		assertTrue(g.isValidMove(start, dest, false, h._buttons));
	}
	
	/**
	 * Tests that isValidMove() does not allow a piece to move right if it is not valid
	 */
	@Test
	public void IsValidMoveRightMoveTest(){
		Hnefatafl h = new Hnefatafl();
		GameLogic g = new GameLogic();
		int[] start = new int[] { 2, 0 };
		int[] dest = new int[] { 1, 0 };
		assertTrue(g.isValidMove(start, dest, false, h._buttons));
	}
	
	/**
	 * Tests that isValidMove() does not allow a piece to move down if it is not valid
	 */
	@Test
	public void IsValidMoveDownMoveTest(){
		Hnefatafl h = new Hnefatafl();
		GameLogic g = new GameLogic();
		int[] start = new int[] { 0, 1 };
		int[] dest = new int[] { 0, 2 };
		assertTrue(g.isValidMove(start, dest, false, h._buttons));
	}
	
	/**
	 * Tests that isValidMove() does not allow a piece to move up if it is not valid
	 */
	@Test
	public void IsValidMoveUpMoveTest(){
		Hnefatafl h = new Hnefatafl();
		GameLogic g = new GameLogic();
		int[] start = new int[] { 0, 2 };
		int[] dest = new int[] { 0, 1 };
		assertTrue(g.isValidMove(start, dest, false, h._buttons));
	}
}
