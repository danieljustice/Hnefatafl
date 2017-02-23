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
    JFrame _frame = new JFrame("Hnefatafl");
    JPanel _ttt = new JPanel();
    JPanel _newPanel = new JPanel();
    public JButton[][] _buttons = new JButton[gameWidth][gameHeight];
    int CLICKS = 0;

    public ImageIcon defenseIcon;
    public ImageIcon axeIcon;
    public ImageIcon emptyImageIcon;

    JButton _firstClick = null;
    JButton _secondClick = null;
    boolean isFirstPlayer = true;
    ImageIcon firstClickImageIcon = null;
    ImageIcon secondClickImageIcon = null;

    public Hnefatafl() {
        //pull in images for icons on the buttons
        try{
            //ImageIcons are public so we can test them in unit tests
            defenseIcon = new ImageIcon(ImageIO.read(new File("src/First Shield.png")));
            axeIcon = new ImageIcon(ImageIO.read(new File("src/First Axe.png")));
            emptyImageIcon =new ImageIcon(ImageIO.read(new File("src/empty.png")));

            //give each icon a description so we can compare them later
            defenseIcon.setDescription("shield");
            axeIcon.setDescription("axe");
            emptyImageIcon.setDescription("empty");
        }catch(Exception e){
            //woops just in case we cant pull in a file
            //Note: path for file we read must be relative to src/ folder
            System.out.println("WOOPS " + e);
        }
    }

    /**
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

        setupGame();
        _frame.setVisible(true);
        return(true);
    }

    public void reloadBoard() {
        _frame.dispose();
        drawBoard();
    }

    /**
     * Initializes the assumed-drawn board with the start state
     */
    public void setupGame(){
        //reinitialize the panels for new games
        _ttt = new JPanel();
        _ttt.setLayout(new GridLayout(gameWidth, gameHeight));


        _newPanel = new JPanel();
        _newPanel.setLayout(new FlowLayout());
        for(int i=0; i<gameWidth; i++){
            for (int j=0; j<gameHeight ; j++) {

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
                // Add this button to the _ttt panel
                _ttt.add(_buttons[i][j]);
            }
        }

        // This will place the tic-tac-toe panel at the top of
        // the frame and the newPanel panel at the bottom
        _frame.add(_ttt, BorderLayout.CENTER);
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

    /**
     * Returns whether a move is valid based on input arrays which store x and y locations
     *
     * @param start an integer array with 2 values, [0] index is x, [1] is y
     * @param destination   an integer array with 2 values, [0] index is x, [1] is y
     * @return  returns true if valid move, false if not
     */
    public boolean isValidMove(int[] start, int[] destination){
        if(start[0] == destination[0] && start[1] == destination[1] )
            return false;
        else if(start[0] == destination[0] || start[1] == destination[1])
            return true;
        return false;
    }

    /**
     * Main method
     */
    public static void main(String[] args) {
        Hnefatafl game = new Hnefatafl();
        game.drawBoard();
    }

    // #################################################################
    // Button listeners
    // #################################################################

    private class ButtonListener implements ActionListener {

        // Every time we click the button, it will perform
        // the following action.

        public void actionPerformed(ActionEvent e) {

            JButton temp = (JButton) e.getSource();
            ImageIcon currentImageIcon = (ImageIcon)temp.getIcon();

            if(_firstClick == null && currentImageIcon.getDescription().equals(emptyImageIcon.getDescription())){
                //Spit out some error message saying there is no game piece here
            }
            else{
                //Turn enforcing
                if(_firstClick == null){
                    _firstClick = (JButton) e.getSource();
                    firstClickImageIcon = (ImageIcon)_firstClick.getIcon();
                    if(isFirstPlayer){
                        if(firstClickImageIcon.getDescription().equals(defenseIcon.getDescription())){
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
                    if(isValidMove(getXandY(_firstClick), getXandY(_secondClick)) ){
                        if(firstClickImageIcon.getDescription().equals(axeIcon.getDescription())) {
                            _secondClick.setIcon(axeIcon);
                            isFirstPlayer=false;
                        }
                        else if(firstClickImageIcon.getDescription().equals(defenseIcon.getDescription())) {
                            _secondClick.setIcon(defenseIcon);
                            isFirstPlayer=true;
                        }
                        _firstClick.setIcon(emptyImageIcon);
                        _firstClick = null;
                        _secondClick = null;
                    }
                    else
                        _firstClick = null;
                        _secondClick = null;
                }
            }
        }
    }

    private class NewButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            _buttons = new JButton[gameWidth][gameHeight];
            isFirstPlayer = true;
            reloadBoard();
        }
    }

    private class SaveButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                FileOutputStream fos = new FileOutputStream("saves.ser");
                ObjectOutputStream oos = new ObjectOutputStream(fos);

                for(int i=0; i < gameWidth; i++){
                    for(int j=0; j < gameHeight;j++){
                        oos.writeObject(_buttons[i][j]);
                    }
                }
                oos.writeObject(isFirstPlayer);
                oos.close();
                fos.close();
            } catch(IOException ex) {
                System.out.println("File Writing Error!");
            }
        }
    }

    private class LoadButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            try{
                FileInputStream fip = new FileInputStream("saves.ser");
                ObjectInputStream oip = new ObjectInputStream(fip);

                for(int i=0; i < gameWidth; i++){
                    for(int j=0; j < gameHeight;j++){
                        _buttons[i][j] = (JButton) oip.readObject();
                    }
                }
                isFirstPlayer = (boolean) oip.readObject();
                oip.close();
                fip.close();
                reloadBoard();

            }catch(IOException ex) {
                System.out.println("File Reading Error!");
            } catch(ClassNotFoundException cex) {
                System.out.println("Class Not Found!");
            }
        }
    }
}
