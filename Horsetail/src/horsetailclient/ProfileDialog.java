package horsetailclient;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ProfileDialog extends JFrame {

	private JLabel label;
	Operator o=null;
	JPanel backG;
	JLabel lblNewLabel;
	Image background;
	
	public ProfileDialog(Operator _o) {
		o=_o;
		
		setTitle("내 정보");
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("./Image/logo.png")));
		setBackground(Color.white);
		setResizable(false);
		setVisible(true);
		setBounds(0, 0, 358, 168);
		setLocationRelativeTo(null);
		
		background=new ImageIcon(ProfileDialog.class.getResource("./Image/blackBoard.png")).getImage();

		backG = new JPanel(){
			public void paintComponent(Graphics g) {//그리는 함수
				g.drawImage(background, 0, 0, null);//background를 그려줌		
				setOpaque(false);// 추가
				super.paintComponent(g);// 추가
			}
		};
		
		backG.setBounds(0, 0, 358,168);
		backG.setBackground(Color.white);
		
		lblNewLabel = new JLabel("        ID        Rating        Wins        Loses");
		lblNewLabel.setIcon(new ImageIcon(MainFrame.class.getResource("./Image/myRecord.png")));
		lblNewLabel.setBounds(0, 0, 344, 50);
		//lblNewLabel.setHorizontalAlignment(JLabel.CENTER);
		lblNewLabel.setBackground(Color.white);
		lblNewLabel.setBorder(null);
		
		String temp = new String();
		Database db = new Database();
		temp = db.CheckMyInfo(o.ID);
		
		
		label = new JLabel(temp);
		label.setBounds(0, 50, 344, 80);
		label.setFont(new Font("맑은 고딕",Font.BOLD, 20));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setBackground(Color.white);
		label.setBorder(null);
		
		setContentPane(backG);
		backG.setLayout(null);
		backG.add(label);
		backG.add(lblNewLabel);
	}

}
