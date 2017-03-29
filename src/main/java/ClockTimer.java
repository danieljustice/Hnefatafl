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


	//Takes in an integer and returns a thread that counts down from that number
	public Thread getTimerThread(int startTime){
		Thread t = new Thread(() -> {
			for (int j = startTime; j >= 0; j--) {
				System.out.println(j + "...");

				// This thread will sleep for >= 1000 milliseconds (1 second)
				// In practice it will be very close to 1000 milliseconds
				// Do not rely on Java (or the JVM in general) for hard
				// real-time guarantees!
				try {
				Thread.sleep(1000);
				} catch (InterruptedException iex) {
				// ignore
				}
			}
	    });
		return t;
	}
	//starts any thread passed into this function
	public void startTimerThread(Thread timerThread){
		timerThread.start();
	}
	//joins (ends) any thread passed into this
	public void stopTimerThread(Thread timerThread){
		try {
			timerThread.join();
		} catch (InterruptedException iex) {
			System.out.println("Some sort of error joining threads. Got no clue why.\n" + iex);
		}
	}
}