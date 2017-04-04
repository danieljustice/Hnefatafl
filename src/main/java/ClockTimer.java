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
	Thread timerThread;
	public boolean shouldHalt = false;

	private static final int initTime = 300;
	private static final int turnTime = 3;

	int timeLeft = initTime;

	public ClockTimer(int startTime) {
		setForeground(Color.BLACK);
		sdf = new SimpleDateFormat("mm:ss");
		setFont(new Font("arial", Font.BOLD, 15));
		setHorizontalAlignment(SwingConstants.CENTER);
		this.setText("" + startTime);
	}

	public ClockTimer() {
		this(initTime);
	}


	public void actionPerformed(ActionEvent ae) {
		Date t = new Date();
		setText(sdf.format(t));
	}

	//starts any thread passed into this function
	public void startTimerThread(){
		try {
			int startTime = Integer.parseInt(this.getText());
			shouldHalt = false;
			timerThread = new Thread(() -> {
			for (int j = startTime; j >= 0; j--) {
				if(shouldHalt){
					break;
				}
				this.setText("" + j);
				//System.out.println(j + "...");

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
		timerThread.start();
		} catch (Exception e) {
			//TODO: handle exception
		}

	}
	//joins (ends) any thread passed into this
	public void stopTimerThread(){
		if(timerThread != null){
			try {
				shouldHalt = true;
				timerThread.join(0);
				int newTime = Integer.parseInt(this.getText()) + turnTime;
				if(newTime > 300){
					newTime = 300;
				}
				this.setText("" + newTime);
			} catch (InterruptedException iex) {
				System.out.println("Some sort of error joining threads. Got no clue why.\n" + iex);
			}
		}else{
			System.out.println("This thread is null");
		}
	}

	public boolean isNull() {
		return(timerThread == null);
	}

	public int getTime() {
		return(Integer.parseInt(this.getText()));
	}
}
