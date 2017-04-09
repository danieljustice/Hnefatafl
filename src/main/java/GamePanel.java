import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.omg.CORBA.SystemException;

public class GamePanel extends JPanel {

    private int _gameWidth;
    private int _gameHeight;
    private ImageIcon _axeIcon;
    private ImageIcon _defenseIcon;
    private ImageIcon _kingIcon;
    private ImageIcon _emptyIcon;
    private GameLogic _gameLogic;

    public boolean isFirstPlayer = true;
    public JButton[][] _buttons;
    private JButton _firstClick = null;
    private JButton _secondClick = null;
    private ImageIcon _firstClickImageIcon = null;
    private ImageIcon _secondClickImageIcon = null;
    private boolean _axeStarted = false;
    private boolean _shieldStarted = false;
    private ClockTimer _axeTimer = new ClockTimer();
    private ClockTimer _shieldTimer = new ClockTimer();

    public GamePanel(
        int gameWidth, int gameHeight,
        ImageIcon axeIcon, ImageIcon defenseIcon,
        ImageIcon kingIcon, ImageIcon emptyIcon,
        ClockTimer axeTimer, ClockTimer shieldTimer
    ) {
        this._gameWidth = gameWidth;
        this._gameHeight = gameHeight;
        this._axeIcon = axeIcon;
        this._defenseIcon = defenseIcon;
        this._kingIcon = kingIcon;
        this._emptyIcon = emptyIcon;
        this._axeTimer = axeTimer;
        this._shieldTimer = shieldTimer;
        _gameLogic = new GameLogic(_gameWidth, _gameHeight);
        _buttons = new JButton[gameWidth][gameHeight];
    }

    public void setupGame() {
        reloadGame(new JButton[_gameWidth][_gameHeight]);
    }

    /**
     * Initializes the assumed-drawn board with the start state
     * @param Not Available
     * @return Not Available
     */
    public void reloadGame(JButton[][] _buttons){
        //reinitialize the panels for new games
        this.removeAll();
        this.setLayout(new GridLayout(_gameWidth, _gameHeight));

        for(int i=0; i<_gameWidth; i++){
            for (int j=0; j<_gameHeight; j++) {
                if(_buttons[i][j] == null) {
                    if((i == 0 && (j > 2 && j < 8)) || (i == 1 && (j == 5))){
                        //top black pieces
                        _buttons[i][j] = new JButton(_axeIcon);
                    }
                    else if((j == 0 && (i > 2 && i < 8)) || (i == 5 && (j == 1))){
                        //left black pieces
                        _buttons[i][j] = new JButton(_axeIcon);
                    }
                    else if((j == 10 && (i > 2 && i < 8)) || (i == 5 && (j == 9))){
                        //right black pieces
                        _buttons[i][j] = new JButton(_axeIcon);
                    }
                    else if((i == 10 && (j > 2 && j < 8)) || (i == 9 && (j == 5))){
                        //bottom black pieces
                        _buttons[i][j] = new JButton(_axeIcon);
                    }
                    else if(i == 5 && j == 5){
                        _buttons[i][j] = new JButton(_kingIcon);
                    }
                    else if((i == 3 && j == 5) || (i == 4 && (j > 3 && j <7)) || (i == 5 && (j > 2 && j <8)) || (i == 6 && (j > 3 && j <7)) || (i == 7 && j == 5)){
                        //center white pieces
                        _buttons[i][j] = new JButton(_defenseIcon);
                    }
                    else{
                        // Make a new button in the array location with text "_"
                        _buttons[i][j] = new JButton(_emptyIcon);

                    }
                }ActionListener listener = new ButtonListener();
                // Associate a new ButtonListener to the button (see below)
                _buttons[i][j].addActionListener(listener);
                // Set the font on the button
                //_buttons[i][j].setFont(new Font("Courier", Font.PLAIN, 48));
                //set button tranparent for cool background
                _buttons[i][j].setOpaque(false);
                _buttons[i][j].setContentAreaFilled(false);
                //_buttons[i][j].setBorderPainted(false);
                // Add this button to the this panel
                _buttons[i][j].setBorder(null);
                this.add(_buttons[i][j]);
            }
        }

        this.setBorder(null);
    }

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

