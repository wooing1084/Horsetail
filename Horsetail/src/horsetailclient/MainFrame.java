package horsetailclient;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import horsetailclient.LoginFrame.MyMouseListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.ImageIcon;

public class MainFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	Operator o = null;

	JPanel ranking;
	JPanel panel;
	JScrollPane scrollPane;
	JScrollPane scrollPane2;
	JLabel post;
	JPanel ebPanel;
	JPanel roomPanel;
	JButton enterButton;
	JButton profile;
	JButton refresh;
	JButton refresh2;
	JDialog myInfo;
	PlayFrame pf;
	private JButton btnNewButton;
	LineBorder bb = new LineBorder(Color.gray, 1, true); // 라벨의 테두리 설정값

	public MainFrame(Operator _o) {
		o = _o;

		setTitle("말 꼬투리");
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/Image/logo.png")));
		setBackground(Color.white);
		setSize(1000, 720);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPane = new JPanel(); // MainFrame에서 바탕이 되는 배경 패널.
		contentPane.setBackground(Color.WHITE);
		contentPane.setBounds(0, 0, 1000, 720);
		getContentPane().add(contentPane);
		contentPane.setLayout(null);

		JLabel label1 = new JLabel("방 제목");
		label1.setBackground(Color.WHITE);
		label1.setHorizontalAlignment(JLabel.CENTER);
		label1.setFont(new Font("맑은 고딕", Font.BOLD, 28));
		label1.setBorder(bb);
		label1.setBounds(12, 10, 281, 43);
		contentPane.add(label1);

		JLabel label2 = new JLabel("인원 수");
		label2.setBackground(Color.WHITE);
		label2.setHorizontalAlignment(JLabel.CENTER);
		label2.setFont(new Font("맑은 고딕", Font.BOLD, 28));
		label2.setBorder(bb);
		label2.setBounds(293, 10, 310, 43);
		contentPane.add(label2);

		ebPanel = new JPanel(); // 스크롤을 올릴 패널
		ebPanel.setBounds(12, 56, 591, 234);
		contentPane.add(ebPanel);
		ebPanel.setLayout(null);

		roomPanel = new JPanel(); // 스크롤 위에 올라갈 패널
		roomPanel.setPreferredSize(new Dimension(576, 0));
		roomPanel.setBackground(Color.white);
		roomPanel.setBorder(new LineBorder(SystemColor.control));
		getContentPane().add(roomPanel);
		roomPanel.setLayout(null);

		scrollPane2 = new JScrollPane(roomPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane2.setBounds(0, 0, 591, 234);
		ebPanel.add(scrollPane2);

		ArrayList<String> temp2 = new ArrayList();
		Database tempDB2 = new Database(); // 랭킹 정보를 가져올 임시 클래스
		temp2 = tempDB2.infoRoom();

		int size = 0;
		for (int i = 0; i < temp2.size(); i++) {
			enterButton = new JButton(temp2.get(i)); // 방 정보가 업뎃 된다는게.. 이게 서버랑 연동해봐야 할듯. 데베에서 그냥 insert로 바꾸면 행이 추가되서 제대로
														// 된 의미로 되는건 아닌듯..
			enterButton.setBackground(Color.WHITE);
			enterButton.setFont(new Font("맑은 고딕", Font.BOLD, 20));
			enterButton.setBounds(0, size, 576, 70);
			enterButton.setBorder(bb);
			roomPanel.setPreferredSize(new Dimension(576, 70 + size));
			size += 70;
			roomPanel.add(enterButton);

		}

		profile = new JButton("내 정보");
		profile.setBounds(638, 189, 336, 43);
		profile.setBackground(Color.WHITE);
		contentPane.add(profile);

		refresh = new JButton();
		refresh.setIcon(new ImageIcon(MainFrame.class.getResource("/Image/refresh.jpg")));
		refresh.setBackground(Color.WHITE);
		refresh.setBounds(638, 56, 91, 90);
		refresh.setBorderPainted(false);
		contentPane.add(refresh);

		panel = new JPanel(); // 스크롤을 올릴 패널
		panel.setBounds(12, 300, 962, 373);
		panel.setBackground(Color.white);
		panel.setBorder(new LineBorder(SystemColor.control));
		panel.setLayout(null);
		contentPane.add(panel);

		ranking = new JPanel(); // 스크롤 위에 올라갈 패널
		ranking.setPreferredSize(new Dimension(947, 0));
		ranking.setBackground(Color.white);
		ranking.setBorder(new LineBorder(SystemColor.control));
		getContentPane().add(ranking);
		ranking.setLayout(null);

		scrollPane = new JScrollPane(ranking, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 0, 962, 373);
		panel.add(scrollPane);

		refresh2 = new JButton();
		refresh2.setBounds(910, 242, 57, 57);
		refresh2.setIcon(new ImageIcon(MainFrame.class.getResource("/Image/refresh2.png")));
		refresh2.setBackground(Color.WHITE);
		refresh2.setBorderPainted(false);
		contentPane.add(refresh2);

		JLabel mainMal = new JLabel();
		mainMal.setBounds(761, 10, 214, 174);
		mainMal.setIcon(new ImageIcon(MainFrame.class.getResource("/Image/mainHorse.jpg")));
		contentPane.add(mainMal);

		ArrayList<String> temp = new ArrayList();
		Database tempDB = new Database(); // 랭킹 정보를 가져올 임시 클래스
		temp = tempDB.checkScore();

		int size2 = 0;
		for (int i = 0; i < temp.size(); i++) {
			post = new JLabel(temp.get(i));
			post.setBounds(0, size2, 947, 70);
			post.setHorizontalAlignment(JLabel.CENTER);
			post.setFont(new Font("맑은 고딕", Font.BOLD, 20));
			post.setBorder(bb);
			ranking.setPreferredSize(new Dimension(947, 70 + size2));
			size2 += 70;
			ranking.add(post);
		}

		MyMouseListener listener = new MyMouseListener();
		profile.addMouseListener(listener);
		profile.addActionListener(this);
		refresh.addActionListener(this);
		refresh2.addActionListener(this);
		enterButton.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == enterButton) { // 멀티룸에 어떻게 해야 입장 가능할지..? 한개의 방밖에 안들어 가짐
			pf = new PlayFrame(o);
		} else if (e.getSource() == profile) {
			ProfileDialog pd = new ProfileDialog(o);
		} else if (e.getSource() == refresh) {
			ebPanel.removeAll();
			ebPanel.updateUI();

			roomPanel = new JPanel(); // 스크롤 위에 올라갈 패널
			roomPanel.setPreferredSize(new Dimension(576, 0));
			roomPanel.setBackground(Color.white);
			roomPanel.setBorder(new LineBorder(SystemColor.control));
			getContentPane().add(roomPanel);
			roomPanel.setLayout(null);

			scrollPane2 = new JScrollPane(roomPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane2.setBounds(0, 0, 591, 234);
			ebPanel.add(scrollPane2);

			ArrayList<String> temp2 = new ArrayList();
			Database tempDB2 = new Database(); // 랭킹 정보를 가져올 임시 클래스
			temp2 = tempDB2.infoRoom();

			int size = 0;
			for (int i = 0; i < temp2.size(); i++) {
				enterButton = new JButton(temp2.get(i)); // 방 정보가 업뎃 된다는게.. 이게 서버랑 연동해봐야 할듯. 데베에서 그냥 insert로 바꾸면 행이 추가되서
															// 제대로 된 의미로 되는건 아닌듯..
				enterButton.setBackground(Color.WHITE);
				enterButton.setBounds(0, size, 591, 70);
				enterButton.setFont(new Font("맑은 고딕", Font.BOLD, 20));
				enterButton.setBorder(bb);
				roomPanel.setPreferredSize(new Dimension(576, 70 + size));
				size += 70;
				roomPanel.add(enterButton);

			}
		} else if (e.getSource() == refresh2) {

			panel.removeAll(); // 패널을 지운다.(새로고침)
			panel.updateUI();

			ranking = new JPanel(); // 스크롤 위에 올라갈 패널
			ranking.setPreferredSize(new Dimension(947, 0));
			ranking.setBackground(Color.white);
			ranking.setBorder(new LineBorder(SystemColor.control));
			getContentPane().add(ranking);
			ranking.setLayout(null);

			scrollPane = new JScrollPane(ranking, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setBounds(0, 0, 962, 373);
			panel.add(scrollPane);

			ArrayList<String> temp = new ArrayList();
			Database tempDB = new Database(); // 랭킹 정보를 가져올 임시 클래스
			temp = tempDB.checkScore();

			int size = 0;
			for (int i = 0; i < temp.size(); i++) {
				if (i == 1) { //1일때 금메달 넣기ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ이미지 어떻게..?
					post = new JLabel(temp.get(i));
					post.setBounds(0, size, 947, 70);
					post.setFont(new Font("맑은 고딕", Font.BOLD, 20));
					post.setHorizontalAlignment(JLabel.CENTER);
					post.setBorder(bb);
					ranking.setPreferredSize(new Dimension(947, 70 + size));
					size += 70;
					ranking.add(post);
				}
			}
		}
	}

	class MyMouseListener implements MouseListener {

		@Override
		public void mouseEntered(MouseEvent e) {
			JButton b = (JButton) e.getSource();
			b.setBackground(new Color(204, 153, 0));
		}

		@Override
		public void mouseExited(MouseEvent e) {
			JButton b = (JButton) e.getSource();
			b.setBackground(Color.WHITE);
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
