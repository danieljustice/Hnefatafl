import static org.junit.Assert.*;
import javax.swing.JButton;
import org.junit.Test;

public class HnefataflTests extends Hnefatafl{
    private Hnefatafl game = new Hnefatafl();

    @Test
    public void testBoardDraws(){
        // Test that the board draws without error
        assertTrue(game.drawBoard());
    }

    //Verifies 
	@Test
	public void GetXYTest() {
		Hnefatafl h = new Hnefatafl();
		JButton jbutton = h._buttons[0][0];
		int[] xy = getXandY(jbutton);
		int[] testvalues = new int[]{ 0, 0 };
		assertEquals(xy[0],testvalues[0]);
		assertEquals(xy[1],testvalues[1]);
	}
	
	@Test
	public void IsValidMoveSameLocationTest(){
		int[] start = new int[] { 0, 0 };
		int[] dest = new int[] { 0, 0 };
		assertFalse(isValidMove(start, dest));
	}
	
	@Test
	public void IsValidMoveDiagonalMoveTest(){
		int[] start = new int[] { 0, 0 };
		int[] dest = new int[] { 1, 1 };
		assertFalse(isValidMove(start, dest));
	}
	
	@Test
	public void IsValidMoveLeftMoveTest(){
		int[] start = new int[] { 0, 0 };
		int[] dest = new int[] { 1, 0 };
		assertTrue(isValidMove(start, dest));
	}
	
	@Test
	public void IsValidMoveRightMoveTest(){
		int[] start = new int[] { 1, 0 };
		int[] dest = new int[] { 0, 0 };
		assertTrue(isValidMove(start, dest));
	}
	
	@Test
	public void IsValidMoveDownMoveTest(){
		int[] start = new int[] { 0, 1 };
		int[] dest = new int[] { 0, 0 };
		assertTrue(isValidMove(start, dest));
	}
	
	@Test
	public void IsValidMoveUpMoveTest(){
		int[] start = new int[] { 0, 0 };
		int[] dest = new int[] { 0, 1 };
		assertTrue(isValidMove(start, dest));
	}
}
