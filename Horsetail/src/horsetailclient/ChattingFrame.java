package horsetailclient;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Util.Protocol;

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
	
	public ChattingFrame(Operator _o, PrintWriter printW) {
		o=_o;
		out = printW;
		
		setBounds(100, 100, 452, 582);
		setResizable(false);
		setVisible(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.white);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textArea = new JTextArea();
		textArea.setBackground(Color.WHITE);
		textArea.setBorder(bb);
		textArea.setBounds(0, 0, 423, 489);
		textArea.setFont(new Font("맑은 고딕", Font.PLAIN, 10));
		contentPane.add(textArea);
		
		textField = new JTextField();
		textField.setBounds(0, 488, 368, 57);
		textField.setBorder(bb);
		contentPane.add(textField);
		textField.setColumns(10);
		
		sendButton = new JButton();
		sendButton.setIcon(new ImageIcon(MainFrame.class.getResource("./Image/send.png")));
		sendButton.setBounds(368, 488, 69, 57);
		sendButton.setBackground(Color.white);
		contentPane.add(sendButton);
		
		scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 0, 438, 489);
		contentPane.add(scrollPane);
		
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
}
