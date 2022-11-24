/*
 * Name : GameTimer.java
 * Author : 이준형
 * Description : 사용하지 않는 더미, 일단 서버단에서 구현해보려고 했으나 클라이언트에 넣는게 더 좋아보임
 */

package wcgserver;

import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {
	public static void main(String[] args) {
		Timer timer = new Timer();
		
		TimerTask task = new TimerTask() {
			
			int i = 20;
			public void run()
			{
				if(i < 0) {
					timer.cancel();
					timeOver();
				}
			}
		};
		
		timer.scheduleAtFixedRate(task, 0, 1000);
	}
	
	public static boolean timeOver() {
		return true;
	}

}
