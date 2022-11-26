/*
 * Name : RecvThread.java
 * Author : 이준형
 * Description : 클라이언트가 접속하면 하나씩 개별로 실행되는 스레드
 */

/*
 * run 메소드의 간략한 프로토콜 처리 방법 :
 * 
 * 프로토콜은 "코드//사용자메시지1//사용자메시지2//..." 의 형식으로 이루어진다.
 * 
 * 코드는 Protocol.java에 미리 3자리 숫자를 이용해 정해져 있다.
 * 회원가입은 100, 단어전송 리퀘스트는 400, 게임탈락 성공 ACK은 501... 이런 식으로
 * 
 * 사용자 메시지는 어떤 프로토콜이냐에 따라 달라지지만,
 * 300번의 채팅 메시지 전송 리쿼스트 프로토콜을 예로 들면
 * 첫 번째 사용자 메시지는 채팅 메시지 내용이다.
 * 즉, 사용자가 서버로 보낸 프로토콜은 "300//ChattingMessage//" 이다.
 * 
 * 서버는 이를 // 단위로 파싱(split 메소드 사용)한다.
 * 0번 위치에는 코드가 들어가기로 약속하였으므로 사용자가 채팅 리퀘스트를 보냈다는 것을 확인하고,
 * 1번 위치에는 메시지가 들어가기로 약속하였으므로 1번 위치의 스트링을 꺼낸다.
 * 
 * 사용자에게 메시지를 보내는 것은 301번의 채팅 메시지 전송 OK라는 ACK을 보내야 한다.
 * 서버는 모든 사용자들에 대해 반복문으로 프로토콜을 전송한다.
 * 첫 번째 사용자 메시지는 채팅을 보낸 사람의 닉네임,
 * 두 번째 사용자 메시지는 채팅의 내용이므로,
 * 만약 SomeUser 라는 닉네임의 사용자가 ChattingMessage라는 메시지를 보냈다면,
 * 서버가 사용자로 보낸 프로토콜은 "301//SomeUser//ChattingMessage//"가 될 것이다.
 * 
 * 이것은 Protocol, 즉 정해진 규약이므로 클라이언트도 코드를 보고 적절히 파싱하면
 * 프로토콜 기반 네트워크 통신이 되는 것이다.
 * 
 * 예시의 코드의 작동을 더 자세히 보고싶다면,
 * else if(line[0].compareTo(Protocol.SENDMESSAGE) == 0)
 * 을 검색해서 찾아서 보면 된다. (160번째 줄 부근)
 */

package horsetailserver;

import java.io.*;
import java.net.*;
import java.util.*;

class RecvThread extends Thread {
	private Socket _socket;
	private User user;

	private InputStreamReader inputStream = null;
	private OutputStreamWriter outputStream = null;

