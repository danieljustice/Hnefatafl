import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.omg.CORBA.SystemException;


public class GamePanel {

    private int _gameWidth;
    private int _gameHeight;
    private JButton[][] _buttons;

    private ImageIcon _defenseIcon;
    private ImageIcon _axeIcon;
    private ImageIcon _kingIcon;
    private ImageIcon _emptyImageIcon;

    public GamePanel(
        int gameWidth, int gameHeight,
        ImageIcon defenseIcon, ImageIcon axeIcon,
        ImageIcon kingIcon, ImageIcon emptyImageIcon
    ) {
        this._gameWidth = gameWidth;
        this._gameHeight = gameHeight;
        this._buttons = new JButton[gameWidth][gameHeight];

        this._defenseIcon = defenseIcon;
        this._axeIcon = axeIcon;
        this._kingIcon = kingIcon;
        this._emptyImageIcon = emptyImageIcon;
    }

    public JButton[][] setupButtons() {
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
                        _buttons[i][j] = new JButton(_emptyImageIcon);

                    }
                }
                // Associate a new ButtonListener to the button (see below)
                // ActionListener buttonListener = new ButtonListener();
                // _buttons[i][j].addActionListener(buttonListener);
                // Set the font on the button
                _buttons[i][j].setFont(new Font("Courier", Font.PLAIN, 48));
                //set button tranparent for cool background
                _buttons[i][j].setOpaque(false);
                _buttons[i][j].setContentAreaFilled(false);
                //_buttons[i][j].setBorderPainted(false);
                // Add this button to the _ttt panel
                _buttons[i][j].setBorder(null);
            }
        }
        return _buttons;
    }


}
