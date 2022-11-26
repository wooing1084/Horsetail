package horsetailclient;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ChattingFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField textField;
	private JTextArea textArea;
	JScrollPane scrollPane;
	JButton sendButton;
	LineBorder bb = new LineBorder(Color.gray, 1, true); //라벨의 테두리 설정값
	Operator o=null;
	
	public ChattingFrame(Operator _o) {
		o=_o;
		
		setBounds(100, 100, 452, 582);
		setResizable(false);
		setVisible(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.white);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textArea = new JTextArea();
		textArea.setBackground(Color.WHITE);
		textArea.setBorder(bb);
		textArea.setBounds(0, 0, 423, 489);
		contentPane.add(textArea);
		
		textField = new JTextField();
		textField.setBounds(0, 488, 368, 57);
		textField.setBorder(bb);
		contentPane.add(textField);
		textField.setColumns(10);
		
		sendButton = new JButton();
		sendButton.setIcon(new ImageIcon(MainFrame.class.getResource("/Image/send.png")));
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
			textArea.append("["+o.ID+"] "+ textField.getText()+"\n");
			
			//서버로 전송하는 부분 있어야함.

			textField.setText(""); //채팅 치는 곳 초기화
		}else{
			this.dispose();
		}
	}
}
