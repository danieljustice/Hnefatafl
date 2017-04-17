import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.*;

/**
 *
 * @author Timothy Kang
 */
public class GameLogic implements GameLogicInterface{

    private int gameWidth;
    private int gameHeight;
	
    public int attackPieces = 20;
	public int defensePieces = 13;

    public GameLogic(int gameWidth, int gameHeight) {
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
    }

    // #################################################################
    // Utility methods
    // #################################################################

    /**
     * Returns integer array contains xy coordinates for button pressed.
     *
     * @param jb    JButton that holds the current pushed button
     * @return  returns an integer array that contains, the buttons location as (x,y) = ([0],[1])
     */
    public int[] getXandY(JButton jb, JButton[][] _buttons){
        int[] xyCord = new int[2];
        for(int i=0; i <gameWidth; i++){
            for(int j=0; j <gameHeight;j++){
                if(jb ==_buttons[j][i]){
                    xyCord[0] = j;
                    xyCord[1] = i;
                    return xyCord;
                }
            }
        }
        return new int[2];
    }
	
	/**
    *
    * kill pieces as they are surrounded Othello style (King is an exception)
    * @param piecePlacement Jbutton that is where the latest piece was placed to see if a piece is destroyed
    * @return returns boolean true if a piece is taken out
    */
    public JButton[][] attackPieces(JButton piecePlacement, ImageIcon emptyImageIcon, ImageIcon kingIcon, ImageIcon axeIcon, ImageIcon defenseIcon, JButton[][] _buttons){
        int[] placement = getXandY(piecePlacement, _buttons);
        //testing for surrounding area
        //i+-2 j+-2
        //check the pieces east north south and west
        ImageIcon currentImageIcon = (ImageIcon)piecePlacement.getIcon();
        ImageIcon surroundingImageIcon;
        ImageIcon victimPiece;

        if(placement[0] - 2 >= 0){
            //north

            //checks the north space piece and stores into this variable
            surroundingImageIcon = (ImageIcon)_buttons[placement[0] - 2][placement[1]].getIcon();

            //checks if the originating piece is a king or shield
            if(currentImageIcon.getDescription().equals(kingIcon.getDescription()) || currentImageIcon.getDescription().equals(defenseIcon.getDescription())){
                if(surroundingImageIcon.getDescription().equals(defenseIcon.getDescription()) || surroundingImageIcon.getDescription().equals(kingIcon.getDescription())){
                    victimPiece = (ImageIcon)_buttons[placement[0] - 1][placement[1]].getIcon();
                    if(!(victimPiece.getDescription().equals(defenseIcon.getDescription()) || victimPiece.getDescription().equals(kingIcon.getDescription()))){
                        _buttons[placement[0] - 1][placement[1]].setIcon(emptyImageIcon);
                    }
                }
            }
            //else axe
            else{
                if(surroundingImageIcon.getDescription().equals(currentImageIcon.getDescription())){
                    victimPiece = (ImageIcon)_buttons[placement[0] - 1][placement[1]].getIcon();
                    if(!(victimPiece.getDescription().equals(currentImageIcon.getDescription()) || victimPiece.getDescription().equals(kingIcon.getDescription()))){
                        _buttons[placement[0] - 1][placement[1]].setIcon(emptyImageIcon);
                    }
                }
            }

        }

        if(placement[0] + 2 < gameHeight){
            //south

            //checks the north space piece and stores into this variable
            surroundingImageIcon = (ImageIcon)_buttons[placement[0] + 2][placement[1]].getIcon();

            //checks if the originating piece is a king or shield
            if(currentImageIcon.getDescription().equals(kingIcon.getDescription()) || currentImageIcon.getDescription().equals(defenseIcon.getDescription())){
                if(surroundingImageIcon.getDescription().equals(defenseIcon.getDescription()) || surroundingImageIcon.getDescription().equals(kingIcon.getDescription())){
                    victimPiece = (ImageIcon)_buttons[placement[0] + 1][placement[1]].getIcon();
                    if(!(victimPiece.getDescription().equals(defenseIcon.getDescription()) || victimPiece.getDescription().equals(kingIcon.getDescription()))){
                        _buttons[placement[0] + 1][placement[1]].setIcon(emptyImageIcon);
                    }
                }
            }
            //else axe
            else{
                if(surroundingImageIcon.getDescription().equals(currentImageIcon.getDescription())){
                    victimPiece = (ImageIcon)_buttons[placement[0] + 1][placement[1]].getIcon();
                    if(!(victimPiece.getDescription().equals(currentImageIcon.getDescription()) || victimPiece.getDescription().equals(kingIcon.getDescription()))){
                        _buttons[placement[0] + 1][placement[1]].setIcon(emptyImageIcon);
                    }
                }
            }
        }
        if(placement[1] - 2 >= 0){
            //west

            //checks the north space piece and stores into this variable
            surroundingImageIcon = (ImageIcon)_buttons[placement[0]][placement[1] - 2].getIcon();

            //checks if the originating piece is a king or shield
            if(currentImageIcon.getDescription().equals(kingIcon.getDescription()) || currentImageIcon.getDescription().equals(defenseIcon.getDescription())){
                if(surroundingImageIcon.getDescription().equals(defenseIcon.getDescription()) || surroundingImageIcon.getDescription().equals(kingIcon.getDescription())){
                    victimPiece = (ImageIcon)_buttons[placement[0]][placement[1] - 1].getIcon();
                    if(!(victimPiece.getDescription().equals(defenseIcon.getDescription()) || victimPiece.getDescription().equals(kingIcon.getDescription()))){
                        _buttons[placement[0]][placement[1] - 1].setIcon(emptyImageIcon);
                    }
                }
            }
            //else axe
            else{
                if(surroundingImageIcon.getDescription().equals(currentImageIcon.getDescription())){
                    victimPiece = (ImageIcon)_buttons[placement[0]][placement[1] - 1].getIcon();
                    if(!(victimPiece.getDescription().equals(currentImageIcon.getDescription()) || victimPiece.getDescription().equals(kingIcon.getDescription()))){
                        _buttons[placement[0]][placement[1] - 1].setIcon(emptyImageIcon);
                    }
                }
            }
        }
        if(placement[1] + 2 < gameWidth){
            //east

            //checks the north space piece and stores into this variable
            surroundingImageIcon = (ImageIcon)_buttons[placement[0]][placement[1] + 2].getIcon();

            //checks if the originating piece is a king or shield
            if(currentImageIcon.getDescription().equals(kingIcon.getDescription()) || currentImageIcon.getDescription().equals(defenseIcon.getDescription())){
                if(surroundingImageIcon.getDescription().equals(defenseIcon.getDescription()) || surroundingImageIcon.getDescription().equals(kingIcon.getDescription())){
                    victimPiece = (ImageIcon)_buttons[placement[0]][placement[1] + 1].getIcon();
                    if(!(victimPiece.getDescription().equals(defenseIcon.getDescription()) || victimPiece.getDescription().equals(kingIcon.getDescription()))){
                        _buttons[placement[0]][placement[1] + 1].setIcon(emptyImageIcon);
                    }
                }
            }
            //else axe
            else{
                if(surroundingImageIcon.getDescription().equals(currentImageIcon.getDescription())){
                    victimPiece = (ImageIcon)_buttons[placement[0]][placement[1] + 1].getIcon();
                    if(!(victimPiece.getDescription().equals(currentImageIcon.getDescription()) || victimPiece.getDescription().equals(kingIcon.getDescription()))){
                        _buttons[placement[0] ][placement[1] + 1].setIcon(emptyImageIcon);
                    }
                }
            }
        }

        return _buttons;
    }

