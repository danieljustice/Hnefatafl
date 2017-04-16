import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.SwingConstants;
import java.text.*;
import java.util.Date;

public class ClockTimer extends JLabel{

	Thread timerThread;
	public boolean shouldHalt = false;
	public boolean isPaused = true;

	private static final int initTime = 300;
	private static final int turnTime = 3;

	int timeLeft = initTime;

	public ClockTimer(int startTime) {
		setForeground(Color.BLACK);
		setFont(new Font("Courier", Font.PLAIN, 36));
		this.setText("" + startTime);
		this.startTimerThread();
	}

	public ClockTimer() {
		this(initTime);
	}

	//starts any thread passed into this function
	public void startTimerThread(){
		try {
			//int startTime = Integer.parseInt(this.getText());
			//int curTime = startTime;
			shouldHalt = false;
			timerThread = new Thread(() -> {
			while(timeLeft > 0) {
				if(shouldHalt){
					break;
				}
				if(!isPaused){
					timeLeft = timeLeft - 1;
				}
				this.setText("" + timeLeft);
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
<<<<<<< HEAD
			return;
=======
			timerThread = null;
>>>>>>> master
	    });
		timerThread.start();
		// timerThread.join();
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
				this.setText("" + newTime);
			} catch (InterruptedException iex) {
				System.out.println("Some sort of error joining threads. Got no clue why.\n" + iex);
			}
		}else{
			System.out.println("This thread is null");
		}
	}

	public void pauseTimerThread(){
		isPaused = true;
		timeLeft = timeLeft + turnTime;
		if(!isNull()){
			this.setText("" + timeLeft);
		}else{
			//this.setText("Game Over!");
		}
	}

	public void continueTimerThread(){
		isPaused = false;
	}
	public boolean isNull() {
		return(timerThread == null);
	}

	public int getTime() {
		return(Integer.parseInt(this.getText()));
	}
}
