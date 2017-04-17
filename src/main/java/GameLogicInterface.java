import javax.swing.ImageIcon;
import javax.swing.JButton;

public interface GameLogicInterface {

    /**
     * Returns integer array contains xy coordinates for button pressed.
     *
     * @param jb    JButton that holds the current pushed button
     * @return  returns an integer array that contains, the buttons location as (x,y) = ([0],[1])
     */
    public int[] getXandY(JButton jb, JButton[][] _buttons);

    /**
    *
    * kill pieces as they are surrounded Othello style (King is an exception)
    * @param piecePlacement Jbutton that is where the latest piece was placed to see if a piece is destroyed
    * @return returns boolean true if a piece is taken out
    */
    public JButton[][] attackPieces(JButton piecePlacement, ImageIcon emptyImageIcon, ImageIcon kingIcon, ImageIcon axeIcon, ImageIcon defenseIcon, JButton[][] _buttons);

    /**
    *  check if there are any pieces left for any team
    *  @param Not Available
    *  @return returns int 0 for no winning condition, int 1 for king surrounded shields lose, int 2 for axe defeated for no more pieces
    */
    public int piecesLeft(ImageIcon axeIcon, ImageIcon kingIcon, ImageIcon defenseIcon, JButton[][] _buttons);

    /**
     * Returns whether a move is valid based on input arrays which store x and y locations
     *
     * @param start an integer array with 2 values, [0] index is x, [1] is y
     * @param destination   an integer array with 2 values, [0] index is x, [1] is y
     * @return  returns true if valid move, false if not
     */
    public boolean isValidMove(int[] start, int[] destination, boolean isKingPiece, JButton[][] _buttons);

    /** Traverses through each space on the board to see if any of them are occupied.
     *
     * @param start integer[] with size 2: index 0 is x cord, index 1 is y cord
     * @param destination start integer[] with size 2: index 0 is x cord, index 1 is y cord
     * @return returns false if any spaces are occupied between the start and destination
     */
    public boolean canMoveToDestination(int[] start, int[] destination, JButton[][] _buttons );

    /** This uses the same integer[] as getXandY and returns whether the destination is occupied or not
     *
     * @param destination integer[] of size 2: index 0 is x cord, index 1 is y cord
     * @return true if the space is occupied by axe, shield or king
     */
    public boolean isSpaceOccupied(int[] destination, JButton[][] _buttons );
	
	/** to retrieve remaining pieces
     *
     * @return pieces remaining
     */
	public int[] numPiecesLeft();
	
	/**
	 *  displays the best option
	 *
	 */
	public JButton[][] displayCorrectChoice(int[] start, int[] destination, JButton[][] _buttons);

	/**
	 *  disables the display the best option
	 *
	 */
    public JButton[][] removeCorrectChoice(JButton[][] _buttons);
	/** find best option on board
	 *  
	 *
	 */
    public JButton[][] showCorrectChoice(JButton[][] _buttons, ImageIcon axeIcon, ImageIcon emptyImageIcon, ImageIcon kingIcon, ImageIcon defenseIcon, boolean isFirstPlayer); 
	/** get weight of points of each space
	 *  
	 *
	 */
    public int surroundingPoints(int x, int y, ImageIcon currentImageIcon, JButton[][] _buttons, ImageIcon axeIcon, ImageIcon emptyImageIcon, ImageIcon kingIcon);
    
	/** check what is ontop of the button
	 *  
	 *
	 */
    public int checkNorth(int x, int y, JButton[][] _buttons, ImageIcon axeIcon, ImageIcon emptyImageIcon, ImageIcon kingIcon);

	/** check what is below of the button
	 *  
	 *
	 */
    public int checkSouth(int x, int y, JButton[][] _buttons, ImageIcon axeIcon, ImageIcon emptyImageIcon, ImageIcon kingIcon);

	/** check what is right of the button
	 *  
	 *
	 */
    public int checkEast(int x, int y, JButton[][] _buttons, ImageIcon axeIcon, ImageIcon emptyImageIcon, ImageIcon kingIcon);

	/** check what is left of the button
	 *  
	 *
	 */
    public int checkWest(int x, int y, JButton[][] _buttons, ImageIcon axeIcon, ImageIcon emptyImageIcon, ImageIcon kingIcon);

	
	public JButton[][] shieldWall(JButton[][] _buttons, ImageIcon axeIcon, ImageIcon emptyImageIcon, ImageIcon kingIcon, ImageIcon defenseIcon);
}