    /**
    *  check if there are any pieces left for any team
    *  @param Not Available
    *  @return returns int 0 for no winning condition, int 1 for king surrounded shields lose, int 2 for axe defeated for no more pieces
    */
    public int piecesLeft(ImageIcon axeIcon, ImageIcon kingIcon, ImageIcon defenseIcon, JButton[][] _buttons){
        ImageIcon currentImageIcon;
        int surroundedKingSides = 0;
        attackPieces = 0;
		defensePieces = 0;

        //loop through the 3d array of jbuttons
        for(int i=0; i <gameWidth; i++){
            for(int j=0; j <gameHeight;j++){
                //
                currentImageIcon = (ImageIcon)_buttons[i][j].getIcon();
                if(currentImageIcon.getDescription().equals(axeIcon.getDescription())){
                    //see if there are pieces left for axes
                    attackPieces++;
                }
                else if(currentImageIcon.getDescription().equals(kingIcon.getDescription())){
                    //check if king is surrounded
                    //testing for surrounding area
                    //i+-1 j+-1
                    //check the pieces east north south and west
                    ImageIcon surroundingImageIcon;
					
					defensePieces++;

                    if(i - 1 >= 0){
                        //north
                        surroundingImageIcon = (ImageIcon)_buttons[i - 1][j].getIcon();
                        if(surroundingImageIcon.getDescription().equals(axeIcon.getDescription())){
                            surroundedKingSides++;
                        }
                    }
                    else{
                        //on edge
                        surroundedKingSides++;
                    }

                    if(i + 1 < gameHeight){
                        //south

                        surroundingImageIcon = (ImageIcon)_buttons[i + 1][j].getIcon();
                        if(surroundingImageIcon.getDescription().equals(axeIcon.getDescription())){
                            surroundedKingSides++;
                        }
                    }
                    else{
                        //on edge
                        surroundedKingSides++;
                    }
                    if(j - 1 >= 0){
                        //west

                        surroundingImageIcon = (ImageIcon)_buttons[i][j - 1].getIcon();
                        if(surroundingImageIcon.getDescription().equals(axeIcon.getDescription())){
                            surroundedKingSides++;
                        }
                    }
                    else{
                        //on edge
                        surroundedKingSides++;
                    }
                    if(j + 1 < gameWidth){
                        //east

                        surroundingImageIcon = (ImageIcon)_buttons[i][j + 1].getIcon();
                        if(surroundingImageIcon.getDescription().equals(axeIcon.getDescription())){
                            surroundedKingSides++;
                        }
                    }
                    else{
                        //on edge
                        surroundedKingSides++;
                    }
                }
				else if(currentImageIcon.getDescription().equals(defenseIcon.getDescription())){
					defensePieces++;
				}
            }
        }
        if(attackPieces == 0){
            //axe defeated
            return 1;
        }
        else if(surroundedKingSides == 4){
            //shield defeated since king is surrounded
            return 2;
        }
        else{
            //keep playing
            return 0;
        }
    }

