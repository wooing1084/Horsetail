package horsetailclient;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Util.Protocol;
import horsetailclient.MainFrame.MyMouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.io.*;

public class PlayFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	Operator o = null;

	//private TimerNum timerNum;
	private Thread threadNum;

	JButton btnNewButton;
	JTextArea dictionaryWindow;
	JTextArea gameWindow;
	ChattingFrame chat;
	JButton sendButton;
	JButton gameStart;
	JScrollPane scrollPane;
	JScrollPane scrollPane2;
	Image background;
	JLabel notationPanel = new JLabel();
	JLabel timerLabel = new JLabel();

	LineBorder bb = new LineBorder(Color.gray, 1, true); // 라벨의 테두리 설정값
	private JTextField textField;

	PrintWriter out = null;

	public PlayFrame(Operator _o, PrintWriter printW) {
		o = _o;
		out = printW;

		setTitle("말 꼬투리");
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("./Image/logo.png")));
		setBackground(Color.white);
		setSize(1000, 720);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(false);
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		background=new ImageIcon(MainFrame.class.getResource("./Image/grass.png")).getImage();

		contentPane = new JPanel(){
			public void paintComponent(Graphics g) {//그리는 함수
				g.drawImage(background, 0, 0, null);//background를 그려줌		
				setOpaque(false);// 추가
				super.paintComponent(g);// 추가
			}
		};

		contentPane.setBackground(Color.WHITE);
		contentPane.setBounds(0, 0, 1000, 720);
		getContentPane().add(contentPane);
		contentPane.setLayout(null);
		
		notationPanel.setText("알림 창");
		notationPanel.setFont(new Font("굴림", Font.BOLD, 30));
		notationPanel.setHorizontalAlignment(JLabel.CENTER);
		notationPanel.setBorder(bb);
		notationPanel.setBounds(115, 53, 750, 50);
		contentPane.add(notationPanel);

		gameStart = new JButton();
		gameStart.setIcon(new ImageIcon(MainFrame.class.getResource("./Image/startButton.png")));
		gameStart.setFont(new Font("굴림", Font.BOLD, 30));
		gameStart.setHorizontalAlignment(JLabel.CENTER);
		gameStart.setBorder(bb);
		gameStart.setBounds(837, 625, 137, 50);
		gameStart.setBorderPainted(false);
		gameStart.setContentAreaFilled(false);
		gameStart.setFocusPainted(false);
		contentPane.add(gameStart);

		timerLabel.setFont(new Font("굴림", Font.BOLD, 30));
		timerLabel.setHorizontalAlignment(JLabel.CENTER);
		timerLabel.setBackground(Color.WHITE);
		timerLabel.setBounds(460, 138, 70, 50);
		contentPane.add(timerLabel);
		
		/*
		int second = 20;
		timerNum = new TimerNum(second);
		threadNum = new Thread(timerNum);
		threadNum.start();
		timerLabel.add(timerNum);
		*/

		gameWindow = new JTextArea();
		gameWindow.setBounds(0, 0, 458, 403);
		gameWindow.setBorder(bb);
		gameWindow.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		gameWindow.setOpaque(false);
		gameWindow.setLineWrap(true);
		contentPane.add(gameWindow);

		dictionaryWindow = new JTextArea();
		dictionaryWindow.setBounds(0, 0, 462, 403);
		dictionaryWindow.setBorder(bb);
		dictionaryWindow.setOpaque(false);
		dictionaryWindow.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		dictionaryWindow.setLineWrap(true);
		contentPane.add(dictionaryWindow);

		btnNewButton = new JButton("채팅");
		btnNewButton.setFont(new Font("굴림", Font.BOLD, 30));
		btnNewButton.setHorizontalAlignment(JLabel.CENTER);
		btnNewButton.setBackground(new Color(051, 204, 051));
		btnNewButton.setBounds(497, 628, 328, 45);
		contentPane.add(btnNewButton);

		textField = new JTextField();
		textField.setBounds(12, 616, 405, 57);
		textField.setOpaque(false);
		contentPane.add(textField);
		textField.setColumns(10);

		sendButton = new JButton();
		sendButton.setIcon(new ImageIcon(MainFrame.class.getResource("./Image/send.png")));
		sendButton.setBackground(Color.white);
		sendButton.setBounds(416, 616, 69, 57);
		sendButton.setBorderPainted(false);
		sendButton.setContentAreaFilled(false);
		sendButton.setFocusPainted(false);
		contentPane.add(sendButton);

		scrollPane = new JScrollPane(gameWindow, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(12, 215, 473, 403);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		contentPane.add(scrollPane);

		scrollPane2 = new JScrollPane(dictionaryWindow, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane2.setBounds(497, 215, 477, 403);
		scrollPane2.setOpaque(false);
		scrollPane2.getViewport().setOpaque(false);
		contentPane.add(scrollPane2);

		JLabel lblNewLabel = new JLabel("단어 입력");
		lblNewLabel.setBounds(148, 170, 162, 45);
		lblNewLabel.setHorizontalAlignment(JLabel.CENTER);
		lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel2 = new JLabel("단어 뜻");
		lblNewLabel2.setBounds(663, 170, 162, 45);
		lblNewLabel2.setHorizontalAlignment(JLabel.CENTER);
		lblNewLabel2.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		contentPane.add(lblNewLabel2);

		MyMouseListener listener = new MyMouseListener();
		btnNewButton.addMouseListener(listener);
		gameStart.addMouseListener(listener);
		sendButton.addMouseListener(listener);
		btnNewButton.addActionListener(this);
		sendButton.addActionListener(this);
		gameStart.addActionListener(this);

	}
	
	public void setTime(String t) {
		timerLabel.setText(t);
	}
	
	public void initPlayFrame() {
		timerLabel.setText("");
		notationPanel.setText("");
		textField.setText("");
		dictionaryWindow.setText("");
		gameWindow.setText("");
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnNewButton) {
			o.cf.setVisible(true);
		}
		
		else if (e.getSource() == gameStart) { // 게임 시작 버튼입니다. 여기 이용하시면 됩니다.
			out.println(Protocol.STARTGAME + "//" + textField.getText());
			out.flush();
		}

		else if (e.getSource() == sendButton) {
			// 메세지 입력없이 전송버튼만 눌렀을 경우
			if (textField.getText().equals("")) {
				return;
			}
			// gameWindow.append("["+o.ID+"] "+ textField.getText()+"\n");

			// 서버로 전송하는 부분 있어야함.

			out.println(Protocol.SENDWORD + "//" + textField.getText());
			out.flush();

			// gameWindow.setText("["+o.ID+"] "+ textField.getText()); //채팅 자신이 보낸거는 바로
			// gaimeWindow창에 뜰 수 있게 해뒀습니다.
			textField.setText(""); // 채팅 치는 곳 초기화
		}
	}
	
	public void setNotation(String s) {
		notationPanel.setText(s);
	}
	
	public void setDict(String s) {
		dictionaryWindow.setText(s);
	}

	class MyMouseListener implements MouseListener {

		@Override
		public void mouseEntered(MouseEvent e) {
			if (e.getSource() == btnNewButton) {
				JButton b = (JButton) e.getSource();
				b.setBackground(new Color(204, 153, 0));
			}else if (e.getSource() == gameStart) {
				JButton b = (JButton) e.getSource();
				b.setIcon(new ImageIcon(MainFrame.class.getResource("./Image/startButton2.png")));
			}else if (e.getSource() == sendButton) {
				JButton b = (JButton) e.getSource();
				b.setIcon(new ImageIcon(MainFrame.class.getResource("./Image/send2.png")));
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if (e.getSource() == btnNewButton) {
				JButton b = (JButton) e.getSource();
				b.setBackground(new Color(051, 204, 051));
			}else if (e.getSource() == gameStart) {
				JButton b = (JButton) e.getSource();
				b.setIcon(new ImageIcon(MainFrame.class.getResource("./Image/startButton.png")));
			}else if (e.getSource() == sendButton) {
				JButton b = (JButton) e.getSource();
				b.setIcon(new ImageIcon(MainFrame.class.getResource("./Image/send.png")));
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
