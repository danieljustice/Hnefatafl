import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Hnefatafl {

    private int gameWidth = 11;
    private int gameHeight = 11;
    private int frameWidth = 850;
    private int frameHeight = 850;
    private JFrame _frame = new JFrame("Hnefatafl");
    private JPanel _ttt = new JPanel();
    JLabel turn = new JLabel("Axe Moves");
    private JPanel _newPanel = new JPanel();

    private JButton _firstClick = null;
    private JButton _secondClick = null;
    private boolean isFirstPlayer = true;
    private ImageIcon firstClickImageIcon = null;
    private ImageIcon secondClickImageIcon = null;

    public JButton[][] _buttons = new JButton[gameWidth][gameHeight];
    public ImageIcon defenseIcon;
    public ImageIcon axeIcon;
    public ImageIcon kingIcon;
    public ImageIcon emptyImageIcon;
    public Image backgroundIcon;

    public Hnefatafl() {
        //pull in images for icons on the buttons
        try{
            //ImageIcons are public so we can test them in unit tests
            defenseIcon = new ImageIcon(ImageIO.read(new File("src/assets/First Shield.png")));
            axeIcon = new ImageIcon(ImageIO.read(new File("src/assets/First Axe.png")));
            kingIcon = new ImageIcon(ImageIO.read(new File("src/Crown.png")));
            emptyImageIcon = new ImageIcon(ImageIO.read(new File("src/assets/empty.png")));
            backgroundIcon = ImageIO.read(new File("src/assets/simpleBoard.png"));
            //give each icon a description so we can compare them later

            defenseIcon.setDescription("shield");
            axeIcon.setDescription("axe");
            emptyImageIcon.setDescription("empty");
            kingIcon.setDescription("K");
            drawBoard();
        }catch(Exception e){
            //woops just in case we cant pull in a file
            //Note: path for file we read must be relative to src/ folder
            System.out.println("WOOPS " + e);
        }
    }
  
    /*
     * Draws the board panel itself and then calls the method to set the initial game state.
     */
    public boolean drawBoard(){
        _frame = new JFrame("Hnefatafl");
        _frame.setSize(frameWidth, frameHeight);
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        _frame.add(tools, BorderLayout.PAGE_START);


        JButton newButton = new JButton("New"); //no functions
        ActionListener newButtonListener = new NewButtonListener();
        newButton.addActionListener(newButtonListener);
        tools.add(newButton);
        tools.addSeparator();

        JButton saveButton = new JButton("Save"); //no functions
        ActionListener saveButtonListener = new SaveButtonListener();
        saveButton.addActionListener(saveButtonListener);
        tools.add(saveButton);
        tools.addSeparator();

        JButton loadButton = new JButton("Load"); //no functions
        ActionListener loadButtonListener = new LoadButtonListener();
        loadButton.addActionListener(loadButtonListener);
        tools.add(loadButton);
        tools.addSeparator();

        tools.add(new JButton("Resign")); //no functions
        tools.addSeparator();
        tools.addSeparator();
        tools.addSeparator();
        tools.addSeparator();
        tools.addSeparator();
        tools.addSeparator();
        tools.addSeparator();
        tools.addSeparator();
        tools.add(turn);
        setupGame();
        _frame.setVisible(true);
        return(true);
    }

    /**
     * Intermediary method to reset the game state to either
     *     1) A previous game state (load)
     *     2) The starting game state (new)
     */
    public void reloadBoard() {
        _frame.dispose();
        drawBoard();
    }

    /**
     * Initializes the assumed-drawn board with the start state
     * @param Not Available
     * @return Not Available
     */
    public void setupGame(){
        //reinitialize the panels for new games
        _ttt = new JPanel();
        _ttt.setLayout(new GridLayout(gameWidth, gameHeight));

        //At some point some smarter math need to be put in to automatically resize
        //any image to be the background

        //changes image into a certain sized BufferedImage
        BufferedImage backgroundImage = new BufferedImage(425, 425, BufferedImage.TYPE_INT_ARGB);

        //this magically resizes the background image to the right size
		Graphics2D graphics2D = (Graphics2D)backgroundImage.getGraphics();
        graphics2D.scale(3.21, 3.21);
       	graphics2D.drawImage(backgroundIcon, 0, 0, null);
       	graphics2D.dispose();

       	//creates new panel with correctly sized background image
        BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImage, 0, 0, 0);


        _newPanel = new JPanel();
        _newPanel.setLayout(new FlowLayout());
        for(int i=0; i<gameWidth; i++){
            for (int j=0; j<gameHeight; j++) {
                if(_buttons[i][j] == null) {
                    if((i == 0 && (j > 2 && j < 8)) || (i == 1 && (j == 5))){
                        //top black pieces
                        _buttons[i][j] = new JButton(axeIcon);
                    }
                    else if((j == 0 && (i > 2 && i < 8)) || (i == 5 && (j == 1))){
                        //left black pieces
                        _buttons[i][j] = new JButton(axeIcon);
                    }
                    else if((j == 10 && (i > 2 && i < 8)) || (i == 5 && (j == 9))){
                        //right black pieces
                        _buttons[i][j] = new JButton(axeIcon);
                    }
                    else if((i == 10 && (j > 2 && j < 8)) || (i == 9 && (j == 5))){
                        //bottom black pieces
                        _buttons[i][j] = new JButton(axeIcon);
                    }
                    else if(i == 5 && j == 5){
                        _buttons[i][j] = new JButton(kingIcon);
                    }
                    else if((i == 3 && j == 5) || (i == 4 && (j > 3 && j <7)) || (i == 5 && (j > 2 && j <8)) || (i == 6 && (j > 3 && j <7)) || (i == 7 && j == 5)){
                        //center white pieces
                        _buttons[i][j] = new JButton(defenseIcon);
                    }
                    else{
                        // Make a new button in the array location with text "_"
                        _buttons[i][j] = new JButton(emptyImageIcon);

                    }
                }
                // Associate a new ButtonListener to the button (see below)
                ActionListener buttonListener = new ButtonListener();
                _buttons[i][j].addActionListener(buttonListener);
                // Set the font on the button
                _buttons[i][j].setFont(new Font("Courier", Font.PLAIN, 48));
                //set button tranparent for cool background
                _buttons[i][j].setOpaque(false);
                _buttons[i][j].setContentAreaFilled(false);
                //_buttons[i][j].setBorderPainted(false);
                // Add this button to the _ttt panel
                _buttons[i][j].setBorder(null);
                _ttt.add(_buttons[i][j]);
            }
        }

        // This will place the tic-tac-toe panel at the top of
        // the frame and the newPanel panel at the bottom
        _frame.add(backgroundPanel, BorderLayout.CENTER);
        _ttt.setBorder(null);
        backgroundPanel.add(_ttt, BorderLayout.CENTER);
        _frame.add(_newPanel, BorderLayout.SOUTH);

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
    public int[] getXandY(JButton jb){
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

    /*
    *
    * kill pieces as they are surrounded Othello style (King is an exception)
    * @param piecePlacement Jbutton that is where the latest piece was placed to see if a piece is destroyed
    * @return returns boolean true if a piece is taken out
    */
    public boolean attackPieces(JButton piecePlacement){
        int[] placement = getXandY(piecePlacement);
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

        if(placement[0] + 2 < 11){
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
        if(placement[1] + 2 < 11){
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

        return true;
    }
    /*
    *  check if there are any pieces left for any team
    *  @param Not Available
    *  @return returns int 0 for no winning condition, int 1 for king surrounded shields lose, int 2 for axe defeated for no more pieces
    */
    public int piecesLeft(){
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

                    if(i + 1 < 11){
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
                    if(j + 1 < 11){
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
    public boolean isValidMove(int[] start, int[] destination, boolean isKingPiece){

        if(start[0] == destination[0] && start[1] == destination[1] )
            return false;
        else if(destination[0] == 5 && destination[1] == 5)
            return false;
        else if( (start[0] == destination[0] || start[1] == destination[1]) ){

        	if(!isSpaceOccupied(destination)){

	        	if(canMoveToDestination(start, destination)){
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
    public boolean canMoveToDestination(int[] start, int[] destination){
        int[] counter = new int[2];
        counter[0] = start[0];
        counter[1] = start[1];
        if(start[0] == destination[0]){
            if(start[1] < destination[1]){
            	counter[1]++;
                while(counter[1] < destination[1]){
                    if(isSpaceOccupied(counter))
                        return false;
                    counter[1]++;
                }
            }
            else
            {
            	counter[1]--;
                while(counter[1] > destination[1]){
                    if(isSpaceOccupied(counter))
                        return false;
                    counter[1]--;
                }
            }
        }
        else if(start[1] == destination[1]){
            if(start[0] < destination[0]){
                counter[0]++;
                while(counter[0] < destination[0]){
                    if(isSpaceOccupied(counter))
                        return false;
                    counter[0]++;
                }
            }
            else
            {
            	counter[0]--;
                while(counter[0] > destination[0]){
                    if(isSpaceOccupied(counter))
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
    public boolean isSpaceOccupied(int[] destination){
        ImageIcon currentImageIcon = (ImageIcon) _buttons[destination[0]][destination[1]].getIcon();
        if( !currentImageIcon.getDescription().equals("empty") )
            return true;
        return false;
    }

    /**
     * Main method
     */
    public static void main(String[] args) {
        Hnefatafl game = new Hnefatafl();
        // game.drawBoard();
    }

    // #################################################################
    // Button listeners
    // #################################################################

    /**
     * Custom action listener executes every time a button on the game board is clicked.
     *
     * Listener also handles win conditions and enforces turns through the use of
     * _firstClick and _secondClick buttons, as well as the isFirstTurn boolean,
     * describing whose turn it is supposed to be.
     */
    private class ButtonListener implements ActionListener {

        // Every time we click the button, it will perform
        // the following action.

        @Override
        public void actionPerformed(ActionEvent e) {

            JButton temp = (JButton) e.getSource();
            ImageIcon currentImageIcon = (ImageIcon)temp.getIcon();
            int noPiecesCheck;

            if(_firstClick == null && currentImageIcon.getDescription().equals(emptyImageIcon.getDescription())){
                //Spit out some error message saying there is no game piece here
            }
            else{
                //Turn enforcing
                if(_firstClick == null){
                    _firstClick = (JButton) e.getSource();
                    firstClickImageIcon = (ImageIcon)_firstClick.getIcon();
                    if(isFirstPlayer){
                        if(firstClickImageIcon.getDescription().equals(defenseIcon.getDescription()) || firstClickImageIcon.getDescription().equals(kingIcon.getDescription())){
                            _firstClick = null;
                            //Might want to add more functionality later. To have a pop up telling user it is not their turn.

                        }
                    }
                    else if(!isFirstPlayer){
                        if(firstClickImageIcon.getDescription().equals(axeIcon.getDescription())){
                            _firstClick = null;
                             //Might want to add more functionality later. To have a pop up telling user it is not their turn.
                        }
                    }

                }
                else{
                    _secondClick = (JButton) e.getSource();
                    secondClickImageIcon = (ImageIcon)_secondClick.getIcon();
                    if(isValidMove(getXandY(_firstClick), getXandY(_secondClick), firstClickImageIcon.getDescription().equals(kingIcon.getDescription())) ){
                        if(firstClickImageIcon.getDescription().equals(axeIcon.getDescription())) {
                            _secondClick.setIcon(axeIcon);
                            attackPieces(_secondClick);
                            isFirstPlayer=false;
                            turn.setText("Shield Moves");
                        }
                        else if(firstClickImageIcon.getDescription().equals(defenseIcon.getDescription())
                                || firstClickImageIcon.getDescription().equals(kingIcon.getDescription()))
                        {
                            if(firstClickImageIcon.getDescription().equals(kingIcon.getDescription())){
                                _secondClick.setIcon(kingIcon);
                                attackPieces(_secondClick);
                            }
                            else{
                                _secondClick.setIcon(defenseIcon);
                                attackPieces(_secondClick);
                            }

                            if(((getXandY(_secondClick)[0] == 0 && getXandY(_secondClick)[1] == 0)
                                    || (getXandY(_secondClick)[0] == 0 && getXandY(_secondClick)[1] == 10)
                                    || (getXandY(_secondClick)[0] == 10 && getXandY(_secondClick)[1] == 0)
                                    || (getXandY(_secondClick)[0] == 10 && getXandY(_secondClick)[1] == 10))
                                    && firstClickImageIcon.getDescription().equals("K"))
                            {
                                turn.setText("Shield Wins!");
                                for(int i = 0; i < 11; i++){
                                    for(int j = 0; j < 11; j++){
                                        _buttons[i][j].setEnabled(false);
                                    }
                                }
                            }
                            else{
                                isFirstPlayer=true;
                                turn.setText("Axe Moves");
                            }
                        }
                        _firstClick.setIcon(emptyImageIcon);
                        _firstClick = null;
                        _secondClick = null;

                        //check if there are no pieces left to see if theres a winner
                        noPiecesCheck = piecesLeft();
                        //shields win
                        if(noPiecesCheck == 1){
                            turn.setText("Shield Wins!");
                            for(int i = 0; i < 11; i++){
                                for(int j = 0; j < 11; j++){
                                    _buttons[i][j].setEnabled(false);
                                }
                            }
                        }
                        //axes win
                        else if(noPiecesCheck == 2){
                            turn.setText("Axes Wins!");
                            for(int i = 0; i < 11; i++){
                                for(int j = 0; j < 11; j++){
                                    _buttons[i][j].setEnabled(false);
                                }
                            }
                        }
                    }
                    else{
                        _firstClick = null;
                        _secondClick = null;
                    }
                }
            }
        }
    }

    /**
     * Custom action listener handles resetting the board to the new game state.
     *
     * Listener also sets the turn to the first player.
     */
    private class NewButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Reset button array to redraw the board in the "new game" state
            _buttons = new JButton[gameWidth][gameHeight];
            // Set the first player's turn
            isFirstPlayer = true;
            reloadBoard();
        }
    }

    /**
     * Custom action listener handles saving the state of the game board using
     * object serialization.
     *
     * Allows the user to save the game state to a file of their choosing in the
     * location of the choosing.
     */
    private class SaveButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                JFileChooser fileChooser = new JFileChooser();
                // Only executes if the user does not cancel. No change to game state regardless.
                if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    FileOutputStream fos = new FileOutputStream(file);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);

                    // Save the current game board button states
                    for(int i=0; i < gameWidth; i++){
                        for(int j=0; j < gameHeight;j++){
                            oos.writeObject(_buttons[i][j]);
                        }
                    }

                    // Save the current player whose turn it is
                    oos.writeObject(isFirstPlayer);
                    oos.writeObject(turn);
                    oos.close();
                    fos.close();
                    JOptionPane.showMessageDialog(null, "File saved!");
                } else {
                    JOptionPane.showMessageDialog(null, "Operation canceled.");
                }
            } catch(IOException ex) {
                System.out.println("File Writing Error!");
            }
        }
    }

    /**
     * Custom action listener handles loading the state of the game board using
     * object deserialization.
     *
     * If the user does not load a game then the current game state is not interrupted
     * and the current game can resume as the user did not attempt to load a
     * previous game state.
     */
    private class LoadButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            try{
                JFileChooser fileChooser = new JFileChooser();
                // Only executes if the user does not cancel. Otherwise game state is not changed.
                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();

                    FileInputStream fip = new FileInputStream(file);
                    ObjectInputStream oip = new ObjectInputStream(fip);

                    // Load the previous game board button states
                    for(int i=0; i < gameWidth; i++){
                        for(int j=0; j < gameHeight;j++){
                            _buttons[i][j] = (JButton) oip.readObject();
                        }
                    }

                    // Load the current player whose turn it is
                    isFirstPlayer = (boolean) oip.readObject();
                    turn = (JLabel) oip.readObject();
                    oip.close();
                    fip.close();
                    JOptionPane.showMessageDialog(null, "File loaded!");
                    reloadBoard();
                } else {
                    JOptionPane.showMessageDialog(null, "Operation canceled.");
                }

            }catch(IOException ex) {
                System.out.println("File Reading Error!");
            } catch(ClassNotFoundException cex) {
                System.out.println("Class Not Found!");
            }
        }
    }
}
