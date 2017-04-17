import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.omg.CORBA.SystemException;

public class Hnefatafl{

    public int gameWidth = 11;
    public int gameHeight = 11;
    private int frameWidth = 1000;
    private int frameHeight = 950;
    private int axeGamePieces = 24;
    private int shieldGamePieces = 13;
    private static final String axeWinMessage = "      Axes Win!      ";
    private static final String shieldWinMessage = "      Shields Win!      ";

    private JFrame _frame = new JFrame("Hnefatafl");
    private JPanel _ttt = new JPanel();
    JLabel turn = new JLabel("  Axe Moves     ");
    private JPanel _newPanel = new JPanel();
    private JPanel _axeTimerPanel = new JPanel();
    private JPanel _shieldTimerPanel = new JPanel();
    private JPanel _axeScorePanel = new JPanel();
    private JPanel _shieldScorePanel = new JPanel();
    public GameLogic gameLogic = new GameLogic(gameWidth, gameHeight);

    private JButton _firstClick = null;
    private JButton _secondClick = null;
    public boolean isFirstPlayer = true;
    private ImageIcon firstClickImageIcon = null;
    private ImageIcon secondClickImageIcon = null;
    private boolean axeStarted = false;
    private boolean shieldStarted = false;

    public JButton[][] _buttons = new JButton[gameWidth][gameHeight];
    public ImageIcon defenseIcon;
    public ImageIcon axeIcon;
    public ImageIcon kingIcon;
    public ImageIcon emptyImageIcon;
    public Image backgroundIcon;

    private ClockTimer axeTimer = null;
    private ClockTimer shieldTimer = null;
    private GamePieces axePieces = null;
    private GamePieces shieldPieces = null;

    public Hnefatafl() {
        //add listeners to the clocktimers
        axeTimer = new ClockTimer();
        shieldTimer = new ClockTimer();
        axeTimer.addPropertyChangeListener(new TimePropertyChangeListener());
        shieldTimer.addPropertyChangeListener(new TimePropertyChangeListener());

        //pull in images for icons on the buttons
        if(loadImages()){
        	drawClock();
            drawPieceRemaining();
            drawBoard();
        }
    }

    /**
    * Loads all images required for game and returns true on success
    * @return true on successfully loading all images, false on failure to load at least one image
    **/
    public boolean loadImages(){
        boolean success = true;

        defenseIcon = ImageFactory.createImageIcon("src/Assets/First Shield.png", "shield");

        axeIcon = ImageFactory.createImageIcon("src/Assets/First Axe.png", "axe");

        kingIcon = ImageFactory.createImageIcon("src/Assets/Crown.png", "king");

        emptyImageIcon = ImageFactory.createImageIcon("src/Assets/empty.png", "empty");

        backgroundIcon = ImageFactory.createBufferedImage("src/Assets/simpleBoard.png");


        return success;
    }

    /**
    *
    */
	public boolean drawClock(){

        _axeTimerPanel = new JPanel();
        _shieldTimerPanel = new JPanel();
        JLabel axeLabel = new JLabel("  Axe Timer:");
        JLabel shieldLabel = new JLabel("Shield Timer:");
        _axeTimerPanel.add(axeLabel);
		_axeTimerPanel.add(axeTimer);
        _shieldTimerPanel.add(shieldLabel);
		_shieldTimerPanel.add(shieldTimer);
        _axeTimerPanel.setVisible(true);
		_shieldTimerPanel.setVisible(true);
	    return true;
	}

    /*
    *
    */
    public boolean drawPieceRemaining(){
        axePieces = new GamePieces(axeGamePieces);
        shieldPieces = new GamePieces(shieldGamePieces);
        _shieldScorePanel = new JPanel();
        JLabel shieldLabel = new JLabel("Shield Remaining:");
        _shieldScorePanel.add(shieldLabel);
        _shieldScorePanel.add(shieldPieces);
        _axeScorePanel = new JPanel();
        JLabel axeLabel = new JLabel("Axe Remaining:");
        _axeScorePanel.add(axeLabel);
        _axeScorePanel.add(axePieces); //add ability to show remaining pieces
        _shieldScorePanel.setVisible(true);
        _axeScorePanel.setVisible(true);
        return true;
    }

