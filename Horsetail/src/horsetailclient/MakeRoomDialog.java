package horsetailclient;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Util.Protocol;
import horsetailclient.MainFrame.MyMouseListener;

public class MakeRoomDialog extends JFrame implements ActionListener{

	private JPanel contentPane;
	Image background;
	JTextField roomName;
	JButton roomCreate;

	Operator o = null;
	PrintWriter out;
	LineBorder bb = new LineBorder(Color.gray, 1, true); // 라벨의 테두리 설정값

	public MakeRoomDialog(Operator _o, PrintWriter printW) {
		o = _o;
		out = printW;

		setTitle("방 생성하기");
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("./Image/logo.png")));
		setBackground(Color.white);
		setResizable(false);
		setVisible(true);
		setLayout(null);
		setBounds(0, 0, 500, 100);
		setLocationRelativeTo(null);

		background = new ImageIcon(MainFrame.class.getResource("./Image/grass.jpg")).getImage();

		contentPane = new JPanel() {
			public void paintComponent(Graphics g) {// 그리는 함수
				g.drawImage(background, 0, 0, null);// background를 그려줌
				setOpaque(false);// 추가
				super.paintComponent(g);// 추가
			}
		}; // 바탕이 되는 배경 패널.

		contentPane.setLayout(null);
		contentPane.setBounds(0, 0, 500, 100);
		setContentPane(contentPane);

		roomName = new JTextField();
		roomName.setBounds(5, 5, 400, 50);
		roomName.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		roomName.setBorder(bb);
		contentPane.add(roomName);

		roomCreate = new JButton();
		roomCreate.setIcon(new ImageIcon(MainFrame.class.getResource("./Image/roomCreate1.png")));
		roomCreate.setBounds(405, 5, 80, 50);
		roomCreate.setBorderPainted(false);
		roomCreate.setContentAreaFilled(false);
		roomCreate.setFocusPainted(false);
		contentPane.add(roomCreate);
		
		
		MyMouseListener listener = new MyMouseListener();
		roomCreate.addMouseListener(listener);
		roomCreate.addActionListener(this);

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == roomCreate) {

			if (roomName.getText().equals("")) { // 방제목 입력없이 방 만들기만 눌렀을 경우
				return;
			}

//			out.println(Protocol.SENDMESSAGE + "//" + roomName.getText()); //준형님 이 부분이 말씀하신 부분인거 같은데 맞는지 모르겠네요. 확인 부탁드려요. 일단 주석해놓겠습니다
//			out.flush();

			roomName.setText(""); // 방 만드는 곳 초기화
		} else {
			this.dispose();
		}
	}

	class MyMouseListener implements MouseListener {

		@Override
		public void mouseEntered(MouseEvent e) {

			JButton b = (JButton) e.getSource();
			b.setIcon(new ImageIcon(MainFrame.class.getResource("./Image/roomCreate2.png")));

		}

		@Override
		public void mouseExited(MouseEvent e) {

			JButton b = (JButton) e.getSource();
			b.setIcon(new ImageIcon(MainFrame.class.getResource("./Image/roomCreate1.png")));

		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

}
