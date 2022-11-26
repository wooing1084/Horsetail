package netTeamProject;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

class TimerNum extends JLabel implements Runnable {
	
	int second;

	public TimerNum(int second) {
		setOpaque(true);
		setBounds(0, 0, 750, 50);
		setForeground(Color.BLUE);
		setBackground(Color.WHITE);
		setText(second + "");
		setFont(new Font("맑은고딕", Font.PLAIN, 50));
		setHorizontalAlignment(JLabel.CENTER);
		
		this.second = second;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);	// 1초
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (second > 0) {
				second -= 1;		// 1초씩 줄어듦
				setText(second + "");
			} else {
				//System.out.println("종료");
				break;
			}
		}
	}
}