    /**
     * Returns whether a move is valid based on input arrays which store x and y locations
     *
     * @param start an integer array with 2 values, [0] index is x, [1] is y
     * @param destination   an integer array with 2 values, [0] index is x, [1] is y
     * @return  returns true if valid move, false if not
     */
    public boolean isValidMove(int[] start, int[] destination, boolean isKingPiece, JButton[][] _buttons){

        if(start[0] == destination[0] && start[1] == destination[1] )
            return false;
        else if(destination[0] == 5 && destination[1] == 5)
            return false;
        else if( (start[0] == destination[0] || start[1] == destination[1]) ){

            if(!isSpaceOccupied(destination, _buttons)){

                if(canMoveToDestination(start, destination, _buttons)){
                    if(!isKingPiece){
                    //checks to see if a normal piece is trying to enter one of the corner squares
                    if(destination[0] == 0 && destination[1] == 0 || destination[0] == 0 && destination[1] == 10
                        || destination[0] == 10 && destination[1] == 0 || destination[0] == 10 && destination[1] == 10){
                            //check to see if pieces are in between
                            return false;
                    }
                }
                    return true;
                }
            }
        }
        return false;
    }

    /** Traverses through each space on the board to see if any of them are occupied.
     *
     * @param start integer[] with size 2: index 0 is x cord, index 1 is y cord
     * @param destination start integer[] with size 2: index 0 is x cord, index 1 is y cord
     * @return returns false if any spaces are occupied between the start and destination
     */
    public boolean canMoveToDestination(int[] start, int[] destination, JButton[][] _buttons ){
        int[] counter = new int[2];
        counter[0] = start[0];
        counter[1] = start[1];
        if(start[0] == destination[0]){
            if(start[1] < destination[1]){
            	counter[1]++;
                while(counter[1] < destination[1]){
                    if(isSpaceOccupied(counter, _buttons))
                        return false;
                    counter[1]++;
                }
            }
            else
            {
            	counter[1]--;
                while(counter[1] > destination[1]){
                    if(isSpaceOccupied(counter, _buttons ))
                        return false;
                    counter[1]--;
                }
            }
        }
        else if(start[1] == destination[1]){
            if(start[0] < destination[0]){
                counter[0]++;
                while(counter[0] < destination[0]){
                    if(isSpaceOccupied(counter, _buttons))
                        return false;
                    counter[0]++;
                }
            }
            else
            {
            	counter[0]--;
                while(counter[0] > destination[0]){
                    if(isSpaceOccupied(counter, _buttons))
                        return false;
                    counter[0]--;
                }
            }
        }
        return true;
    }

