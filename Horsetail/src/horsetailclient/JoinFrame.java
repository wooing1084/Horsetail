package horsetailclient;

import javax.swing.*;

import Util.Protocol;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class JoinFrame extends JFrame {
	//Database db = new Database();
	/* Panel */
	JPanel panel = new JPanel();

	/* Label */
	JLabel idL = new JLabel("아이디");
	JLabel pwL = new JLabel("비밀번호");

	/* TextField */
	JTextField id = new JTextField();
	JPasswordField pw = new JPasswordField();

	/* Button */
	JButton joinBtn = new JButton("가입하기");
	JButton cancelBtn = new JButton("가입취소");

	Operator o = null;
	PrintWriter out = null;

	JoinFrame(Operator _o, PrintWriter printW) {
		o = _o;
		out = printW;

		setTitle("회원가입");
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("./Image/logo.png")));
		setBackground(Color.white);

		/* Label 크기 작업 */
		idL.setPreferredSize(new Dimension(50, 30));
		pwL.setPreferredSize(new Dimension(50, 30));

		/* TextField 크기 작업 */
		id.setPreferredSize(new Dimension(140, 30));
		pw.setPreferredSize(new Dimension(140, 30));

		/* Button 크기 작업 */
		joinBtn.setPreferredSize(new Dimension(95, 25));
		cancelBtn.setPreferredSize(new Dimension(95, 25));

		/* Panel 추가 작업 */
		panel.setBackground(Color.WHITE);
		setContentPane(panel);

		panel.add(idL);
		panel.add(id);

		panel.add(pwL);
		panel.add(pw);

		panel.add(cancelBtn);
		panel.add(joinBtn);

		/* Button 이벤트 리스너 추가 */
		ButtonListener bl = new ButtonListener();

		joinBtn.setBackground(Color.white);
		cancelBtn.setBackground(Color.white);

		cancelBtn.addActionListener(bl);
		joinBtn.addActionListener(bl);

		MyMouseListener listener = new MyMouseListener();
		cancelBtn.addMouseListener(listener);
		joinBtn.addMouseListener(listener);

		setSize(250, 150);
		setLocationRelativeTo(null);
		setVisible(false);
		setResizable(false);
	}

	public void showDialog(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}

	/* Button 이벤트 리스너 */
	class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();

			/* TextField에 입력된 회원 정보들을 변수에 초기화 */
			String uid = id.getText();
			String upass = "";
			for (int i = 0; i < pw.getPassword().length; i++) {
				upass = upass + pw.getPassword()[i];
			}

			/* 가입취소 버튼 이벤트 */
			if (b.getText().equals("가입취소")) {
				dispose();
			}

			/* 가입하기 버튼 이벤트 */
			else if (b.getText().equals("가입하기")) {
				if (uid.equals("") || upass.equals("")) {
					JOptionPane.showMessageDialog(null, "모든 정보를 기입해주세요", "회원가입 실패", JOptionPane.ERROR_MESSAGE);
					System.out.println("회원가입 실패 > 회원정보 미입력");
				}

				else if (!uid.equals("") && !upass.equals("")) {
					out.println(Protocol.REGISTER + "//" + uid + "%" + upass);
					out.flush();
				}
			}

		}
	}
}

class MyMouseListener implements MouseListener {

	@Override // 마우스가 버튼 안으로 들어오면 빨간색으로 바뀜
	public void mouseEntered(MouseEvent e) {
		JButton b = (JButton) e.getSource();
		b.setBackground(new Color(204, 153, 0));
	}

	@Override // 마우스가 버튼 밖으로 나가면 노란색으로 바뀜
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