	public RecvThread(Socket socket){
		_socket = socket;
		user = null;

		try {
			inputStream = new InputStreamReader(_socket.getInputStream());
			outputStream = new OutputStreamWriter(_socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public RecvThread(Socket socket, User _user){
		_socket = socket;
		user = _user;
		try {
			inputStream = new InputStreamReader(_socket.getInputStream());
			outputStream = new OutputStreamWriter(_socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void SendMessage(String msg){
		PrintWriter writer = new PrintWriter(outputStream);
		writer.println(msg);
		writer.flush();
	}

	public void Close(){
		try {
			inputStream.close();
			outputStream.close();
			_socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void run(){
		BufferedReader reader = new BufferedReader(inputStream);

		//request protocol
		//req num/roomID/msg
		while(true){
			if(_socket.isClosed()){
				break;
			}

			if(user != null)
				System.out.println(user.getNick()+ "cycle:");
			else
				System.out.println("unknown cycle:");

			try {
				String req = reader.readLine();

				if(user != null)
					System.out.println(user.getNick() + ":" + req);
				else
					System.out.println("unknown:" + req);

				String[] reqs = req.split("/");

				if(reqs[0].compareTo("150") == 0){
					GameRoom gr = RoomManager.CreateRoom(this);
					SendMessage(Protocol.ROOMCREATE_OK + "/" + gr.GetRoomID());
				}

				if(reqs[0].compareTo("160") == 0){
					RoomManager.JoinRoom(reqs[1], this);
					SendMessage(Protocol.JOINROOM_OK + "/" + reqs[1]);
				}

				if(reqs[0].compareTo("300") == 0){
					int idx = RoomManager.GetRoomIdx(reqs[1]);

					RoomManager.GetRoomList().get(idx).BroadCast(reqs[2]);
				}

			} catch (SocketException e){
				e.printStackTrace();
				Server.RemoveUser(this);
				break;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			Server.RemoveUser(this);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
//	private static final int MAX_USERS = 4;
//
//	private Socket conSoc = null;
//
//	private User user = null;
//	private GameRoom game = null;
//
//	private BufferedReader in = null;
//	private PrintWriter out = null;
//
//	private ArrayList<RecvThread> allUserList;
//
//	public RecvThread(Socket s, ArrayList<RecvThread> ul, GameRoom g) throws Exception {
//		this.conSoc = s;
//		this.allUserList = ul;
//		this.user = new User();
//		this.game = g;
//
//		in = new BufferedReader(new InputStreamReader(conSoc.getInputStream()));
//		out = new PrintWriter(new OutputStreamWriter(conSoc.getOutputStream()));
//	}
//
//	public void run() {
//		try {
//
//			String[] line = null;
//
//			while(true) {
//
//				line = in.readLine().split("//");
//
//				if(line == null) {
//					break;
//				}
//
//				else if(line[0].compareTo(Protocol.REGISTER) == 0) {
//					String contents[] = line[1].split("%");
//
//					// SQL 회원가입 코드 추가
//				}
//
//				else if(line[0].compareTo(Protocol.IDVALIDCHECK) == 0) {
//					// SQL 회원가입 시 닉네임 중복체크 코드 추가
//
//					int count = 0;
//
//					// count == SQL select 문으로 가져온 개수
//
//					if(count == 0) {
//						out.println(Protocol.IDVALIDCHECK_OK + "//" + "MSG");
//						out.flush();
//					}
//					else {
//						out.println(Protocol.IDVALIDCHECK_NO + "//" + "MSG");
//						out.flush();
//					}
//				}
//
//				else if(line[0].compareTo(Protocol.LOGIN) == 0) {
//					String content[] = line[1].split("%");
//
//					// SQL 로그인 시 ID 및 닉네임 맞는지 검사하는 코드 추가
//
//					int count = 0;
//
//					// count == SQL select 문으로 가져온 개수
//
//					if(count == 0) { // 로그인 실패
//						out.println(Protocol.LOGIN_NO + "//" + "MSG");
//						out.flush();
//
//						user.initialize();
//					}
//
//					else if(allUserList.size() >= MAX_USERS) { // 싱글룸 정원 초과
//						out.println(Protocol.ROOMFULL + "//" + "MSG");
//						out.flush();
//					}
//
//					else { // 로그인 성공
//						allUserList.add(this);
//
//						String userList = "";
//						for(int i = 0; i < allUserList.size(); i++) {
//							userList += allUserList.get(i).user.getNick() + "%";
//						}
//
//						for(int i = 0; i < allUserList.size(); i++) {
//							allUserList.get(i).out.println(
//								Protocol.LOGIN_OK + "//" + user.getNick() + "//님이 입장했습니다.//" + userList
//							);
//							allUserList.get(i).out.flush();
//						}
//					}
//				}
//
//				else if(line[0].compareTo(Protocol.STARTGAME) == 0) {
//
//					if(allUserList.size() <= 1) {
//						out.println(Protocol.TOOSMALLUSER + "//" + "MSG");
//						out.flush();
//					}
//
//					else {
//						allUserList.get(0).user.setTurn(true);
//						for(int i = 0; i < allUserList.size(); i++) {
//							allUserList.get(i).out.println(
//									Protocol.STARTGAME_OK + "//" + "MSG"
//							);
//							allUserList.get(i).out.flush();
//						}
//					}
//				}
//
//				else if(line[0].compareTo(Protocol.SENDMESSAGE) == 0) {
//					for(int i = 0; i < allUserList.size(); i++) {
//						allUserList.get(i).out.println(
//							Protocol.SENDMESSAGE_OK + "//" + user.getNick() + "//" + line[1]
//						);
//						allUserList.get(i).out.flush();
//					}
//				}
//
//				else if(line[0].compareTo(Protocol.STAT) == 0) {
//
//				}
//
//				else if(line[0].compareTo(Protocol.RANKING) == 0) {
//
//				}
//
//				else if(line[0].compareTo(Protocol.SENDWORD) == 0) {
//
//					if(game.isOneChar(line[1])) {
//						out.println(Protocol.WORDONLYONECHAR + "//" + "MSG");
//						out.flush();
//					}
//
//					else if(game.isNotChain(line[1])) {
//						out.println(Protocol.WORDNOTCHAIN + "//" + "MSG");
//						out.flush();
//					}
//
//					else if(game.isOverlap(line[1])) {
//						out.println(Protocol.WORDOVERLAP + "//" + "MSG");
//						out.flush();
//					}
//
//					else if(game.isNotExist(line[1])) {
//						out.println(Protocol.WORDNOTEXIST + "//" + "MSG");
//						out.flush();
//					}
//
//					else {
//						game.addWord(line[1]);
//						out.println(Protocol.SENDWORD_OK + "//" + user.getNick());
//						out.flush();
//
//						int idx = allUserList.indexOf((Object)user.getNick());
//						int i = 1;
//						while(!allUserList.get((idx + i) % allUserList.size()).user.getAlive()) {
//							i++;
//						}
//						allUserList.get((idx + i) % allUserList.size()).user.setTurn(true);
//						this.user.setTurn(false);
//
//						out.println(Protocol.YOURTURN + "//" + allUserList.get((idx + i) % allUserList.size()).user.getNick());
//						out.flush();
//					}
//				}
//
//				else if(line[0].compareTo(Protocol.SENDDEF) == 0) {
//					String def = game.getDefinition();
//					out.println(Protocol.SENDDEF_OK + "//" + def);
//					out.flush();
//				}
//
//				/*
//				// 아래의 GAMEOUT과 통합할까 고민 중
//				else if(line[0].compareTo(Protocol.TIMEOUT) == 0) { // 타이머가 0이 되면 request
//					user.setAlive(false);
//					out.println(Protocol.TIMEOUT_OK + "//" + user.getNick());
//					out.flush();
//				}
//				*/
//
//				else if(line[0].compareTo(Protocol.GAMEOUT) == 0) { // 남은 기회가 0이 되면 request
//					user.setAlive(false);
//					game.addDeadUser(user.getNick());
//					out.println(Protocol.GAMEOUT_OK + "//" + user.getNick());
//					out.flush();
//
//					if(allUserList.size() == game.getDeadUserNum() - 1)
//					{
//						for(int i = 0; i < allUserList.size(); i++) {
//							allUserList.get(i).out.println(
//									Protocol.GAMEEND + "//게임이 종료되었습니다.//" + game.getDeadUserNick()
//								);
//							allUserList.get(i).out.flush();
//						}
//					}
//				}
//
//				else if(line[0].compareTo(Protocol.EXITPROGRAM) == 0) {
//					out.println(Protocol.EXITPROGRAM_OK + "//" + "MSG");
//					out.flush();
//					break;
//				}
//			}
//
//			in.close();
//			out.close();
//			conSoc.close();
//		}
//		catch(IOException e) {
//			e.printStackTrace();
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
}
