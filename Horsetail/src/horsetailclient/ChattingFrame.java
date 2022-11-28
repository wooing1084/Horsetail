package horsetailclient;

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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Util.Protocol;
import horsetailclient.PlayFrame.MyMouseListener;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.io.*;

public class ChattingFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField textField;
	public JTextArea textArea;
	JScrollPane scrollPane;
	JButton sendButton;
	LineBorder bb = new LineBorder(Color.gray, 1, true); //라벨의 테두리 설정값
	Operator o=null;
	PrintWriter out = null;
	Image background;
	
	public ChattingFrame(Operator _o, PrintWriter printW) {
		o=_o;
		out = printW;
		
		setTitle("채팅");
		setBounds(100, 100, 452, 582);
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("./Image/logo.png")));
		setResizable(false);
		setVisible(false);
		
		background=new ImageIcon(MainFrame.class.getResource("./Image/chatRoom.png")).getImage();

		contentPane = new JPanel(){
			public void paintComponent(Graphics g) {//그리는 함수
				g.drawImage(background, 0, 0, null);//background를 그려줌		
				setOpaque(false);// 추가
				super.paintComponent(g);// 추가
			}
		};
		contentPane.setBorder(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textArea = new JTextArea();
		textArea.setBorder(bb);
		textArea.setBounds(0, 0, 423, 489);
		textArea.setOpaque(false);
		textArea.setFont(new Font("맑은 고딕", Font.PLAIN, 15));

		contentPane.add(textArea);
		
		textField = new JTextField();
		textField.setBounds(0, 488, 368, 57);
		textField.setBorder(bb);
		textField.setBackground(Color.white);
		//textField.setOpaque(false);
		contentPane.add(textField);
		textField.setColumns(10);
		
		sendButton = new JButton();
		sendButton.setIcon(new ImageIcon(MainFrame.class.getResource("./Image/send.png")));
		sendButton.setBounds(368, 488, 69, 57);
//		sendButton.setBorderPainted(false);
//		sendButton.setContentAreaFilled(false);
//		sendButton.setFocusPainted(false);
		sendButton.setBackground(Color.yellow);
		contentPane.add(sendButton);
		
		scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 0, 438, 489);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		contentPane.add(scrollPane);
		
		MyMouseListener listener = new MyMouseListener();
		sendButton.addMouseListener(listener);
		sendButton.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == sendButton) {
			//메세지 입력없이 전송버튼만 눌렀을 경우
			if(textField.getText().equals("")){
                   return;
            }                  
			
			out.println(Protocol.SENDMESSAGE + "//" + o.roomID + "//" + textField.getText());
			out.flush();

			textField.setText(""); //채팅 치는 곳 초기화
		}else{
			this.dispose();
		}
	}
	
	public void initChat() {
		textArea.setText("");
		textField.setText("");
	}
	
	class MyMouseListener implements MouseListener {

		@Override
		public void mouseEntered(MouseEvent e) {
			if (e.getSource() == sendButton) {
				JButton b = (JButton) e.getSource();
				b.setIcon(new ImageIcon(MainFrame.class.getResource("./Image/send2.png")));
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if (e.getSource() == sendButton) {
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
