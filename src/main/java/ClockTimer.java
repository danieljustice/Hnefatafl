import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.SwingConstants;
import java.text.*;
import java.util.Date;
 
public class ClockTimer extends JLabel implements ActionListener {
 
	private static final long serialVersionUID = 1L;
	SimpleDateFormat sdf;
	
	public ClockTimer() {
    
		setForeground(Color.BLACK);
		sdf = new SimpleDateFormat("mm:ss");                    
		setFont(new Font("arial", Font.BOLD, 15));                    
		setHorizontalAlignment(SwingConstants.CENTER);                    
		Timer t = new Timer(1000, this);
		t.start();
	}
 
	public void actionPerformed(ActionEvent ae) {		
		Date t = new Date();
		setText(sdf.format(t));	
	}
}