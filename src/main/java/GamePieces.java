import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.omg.CORBA.SystemException;

public class GamePieces extends JLabel {

	private int pieces = 15;

	public GamePieces(int numberOfPieces) {
		pieces = numberOfPieces;
		setForeground(Color.RED);
		setFont(new Font("Courier", Font.PLAIN, 36));
		this.setText("" + pieces);
	}

	public void setPieces(int number){
		pieces = number;
		this.setText("" + pieces);
	}

	public int getPieces(){
		return pieces;
	}
}
