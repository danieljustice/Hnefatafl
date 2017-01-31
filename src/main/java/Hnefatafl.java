import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;

public class Hnefatafl {
    JFrame _frame = new JFrame("Tic-Tac-Toe");
    JPanel _ttt = new JPanel();
    JPanel _newPanel = new JPanel();
    JButton[] _buttons = new JButton[121];
    int CLICKS = 0;
    
    public Hnefatafl() {
	_frame.setSize(850, 850);
	_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	_ttt.setLayout(new GridLayout(11, 11));
	_newPanel.setLayout(new FlowLayout());

	// This will place the tic-tac-toe panel at the top of
	// the frame and the newPanel panel at the bottom
	_frame.add(_ttt, BorderLayout.NORTH);
	_frame.add(_newPanel, BorderLayout.SOUTH);

	
	for (int j=0; j<121 ; j++) {
	    // Make a new button in the array location with text "_"
	    _buttons[j] = new JButton("_");
	    // Associate a new ButtonListener to the button (see below)
	    ActionListener buttonListener = new ButtonListener();
	    _buttons[j].addActionListener(buttonListener);
	    // Set the font on the button
	    _buttons[j].setFont(new Font("Courier", Font.PLAIN, 48));
	    // Add this button to the _ttt panel
	    _ttt.add(_buttons[j]);
	}

	_frame.setVisible(true);

    }
    
    public static void main(String[] args) {
	new Hnefatafl();
    }

    class ButtonListener implements ActionListener {

	// Every time we click the button, it will perform
	// the following action.

	public void actionPerformed(ActionEvent e) {
	    JButton source = (JButton) e.getSource();
	    String currentText = source.getText();
	    if (currentText.equals("_")) {
	    	if(CLICKS % 2 == 0)
	    		source.setText("X");
	    	else
	    		source.setText("0");
	    	CLICKS++;
	    } else {
		source.setText("_");
	    }
	}
    
    }

    
}
