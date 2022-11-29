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
 * 코드는 Util.Protocol.java에 미리 3자리 숫자를 이용해 정해져 있다.
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
 * 이것은 Util.Protocol, 즉 정해진 규약이므로 클라이언트도 코드를 보고 적절히 파싱하면
 * 프로토콜 기반 네트워크 통신이 되는 것이다.
 * 
 * 예시의 코드의 작동을 더 자세히 보고싶다면,
 * else if(line[0].compareTo(Util.Protocol.SENDMESSAGE) == 0)
 * 을 검색해서 찾아서 보면 된다. (160번째 줄 부근)
 */

package horsetailserver;

import Util.Protocol;

import java.io.*;
import java.net.*;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

class RecvThread extends Thread {
	private Socket _socket;
	private User user;
	private int nowRoomIndex = -1;

	private InputStreamReader inputStream = null;
	private OutputStreamWriter outputStream = null;

	public RecvThread(Socket socket) {
		_socket = socket;
		user = null;

		try {
			inputStream = new InputStreamReader(_socket.getInputStream());
			outputStream = new OutputStreamWriter(_socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public RecvThread(Socket socket, User _user) {
		_socket = socket;
		user = _user;
		try {
			inputStream = new InputStreamReader(_socket.getInputStream());
			outputStream = new OutputStreamWriter(_socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void SendMessage(String msg) {
		PrintWriter writer = new PrintWriter(outputStream);
		writer.println(msg);
		writer.flush();
	}

	//0 : fail
	//1 : success
	public int Close() {
		try {
			if (nowRoomIndex != -1)
				RoomManager.GetRoomList().get(nowRoomIndex).ExitRoom(this);

			inputStream.close();
			outputStream.close();
			_socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return 1;

	}

	public void run() {
		BufferedReader reader = new BufferedReader(inputStream);

		while (true) {
			if (_socket.isClosed()) {
				break;
			}

			try {
				String req = reader.readLine();

				if (user != null)
					System.out.println(user.getNick() + ":" + req);
				else
					System.out.println("unknown: " + req);

				//메세지 처리함수
				MessageExecutes(req);

			} catch (SocketException e) {
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

	public static String[] DivideRequest(String req) {
		String[] result = req.split("//");

		return result;
	}

	public static String MakeResponse(String tag, ArrayList<String> res) {
		String result = tag;

		if (res.size() != 0) {
			result += "//";

			for (int i = 0; i < res.size(); i++) {
				result += res.get(i);
				if (i < res.size() - 1)
					result += "%";
			}
		}

		return result;
	}

	private void MessageExecutes(String req) {
		String[] reqs = DivideRequest(req);

		//회원가입
		if (reqs[0].compareTo(Protocol.REGISTER) == 0) {
			String[] args = reqs[1].split("%");

			int result = SQLMethods.SignUp(args[0], args[1]);

			if (result == 1)
				SendMessage(Protocol.REGISTER_OK);
			else
				SendMessage(Protocol.REGISTER_NO);

		}
		//ID중복 요청
		if (reqs[0].compareTo(Protocol.IDVALIDCHECK) == 0) {
			String q1 = "select id from user where id = \"" + reqs[1] + "\";";
			ResultSet rs = SQLMethods.ExecuteQuery(q1);

			try {
				if (rs.next()) {
					if (rs.getString(1).compareTo("") == 0)
						SendMessage(Protocol.IDVALIDCHECK_OK);
					else
						SendMessage(Protocol.IDVALIDCHECK_NO);
				} else
					SendMessage(Protocol.IDVALIDCHECK_OK);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		//로그인
		else if (reqs[0].compareTo(Protocol.LOGIN) == 0) {
			String[] args = reqs[1].split("%");

			User u = SQLMethods.LogIn(args[0], args[1]);
			user = u;
			Server.AddUser(user);

			if (user == null) {
				System.out.println(Protocol.LOGIN_NO);
				SendMessage(Protocol.LOGIN_NO);
			} else {
				System.out.println(Protocol.LOGIN_OK + "//" + user.toString());
				SendMessage(Protocol.LOGIN_OK + "//" + user.toString());
			}
		}
		//방 정보 요청
		else if (reqs[0].compareTo(Protocol.ROOMS) == 0) {
			if (RoomManager.GetRoomList().size() == 0)
				SendMessage(Protocol.ROOMS_NO);
			else {
				ArrayList<GameRoom> gameRooms = RoomManager.GetRoomList();
				String res = Protocol.ROOMS_OK + "//";

				for (int i = 0; i < gameRooms.size(); i++) {
					GameRoom gr = gameRooms.get(i);
					res += gr.GetRoomID() + "%";
				}

				res += "//";

				for (int i = 0; i < gameRooms.size(); i++) {
					GameRoom gr = gameRooms.get(i);
					res += gr.GetRoomName() + "%";
				}

				SendMessage(res);
			}
		}
		//방 생성
		else if (reqs[0].compareTo(Protocol.ROOMCREATE) == 0) {
			GameRoom gr = RoomManager.CreateRoom(this);

			if (gr == null) {
				SendMessage(Protocol.ROOMCREATE_NO);
				return;
			}
			gr.SetRoomName(reqs[1]);
			SendMessage(Protocol.ROOMCREATE_OK + "//" + gr.GetRoomID());
		}
		//방 참가
		else if (reqs[0].compareTo(Protocol.JOINROOM) == 0) {
			int r = RoomManager.JoinRoom(reqs[1], this);

			if (r == -1)
				SendMessage(Protocol.ROOMFULL);
			else if (r == 0)
				SendMessage(Protocol.JOINROOM_NO);
			else
				SendMessage(Protocol.JOINROOM_OK + "//" + reqs[1]);
		}
		//방 유저 목록 요청
		else if (reqs[0].compareTo(Protocol.ROOMUSERS) == 0) {
			if (nowRoomIndex == -1) {
				SendMessage(Protocol.ROOMUSERS_NAK);
				return;
			}

			if (RoomManager.GetRoomList().get(nowRoomIndex).GetUserList().size() == 0) {
				SendMessage(Protocol.ROOMUSERS_NAK);
				return;
			}

			ArrayList<RecvThread> users = RoomManager.GetRoomList().get(nowRoomIndex).GetUserList();
			String res = Protocol.ROOMUSERS_OK;
			for (int i = 0; i < users.size(); i++) {
				res += "//" + users.get(i).GetUser().toStringWithoutPassword();
			}

			SendMessage(res);
		}
		//방 나가기
		else if (reqs[0].compareTo(Protocol.ROOMEXIT) == 0) {
			if (nowRoomIndex == -1) {
				SendMessage(Protocol.ROOMEXIT_NO);
				return;
			}

			RoomManager.GetRoomList().get(nowRoomIndex).ExitRoom(this);
			SendMessage(Protocol.ROOMEXIT_OK);
		} else if (reqs[0].compareTo(Protocol.STAT) == 0) {
			String res = SQLMethods.GetStat(user.getId());
			SendMessage(res);
		} else if (reqs[0].compareTo(Protocol.RANKING) == 0) {
			String ranking = SQLMethods.getRankingID();
			SendMessage(ranking);
		}
		//게임시작 구현

		else if (reqs[0].compareTo(Protocol.STARTGAME) == 0) {
			GameRoom gr = RoomManager.GetRoomList().get(nowRoomIndex);
			gr.GameStart(this);
		}

		//--------

		//메세지 보내기
		else if (reqs[0].compareTo(Protocol.SENDMESSAGE) == 0) {
			int idx = RoomManager.GetRoomIdx(reqs[1]);
			GameRoom gr = RoomManager.GetRoomList().get(idx);
			gr.BroadCast(Protocol.SENDMESSAGE_OK + "//" + user.getId() + "//" + reqs[2]);
		} else if (reqs[0].compareTo(Protocol.SENDWORD) == 0) {
			GameRoom gr = RoomManager.GetRoomList().get(nowRoomIndex);
			//GameRoom의 Game에 단어전송함수 호출
			gr.EnterWordToGame(reqs[1], this);
		}
		//프로그램종료
		else if (reqs[0].compareTo(Protocol.EXITPROGRAM) == 0) {
			SendMessage(Protocol.EXITPROGRAM_OK);

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			int r = Server.RemoveUser(this);




		} else {
			SendMessage(Protocol.INVALIDTAG);
		}

	}

	//getter
	public User GetUser() {
		return user;
	}

	public int GetRoomIndex() {
		return nowRoomIndex;
	}

	//setter
	public void SetUser(User _user) {
		user = _user;
	}

	public void SetRoomIndex(int idx) {
		nowRoomIndex = idx;
	}
}