    /** This uses the same integer[] as getXandY and returns whether the destination is occupied or not
     *
     * @param destination integer[] of size 2: index 0 is x cord, index 1 is y cord
     * @return true if the space is occupied by axe, shield or king
     */
    public boolean isSpaceOccupied(int[] destination, JButton[][] _buttons ){
        ImageIcon currentImageIcon = (ImageIcon) _buttons[destination[0]][destination[1]].getIcon();
        if(currentImageIcon==null || currentImageIcon.getDescription() == null)
        	return true;
        if( !currentImageIcon.getDescription().equals("empty"))
            return true;
        return false;
    }
	
	/** to retrieve remaining pieces
     *
     * @return pieces remaining
     */
	public int[] numPiecesLeft(){
		int[] temp = new int[2];
		temp[0] = attackPieces;
		temp[1] = defensePieces;
		
		return temp;
	}
	
	public JButton[][] displayCorrectChoice(int[] start, int[] destination, JButton[][] _buttons) {
        _buttons[start[0]][start[1]].setBorder(new LineBorder(Color.YELLOW, 5));
        _buttons[destination[0]][destination[1]].setBorder(new LineBorder(Color.YELLOW, 5));
		return _buttons;
    }

    public JButton[][] removeCorrectChoice(JButton[][] _buttons) {
        javax.swing.border.Border emptyBorder = BorderFactory.createEmptyBorder();
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {

                _buttons[i][j].setBorder(emptyBorder);
            }
        }
		return _buttons;
    }

    public JButton[][] showCorrectChoice(JButton[][] _buttons, ImageIcon axeIcon, ImageIcon emptyImageIcon, ImageIcon kingIcon, ImageIcon defenseIcon, boolean isFirstPlayer) {
        ImageIcon currentImageIcon;
        ImageIcon temp;
        int score = 0;
        int[] start = new int[2];
        int[] destination = new int[2];
        boolean king = false;

        if (isFirstPlayer) {
            for (int i = 0; i < gameWidth; i++) {
                for (int j = 0; j < gameHeight; j++) {
                    currentImageIcon = (ImageIcon) _buttons[i][j].getIcon();
                    temp = currentImageIcon;
                    if (currentImageIcon.getDescription().equals(axeIcon.getDescription())) {
                        //check updown left right for where this piece can go
                        for (int k = j - 1; k >= 0; k--) {

                            currentImageIcon = (ImageIcon) _buttons[i][k].getIcon();
                            if (!currentImageIcon.getDescription().equals(emptyImageIcon.getDescription())) {
                                break;
                            }
                            if (!king && ((i == 0 && k == 0) || (i == 10 && k == 0) || (i == 0 && k == 10) || (i == 10 && k == 10))) {
                                break;
                            }
                            if (score < surroundingPoints(i, k, temp, _buttons, axeIcon, emptyImageIcon, kingIcon)) {
                                score = surroundingPoints(i, k, temp, _buttons, axeIcon, emptyImageIcon, kingIcon);
                                start[0] = i;
                                start[1] = j;
                                destination[0] = i;
                                destination[1] = k;
                            }

                        }

                        for (int k = j + 1; k < 11; k++) {
                            currentImageIcon = (ImageIcon) _buttons[i][k].getIcon();
                            if (!currentImageIcon.getDescription().equals(emptyImageIcon.getDescription())) {
                                break;
                            }
                            if (!king && ((i == 0 && k == 0) || (i == 10 && k == 0) || (i == 0 && k == 10) || (i == 10 && k == 10))) {
                                break;
                            }
                            if (score < surroundingPoints(i, k, temp, _buttons, axeIcon, emptyImageIcon, kingIcon)) {
                                score = surroundingPoints(i, k, temp, _buttons, axeIcon, emptyImageIcon, kingIcon);
                                start[0] = i;
                                start[1] = j;
                                destination[0] = i;
                                destination[1] = k;
                            }
                        }

                        for (int k = i - 1; k >= 0; k--) {
                            currentImageIcon = (ImageIcon) _buttons[k][j].getIcon();
                            if (!currentImageIcon.getDescription().equals(emptyImageIcon.getDescription())) {
                                break;
                            }
                            if (!king && ((j == 0 && k == 0) || (j == 10 && k == 0) || (j == 0 && k == 10) || (j == 10 && k == 10))) {
                                break;
                            }
                            if (score < surroundingPoints(k, j, temp, _buttons, axeIcon, emptyImageIcon, kingIcon)) {
                                score = surroundingPoints(k, j, temp, _buttons, axeIcon, emptyImageIcon, kingIcon);
                                start[0] = i;
                                start[1] = j;
                                destination[0] = k;
                                destination[1] = j;
                            }
                        }

                        for (int k = i + 1; k < 11; k++) {
                            currentImageIcon = (ImageIcon) _buttons[k][j].getIcon();
                            if (!currentImageIcon.getDescription().equals(emptyImageIcon.getDescription())) {
                                break;
                            }
                            if (!king && ((j == 0 && k == 0) || (j == 10 && k == 0) || (j == 0 && k == 10) || (j == 10 && k == 10))) {
                                break;
                            }
                            if (score < surroundingPoints(k, j, temp, _buttons, axeIcon, emptyImageIcon, kingIcon)) {
                                score = surroundingPoints(k, j, temp, _buttons, axeIcon, emptyImageIcon, kingIcon);
                                start[0] = i;
                                start[1] = j;
                                destination[0] = k;
                                destination[1] = j;
                            }
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < gameWidth; i++) {
                for (int j = 0; j < gameHeight; j++) {
                    currentImageIcon = (ImageIcon) _buttons[i][j].getIcon();
                    temp = currentImageIcon;
                    if (currentImageIcon.getDescription().equals(kingIcon.getDescription())) {
                        king = true;
                    } else {
                        king = false;
                    }

                    if (currentImageIcon.getDescription().equals(defenseIcon.getDescription()) || currentImageIcon.getDescription().equals(kingIcon.getDescription())) {
                        //check updown left right for where this piece can go

                        for (int k = j - 1; k >= 0; k--) {
                            currentImageIcon = (ImageIcon) _buttons[i][k].getIcon();
                            if (!currentImageIcon.getDescription().equals(emptyImageIcon.getDescription())) {
                                break;
                            }
                            if (king && ((i == 0 && k == 0) || (i == 10 && k == 0) || (i == 0 && k == 10) || (i == 10 && k == 10))) {
                                score = 99;
                                start[0] = i;
                                start[1] = j;
                                destination[0] = i;
                                destination[1] = k;
                            }
                            if (!king && ((i == 0 && k == 0) || (i == 10 && k == 0) || (i == 0 && k == 10) || (i == 10 && k == 10))) {
                                break;
                            }
                            if (score < surroundingPoints(i, k, temp, _buttons, axeIcon, emptyImageIcon, kingIcon)) {
                                score = surroundingPoints(i, k, temp, _buttons, axeIcon, emptyImageIcon, kingIcon);
                                start[0] = i;
                                start[1] = j;
                                destination[0] = i;
                                destination[1] = k;
                            }
                        }

                        for (int k = j + 1; k < 11; k++) {
                            currentImageIcon = (ImageIcon) _buttons[i][k].getIcon();
                            if (!currentImageIcon.getDescription().equals(emptyImageIcon.getDescription())) {
                                break;
                            }
                            if (king && ((i == 0 && k == 0) || (i == 10 && k == 0) || (i == 0 && k == 10) || (i == 10 && k == 10))) {
                                score = 99;
                                start[0] = i;
                                start[1] = j;
                                destination[0] = i;
                                destination[1] = k;
                            }
                            if (!king && ((i == 0 && k == 0) || (i == 10 && k == 0) || (i == 0 && k == 10) || (i == 10 && k == 10))) {
                                break;
                            }
                            if (score < surroundingPoints(i, k, temp, _buttons, axeIcon, emptyImageIcon, kingIcon)) {
                                score = surroundingPoints(i, k, temp, _buttons, axeIcon, emptyImageIcon, kingIcon);
                                start[0] = i;
                                start[1] = j;
                                destination[0] = i;
                                destination[1] = k;
                            }
                        }

                        for (int k = i - 1; k >= 0; k--) {
                            currentImageIcon = (ImageIcon) _buttons[k][j].getIcon();
                            if (!currentImageIcon.getDescription().equals(emptyImageIcon.getDescription())) {
                                break;
                            }
                            if (king && ((j == 0 && k == 0) || (j == 10 && k == 0) || (j == 0 && k == 10) || (j == 10 && k == 10))) {
                                score = 99;
                                start[0] = i;
                                start[1] = j;
                                destination[0] = k;
                                destination[1] = j;
                            }
                            if (!king && ((j == 0 && k == 0) || (j == 10 && k == 0) || (j == 0 && k == 10) || (j == 10 && k == 10))) {
                                break;
                            }
                            if (score < surroundingPoints(k, j, temp, _buttons, axeIcon, emptyImageIcon, kingIcon)) {
                                score = surroundingPoints(k, j, temp, _buttons, axeIcon, emptyImageIcon, kingIcon);
                                start[0] = i;
                                start[1] = j;
                                destination[0] = k;
                                destination[1] = j;
                            }
                        }

                        for (int k = i + 1; k < 11; k++) {
                            currentImageIcon = (ImageIcon) _buttons[k][j].getIcon();
                            if (!currentImageIcon.getDescription().equals(emptyImageIcon.getDescription())) {
                                break;
                            }
                            if (king && ((j == 0 && k == 0) || (j == 10 && k == 0) || (j == 0 && k == 10) || (j == 10 && k == 10))) {
                                score = 99;
                                start[0] = i;
                                start[1] = j;
                                destination[0] = k;
                                destination[1] = j;
                            }
                            if (!king && ((j == 0 && k == 0) || (j == 10 && k == 0) || (j == 0 && k == 10) || (j == 10 && k == 10))) {
                                break;
                            }
                            if (score < surroundingPoints(k, j, temp, _buttons, axeIcon, emptyImageIcon, kingIcon)) {
                                score = surroundingPoints(k, j, temp, _buttons, axeIcon, emptyImageIcon, kingIcon);
                                start[0] = i;
                                start[1] = j;
                                destination[0] = k;
                                destination[1] = j;
                            }
                        }
                    }
                }
            }
        }
        
        return displayCorrectChoice(start, destination, _buttons);
    }

    public int surroundingPoints(int x, int y, ImageIcon currentImageIcon, JButton[][] _buttons, ImageIcon axeIcon, ImageIcon emptyImageIcon, ImageIcon kingIcon) {
        int score = 1;
        //up down left right
        //checkNorth
        if (currentImageIcon.getDescription().equals(axeIcon.getDescription())) {
            if (y > 0) {
                if (checkNorth(x, y, _buttons, axeIcon, emptyImageIcon, kingIcon) == 1) {
                    score += 3;
                } else if (checkNorth(x, y, _buttons, axeIcon, emptyImageIcon, kingIcon) == 2) {
                    if (y - 1 > 0) {
                        if (checkNorth(x, y - 1, _buttons, axeIcon, emptyImageIcon, kingIcon) == 1) {
                            score += 6;
                        } else {
                            if (checkNorth(x, y - 1, _buttons, axeIcon, emptyImageIcon, kingIcon) != 0) {
                                return 1;
                            } else {
                                score += 2;
                            }
                        }
                    } else {
                        score += 2;
                    }
                } else if (checkNorth(x, y, _buttons, axeIcon, emptyImageIcon, kingIcon) == 3) {
                    score += 5;
                } else {
                    score += 4;
                }
            }
            if (y < 10) {
                if (checkSouth(x, y, _buttons, axeIcon, emptyImageIcon, kingIcon) == 1) {
                    score += 3;
                } else if (checkSouth(x, y, _buttons, axeIcon, emptyImageIcon, kingIcon) == 2) {
                    if (y + 1 < 10) {
                        if (checkSouth(x, y + 1, _buttons, axeIcon, emptyImageIcon, kingIcon) == 1) {
                            score += 6;
                        } else {
                            if (checkSouth(x, y + 1, _buttons, axeIcon, emptyImageIcon, kingIcon) != 0) {
                                return 1;
                            } else {
                                score += 2;
                            }
                        }
                    } else {
                        score += 2;
                    }
                } else if (checkSouth(x, y, _buttons, axeIcon, emptyImageIcon, kingIcon) == 3) {
                    score += 5;
                } else {
                    score += 4;
                }
            }
            if (x > 0) {
                if (checkWest(x, y, _buttons, axeIcon, emptyImageIcon, kingIcon) == 1) {
                    score += 3;
                } else if (checkWest(x, y, _buttons, axeIcon, emptyImageIcon, kingIcon) == 2) {
                    if (x - 1 > 0) {
                        if (checkWest(x - 1, y, _buttons, axeIcon, emptyImageIcon, kingIcon) == 1) {
                            score += 6;
                        } else {
                            if (checkWest(x - 1, y, _buttons, axeIcon, emptyImageIcon, kingIcon) != 0) {
                                return 1;
                            } else {
                                score += 2;
                            }
                        }
                    } else {
                        score += 2;
                    }
                } else if (checkWest(x, y, _buttons, axeIcon, emptyImageIcon, kingIcon) == 3) {
                    score += 5;
                } else {
                    score += 4;
                }
            }
            if (x < 10) {
                if (checkEast(x, y, _buttons, axeIcon, emptyImageIcon, kingIcon) == 1) {
                    score += 3;
                } else if (checkEast(x, y, _buttons, axeIcon, emptyImageIcon, kingIcon) == 2) {
                    if (x + 1 < 10) {
                        if (checkEast(x + 1, y, _buttons, axeIcon, emptyImageIcon, kingIcon) == 1) {
                            score += 6;
                        } else {
                            if (checkEast(x + 1, y, _buttons, axeIcon, emptyImageIcon, kingIcon) != 0) {
                                return 1;
                            } else {
                                score += 2;
                            }
                        }
                    } else {
                        score += 2;
                    }
                } else if (checkEast(x, y, _buttons, axeIcon, emptyImageIcon, kingIcon) == 3) {
                    score += 5;
                } else {
                    score += 4;
                }
            }
        } else {
            if (y > 0) {
                if (checkNorth(x, y, _buttons, axeIcon, emptyImageIcon, kingIcon) == 2) {
                    score += 3;
                } else if (checkNorth(x, y, _buttons, axeIcon, emptyImageIcon, kingIcon) == 1) {
                    if (y - 1 > 0) {
                        if (checkNorth(x, y - 1, _buttons, axeIcon, emptyImageIcon, kingIcon) == 2) {
                            score += 6;
                        } else {
                            if (checkNorth(x, y - 1, _buttons, axeIcon, emptyImageIcon, kingIcon) != 0) {
                                return 1;
                            } else {
                                score += 2;
                            }
                        }
                    } else {
                        score += 2;
                    }
                } else if (checkNorth(x, y, _buttons, axeIcon, emptyImageIcon, kingIcon) == 0) {
                    score += 4;
                }
            }
            if (y < 10) {
                if (checkSouth(x, y, _buttons, axeIcon, emptyImageIcon, kingIcon) == 2) {
                    score += 3;
                } else if (checkSouth(x, y, _buttons, axeIcon, emptyImageIcon, kingIcon) == 1) {
                    if (y + 1 < 10) {
                        if (checkSouth(x, y + 1, _buttons, axeIcon, emptyImageIcon, kingIcon) == 2) {
                            score += 6;
                        } else {
                            if (checkSouth(x, y + 1, _buttons, axeIcon, emptyImageIcon, kingIcon) != 0) {
                                return 1;
                            } else {
                                score += 2;
                            }
                        }
                    } else {
                        score += 2;
                    }
                } else if (checkSouth(x, y, _buttons, axeIcon, emptyImageIcon, kingIcon) == 0) {
                    score += 4;
                }
            }
            if (x > 0) {
                if (checkWest(x, y, _buttons, axeIcon, emptyImageIcon, kingIcon) == 2) {
                    score += 3;
                } else if (checkWest(x, y, _buttons, axeIcon, emptyImageIcon, kingIcon) == 1) {
                    if (x - 1 > 0) {
                        if (checkWest(x - 1, y, _buttons, axeIcon, emptyImageIcon, kingIcon) == 2) {
                            score += 6;
                        } else {
                            if (checkWest(x - 1, y, _buttons, axeIcon, emptyImageIcon, kingIcon) != 0) {
                                return 1;
                            } else {
                                score += 2;
                            }
                        }
                    } else {
                        score += 2;
                    }
                } else if (checkWest(x, y, _buttons, axeIcon, emptyImageIcon, kingIcon) == 0) {
                    score += 4;
                }
            }
            if (x < 10) {
                if (checkEast(x, y, _buttons, axeIcon, emptyImageIcon, kingIcon) == 2) {
                    score += 3;
                } else if (checkEast(x, y, _buttons, axeIcon, emptyImageIcon, kingIcon) == 1) {
                    if (x + 1 < 10) {
                        if (checkEast(x + 1, y, _buttons, axeIcon, emptyImageIcon, kingIcon) == 2) {
                            score += 6;
                        } else {
                            if (checkEast(x + 1, y, _buttons, axeIcon, emptyImageIcon, kingIcon) != 0) {
                                return 1;
                            } else {
                                score += 2;
                            }
                        }
                    } else {
                        score += 2;
                    }
                } else if (checkEast(x, y, _buttons, axeIcon, emptyImageIcon, kingIcon) == 0) {
                    score += 4;
                }
            }
        }
        
        return score;
    }

    public int checkNorth(int x, int y, JButton[][] _buttons, ImageIcon axeIcon, ImageIcon emptyImageIcon, ImageIcon kingIcon) {
        ImageIcon currentImageIcon = (ImageIcon) _buttons[x][y - 1].getIcon();
        if (currentImageIcon.getDescription().equals(axeIcon.getDescription())) {
            return 1;
        } else if (currentImageIcon.getDescription().equals(emptyImageIcon.getDescription())) {
            return 0;
        } else if (currentImageIcon.getDescription().equals(kingIcon.getDescription())) {
            return 3;
        } else {
            return 2;
        }
    }

    public int checkSouth(int x, int y, JButton[][] _buttons, ImageIcon axeIcon, ImageIcon emptyImageIcon, ImageIcon kingIcon) {
        ImageIcon currentImageIcon = (ImageIcon) _buttons[x][y + 1].getIcon();
        if (currentImageIcon.getDescription().equals(axeIcon.getDescription())) {
            return 1;
        } else if (currentImageIcon.getDescription().equals(emptyImageIcon.getDescription())) {
            return 0;
        } else if (currentImageIcon.getDescription().equals(kingIcon.getDescription())) {
            return 3;
        } else {
            return 2;
        }
    }

    public int checkEast(int x, int y, JButton[][] _buttons, ImageIcon axeIcon, ImageIcon emptyImageIcon, ImageIcon kingIcon) {
        ImageIcon currentImageIcon = (ImageIcon) _buttons[x + 1][y].getIcon();
        if (currentImageIcon.getDescription().equals(axeIcon.getDescription())) {
            return 1;
        } else if (currentImageIcon.getDescription().equals(emptyImageIcon.getDescription())) {
            return 0;
        } else if (currentImageIcon.getDescription().equals(kingIcon.getDescription())) {
            return 3;
        } else {
            return 2;
        }
    }

    public int checkWest(int x, int y, JButton[][] _buttons, ImageIcon axeIcon, ImageIcon emptyImageIcon, ImageIcon kingIcon) {
        ImageIcon currentImageIcon = (ImageIcon) _buttons[x - 1][y].getIcon();
        if (currentImageIcon.getDescription().equals(axeIcon.getDescription())) {
            return 1;
        } else if (currentImageIcon.getDescription().equals(emptyImageIcon.getDescription())) {
            return 0;
        } else if (currentImageIcon.getDescription().equals(kingIcon.getDescription())) {
            return 3;
        } else {
            return 2;
        }
    }
}
