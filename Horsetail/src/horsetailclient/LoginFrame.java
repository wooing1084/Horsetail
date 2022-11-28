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
	JPanel centerPanel = new JPanel();
	
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
	
	Image background;
	
	LoginFrame(Operator _o, PrintWriter printW){
		o = _o;
		out = printW;
		
		setTitle("로그인");
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("./Image/logo.png")));
		setBackground(Color.white);
		
		background=new ImageIcon(MainFrame.class.getResource("./Image/loginBackGround.png")).getImage();

		centerPanel = new JPanel(){
			public void paintComponent(Graphics g) {//그리는 함수
				g.drawImage(background, 0, 0, null);//background를 그려줌		
				setOpaque(false);// 추가
				super.paintComponent(g);// 추가
			}
		};
		
		/* Panel 크기 작업 */
		centerPanel.setBounds(0, 0, 1000, 720);
		
		/* Label 크기 작업 */
		idL.setBounds(350, 350, 50, 30);
		pwL.setBounds(350, 385, 50, 30);
		
		/* TextField 크기 작업 */
		id.setBounds(412, 350, 140, 30);
		pw.setBounds(412, 386, 140, 30);
		
		/* Button 크기 작업 */
		loginBtn.setBackground(Color.WHITE);
		loginBtn.setBounds(564, 350, 75, 70);
		joinBtn.setBackground(Color.WHITE);
		joinBtn.setBounds(346, 426, 135, 25);
		exitBtn.setBackground(Color.WHITE);
		exitBtn.setBounds(493, 426, 135, 25);
		
		/* Panel 추가 작업 */
		setContentPane(centerPanel);	//panel을 기본 컨테이너로 설정
		centerPanel.setLayout(null);
		
		/* westPanel 컴포넌트 */
		centerPanel.add(idL);
		centerPanel.add(id);
		centerPanel.add(pwL);
		centerPanel.add(pw);
		
		/* eastPanel 컴포넌트 */
		centerPanel.add(loginBtn);
		
		/* southPanel 컴포넌트 */
		centerPanel.add(exitBtn);
		centerPanel.add(joinBtn);	
		
		/* Button 이벤트 리스너 추가 */
		ButtonListener bl = new ButtonListener();
		
		loginBtn.addActionListener(bl);
		exitBtn.addActionListener(bl);
		joinBtn.addActionListener(bl);
		
		MyMouseListener listener = new MyMouseListener();
        loginBtn.addMouseListener(listener);
        exitBtn.addMouseListener(listener);
        joinBtn.addMouseListener(listener);
		
		setSize(1000, 720);
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

