package horsetailclient;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
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
	
	public ProfileDialog(Operator _o) {
		o=_o;
		
		setTitle("내 정보");
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("./Image/logo.png")));
		setBackground(Color.white);
		setResizable(false);
		setVisible(true);
		setBounds(0, 0, 358, 168);
		setLocationRelativeTo(null);
		
		backG = new JPanel();
		backG.setPreferredSize(new Dimension(500,200));
		backG.setBackground(Color.white);
		
		lblNewLabel = new JLabel("             ID          Password          Score");
		lblNewLabel.setIcon(new ImageIcon(MainFrame.class.getResource("./Image/myRecord.png")));
		lblNewLabel.setBounds(0, 0, 344, 50);
		//lblNewLabel.setHorizontalAlignment(JLabel.CENTER);
		lblNewLabel.setBackground(Color.white);
		lblNewLabel.setBorder(new LineBorder(SystemColor.control));
		
		String temp = new String();
		Database db = new Database();
		temp = db.CheckMyInfo(o.ID);
		
		
		label = new JLabel(temp);
		label.setBounds(0, 50, 344, 80);
		label.setFont(new Font("맑은 고딕",Font.BOLD, 20));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setBackground(Color.white);
		label.setBorder(new LineBorder(SystemColor.control));
		
		setContentPane(backG);
		backG.setLayout(null);
		backG.add(label);
		backG.add(lblNewLabel);
	}

}
