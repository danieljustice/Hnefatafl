import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.omg.CORBA.SystemException;

public class Hnefatafl extends ClockTimer{

    public int gameWidth = 11;
    public int gameHeight = 11;
    private int frameWidth = 850;
    private int frameHeight = 850;
    private JFrame _frame = new JFrame("Hnefatafl");
    private JPanel _ttt = new JPanel();
    JLabel turn = new JLabel("Axe Moves");
    private JPanel _newPanel = new JPanel();
    private JPanel _timerPanel = new JPanel();


    // private boolean isFirstPlayer = true;
    // private JButton _firstClick = null;
    // private JButton _secondClick = null;
    // private ImageIcon firstClickImageIcon = null;
    // private ImageIcon secondClickImageIcon = null;
    // private boolean axeStarted = false;
    // private boolean shieldStarted = false;
    private ClockTimer axeTimer = new ClockTimer();
    private ClockTimer shieldTimer = new ClockTimer();
    private GamePanel gamePanel;

    public JButton[][] _buttons = new JButton[gameWidth][gameHeight];
    public ImageIcon defenseIcon;
    public ImageIcon axeIcon;
    public ImageIcon kingIcon;
    public ImageIcon emptyImageIcon;
    public Image backgroundIcon;


    public Hnefatafl() {
        //pull in images for icons on the buttons
        if(loadImages()){
        	drawClock();
            gamePanel = new GamePanel(
                gameWidth, gameHeight,
                axeIcon, defenseIcon,
                kingIcon, emptyImageIcon,
                axeTimer, shieldTimer
            );
            drawBoard();
        }
    }

    /**
    * Loads all images required for game and returns true on success
    * @return true on successfully loading all images, false on failure to load at least one image
    **/
    public boolean loadImages(){
        boolean success = true;
        try {
            defenseIcon = new ImageIcon(ImageIO.read(new File("src/Assets/First Shield.png")));
            defenseIcon.setDescription("shield");
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println("Shield: " + e);
            success = false;
        }


        try {
            axeIcon = new ImageIcon(ImageIO.read(new File("src/Assets/First Axe.png")));
            axeIcon.setDescription("axe");
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println("Axe: " + e);
            success = false;
        }

        try {
            kingIcon = new ImageIcon(ImageIO.read(new File("src/Crown.PNG")));

            kingIcon.setDescription("king");
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println("Crown: " + e);
            success = false;
        }


        try {
            emptyImageIcon = new ImageIcon(ImageIO.read(new File("src/Assets/empty.png")));
            emptyImageIcon.setDescription("empty");

        } catch (Exception e) {
            //TODO: handle exception
            System.out.println("Empty: " + e);
            success = false;
        }
        try {
            backgroundIcon = ImageIO.read(new File("src/Assets/simpleBoard.png"));
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println("Board: " + e);
            success = false;
        }
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

	JButton resignButton = new JButton("Resign");
        ActionListener resignButtonListener = new ResignButtonListener();
	resignButton.addActionListener(resignButtonListener);
	tools.add(resignButton); //no functions

        tools.addSeparator();
        tools.addSeparator();
        tools.addSeparator();
        tools.addSeparator();
        tools.addSeparator();
        tools.addSeparator();
        tools.addSeparator();
        tools.addSeparator();
        tools.add(turn);
        tools.add(_timerPanel, BorderLayout.PAGE_START);
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
        drawBoard();
    }

    /**
     * Initializes the assumed-drawn board with the start state
     * @param Not Available
     * @return Not Available
     */
    public void setupGame(){
        //reinitialize the panels for new games
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

        gamePanel.reloadGame(_buttons);

        // This will place the tic-tac-toe panel at the top of
        // the frame and the newPanel panel at the bottom
        _frame.add(backgroundPanel, BorderLayout.CENTER);
        backgroundPanel.add(gamePanel, BorderLayout.CENTER);

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
     * Custom action listener handles resetting the board to the new game state.
     *
     * Listener also sets the turn to the first player.
     */
    private class NewButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // // Reset button array to redraw the board in the "new game" state
            // _buttons = new JButton[gameWidth][gameHeight];
            // // Set the first player's turn
            // isFirstPlayer = true;
            // turn.setText("Axe Moves");
            // axeTimer = new ClockTimer();
            // shieldTimer = new ClockTimer();
            // axeStarted = false;
            // shieldStarted = false;
            // reloadBoard();
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
            // try{
            //     JFileChooser fileChooser = new JFileChooser();
            //     // Only executes if the user does not cancel. No change to game state regardless.
            //     if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            //         File file = fileChooser.getSelectedFile();
            //         FileOutputStream fos = new FileOutputStream(file);
            //         ObjectOutputStream oos = new ObjectOutputStream(fos);

            //         // Save the current game board button states
            //         for(int i=0; i < gameWidth; i++){
            //             for(int j=0; j < gameHeight;j++){
            //                 oos.writeObject(_buttons[i][j]);
            //             }
            //         }

            //         // Save the current player whose turn it is
            //         oos.writeObject(isFirstPlayer);
            //         oos.writeObject(turn);
            //         oos.writeObject(axeTimer.getTime());
            //         oos.writeObject(shieldTimer.getTime());
            //         oos.close();
            //         fos.close();
            //         JOptionPane.showMessageDialog(null, "File saved!");
            //     } else {
            //         JOptionPane.showMessageDialog(null, "Operation canceled.");
            //     }
            // } catch(IOException ex) {
            //     System.out.println("File Writing Error!");
            // }
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

            // try{
            //     JFileChooser fileChooser = new JFileChooser();
            //     // Only executes if the user does not cancel. Otherwise game state is not changed.
            //     if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            //         File file = fileChooser.getSelectedFile();

            //         FileInputStream fip = new FileInputStream(file);
            //         ObjectInputStream oip = new ObjectInputStream(fip);

            //         // Load the previous game board button states
            //         for(int i=0; i < gameWidth; i++){
            //             for(int j=0; j < gameHeight;j++){
            //                 _buttons[i][j] = (JButton) oip.readObject();
            //             }
            //         }

            //         // Load the current player whose turn it is
            //         isFirstPlayer = (boolean) oip.readObject();
            //         turn = (JLabel) oip.readObject();
            //         int axeTime = (int) oip.readObject();
            //         int shieldTime = (int) oip.readObject();
            //         axeTimer = new ClockTimer(axeTime);
            //         shieldTimer = new ClockTimer(shieldTime);
            //         oip.close();
            //         fip.close();
            //         JOptionPane.showMessageDialog(null, "File loaded!");
            //         reloadBoard();
            //     } else {
            //         JOptionPane.showMessageDialog(null, "Operation canceled.");
            //     }

            // }catch(IOException ex) {
            //     System.out.println("File Reading Error!");
            // } catch(ClassNotFoundException cex) {
            //     System.out.println("Class Not Found!");
            // }
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
            if(gamePanel.isFirstPlayer){
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
}

