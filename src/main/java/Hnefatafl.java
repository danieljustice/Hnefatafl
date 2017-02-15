import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Hnefatafl {
    JFrame _frame = new JFrame("Hnefatafl");
    JPanel _ttt = new JPanel();
    JPanel _newPanel = new JPanel();
    public JButton[][] _buttons = new JButton[11][11];
    int CLICKS = 0;
    public ImageIcon defenseIcon;
    public ImageIcon axeIcon;
    public ImageIcon emptyImageIcon;
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

    public boolean drawBoard(){
    	
        _frame = new JFrame("Hnefatafl");
        _frame.setSize(850, 850);
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        _frame.add(tools, BorderLayout.PAGE_START);
        Action newGameAction = new AbstractAction("New") {

            @Override
            public void actionPerformed(ActionEvent e) {
                _frame.dispose();
                drawBoard();
            }
        };
        tools.add(newGameAction);
        tools.add(new JButton("Save")); //no functions
        tools.addSeparator();
        tools.add(new JButton("Resign")); //no functions
        tools.addSeparator();
        
        setupGame();
        _frame.setVisible(true);
        return(true);
    }
    
    public void setupGame(){
        //reinitialize the panels for new games
        _ttt = new JPanel();
        _ttt.setLayout(new GridLayout(11, 11));
        
        
        _newPanel = new JPanel();
        _newPanel.setLayout(new FlowLayout());
        for(int i=0; i<11; i++){
            for (int j=0; j<11 ; j++) {
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
    
    public static void main(String[] args) {
        Hnefatafl game = new Hnefatafl();
        game.drawBoard();
    }

    class ButtonListener implements ActionListener {

        // Every time we click the button, it will perform
        // the following action.

        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            ImageIcon currentText = (ImageIcon)source.getIcon();
            //compares the description of the ImageIcon on the button clicked to see if it is an
            //empty space
            if (currentText.getDescription().compareTo(emptyImageIcon.getDescription()) == 0) {
                //if it is an empty space, set ImageIcon to axe or shield based off of # of clicks
                if(CLICKS % 2 == 0)
                    source.setIcon(axeIcon);
                else
                    source.setIcon(defenseIcon);
                CLICKS++;
            } else {
                //if not an empty button, make it an empty button
                source.setIcon(emptyImageIcon);
            }
        }
    }

}