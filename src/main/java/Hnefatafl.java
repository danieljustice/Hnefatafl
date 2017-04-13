import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.omg.CORBA.SystemException;

public class Hnefatafl extends JPanel implements BackgroundInterface{

    public int gameWidth = 11;
    public int gameHeight = 11;
    private int frameWidth = 850;
    private int frameHeight = 850;
    private JFrame _frame = new JFrame("Hnefatafl");
    private JPanel _ttt = new JPanel();
    JLabel turn = new JLabel("Axe Moves");
    private JPanel _newPanel = new JPanel();
    private JPanel _timerPanel = new JPanel();
    public GameLogic gameLogic = new GameLogic(gameWidth, gameHeight);


    private JButton _firstClick = null;
    private JButton _secondClick = null;
    private boolean isFirstPlayer = true;
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


    private ClockTimer axeTimer = new ClockTimer();
    private ClockTimer shieldTimer = new ClockTimer();
    public Hnefatafl() {
        //pull in images for icons on the buttons
        if(loadImages()){
            //setupGame();
        }
    }

    /**
    * Loads all images required for game and returns true on success
    * @return true on successfully loading all images, false on failure to load at least one image
    **/
    public boolean loadImages(){
        boolean success = true;
        
        defenseIcon = ImageFactory.createImageIcon("src/assets/First Shield.png", "shield");
        
        axeIcon = ImageFactory.createImageIcon("src/assets/First Axe.png", "axe");
        
        kingIcon = ImageFactory.createImageIcon("src/Crown.PNG", "king");
        
        emptyImageIcon = ImageFactory.createImageIcon("src/assets/empty.png", "empty");


        // try {
        //     backgroundIcon = ImageIO.read(new File("src/assets/simpleBoard.png"));
        // } catch (Exception e) {
        //     //TODO: handle exception
        //     System.out.println("Board: " + e);
        //     success = false;
        // }
        return success;
    }

    /**
    *
    */
	public boolean drawClock(){

		//JFrame.setDefaultLookAndFeelDecorated(true);
		_timerPanel = new JPanel();
		//_timerPanel.setSize(300,150);
		_timerPanel.setLayout(new GridLayout(1, 1));
		_timerPanel.add(axeTimer);
		_timerPanel.add(shieldTimer);
		_timerPanel.setVisible(true);
	    return true;
	}


    /**
     * Intermediary method to reset the game state to either
     *     1) A previous game state (load)
     *     2) The starting game state (new)
     */
    public void reloadBoard() {
        _frame.dispose();
        setupGame();
    }

    /**
     * Initializes the assumed-drawn board with the start state
     * @param Not Available
     * @return Not Available
     */
    public JPanel setupGame(){
        //reinitialize the panels for new games
        _ttt = new JPanel();
        _ttt.setLayout(new GridLayout(gameWidth, gameHeight));

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
                // Add this button to the _ttt panel
                _buttons[i][j].setBorder(null);
                _ttt.add(_buttons[i][j]);
            }
        }

        _ttt.setBorder(null);
        return _ttt;    
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
    public class ButtonListener implements ActionListener {

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
                    System.out.println("First click is null");
                    _firstClick = (JButton) e.getSource();
                    firstClickImageIcon = (ImageIcon)_firstClick.getIcon();
                    if(isFirstPlayer){
                        System.out.println("Is First Player");
                        if(firstClickImageIcon.getDescription().equals(defenseIcon.getDescription()) || firstClickImageIcon.getDescription().equals(kingIcon.getDescription())){
                           System.out.println("First Here");
                            _firstClick = null;
                            //Might want to add more functionality later. To have a pop up telling user it is not their turn.

                        }
                    }
                    else if(!isFirstPlayer){
                        System.out.println("Not First");
                        if(firstClickImageIcon.getDescription().equals(axeIcon.getDescription())){
                            _firstClick = null;
                             //Might want to add more functionality later. To have a pop up telling user it is not their turn.
                        }
                    }

                }
                else{
                    _secondClick = (JButton) e.getSource();
                    secondClickImageIcon = (ImageIcon)_secondClick.getIcon();
                    System.out.println("Not First Click");
                    if(gameLogic.isValidMove(gameLogic.getXandY(_firstClick, _buttons), gameLogic.getXandY(_secondClick, _buttons), firstClickImageIcon.getDescription().equals(kingIcon.getDescription()), _buttons)){
                        if(firstClickImageIcon.getDescription().equals(axeIcon.getDescription())) {
                            _secondClick.setIcon(axeIcon);
                            //attackPieces(JButton piecePlacement, ImageIcon emptyImageIcon, ImageIcon kingIcon, ImageIcon axeIcon, ImageIcon defenseIcon, int gameWidth, int gameHeight, JButton[][] _buttons)
                            _buttons = gameLogic.attackPieces(_secondClick, emptyImageIcon, kingIcon, axeIcon, defenseIcon, _buttons);
                            isFirstPlayer=false;
                            axeTimer.stopTimerThread();
                            shieldStarted = true;
                            shieldTimer.startTimerThread();

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
                                    axeStarted = true;
                                   axeTimer.startTimerThread();
                                turn.setText("Axe Moves");

                                shieldTimer.stopTimerThread();
                            }
                        }
                        _firstClick.setIcon(emptyImageIcon);
                        _firstClick = null;
                        _secondClick = null;

                        //check if there are no pieces left to see if theres a winner
                        noPiecesCheck = gameLogic.piecesLeft(axeIcon, kingIcon, _buttons);
                        //shields win
                        if(noPiecesCheck == 1 || (axeTimer.isNull() && axeStarted)){
                            turn.setText("Shield Wins!");
                            for(int i = 0; i < 11; i++){
                                for(int j = 0; j < 11; j++){
                                    _buttons[i][j].setEnabled(false);
                                }
                            }
                        }
                        //axes win
                        else if(noPiecesCheck == 2 || (shieldTimer.isNull() && shieldStarted)){
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
    public class NewButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Reset button array to redraw the board in the "new game" state
            _buttons = new JButton[gameWidth][gameHeight];
            // Set the first player's turn
            isFirstPlayer = true;
            turn.setText("Axe Moves");
            axeTimer = new ClockTimer();
            shieldTimer = new ClockTimer();
            axeStarted = false;
            shieldStarted = false;
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
    public class SaveButtonListener implements ActionListener{
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
                    oos.writeObject(axeTimer.getTime());
                    oos.writeObject(shieldTimer.getTime());
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
    public class LoadButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            loadGameData();
        }
    }


    /**
     * Custom action listener handles resigning the game on the board
     *
     * the current user that hits the resign button should forfeit and the next
     */
	public class ResignButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(isFirstPlayer){
				turn.setText("Shields Wins!");
				for(int i = 0; i < 11; i++){
					for(int j = 0; j < 11; j++){
						_buttons[i][j].setEnabled(false);
					}
				}
			}
			else{
				turn.setText("Axes Wins!");
				for(int i = 0; i < 11; i++){
					for(int j = 0; j < 11; j++){
						_buttons[i][j].setEnabled(false);
					}
				}
			}
        }
    }



    public JPanel loadGameData(){
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
                    int axeTime = (int) oip.readObject();
                    int shieldTime = (int) oip.readObject();
                    axeTimer = new ClockTimer(axeTime);
                    shieldTimer = new ClockTimer(shieldTime);
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
        return null;
    }

    public boolean saveGameData(){
        return false;
    }

    public JPanel[] removeGamePanel(){
        return null;
    }

    public boolean addGamePanel(JPanel gamePanel){
        return false;
    }
}

