import javax.swing.ImageIcon;
import javax.swing.JButton;
import game_pieces.*;

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
     * @param jb    GamePiece that holds the current pushed button
     * @return  returns an integer array that contains, the buttons location as (x,y) = ([0],[1])
     */
    public int[] getXandY(GamePiece jb, GamePiece[][] _buttons){
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
    public GamePiece[][] attackPieces(GamePiece piecePlacement, GamePiece[][] _buttons){
        int[] placement = getXandY(piecePlacement, _buttons);
        //testing for surrounding area
        //i+-2 j+-2
        //check the pieces east north south and west

        GamePiece victimPiece;
        GamePiece surroundingPiece;

        if(placement[0] - 2 >= 0){
            //north

            //checks the north space piece and stores into this variable
            surroundingPiece = _buttons[placement[0] - 2][placement[1]];

            //checks if the originating piece is a king or shield
            if(isKing(piecePlacement) || isDefender(piecePlacement)){
                if(isDefender(surroundingPiece) || isKing(surroundingPiece)){
                    victimPiece = _buttons[placement[0] - 1][placement[1]];
                    if(!(isDefender(victimPiece)) || isKing(victimPiece)){
                        _buttons[placement[0] - 1][placement[1]] = new EmptyPiece();
                    }
                }
            }
            //else axe
            else{
                if(surroundingPiece.equals(piecePlacement)){
                    victimPiece = _buttons[placement[0] - 1][placement[1]];
                    if(!(victimPiece.getType() == piecePlacement.getType() || isKing(victimPiece))){
                        _buttons[placement[0] - 1][placement[1]] = new EmptyPiece();
                    }
                }
            }

        }

        if(placement[0] + 2 < gameHeight){
            //south

            //checks the north space piece and stores into this variable
            surroundingPiece = _buttons[placement[0] + 2][placement[1]];

            //checks if the originating piece is a king or shield
            if(isKing(piecePlacement) || isDefender(piecePlacement)){
                if(isDefender(surroundingPiece) || isKing(surroundingPiece)){
                    victimPiece = _buttons[placement[0] + 1][placement[1]];
                    if(!(isDefender(victimPiece)) || isKing(victimPiece)){
                        _buttons[placement[0] + 1][placement[1]] = new EmptyPiece();
                    }
                }
            }
            //else axe
            else{
                if(surroundingPiece.equals(piecePlacement)){
                    victimPiece = _buttons[placement[0] + 1][placement[1]];
                    if(!(victimPiece.getType() == piecePlacement.getType()) || isKing(victimPiece)){
                        _buttons[placement[0] + 1][placement[1]] = new EmptyPiece();
                    }
                }
            }
        }
        if(placement[1] - 2 >= 0){
            //west

            //checks the north space piece and stores into this variable
            surroundingPiece = _buttons[placement[0]][placement[1] - 2];

            //checks if the originating piece is a king or shield
            if(isKing(piecePlacement) || isDefender(piecePlacement)){
                if(isDefender(surroundingPiece) || isKing(surroundingPiece)){
                    victimPiece = _buttons[placement[0]][placement[1] - 1];
                    if(!(isDefender(victimPiece)) || isKing(victimPiece)){
                        _buttons[placement[0]][placement[1] - 1] = new EmptyPiece();
                    }
                }
            }
            //else axe
            else{
                if(surroundingPiece.equals(piecePlacement)){
                    victimPiece = _buttons[placement[0]][placement[1] - 1];
                    if(!(victimPiece.getType() == piecePlacement.getType() || isKing(victimPiece))){
                        _buttons[placement[0]][placement[1] - 1] = new EmptyPiece();
                    }
                }
            }
        }
        if(placement[1] + 2 < gameWidth){
            //east

            //checks the north space piece and stores into this variable
            surroundingPiece = _buttons[placement[0]][placement[1] + 2];

            //checks if the originating piece is a king or shield
            if(isKing(piecePlacement) || isDefender(piecePlacement)){
                if(isDefender(surroundingPiece) || isKing(surroundingPiece)){
                    victimPiece = _buttons[placement[0]][placement[1] + 1];
                    if(!(isDefender(victimPiece) || isKing(victimPiece))){
                        _buttons[placement[0]][placement[1] + 1] = new EmptyPiece();
                    }
                }
            }
            //else axe
            else{
                if(surroundingPiece.equals(piecePlacement)){
                    victimPiece = _buttons[placement[0]][placement[1] + 1];
                    if(!(victimPiece.getType() == piecePlacement.getType() || isKing(victimPiece))){
                        _buttons[placement[0] ][placement[1] + 1] = new EmptyPiece();
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
    public int piecesLeft(GamePiece[][] _buttons){
        int surroundedKingSides = 0;
        int attackPieces = 0;

        //loop through the 3d array of jbuttons
        for(int i=0; i <gameWidth; i++){
            for(int j=0; j <gameHeight;j++){
                //
                GamePiece piecePlacement = _buttons[i][j];
                if(isAttacker(piecePlacement)){
                    //see if there are pieces left for axes
                    attackPieces++;
                }
                else if(isKing(piecePlacement)){
                    //check if king is surrounded
                    //testing for surrounding area
                    //i+-1 j+-1
                    //check the pieces east north south and west
                    GamePiece surroundingPiece;

                    if(i - 1 >= 0){
                        //north
                        surroundingPiece = _buttons[i - 1][j];
                        if(isAttacker(surroundingPiece)){
                            surroundedKingSides++;
                        }
                    }
                    else{
                        //on edge
                        surroundedKingSides++;
                    }

                    if(i + 1 < gameHeight){
                        //south

                        surroundingPiece = _buttons[i + 1][j];
                        if(isAttacker(surroundingPiece)){
                            surroundedKingSides++;
                        }
                    }
                    else{
                        //on edge
                        surroundedKingSides++;
                    }
                    if(j - 1 >= 0){
                        //west

                        surroundingPiece = _buttons[i][j - 1];
                        if(isAttacker(surroundingPiece)){
                            surroundedKingSides++;
                        }
                    }
                    else{
                        //on edge
                        surroundedKingSides++;
                    }
                    if(j + 1 < gameWidth){
                        //east

                        surroundingPiece = _buttons[i][j + 1];
                        if(isAttacker(surroundingPiece)){
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
    public boolean isValidMove(int[] start, int[] destination, boolean isKingPiece, GamePiece[][] _buttons){

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
    public boolean canMoveToDestination(int[] start, int[] destination, GamePiece[][] _buttons ){
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
    public boolean isSpaceOccupied(int[] destination, GamePiece[][] _buttons ){
        GamePiece piecePlacement =  _buttons[destination[0]][destination[1]];
        if(piecePlacement==null || !isEmpty(piecePlacement))
        	return true;
        return false;
    }

    public boolean isDefender(GamePiece piece) {
        return (piece.getType() == GamePieceType.DEFENDER);
    }

    public boolean isAttacker(GamePiece piece) {
        return (piece.getType() == GamePieceType.ATTACKER);
    }

    public boolean isEmpty(GamePiece piece) {
        return (piece.getType() == GamePieceType.EMPTY);
    }

    public boolean isKing(GamePiece piece) {
        return (piece.getType() == GamePieceType.KING);
    }
}
