package horsetailclient;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
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
	
	private TimerNum timerNum;
	private Thread threadNum;
	
	JButton btnNewButton;
	JTextArea dictionaryWindow;
	JTextArea gameWindow;
	ChattingFrame chat;
	JButton sendButton;
	JScrollPane scrollPane;
	JScrollPane scrollPane2;
	
	LineBorder bb = new LineBorder(Color.gray, 1, true); //라벨의 테두리 설정값
	private JTextField textField;
	
	PrintWriter out = null;

	public PlayFrame(Operator _o, PrintWriter printW) {
		o=_o;
		out = printW;
		
		setTitle("말 꼬투리");
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("./Image/logo.png")));
		setBackground(Color.white);
		setSize(1000, 720);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(false);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel(); //MainFrame에서 바탕이 되는 배경 패널.
		contentPane.setBackground(Color.WHITE);
		contentPane.setBounds(0, 0, 1000, 720);
		getContentPane().add(contentPane);
		contentPane.setLayout(null);
		
		JLabel notationPanel = new JLabel("알림 창");
		notationPanel.setFont(new Font("굴림", Font.BOLD, 30));
		notationPanel.setHorizontalAlignment(JLabel.CENTER);
		notationPanel.setBorder(bb);
		notationPanel.setBounds(115, 53, 750, 50);
		contentPane.add(notationPanel);
		
		JLabel timerLabel = new JLabel();
		timerLabel.setFont(new Font("굴림", Font.BOLD, 30));
		timerLabel.setHorizontalAlignment(JLabel.CENTER);
		timerLabel.setBackground(Color.WHITE);
		timerLabel.setBounds(460, 138, 70, 50);
		contentPane.add(timerLabel);
		
		int second = 20;
		timerNum = new TimerNum(second);
		threadNum = new Thread(timerNum);
		threadNum.start();
		timerLabel.add(timerNum);
		
		gameWindow = new JTextArea();
		gameWindow.setBounds(0, 0, 458, 403);
		gameWindow.setBorder(bb);
		contentPane.add(gameWindow);
		
		dictionaryWindow = new JTextArea();
		dictionaryWindow.setBounds(0, 0, 462, 403);
		dictionaryWindow.setBorder(bb);
		contentPane.add(dictionaryWindow);
		
		btnNewButton = new JButton("채팅");
		btnNewButton.setFont(new Font("굴림", Font.BOLD, 30));
		btnNewButton.setHorizontalAlignment(JLabel.CENTER);
		btnNewButton.setBackground(Color.white);
		btnNewButton.setBounds(497, 628, 477, 45);
		contentPane.add(btnNewButton);
		
		textField = new JTextField();
		textField.setBounds(12, 616, 405, 57);
		contentPane.add(textField);
		textField.setColumns(10);
		
		sendButton = new JButton();
		sendButton.setIcon(new ImageIcon(MainFrame.class.getResource("/Image/send.png")));
		sendButton.setBackground(Color.white);
		sendButton.setBounds(416, 616, 69, 57);
		contentPane.add(sendButton);
		
		scrollPane = new JScrollPane(gameWindow, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(12, 215, 473, 403);
		contentPane.add(scrollPane);
		
		scrollPane2 = new JScrollPane(dictionaryWindow, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane2.setBounds(497, 215, 477, 403);
		contentPane.add(scrollPane2);
		
		
		
		JLabel lblNewLabel = new JLabel("단어 입력");
		lblNewLabel.setBounds(51, 168, 162, 45);
		lblNewLabel.setHorizontalAlignment(JLabel.CENTER);
		lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		contentPane.add(lblNewLabel);
		
		MyMouseListener listener = new MyMouseListener();
		btnNewButton.addMouseListener(listener);
		btnNewButton.addActionListener(this);
		sendButton.addActionListener(this);
	
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnNewButton) {
			o.cf.setVisible(true);
		}else if (e.getSource() == sendButton) {
			//메세지 입력없이 전송버튼만 눌렀을 경우
			if(textField.getText().equals("")){
                   return;
            }                  
			//gameWindow.append("["+o.ID+"] "+ textField.getText()+"\n");
			
			//서버로 전송하는 부분 있어야함.
			
			out.println(Protocol.SENDWORD + "//" + textField.getText());
			out.flush();

			textField.setText(""); //채팅 치는 곳 초기화
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
