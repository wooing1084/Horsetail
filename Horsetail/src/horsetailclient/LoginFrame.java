package horsetailclient;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;

import Util.Protocol;

import java.awt.event.*;
import java.io.*;

public class LoginFrame extends JFrame {
	MainFrame mf;
	JoinFrame jf;
	/* Panel */
	JPanel basePanel = new JPanel(new BorderLayout());
	JPanel centerPanel = new JPanel(new BorderLayout());
	JPanel westPanel = new JPanel();
	JPanel eastPanel = new JPanel();
	JPanel southPanel = new JPanel();
	
	/* Label */
	JLabel idL = new JLabel("아이디");
	JLabel pwL = new JLabel("비밀번호");
	
	/* TextField */
	JTextField id = new JTextField();
	JPasswordField pw = new JPasswordField();
	
	/* Button */
	JButton loginBtn = new JButton("로그인");
	JButton joinBtn = new JButton("회원가입");
	JButton exitBtn = new JButton("프로그램 종료");
	
	Operator o = null;
	PrintWriter out = null;
	
	LoginFrame(Operator _o, PrintWriter printW){
		o = _o;
		out = printW;
		
		setTitle("로그인");
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("./Image/logo.png")));
		setBackground(Color.white);
		
		/* Panel 크기 작업 */
		centerPanel.setPreferredSize(new Dimension(260, 80));
		westPanel.setBackground(Color.WHITE);
		westPanel.setPreferredSize(new Dimension(210, 75));
		eastPanel.setBackground(Color.WHITE);
		eastPanel.setPreferredSize(new Dimension(90, 75));
		southPanel.setBackground(Color.WHITE);
		southPanel.setPreferredSize(new Dimension(290, 40));
		
		/* Label 크기 작업 */
		idL.setPreferredSize(new Dimension(50, 30));
		pwL.setPreferredSize(new Dimension(50, 30));
		
		/* TextField 크기 작업 */
		id.setPreferredSize(new Dimension(140, 30));
		pw.setPreferredSize(new Dimension(140, 30));
		
		/* Button 크기 작업 */
		loginBtn.setBackground(Color.WHITE);
		loginBtn.setPreferredSize(new Dimension(75, 63));
		joinBtn.setBackground(Color.WHITE);
		joinBtn.setPreferredSize(new Dimension(135, 25));
		exitBtn.setBackground(Color.WHITE);
		exitBtn.setPreferredSize(new Dimension(135, 25));
		
		/* Panel 추가 작업 */
		setContentPane(basePanel);	//panel을 기본 컨테이너로 설정
		
		basePanel.add(centerPanel, BorderLayout.CENTER);
		basePanel.add(southPanel, BorderLayout.SOUTH);
		centerPanel.add(westPanel, BorderLayout.WEST);
		centerPanel.add(eastPanel, BorderLayout.EAST);
		
		westPanel.setLayout(new FlowLayout());
		eastPanel.setLayout(new FlowLayout());
		southPanel.setLayout(new FlowLayout());
		
		/* westPanel 컴포넌트 */
		westPanel.add(idL);
		westPanel.add(id);
		westPanel.add(pwL);
		westPanel.add(pw);
		
		/* eastPanel 컴포넌트 */
		eastPanel.add(loginBtn);
		
		/* southPanel 컴포넌트 */
		southPanel.add(exitBtn);
		southPanel.add(joinBtn);
		
		/* Button 이벤트 리스너 추가 */
		ButtonListener bl = new ButtonListener();
		
		loginBtn.addActionListener(bl);
		exitBtn.addActionListener(bl);
		joinBtn.addActionListener(bl);
		
		MyMouseListener listener = new MyMouseListener();
        loginBtn.addMouseListener(listener);
        exitBtn.addMouseListener(listener);
        joinBtn.addMouseListener(listener);
		
		setSize(310, 150);
		setLocationRelativeTo(null);
		setBackground(Color.white);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void showDialog(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}
	
	/* Button 이벤트 리스너 */
	class ButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource();
			
			/* TextField에 입력된 아이디와 비밀번호를 변수에 초기화 */
			String uid = id.getText(); //입력된 ID를 가져와서 uid에 저장
			String upass = "";
			for(int i=0; i<pw.getPassword().length; i++) { //입력된 패스워드를 가져와서 upass String에 저장
				upass = upass + pw.getPassword()[i];
			}
			
			/* 게임종료 버튼 이벤트 */
			if(b.getText().equals("프로그램 종료")) {
				System.out.println("프로그램 종료");
				System.exit(0);
			}
			
			/* 회원가입 버튼 이벤트 */
			else if(b.getText().equals("회원가입")) {
				o.jf.setVisible(true);
			}
			
			/* 로그인 버튼 이벤트 */
			else if(b.getText().equals("로그인")) {
				if(uid.equals("") || upass.equals("")) {
					JOptionPane.showMessageDialog(null, "아이디와 비밀번호 모두 입력해주세요", "로그인 실패", JOptionPane.ERROR_MESSAGE);
					System.out.println("로그인 실패 > 로그인 정보 미입력");
				}
				
				else if(uid != null && upass != null) {
					out.println(Protocol.LOGIN + "//" + uid + "%" + upass);
					out.flush();
				}
			}
			
			
		}
	}
	
	class MyMouseListener implements MouseListener{

	    @Override//마우스가 버튼 안으로 들어오면 빨간색으로 바뀜
	    public void mouseEntered(MouseEvent e) {
	        JButton b = (JButton)e.getSource();
	        b.setBackground(new Color(204, 153, 0));
	    }

	    @Override//마우스가 버튼 밖으로 나가면 노란색으로 바뀜
	    public void mouseExited(MouseEvent e) {
	        JButton b = (JButton)e.getSource();
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