    public boolean redrawPieceRemaining(){
        _shieldScorePanel.remove(shieldPieces);
        shieldPieces.setPieces(shieldGamePieces);
        _shieldScorePanel.add(shieldPieces);
        _shieldScorePanel.setVisible(true);
        _axeScorePanel.remove(axePieces);
        axePieces.setPieces(axeGamePieces);
        _axeScorePanel.add(axePieces);
        _axeScorePanel.setVisible(true);
        return true;
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
        //tools.setLayout(new GridLayout(2, 1));
        _frame.add(tools, BorderLayout.PAGE_START);

        JButton newButton = new JButton("New"); //no functions
        ActionListener newButtonListener = new NewButtonListener();
        newButton.addActionListener(newButtonListener);
        tools.add(newButton);
        //tools.addSeparator();

        JButton saveButton = new JButton("Save"); //no functions
        ActionListener saveButtonListener = new SaveButtonListener();
        saveButton.addActionListener(saveButtonListener);
        tools.add(saveButton);
        //tools.addSeparator();

        JButton loadButton = new JButton("Load"); //no functions
        ActionListener loadButtonListener = new LoadButtonListener();
        loadButton.addActionListener(loadButtonListener);
        tools.add(loadButton);
        //tools.addSeparator();

        JButton resignButton = new JButton("Resign");
        ActionListener resignButtonListener = new ResignButtonListener();
        resignButton.addActionListener(resignButtonListener);
        tools.add(resignButton); //no functions

		JButton bestButton = new JButton("Best Move");
        ActionListener bestButtonListener = new bestButtonListener();
		bestButton.addActionListener(bestButtonListener);
		tools.add(bestButton);

        //tools.addSeparator();
        tools.add(turn);

        //tools.addSeparator();
        //tools.addSeparator();
        //tools.addSeparator();
        //tools.addSeparator();
        //tools.addSeparator();
        //tools.addSeparator();
        //tools.addSeparator();



        tools.add(_axeTimerPanel);
        tools.add(_shieldTimerPanel);
        tools.add(_axeScorePanel);
        tools.add(_shieldScorePanel);
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
        drawClock();
        redrawPieceRemaining();
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

    /**
     * Method handles disabling buttons and setting the win message at
     * the end of the game.
     * Also pauses timers to prevent the loser from gaining a win status
     * when the original winner's clock reaches 0 post-game.
     * @param msg [description]
     */
    public void endGame(String msg) {
        turn.setText(msg);
        for(int i = 0; i < 11; i++){
            for(int j = 0; j < 11; j++){
                _buttons[i][j].setEnabled(false);
            }
        }
        axeTimer.pauseTimerThreadNoIncrement();
        shieldTimer.pauseTimerThreadNoIncrement();
    }

    /**
     * Main method
     */
    public static void main(String[] args) {
        Hnefatafl game = new Hnefatafl();
        //game.drawBoard();
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

            if(isFirstPlayer) {
                axeTimer.continueTimerThread();
            } else {
                shieldTimer.continueTimerThread();
            }


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
                    if(gameLogic.isValidMove(gameLogic.getXandY(_firstClick, _buttons), gameLogic.getXandY(_secondClick, _buttons), firstClickImageIcon.getDescription().equals(kingIcon.getDescription()), _buttons)){
                        if(firstClickImageIcon.getDescription().equals(axeIcon.getDescription())) {
                            _secondClick.setIcon(axeIcon);
                            //attackPieces(JButton piecePlacement, ImageIcon emptyImageIcon, ImageIcon kingIcon, ImageIcon axeIcon, ImageIcon defenseIcon, int gameWidth, int gameHeight, JButton[][] _buttons)
                            _buttons = gameLogic.attackPieces(_secondClick, emptyImageIcon, kingIcon, axeIcon, defenseIcon, _buttons);
                            shieldStarted = true;
                            shieldTimer.continueTimerThread();
                            isFirstPlayer=false;
                            axeTimer.pauseTimerThread();
							_buttons = gameLogic.removeCorrectChoice(_buttons);
                            turn.setText("Shield Moves");
                        }
                        else if(firstClickImageIcon.getDescription().equals(defenseIcon.getDescription())
                                || firstClickImageIcon.getDescription().equals(kingIcon.getDescription()))
                        {
                            if(firstClickImageIcon.getDescription().equals(kingIcon.getDescription())){
                                _secondClick.setIcon(kingIcon);
                                _buttons = gameLogic.attackPieces(_secondClick, emptyImageIcon, kingIcon, axeIcon, defenseIcon, _buttons);

                            }
                            else{
                                _secondClick.setIcon(defenseIcon);
                                _buttons = gameLogic.attackPieces(_secondClick, emptyImageIcon, kingIcon, axeIcon, defenseIcon, _buttons);

                            }

                            if(((gameLogic.getXandY(_secondClick, _buttons)[0] == 0 && gameLogic.getXandY(_secondClick, _buttons)[1] == 0)
                                    || (gameLogic.getXandY(_secondClick, _buttons)[0] == 0 && gameLogic.getXandY(_secondClick, _buttons)[1] == 10)
                                    || (gameLogic.getXandY(_secondClick, _buttons)[0] == 10 && gameLogic.getXandY(_secondClick, _buttons)[1] == 0)
                                    || (gameLogic.getXandY(_secondClick, _buttons)[0] == 10 && gameLogic.getXandY(_secondClick, _buttons)[1] == 10))
                                    && firstClickImageIcon.getDescription().equals("king"))
                            {
                                endGame(shieldWinMessage);
                            }
                            else{
                                isFirstPlayer=true;
                                axeStarted = true;
                                axeTimer.continueTimerThread();
                                shieldTimer.pauseTimerThread();
								_buttons = gameLogic.removeCorrectChoice(_buttons);
                                turn.setText("Axe Moves");
                            }
                        }
                        _firstClick.setIcon(emptyImageIcon);
                        _firstClick = null;
                        _secondClick = null;

                        //check if there are no pieces left to see if theres a winner
                        noPiecesCheck = gameLogic.piecesLeft(axeIcon, kingIcon, defenseIcon, _buttons);
						int[] tempPieces = gameLogic.numPiecesLeft();

						axeGamePieces = tempPieces[0];
						shieldGamePieces = tempPieces[1];

						redrawPieceRemaining();

                        //shields win
                        if(noPiecesCheck == 1 || (axeTimer.isNull() && axeStarted) || axeTimer.timeLeft == 0){
                            endGame(shieldWinMessage);
                        }
                        //axes win
                        else if(noPiecesCheck == 2 || (shieldTimer.isNull() && shieldStarted) || shieldTimer.timeLeft == 0){
                            endGame(axeWinMessage);
                        }
                    }
                    else{
                        _firstClick = null;
                        _secondClick = null;
                    }
                }
            }
            if(gameLogic.exitFort(_buttons)){
                endGame(shieldWinMessage);
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
            turn.setText("  Axe Moves     ");
            axeTimer = new ClockTimer();
            axeTimer.addPropertyChangeListener(new TimePropertyChangeListener());
            shieldTimer = new ClockTimer();
            shieldTimer.addPropertyChangeListener(new TimePropertyChangeListener());
            axeStarted = false;
            shieldStarted = false;
            axeGamePieces = 24;
            shieldGamePieces = 13;
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
                int axeTempTime = axeTimer.getTime();
                int shieldTempTime = shieldTimer.getTime();
                if(isFirstPlayer) {
                    axeTimer.pauseTimerThreadNoIncrement();
                } else {
                    shieldTimer.pauseTimerThreadNoIncrement();
                }
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
                    oos.writeObject(axeTempTime);
                    oos.writeObject(shieldTempTime);
                    oos.close();
                    fos.close();
                    JOptionPane.showMessageDialog(null, "File saved!");
                } else {
                    JOptionPane.showMessageDialog(null, "Operation canceled.");
                }
                if(isFirstPlayer) {
                    axeTimer.continueTimerThread();
                } else {
                    shieldTimer.continueTimerThread();
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
                if(isFirstPlayer) {
                    axeTimer.pauseTimerThreadNoIncrement();
                } else {
                    shieldTimer.pauseTimerThreadNoIncrement();
                }
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
                    int axeTime = (int) oip.readObject();
                    int shieldTime = (int) oip.readObject();

                    axeTimer = new ClockTimer(axeTime);
                    axeTimer.addPropertyChangeListener(new TimePropertyChangeListener());
                    shieldTimer = new ClockTimer(shieldTime);
                    shieldTimer.addPropertyChangeListener(new TimePropertyChangeListener());
                    oip.close();
                    fip.close();
                    JOptionPane.showMessageDialog(null, "File loaded!");
                    reloadBoard();
                } else {
                    JOptionPane.showMessageDialog(null, "Operation canceled.");
                }

