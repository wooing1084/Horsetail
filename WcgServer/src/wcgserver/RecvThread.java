/*
 * Name : RecvThread.java
 * Author : 이준형
 * Description : 클라이언트가 접속하면 하나씩 개별로 실행되는 스레드
 */

package wcgserver;

import java.io.*;
import java.net.*;
import java.util.*;

class RecvThread extends Thread {
	
	private static final int MAX_USERS = 4;
	
	private Socket conSoc = null;
	
	private User user = null;
	private Game game = null;
	
	private BufferedReader in = null;
	private PrintWriter out = null;
	
	private ArrayList<RecvThread> allUserList;
	
	public RecvThread(Socket s, ArrayList<RecvThread> ul, Game g) throws Exception {
		this.conSoc = s;
		this.allUserList = ul;
		this.user = new User();
		this.game = g;
		
		in = new BufferedReader(new InputStreamReader(conSoc.getInputStream()));
		out = new PrintWriter(new OutputStreamWriter(conSoc.getOutputStream()));
	}
	
	public void run() {
		try {
			
			String[] line = null;
			
			while(true) {
				
				line = in.readLine().split("//");
				
				if(line == null) {
					break;
				}
				
				else if(line[0].compareTo(Protocol.REGISTER) == 0) {
					String contents[] = line[1].split("%");
					
					// SQL 회원가입 코드 추가
				}
				
				else if(line[0].compareTo(Protocol.IDVALIDCHECK) == 0) {
					// SQL 회원가입 시 닉네임 중복체크 코드 추가
					
					int count = 0;
					
					// count == SQL select 문으로 가져온 개수
					
					if(count == 0) {
						out.println(Protocol.IDVALIDCHECK_OK + "//" + "MSG");
						out.flush();
					}
					else {
						out.println(Protocol.IDVALIDCHECK_NO + "//" + "MSG");
						out.flush();
					}
				}
				
				else if(line[0].compareTo(Protocol.LOGIN) == 0) {
					String content[] = line[1].split("%");
					
					// SQL 로그인 시 ID 및 닉네임 맞는지 검사하는 코드 추가
					
					int count = 0;
					
					// count == SQL select 문으로 가져온 개수
					
					if(count == 0) { // 로그인 실패
						out.println(Protocol.LOGIN_NO + "//" + "MSG");
						out.flush();
						
						user.initialize();
					}
					
					else if(allUserList.size() >= MAX_USERS) { // 싱글룸 정원 초과
						out.println(Protocol.ROOMFULL + "//" + "MSG");
						out.flush();
					}
					
					else { // 로그인 성공
						allUserList.add(this);
						
						String userList = "";
						for(int i = 0; i < allUserList.size(); i++) {
							userList += allUserList.get(i).user.getNick() + "%";
						}
						
						for(int i = 0; i < allUserList.size(); i++) {
							allUserList.get(i).out.println(
								Protocol.LOGIN_OK + "//" + user.getNick() + "//님이 입장했습니다.//" + userList
							);
							allUserList.get(i).out.flush();
						}
					}
				}
				
				else if(line[0].compareTo(Protocol.STARTGAME) == 0) {
					
					if(allUserList.size() <= 1) {
						out.println(Protocol.TOOSMALLUSER + "//" + "MSG");
						out.flush();
					}
					
					else {
						allUserList.get(0).user.setTurn(true);
						for(int i = 0; i < allUserList.size(); i++) {
							allUserList.get(i).out.println(
									Protocol.STARTGAME_OK + "//" + "MSG"
							);
							allUserList.get(i).out.flush();
						}
					}
				}
				
				else if(line[0].compareTo(Protocol.SENDMESSAGE) == 0) {
					for(int i = 0; i < allUserList.size(); i++) {
						allUserList.get(i).out.println(
							Protocol.SENDMESSAGE_OK + "//" + user.getNick() + "//" + line[1]
						);
						allUserList.get(i).out.flush();
					}
				}
				
				else if(line[0].compareTo(Protocol.STAT) == 0) {
					
				}
				
				else if(line[0].compareTo(Protocol.RANKING) == 0) {
	
				}
				
				else if(line[0].compareTo(Protocol.SENDWORD) == 0) {
					
					if(game.isOneChar(line[1])) {
						out.println(Protocol.WORDONLYONECHAR + "//" + "MSG");
						out.flush();
					}
					
					else if(game.isNotChain(line[1])) {
						out.println(Protocol.WORDNOTCHAIN + "//" + "MSG");
						out.flush();
					}
					
					else if(game.isOverlap(line[1])) {
						out.println(Protocol.WORDOVERLAP + "//" + "MSG");
						out.flush();
					}
					
					else if(game.isNotExist(line[1])) {
						out.println(Protocol.WORDNOTEXIST + "//" + "MSG");
						out.flush();
					}
					
					else {
						game.addWord(line[1]);
						out.println(Protocol.SENDWORD_OK + "//" + user.getNick());
						out.flush();
						
						int idx = allUserList.indexOf((Object)user.getNick());
						int i = 1;
						while(!allUserList.get((idx + i) % allUserList.size()).user.getAlive()) {
							i++;
						}
						allUserList.get((idx + i) % allUserList.size()).user.setTurn(true);
						this.user.setTurn(false);
						
						out.println(Protocol.YOURTURN + "//" + allUserList.get((idx + i) % allUserList.size()).user.getNick());
						out.flush();
					}
				}
				
				else if(line[0].compareTo(Protocol.SENDDEF) == 0) {
					String def = game.getDefinition();
					out.println(Protocol.SENDDEF_OK + "//" + def);
					out.flush();
				}
				
				/*
				// 아래의 GAMEOUT과 통합할까 고민 중
				else if(line[0].compareTo(Protocol.TIMEOUT) == 0) { // 타이머가 0이 되면 request
					user.setAlive(false);
					out.println(Protocol.TIMEOUT_OK + "//" + user.getNick());
					out.flush();
				}
				*/
				
				else if(line[0].compareTo(Protocol.GAMEOUT) == 0) { // 남은 기회가 0이 되면 request
					user.setAlive(false);
					game.addDeadUser(user.getNick());
					out.println(Protocol.GAMEOUT_OK + "//" + user.getNick());
					out.flush();
					
					if(allUserList.size() == game.getDeadUserNum() - 1)
					{
						for(int i = 0; i < allUserList.size(); i++) {
							allUserList.get(i).out.println(
									Protocol.GAMEEND + "//게임이 종료되었습니다.//" + game.getDeadUserNick()
								);
							allUserList.get(i).out.flush();
						}
					}
				}
				
				else if(line[0].compareTo(Protocol.EXITPROGRAM) == 0) {
					out.println(Protocol.EXITPROGRAM_OK + "//" + "MSG");
					out.flush();
					break;
				}
			}

			in.close();
			out.close();
			conSoc.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
