package horsetailclient;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Util.Protocol;
import horsetailclient.LoginFrame.MyMouseListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.ImageIcon;

import java.io.*;
import java.util.*;

public class MainFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	Operator o = null;
	//Image background2=null;
	JLabel horseGif=new JLabel();
	JPanel postPanel;
	JPanel ranking;
	JPanel panel;
	JScrollPane scrollPane;
	JScrollPane scrollPane2;
	JLabel post;
	JPanel ebPanel;
	JPanel roomPanel;
	JLabel roomLabel;
	JButton enterButton;
	JButton profile;
	JButton refresh;
	JButton refresh2;
	JButton newRoom;
	JDialog myInfo;
	PlayFrame pf;
	private JButton btnNewButton;
	JButton[] PostButtonArr = new JButton[100];
	LineBorder bb = new LineBorder(Color.gray, 1, true); // 라벨의 테두리 설정값
	
	Image background=null;
	
	PrintWriter out = null;
	
	ArrayList<String> rankingList = new ArrayList<String>();
	ArrayList<String> roomIdList = new ArrayList<String>();
	ArrayList<String> roomNameList = new ArrayList<String>();
	
	public MainFrame(Operator _o, PrintWriter printW) {
		o = _o;
		out = printW;

		setTitle("말 꼬투리");
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("./Image/logo.png")));
		setBackground(Color.white);
		setSize(1000, 720);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		setResizable(false);
		setVisible(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		background=new ImageIcon(MainFrame.class.getResource("./Image/grass.png")).getImage();

		contentPane = new JPanel(){
			public void paintComponent(Graphics g) {//그리는 함수
				g.drawImage(background, 0, 0, null);//background를 그려줌		
				setOpaque(false);// 추가
				super.paintComponent(g);// 추가
			}
		}; // MainFrame에서 바탕이 되는 배경 패널.
		
		contentPane.setBackground(Color.WHITE);
		contentPane.setBounds(0, 0, 1000, 720);
		getContentPane().add(contentPane);
		contentPane.setLayout(null);

		JLabel label1 = new JLabel("방 제목");
		label1.setBackground(Color.WHITE);
		label1.setHorizontalAlignment(JLabel.CENTER);
		label1.setFont(new Font("맑은 고딕", Font.BOLD, 28));
		label1.setBorder(null);
		label1.setBounds(12, 10, 281, 43);
		contentPane.add(label1);

		JLabel label2 = new JLabel("인원 수");
		label2.setBackground(Color.WHITE);
		label2.setHorizontalAlignment(JLabel.CENTER);
		label2.setFont(new Font("맑은 고딕", Font.BOLD, 28));
		label2.setBorder(null);
		label2.setBounds(293, 10, 310, 43);
		contentPane.add(label2);

		ebPanel = new JPanel(); // 스크롤을 올릴 패널
		ebPanel.setBounds(12, 56, 591, 234);
		ebPanel.setOpaque(false);
		contentPane.add(ebPanel);
		ebPanel.setLayout(null);

		roomPanel = new JPanel(); // 스크롤 위에 올라갈 패널
		roomPanel.setPreferredSize(new Dimension(576, 0));
		roomPanel.setBackground(Color.white);
		roomPanel.setOpaque(false);
		roomPanel.setBorder(new LineBorder(SystemColor.control));
		getContentPane().add(roomPanel);
		roomPanel.setLayout(null);

		scrollPane2 = new JScrollPane(roomPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane2.setBounds(0, 0, 591, 234);
		scrollPane2.setOpaque(false);
		scrollPane2.getViewport().setOpaque(false);
		ebPanel.add(scrollPane2);
		
		newRoom = new JButton("방 만들기");
		newRoom.setFont(new Font("맑은 고딕", Font.BOLD, 10));
		newRoom.setIcon(new ImageIcon(MainFrame.class.getResource("./Image/roomCreate1.png")));
		newRoom.setBounds(604, 123, 79, 50);
		newRoom.setForeground(Color.WHITE);
		newRoom.setBackground(new Color(204, 153, 0));
		newRoom.setBorderPainted(false);
		contentPane.add(newRoom);

		//ArrayList<String> temp2 = new ArrayList();
		//Database tempDB2 = new Database();
		//temp2 = tempDB2.infoRoom();
		
		
		
		out.println(Protocol.ROOMS);
		out.flush();
		
//		ebPanel.removeAll();
//		ebPanel.updateUI();
		
		int size = 0;
		for (int i = 0; i < roomNameList.size(); i++) {
			enterButton = new JButton(); 
			enterButton.setIcon(new ImageIcon(MainFrame.class.getResource("./Image/enter.png")));
			enterButton.setBorderPainted(false);
			enterButton.setContentAreaFilled(false);
			enterButton.setFocusPainted(false);
			enterButton.setBounds(506, size, 70, 70);
			enterButton.setBorder(bb);
			enterButton.setText(roomIdList.get(i));
			
			roomLabel = new JLabel(roomNameList.get(i));
			roomLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
			roomLabel.setBounds(0, size, 506, 70);
			roomLabel.setBorder(bb);
//			enterButton.setBorderPainted(false);
//			enterButton.setContentAreaFilled(false);
//			enterButton.setFocusPainted(false);
			roomPanel.setPreferredSize(new Dimension(576, 70 + size));
			size += 70;
			roomPanel.add(enterButton);
			roomPanel.add(roomLabel);
			
			PostButtonArr[i] = enterButton;
			PostButtonArr[i].addActionListener(this);

		}

		profile = new JButton("");
		profile.setIcon(new ImageIcon(MainFrame.class.getResource("./Image/myInfo.png")));
		profile.setBounds(761, 189, 213, 43);
		profile.setBorderPainted(false);
		profile.setContentAreaFilled(false);
		profile.setFocusPainted(false);
		contentPane.add(profile);

		
		refresh = new JButton();
		refresh.setIcon(new ImageIcon(MainFrame.class.getResource("./Image/refresh.png")));
		refresh.setBackground(Color.WHITE);
		refresh.setBounds(604, 56, 57, 57);
		refresh.setVisible(true);
//		refresh.setLayout(null);
		refresh.setBorderPainted(false);
		refresh.setContentAreaFilled(false);
		refresh.setFocusPainted(false);
		contentPane.add(refresh);

		panel = new JPanel(); // 스크롤을 올릴 패널
		panel.setBounds(12, 300, 962, 373);
		panel.setBackground(Color.white);
		panel.setBorder(new LineBorder(SystemColor.control));
		panel.setOpaque(false);
		panel.setLayout(null);
		contentPane.add(panel);

		ranking = new JPanel(); // 스크롤 위에 올라갈 패널
		ranking.setPreferredSize(new Dimension(947, 0));
		ranking.setBackground(Color.white);
		ranking.setOpaque(false);
		ranking.setBorder(new LineBorder(SystemColor.control));
		getContentPane().add(ranking);
		ranking.setLayout(null);

		scrollPane = new JScrollPane(ranking, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 0, 962, 373);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		panel.add(scrollPane);

		refresh2 = new JButton();
		refresh2.setBounds(917, 243, 57, 57);
		refresh2.setIcon(new ImageIcon(MainFrame.class.getResource("./Image/refresh.png")));
		refresh2.setBackground(Color.WHITE);
		refresh2.setBorderPainted(false);
		refresh2.setContentAreaFilled(false);
		refresh2.setFocusPainted(false);
		refresh2.setVisible(true);
		contentPane.add(refresh2);

		
		
		ImageIcon background3=new ImageIcon(this.getClass().getResource("./Image/horseRun.gif"));
		horseGif.setIcon(background3);
		horseGif.setOpaque(true);
		horseGif.setBounds(761, 10, 214, 174);
		contentPane.add(horseGif);

		//ArrayList<String> temp = new ArrayList();
		//Database tempDB = new Database(); // 랭킹 정보를 가져올 임시 클래스
		//temp = tempDB.checkScore();
		
		out.println(Protocol.RANKING);
		out.flush();
		
//		panel.removeAll(); // 패널을 지운다.(새로고침)
//		panel.updateUI();
		
		int size2 = 0;
		for (int i = 0; i < rankingList.size(); i++) {
			if (i == 0) {
				post = new JLabel(rankingList.get(i));
				post.setBounds(0, size2, 947, 70);
				//post.setHorizontalAlignment(JLabel.CENTER);
				post.setFont(new Font("맑은 고딕", Font.BOLD, 20));
				post.setBorder(bb);
				post.setIcon(new ImageIcon(MainFrame.class.getResource("./Image/gold.png")));
				ranking.setPreferredSize(new Dimension(947, 70 + size2));
				size2 += 70;
				ranking.add(post);
			} else if (i == 1) {
				post = new JLabel(rankingList.get(i));
				post.setBounds(0, size2, 947, 70);
				//post.setHorizontalAlignment(JLabel.CENTER);
				post.setFont(new Font("맑은 고딕", Font.BOLD, 20));
				post.setBorder(bb);
				post.setIcon(new ImageIcon(MainFrame.class.getResource("./Image/silver.png")));
				ranking.setPreferredSize(new Dimension(947, 70 + size2));
				size2 += 70;
				ranking.add(post);
			} else if (i == 2) {
				post = new JLabel(rankingList.get(i));
				post.setBounds(0, size2, 947, 70);
				//post.setHorizontalAlignment(JLabel.CENTER);
				post.setFont(new Font("맑은 고딕", Font.BOLD, 20));
				post.setBorder(bb);
				post.setIcon(new ImageIcon(MainFrame.class.getResource("./Image/copper.png")));
				ranking.setPreferredSize(new Dimension(947, 70 + size2));
				size2 += 70;
				ranking.add(post);
			} else {
				post = new JLabel(rankingList.get(i));
				post.setBounds(0, size2, 947, 70);
				//post.setHorizontalAlignment(JLabel.CENTER);
				post.setFont(new Font("맑은 고딕", Font.BOLD, 20));
				post.setBorder(bb);
				ranking.setPreferredSize(new Dimension(947, 70 + size2));
				size2 += 70;
				ranking.add(post);
			}
		}

		MyMouseListener listener = new MyMouseListener();
		profile.addMouseListener(listener);
		newRoom.addMouseListener(listener);
		profile.addActionListener(this);
		refresh.addActionListener(this);
		newRoom.addActionListener(this);
		refresh2.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == enterButton) { 
			System.out.println(enterButton.getText());
			out.println(Protocol.JOINROOM + "//" + enterButton.getText());
			out.flush();
		} else if (e.getSource() == profile) {
			ProfileDialog pd = new ProfileDialog(o);
		}else if (e.getSource() == newRoom) {
			o.mrd.setVisible(true); //방만들기 버튼 누르면 방만들기 다이얼로그로 이동
		} else if (e.getSource() == refresh) { // 이게 방목록 버튼
			out.println(Protocol.ROOMS);
			out.flush();
			
			ebPanel.removeAll();
			ebPanel.updateUI();

			roomPanel = new JPanel(); // 스크롤 위에 올라갈 패널
			roomPanel.setPreferredSize(new Dimension(576, 0));
			roomPanel.setBackground(Color.white);
			roomPanel.setBorder(new LineBorder(SystemColor.control));
			roomPanel.setOpaque(false);
			getContentPane().add(roomPanel);
			roomPanel.setLayout(null);

			scrollPane2 = new JScrollPane(roomPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane2.setBounds(0, 0, 591, 234);
			scrollPane2.setOpaque(false);
			scrollPane2.getViewport().setOpaque(false);
			ebPanel.add(scrollPane2);

			//ArrayList<String> temp2 = new ArrayList();
			//Database tempDB2 = new Database(); // 랭킹 정보를 가져올 임시 클래스
			//temp2 = tempDB2.infoRoom();

			int size = 0;
			for (int i = 0; i < roomNameList.size(); i++) {
				enterButton = new JButton(); 
				enterButton.setIcon(new ImageIcon(MainFrame.class.getResource("./Image/enter.png")));
				enterButton.setBorderPainted(false);
				enterButton.setContentAreaFilled(false);
				enterButton.setFocusPainted(false);
				enterButton.setBounds(506, size, 70, 70);
				enterButton.setBorder(bb);
				enterButton.setText(roomIdList.get(i));
				
				roomLabel = new JLabel(roomNameList.get(i));
				roomLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
				roomLabel.setBounds(0, size, 506, 70);
				roomLabel.setBorder(bb);
				roomLabel.setOpaque(false);
//				enterButton.setBorderPainted(false);
//				enterButton.setContentAreaFilled(false);
//				enterButton.setFocusPainted(false);
				roomPanel.setPreferredSize(new Dimension(576, 70 + size));
				size += 70;
				roomPanel.add(enterButton);
				roomPanel.add(roomLabel);
				
				PostButtonArr[i] = enterButton;
				PostButtonArr[i].addActionListener(this);
			}
		} else if (e.getSource() == refresh2) { // 이게 랭킹 버튼
			out.println(Protocol.RANKING);
			out.flush();
			
			panel.removeAll(); // 패널을 지운다.(새로고침)
			panel.updateUI();

			ranking = new JPanel(); // 스크롤 위에 올라갈 패널
			ranking.setPreferredSize(new Dimension(947, 0));
			ranking.setBackground(Color.white);
			ranking.setBorder(new LineBorder(SystemColor.control));
			ranking.setOpaque(false);
			getContentPane().add(ranking);
			ranking.setLayout(null);

			scrollPane = new JScrollPane(ranking, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setBounds(0, 0, 962, 373);
			scrollPane.setOpaque(false);
			scrollPane.getViewport().setOpaque(false);
			panel.add(scrollPane);

			//ArrayList<String> temp = new ArrayList();
			//Database tempDB = new Database(); // 랭킹 정보를 가져올 임시 클래스
			//temp = tempDB.checkScore();
			
			int size = 0;
			for (int i = 0; i < rankingList.size(); i++) {
				if (i == 0) {
					post = new JLabel(rankingList.get(i));
					post.setBounds(0, size, 947, 70);
					//post.setHorizontalAlignment(JLabel.CENTER);
					post.setFont(new Font("맑은 고딕", Font.BOLD, 20));
					post.setBorder(bb);
					post.setIcon(new ImageIcon(MainFrame.class.getResource("./Image/gold.png")));
					ranking.setPreferredSize(new Dimension(947, 70 + size));
					size += 70;
					ranking.add(post);
				} else if (i == 1) {
					post = new JLabel(rankingList.get(i));
					post.setBounds(0, size, 947, 70);
					//post.setHorizontalAlignment(JLabel.CENTER);
					post.setFont(new Font("맑은 고딕", Font.BOLD, 20));
					post.setBorder(bb);
					post.setIcon(new ImageIcon(MainFrame.class.getResource("./Image/silver.png")));
					ranking.setPreferredSize(new Dimension(947, 70 + size));
					size += 70;
					ranking.add(post);
				} else if (i == 2) {
					post = new JLabel(rankingList.get(i));
					post.setBounds(0, size, 947, 70);
					//post.setHorizontalAlignment(JLabel.CENTER);
					post.setFont(new Font("맑은 고딕", Font.BOLD, 20));
					post.setBorder(bb);
					post.setIcon(new ImageIcon(MainFrame.class.getResource("./Image/copper.png")));
					ranking.setPreferredSize(new Dimension(947, 70 + size));
					size += 70;
					ranking.add(post);
				} else {
					post = new JLabel(rankingList.get(i));
					post.setBounds(0, size, 947, 70);
					//post.setHorizontalAlignment(JLabel.CENTER);
					post.setFont(new Font("맑은 고딕", Font.BOLD, 20));
					post.setBorder(bb);
					ranking.setPreferredSize(new Dimension(947, 70 + size));
					size += 70;
					ranking.add(post);
				}

			}
		}
	}
	
	public void setRankingList(String[] rawData) {
		rankingList = new ArrayList<String>(Arrays.asList(rawData));
	}
	
	public void setRoomIdList(String[] rawData) {
		roomIdList = new ArrayList<String>(Arrays.asList(rawData));
	}
	
	public void initRoomIdList() {
		roomIdList.clear();
	}
	
	public void setRoomNameList(String[] rawData) {
		roomNameList = new ArrayList<String>(Arrays.asList(rawData));
	}
	
	public void initRoomNameList() {
		roomNameList.clear();
	}

	class MyMouseListener implements MouseListener {

		@Override
		public void mouseEntered(MouseEvent e) {
			if (e.getSource() == profile) {
				JButton b = (JButton) e.getSource();
				b.setIcon(new ImageIcon(MainFrame.class.getResource("./Image/myInfo2.png")));
			} else if (e.getSource() == newRoom) {
				JButton b = (JButton) e.getSource();
				b.setIcon(new ImageIcon(MainFrame.class.getResource("./Image/roomCreate2.png")));

			}else {
				JButton b = (JButton) e.getSource();
				b.setBackground(new Color(204, 153, 0));
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if (e.getSource() == profile) {
				JButton b = (JButton) e.getSource();
				b.setIcon(new ImageIcon(MainFrame.class.getResource("./Image/myInfo.png")));
			} else if (e.getSource() == newRoom) {
				JButton b = (JButton) e.getSource();
				b.setIcon(new ImageIcon(MainFrame.class.getResource("./Image/roomCreate1.png")));
			}else {
				JButton b = (JButton) e.getSource();
				b.setBackground(Color.WHITE);
			}
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