            if(_firstClick == null && currentImageIcon.getDescription().equals(_emptyIcon.getDescription())){
                //Spit out some error message saying there is no game piece here
            }
            else{
                //Turn enforcing
                if(_firstClick == null){
                    _firstClick = (JButton) e.getSource();
                    _firstClickImageIcon = (ImageIcon)_firstClick.getIcon();
                    if(isFirstPlayer){
                        if(_firstClickImageIcon.getDescription().equals(_defenseIcon.getDescription()) || _firstClickImageIcon.getDescription().equals(_kingIcon.getDescription())){
                            _firstClick = null;
                            //Might want to add more functionality later. To have a pop up telling user it is not their turn.

                        }
                    }
                    else if(!isFirstPlayer){
                        if(_firstClickImageIcon.getDescription().equals(_axeIcon.getDescription())){
                            _firstClick = null;
                             //Might want to add more functionality later. To have a pop up telling user it is not their turn.
                        }
                    }

                }
                else{
                    _secondClick = (JButton) e.getSource();
                    _secondClickImageIcon = (ImageIcon)_secondClick.getIcon();
                    if(_gameLogic.isValidMove(_gameLogic.getXandY(_firstClick, _buttons), _gameLogic.getXandY(_secondClick, _buttons), _firstClickImageIcon.getDescription().equals(_kingIcon.getDescription()), _buttons)){
                        if(_firstClickImageIcon.getDescription().equals(_axeIcon.getDescription())) {
                            _secondClick.setIcon(_axeIcon);
                            //attackPieces(JButton piecePlacement, ImageIcon _emptyIcon, ImageIcon kingIcon, ImageIcon axeIcon, ImageIcon defenseIcon, int gameWidth, int gameHeight, JButton[][] _buttons)
                            _buttons = _gameLogic.attackPieces(_secondClick, _emptyIcon, _kingIcon, _axeIcon, _defenseIcon, _buttons);
                            isFirstPlayer=false;
                            _axeTimer.stopTimerThread();
                            _shieldStarted = true;
                            _shieldTimer.startTimerThread();

                            //turn.setText("Shield Moves");
                        }
                        else if(_firstClickImageIcon.getDescription().equals(_defenseIcon.getDescription())
                                || _firstClickImageIcon.getDescription().equals(_kingIcon.getDescription()))
                        {
                            if(_firstClickImageIcon.getDescription().equals(_kingIcon.getDescription())){
                                _secondClick.setIcon(_kingIcon);
                                _buttons = _gameLogic.attackPieces(_secondClick, _emptyIcon, _kingIcon, _axeIcon, _defenseIcon, _buttons);

                            }
                            else{
                                _secondClick.setIcon(_defenseIcon);
                                _buttons = _gameLogic.attackPieces(_secondClick, _emptyIcon, _kingIcon, _axeIcon, _defenseIcon, _buttons);

                            }

                            if(((_gameLogic.getXandY(_secondClick, _buttons)[0] == 0 && _gameLogic.getXandY(_secondClick, _buttons)[1] == 0)
                                    || (_gameLogic.getXandY(_secondClick, _buttons)[0] == 0 && _gameLogic.getXandY(_secondClick, _buttons)[1] == 10)
                                    || (_gameLogic.getXandY(_secondClick, _buttons)[0] == 10 && _gameLogic.getXandY(_secondClick, _buttons)[1] == 0)
                                    || (_gameLogic.getXandY(_secondClick, _buttons)[0] == 10 && _gameLogic.getXandY(_secondClick, _buttons)[1] == 10))
                                    && _firstClickImageIcon.getDescription().equals("K"))
                            {
                                //turn.setText("Shield Wins!");
                                for(int i = 0; i < 11; i++){
                                    for(int j = 0; j < 11; j++){
                                        _buttons[i][j].setEnabled(false);
                                    }
                                }
                            }
                            else{
                                isFirstPlayer=true;
                                _axeStarted = true;
                                _axeTimer.startTimerThread();
                                //turn.setText("Axe Moves");

                                _shieldTimer.stopTimerThread();
                            }
                        }
                        _firstClick.setIcon(_emptyIcon);
                        _firstClick = null;
                        _secondClick = null;

                        //check if there are no pieces left to see if theres a winner
                        noPiecesCheck = _gameLogic.piecesLeft(_axeIcon, _kingIcon, _buttons);
                        //shields win
                        // if(noPiecesCheck == 1 || (_axeTimer.isNull() && _axeStarted)){
                        //     turn.setText("Shield Wins!");
                        //     for(int i = 0; i < 11; i++){
                        //         for(int j = 0; j < 11; j++){
                        //             _buttons[i][j].setEnabled(false);
                        //         }
                        //     }
                        // }
                        // //axes win
                        // else if(noPiecesCheck == 2 || (_shieldTimer.isNull() && _shieldStarted)){
                        //     turn.setText("Axes Wins!");
                        //     for(int i = 0; i < 11; i++){
                        //         for(int j = 0; j < 11; j++){
                        //             _buttons[i][j].setEnabled(false);
                        //         }
                        //     }
                        // }
                    }
                    else{
                        _firstClick = null;
                        _secondClick = null;
                    }
                }
            }
        }
    }
}
