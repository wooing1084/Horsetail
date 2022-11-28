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
	MakeRoomDialog mrd = null;
	
	static BufferedReader in = null;
	static PrintWriter out = null;
	
	public static String ID;
	public static String roomID;
	
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
		opt.mrd = new MakeRoomDialog(opt, out);
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
				
				else if (line[0].compareTo(Protocol.ROOMS_OK) == 0) {
					System.out.println("방 목록 로딩 성공");
					String[] id = line[1].split("%");
					String[] name = line[2].split("%");
					mf.initRoomIdList();
					mf.setRoomIdList(id);
					mf.initRoomNameList();
					mf.setRoomNameList(name);
				}
				
				else if (line[0].compareTo(Protocol.ROOMS_NO) == 0) {
					System.out.println("방이 없음");
					//lf.showDialog("방이 없습니다. 먼저 만들어보세요!");
				}
				
				else if (line[0].compareTo(Protocol.ROOMCREATE_OK) == 0) {
					System.out.println("방 생성 성공");
					lf.showDialog("방 생성에 성공하였습니다");
					mf.setVisible(false);
					pf.setVisible(true);
					roomID = line[1];
				}
				
				else if (line[0].compareTo(Protocol.ROOMCREATE_NO) == 0) {
					System.out.println("방 생성 성공");
					lf.showDialog("방 생성에 실패하였습니다");
				}
				
				else if (line[0].compareTo(Protocol.ROOMFULL) == 0) {
					System.out.println("방 정원 초과");
					lf.showDialog("방이 꽉 찼습니다");
				}
				
				else if (line[0].compareTo(Protocol.JOINROOM_OK) == 0) {
					System.out.println("방 입장 성공");
					lf.showDialog("방 입장에 성공하였습니다");
					mf.setVisible(false);
					pf.setVisible(true);
					roomID = line[1];
				}
				
				else if (line[0].compareTo(Protocol.JOINROOM_NO) == 0) {
					System.out.println("방 입장 실패");
					lf.showDialog("방 입장에 실패하였습니다");
				}
				
				else if (line[0].compareTo(Protocol.STARTGAME_OK) == 0) {
					pf.setNotation("게임을 시작합니다!");
				}
				
				else if (line[0].compareTo(Protocol.STARTGAME_NO) == 0) {
					lf.showDialog("게임 시작에 실패했습니다");
				}
				
				else if (line[0].compareTo(Protocol.TOOSMALLUSER) == 0) {
					lf.showDialog("사용자가 너무 적어 게임을 시작할 수 없습니다");
				}
				
				else if (line[0].compareTo(Protocol.SENDMESSAGE_OK) == 0) {
					String chatSender = line[1];
					String chat = line[2];
					cf.textArea.append("["+chatSender+"] "+ chat +"\n");
				}
				
				else if (line[0].compareTo(Protocol.SENDMESSAGE_NO) == 0) {
					lf.showDialog("메시지 전송에 실패했습니다");
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
					lf.showDialog("랭킹 불러오기에 실패했습니다");
				}
				
				else if (line[0].compareTo(Protocol.SENDWORD_OK) == 0) {
					String word = line[1];
					// RecvThread부분 SENDWORD 프로토콜 BroadCast로 꼭 수정!!!!
					pf.gameWindow.append(word +"\n");
				}
				
				else if (line[0].compareTo(Protocol.SENDWORD_NO) == 0) {
					lf.showDialog("현재 턴이 아닙니다!");
				}
				
				else if (line[0].compareTo(Protocol.WORDONLYONECHAR) == 0) {
					pf.setNotation("\"" + line[1] + "\"은(는) 한 글자 단어입니다!");
				}
				
				else if (line[0].compareTo(Protocol.WORDNOTCHAIN) == 0) {
					pf.setNotation("\"" + line[1] + "\"은(는) 이전 단어와 이어지지 않습니다!");
				}
				
				else if (line[0].compareTo(Protocol.WORDOVERLAP) == 0) {
					pf.setNotation("\"" + line[1] + "\"은(는) 이미 나온 단어입니다!");
				}
				
				else if (line[0].compareTo(Protocol.WORDNOTEXIST) == 0) {
					pf.setNotation("\"" + line[1] + "\"은(는) 존재하지 않는 단어입니다!");
				}
				
				else if (line[0].compareTo(Protocol.SENDDEF) == 0) {
					String words[] = line[1].split("%");
					String set = "품사 : " +  words[0] + "\n의미 : " + words[1];
					pf.setDict(set);
				}
				
				else if (line[0].compareTo(Protocol.SENDDEF_OK) == 0) {
					
				}
				
				else if (line[0].compareTo(Protocol.SENDDEF_NO) == 0) {

				}
				
				else if (line[0].compareTo(Protocol.NOWTIME) == 0) {
					pf.setTime(line[1]);
				}
				
				else if (line[0].compareTo(Protocol.TIMEOUT_OK) == 0) {
					String timeoutPlayer = line[1];
				}
				
				else if (line[0].compareTo(Protocol.TIMEOUT_NO) == 0) {

				}
				
				else if (line[0].compareTo(Protocol.YOURTURN) == 0) {
					pf.setNotation("당신 차례입니다!");
				}
				
				else if (line[0].compareTo(Protocol.ANOTHERTURN) == 0) {
					pf.setNotation(line[1] + "님의 차례입니다.");
				}
				
				else if (line[0].compareTo(Protocol.GAMEOUT_OK) == 0) {
					String gameoutPlayer = line[1];
				}
				
				else if (line[0].compareTo(Protocol.GAMEOUT_NO) == 0) {

				}
				
				else if (line[0].compareTo(Protocol.GAMEEND) == 0) {
					lf.showDialog("게임이 종료되었습니다!");
					pf.setVisible(false);
					mf.setVisible(true);
					pf.initPlayFrame();
					cf.initChat();
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