import java.awt.Point;

import javax.print.attribute.standard.RequestingUserName;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.text.StyledEditorKit.BoldAction;

import jdk.nashorn.internal.ir.ReturnNode;

/**
 *
 * @author Timothy Kang
 */
public class GameLogic implements GameLogicInterface{

    private int gameWidth;
    private int gameHeight;

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
    public int piecesLeft(ImageIcon axeIcon, ImageIcon kingIcon, JButton[][] _buttons){
        ImageIcon currentImageIcon;
        int surroundedKingSides = 0;
        int attackPieces = 0;

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


    public boolean exitFort(JButton[][] _buttons){
        Boolean isExitFort = false;
        Boolean isOnEdge = kingIsAtEdge(_buttons);

        Point kingPosition = findKing(_buttons);
     //   System.out.println("King is at: " + kingPosition.toString());
        //if king is on the board
        if(isOnEdge){
            if(kingPosition != null){
                boolean emptyNorth = checkIfEmpty(_buttons, kingPosition.y + 1, kingPosition.x);
                boolean emptyEast = checkIfEmpty(_buttons, kingPosition.y, kingPosition.x+1);
                boolean emptySouth = checkIfEmpty(_buttons, kingPosition.y-1, kingPosition.x);
                boolean emptyWest = checkIfEmpty(_buttons, kingPosition.y, kingPosition.x-1);
                if(emptyNorth || emptyEast || emptySouth || emptyWest){
                    // boolean north = false;
                    // boolean south = false;
                    // boolean east = false;
                    // boolean west = false;

                    String[][] stringMatrix = createStringMatrix(_buttons);
                    stringMatrix[kingPosition.y][kingPosition.x] = "shield";
                    // //if there is at least one empty spot next to the king
                    // if(emptyNorth){
                    //     //recursively check surroundings to see if it is an exit fort
                    //     System.out.println("Calling north recursive call");
                    //     north = checkSurroundings(stringMatrix, kingPosition.y+1, kingPosition.x);
                    // }
                    // if(emptyEast){
                    //     //recursively check surroundings to see if it is an exit fort
                    //     System.out.println("Calling east recursive call");
                    //     east = checkSurroundings(stringMatrix, kingPosition.y, kingPosition.x+1);
                    // }
                    // if(emptySouth){
                    //     //recursively check surroundings to see if it is an exit fort
                    //     System.out.println("Calling south recursive call");
                    //     south = checkSurroundings(stringMatrix, kingPosition.y-1, kingPosition.x);
                    // }
                    // if(emptyWest){
                    //     //recursively check surroundings to see if it is an exit fort
                    //     System.out.println("Calling west recursive call");
                    //     west = checkSurroundings(stringMatrix, kingPosition.y, kingPosition.x-1);
                    // }
                    // isExitFort = north || east || south || west;
                    isExitFort = checkSurroundings(stringMatrix, kingPosition.y, kingPosition.x);
                }
                
            }
        }
        
        return isExitFort;
    }


    public String[][] createStringMatrix(JButton[][] _buttons){
        String[][] stringMatrix = new String[gameHeight][gameWidth];
        for(int y = 0; y < gameHeight; y++){
            for(int x = 0; x < gameWidth; x++){
                ImageIcon currentImageIcon = (ImageIcon) _buttons[y][x].getIcon();
                stringMatrix[y][x] = currentImageIcon.getDescription();
            }
        }
        return stringMatrix;
    }
    public Point findKing(JButton[][] _buttons){
        Point position = null;  

        for(int y = 0; y < gameHeight; y++){
            for(int x = 0; x < gameWidth; x++){
                ImageIcon currentImageIcon = (ImageIcon) _buttons[y][x].getIcon();
                if(currentImageIcon.getDescription().equals("king")){
                    position = new Point(x, y);
                    return position;
                }
            }
        }
        return position;
    }

    public boolean checkIfEmpty(JButton[][] _buttons, int y, int x){
        boolean isEmpty = false;
        //not empty if the position is off the game board
        if(y < 0 || x < 0 || y > gameHeight-1 || x > gameWidth -1){
            return false;
        }
        ImageIcon currentImageIcon = (ImageIcon) _buttons[y][x].getIcon();
        if(currentImageIcon.getDescription().equals("empty")){
            isEmpty = true;
        }
        return isEmpty;
    }


    //This is the recursive function that will determine if there are no axes within the shield fort surrounding the king
    public boolean checkSurroundings(String[][] stringMatrix, int y, int x){
        if(x < 0 || x > gameWidth-1 || y < 0 || y > gameHeight - 1 ){
            return true;
        }
        //System.out.println(y + " " + x);
        boolean north = false;
        boolean east = false;
        boolean south = false;
        boolean west = false;
        String[][] newStringMatrix = new String[stringMatrix.length][stringMatrix.length];
        
        for(int i = 0; i < stringMatrix.length; i++){

            for(int j = 0; j < stringMatrix[i].length; j++){
                
                newStringMatrix[i][j] = stringMatrix[i][j];
            }
            
        }

        //sets current button description to a shield to prevent going back over a button that has already been visited
        newStringMatrix[y][x] = "shield";
        //Check North
        if(y+1 > gameHeight-1 || y+1 < 0){
            north = true;
        }else{
            //System.out.println(y + " " + gameHeight + " " +  x);
            if(newStringMatrix[y+1][x].equals("empty")){
                north = checkSurroundings(newStringMatrix, y + 1, x);
                if(north == false){
                    return false;
                }
            }else if(newStringMatrix[y+1][x].equals("shield")){
                north = true;
            }else{
                north = false;
            }
        }

        //Check East
        if(x+1 > gameWidth-1 || x+1 < 0){
            east = true;
        }else{

            if(newStringMatrix[y][x+1].equals("empty")){
                east = checkSurroundings(newStringMatrix, y, x + 1);
                if(east == false){
                    return false;
                }
            }else if(newStringMatrix[y][x+1].equals("shield")){
                east = true;
            }else{
                east = false;
            }
        }
            

        //Check South
        if(y-1 > gameHeight-1 || y-1 < 0){
            south = true;
        }else{
            
            if(newStringMatrix[y-1][x].equals("empty")){
                south = checkSurroundings(newStringMatrix, y-1, x);
                if(south == false){
                    return false;
                }
            }else if(newStringMatrix[y-1][x].equals("shield")){
                south = true;
            }else{
                south = false;
            }
        }

        //Check West
        if(x-1 > gameWidth-1 || x-1 < 0){
            west = true;
        }else{

            if(newStringMatrix[y][x-1].equals("empty")){
                west = checkSurroundings(newStringMatrix, y, x - 1);
                if(west == false){
                    return false;
                }
            }else if(newStringMatrix[y][x-1].equals("shield")){
                west = true;
            }else{
                west = false;
            }
        }
        //System.out.println("Surroundings at x = " + x + " y = " + y + " are valid: " +north + east + south + west);
        return north && east && south && west;
    }
    public Boolean kingIsAtEdge(JButton[][] _buttons){
        Boolean kingOnTopRow = false;
        Boolean kingOnBotRow = false;
        Boolean kingOnLeftCol = false;
        Boolean kingOnRightCol = false;
        //checks for if king is in the top row
        for(int i =0; i < _buttons.length; i++){
            ImageIcon currentImageIcon = (ImageIcon) _buttons[0][i].getIcon();
            if(currentImageIcon.getDescription().equals("king")){
                kingOnTopRow = true;
            }
        }
        //checks for if the king is in the bottom row
        for(int i =0; i < _buttons.length; i++){
            ImageIcon currentImageIcon = (ImageIcon) _buttons[10][i].getIcon();
            if(currentImageIcon.getDescription().equals("king")){
                kingOnBotRow = true;
            }
        }
        //checks for if the king is in the left column
        for(int i =0; i < _buttons.length; i++){
            ImageIcon currentImageIcon = (ImageIcon) _buttons[i][0].getIcon();
            if(currentImageIcon.getDescription().equals("king")){
                kingOnLeftCol = true;
            }
        }
        //checks for if the king is int he righ column  
        for(int i =0; i < _buttons.length; i++){
            ImageIcon currentImageIcon = (ImageIcon) _buttons[i][10].getIcon();
            if(currentImageIcon.getDescription().equals("king")){
                kingOnRightCol = true;
            }
        }

        
        return kingOnTopRow || kingOnBotRow || kingOnLeftCol || kingOnRightCol;
    }
}