                if(isFirstPlayer) {
                    axeTimer.continueTimerThread();
                } else {
                    shieldTimer.continueTimerThread();
                }
            }catch(IOException ex) {
                System.out.println("File Reading Error!");
            } catch(ClassNotFoundException cex) {
                System.out.println("Class Not Found!");
            }
        }
    }


    /**
     * Custom action listener handles resigning the game on the board
     *
     * the current user that hits the resign button should forfeit and the next
     */
	private class ResignButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(isFirstPlayer){
				endGame(shieldWinMessage);
			}
			else{
				endGame(axeWinMessage);
			}

        }
    }

    /**
     * Custom action listener handles binding to the "Best Move" button,
     * which is our implementation of one of the Hard tasks.
     */
	private class bestButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            _buttons = gameLogic.showCorrectChoice(_buttons, axeIcon, emptyImageIcon, kingIcon, defenseIcon, isFirstPlayer);
        }
    }

    /**
     * Custom Property Change Listener that handles when either
     * clocktimer reaches the time zero (0).  When one does, that
     * player loses and the endgame script is called.
     */
    private class TimePropertyChangeListener implements PropertyChangeListener{
        @Override
        public void propertyChange(PropertyChangeEvent e) {
            // If this is null, do nothing (catches a bug that happens when a new game is created)
            if(e.getNewValue() != null){
                //Checks to see if it was the text property that changed
                boolean isText = e.getPropertyName().equalsIgnoreCase("text");
                //Checks to see if the new value is 0
                boolean isAtZero = e.getNewValue().equals("0");

                if(isText && isAtZero){
                   //Uncomment for debug purposes
                   //System.out.println("Should call an end game function here");
                    if(isFirstPlayer) {
                        endGame(shieldWinMessage);
                    } else {
                        endGame(axeWinMessage);
                    }
                }
            }
        }
    }
}

