package horsetailclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import Util.Protocol;

public class Operator implements Runnable {
	
	//Database db = null;
	LoginFrame lf = null;
	MainFrame mf = null;
	JoinFrame jf = null;
	PlayFrame pf = null;
	ChattingFrame cf = null;
	
	static BufferedReader in = null;
	static PrintWriter out = null;
	
	public String ID;
	
	public static void main(String[] args) {
		Socket conSoc = null;
		String ip = "127.0.0.1";
		int port = 37101;
		
		in = null;
		out = null;
		
		try {
			conSoc = new Socket(ip, port);
			in = new BufferedReader(new InputStreamReader(conSoc.getInputStream()));
			out = new PrintWriter(new OutputStreamWriter(conSoc.getOutputStream()));
		}
		catch(UnknownHostException e) {
			System.out.println("호스트를 찾을 수 없습니다.");
			e.printStackTrace();
		}
		catch(SocketException e) {
			System.out.println("소켓 연결에 문제가 발생하였습니다.");
			e.printStackTrace();
		}
		catch(IOException e) {
			System.out.println("서버와의 IO 연결에 실패했습니다.");
			e.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		Operator opt = new Operator();
		Thread thread = new Thread(opt);
		thread.start();
		//opt.db = new Database();
		opt.lf = new LoginFrame(opt, out);
		opt.mf = new MainFrame(opt, out);
		opt.jf = new JoinFrame(opt, out);
		opt.pf = new PlayFrame(opt, out);
		opt.cf = new ChattingFrame(opt, out);
	}
	
	@Override
	public void run() {
		
		String line[] = null;
		
		while(true) {
			try {
				line = in.readLine().split("//");
				
				if(line == null) {
					in.close();
					out.close();
					System.exit(0);
				}
				
				else if(line[0].compareTo(Protocol.REGISTER_OK) == 0) {
					System.out.println("회원가입 성공");
					jf.showDialog("회원가입에 성공하였습니다");
					jf.dispose();
				}
				
				else if (line[0].compareTo(Protocol.REGISTER_NO) == 0) {
					System.out.println("회원가입 실패");
					jf.showDialog("회원가입에 실패하였습니다");
				}

				else if (line[0].compareTo(Protocol.IDVALIDCHECK_OK) == 0) {

				}

				else if (line[0].compareTo(Protocol.IDVALIDCHECK_NO) == 0) {

				}
				
				else if (line[0].compareTo(Protocol.LOGIN_OK) == 0) {
					System.out.println("로그인 성공");
					ID = lf.id.getText();
					lf.showDialog("로그인에 성공하였습니다");
					lf.dispose(); // 로그인 성공하면 로그인 창 닫힘
					mf.setVisible(true);
				}

				else if (line[0].compareTo(Protocol.LOGIN_NO) == 0) {
					System.out.println("로그인 실패");
					lf.showDialog("로그인에 실패하였습니다");
				}
				
				else if (line[0].compareTo(Protocol.STARTGAME_OK) == 0) {
					pf.setVisible(true);
				}
				
				else if (line[0].compareTo(Protocol.STARTGAME_NO) == 0) {

				}
				
				else if (line[0].compareTo(Protocol.TOOSMALLUSER) == 0) {

				}
				
				else if (line[0].compareTo(Protocol.SENDMESSAGE_OK) == 0) {
					String chatSender = line[1];
					String chat = line[2];
					// RecvThread부분 SENDWORD 프로토콜 BroadCast로 꼭 수정!!!!
					cf.textArea.append("["+chatSender+"] "+ chat +"\n");
				}
				
				else if (line[0].compareTo(Protocol.SENDMESSAGE_NO) == 0) {

				}
				
				else if (line[0].compareTo(Protocol.STAT_OK) == 0) {
					String wins = line[1];
					String loses = line[2];
				}
				
				else if (line[0].compareTo(Protocol.STAT_NO) == 0) {

				}
				
				else if (line[0].compareTo(Protocol.RANKING_OK) == 0) {
					String[] id = line[1].split("%");
					mf.setRankingList(id);
				}
				
				else if (line[0].compareTo(Protocol.RANKING_NO) == 0) {

				}
				
				else if (line[0].compareTo(Protocol.SENDWORD_OK) == 0) {
					String wordSender = line[1];
					String word = line[2];
					// RecvThread부분 SENDWORD 프로토콜 BroadCast로 꼭 수정!!!!
					pf.gameWindow.append("["+wordSender+"] "+ word +"\n");
				}
				
				else if (line[0].compareTo(Protocol.SENDWORD_NO) == 0) {

				}
				
				else if (line[0].compareTo(Protocol.WORDONLYONECHAR) == 0) {

				}
				
				else if (line[0].compareTo(Protocol.WORDNOTCHAIN) == 0) {

				}
				
				else if (line[0].compareTo(Protocol.WORDOVERLAP) == 0) {

				}
				
				else if (line[0].compareTo(Protocol.WORDNOTEXIST) == 0) {

				}
				
				else if (line[0].compareTo(Protocol.SENDDEF_OK) == 0) {
					String def = line[1];
				}
				
				else if (line[0].compareTo(Protocol.SENDDEF_NO) == 0) {

				}
				
				else if (line[0].compareTo(Protocol.TIMEOUT_OK) == 0) {
					String timeoutPlayer = line[1];
				}
				
				else if (line[0].compareTo(Protocol.TIMEOUT_NO) == 0) {

				}
				
				else if (line[0].compareTo(Protocol.ANOTHERTURN) == 0) {

				}
				
				else if (line[0].compareTo(Protocol.GAMEOUT_OK) == 0) {
					String gameoutPlayer = line[1];
				}
				
				else if (line[0].compareTo(Protocol.GAMEOUT_NO) == 0) {

				}
				
				else if (line[0].compareTo(Protocol.GAMEEND_OK) == 0) {

				}
				
				else if (line[0].compareTo(Protocol.GAMEEND_NO) == 0) {

				}
				
				else if (line[0].compareTo(Protocol.EXITPROGRAM_OK) == 0) {

				}
				
				else if (line[0].compareTo(Protocol.EXITPROGRAM_NO) == 0) {

				}
				
				else if (line[0].compareTo(Protocol.INVALIDTAG) == 0) {
					System.out.println("에러 프로토콜");
				}
			}
			catch(IOException e) {
				e.printStackTrace();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}