/*
 * Name : Game.java
 * Author : 이준형
 * Description : 서버 레벨에서 게임을 관리하는 클래스
 */

package horsetailserver;
import Util.Protocol;

import java.util.*;

public class GameRoom {
	private String _roomID;
	private RecvThread _owner;
	private String _roomName;
	private ArrayList<RecvThread> _userList;
	private ArrayList<String> words;
	private ArrayList<String> deadUsers;

	public GameRoom(){
		_userList = new ArrayList<RecvThread>();
	}

	public GameRoom(RecvThread owner){
		_owner = owner;
		_userList = new ArrayList<RecvThread>();
		_userList.add(owner);
	}

	//0: full
	//1: success
	public int EnterRoom(RecvThread user){
		_userList.add(user);

		BroadCast(Protocol.JOINED_USER + "//" + user.GetUser().toStringWithoutPassword());

		return 1;
	}

	public void ExitRoom(RecvThread user){
		user.SetRoomIndex(-1);
		_userList.remove(user);

		if(_userList.size() <= 0){
			//방 지우기
			RoomManager.RemoveRoom(this);

		}
		if(_userList.size() == 1){
			this._owner = _userList.get(0);

		}
	}

	public void BroadCast(String msg){
		for(int i =0;i<_userList.size();i++)
		{
			_userList.get(i).SendMessage(msg);
		}
	}


	//Getter
	public String GetRoomID(){
		return _roomID;
	}
	public  RecvThread GetOWner(){
		return _owner;
	}
	public String GetRoomName(){
		return _roomName;
	}
	public ArrayList<RecvThread> GetUserList(){
		return _userList;
	}

	//Setter
	public void SetRoomID(String id){
		_roomID = id;
	}
	public void SetRoomOwner(RecvThread user){
		_owner = user;
	}
	public void SetRoomName(String name){
		_roomName = name;
	}
	
	public boolean isOneChar(String w) {
		if(w.length() == 1) {
			return true;
		}
		
		return false;
	}
	
	public boolean isNotChain(String w) {
		int lastIdx = words.size() - 1;
		String lastWord = words.get(lastIdx);
		
		char lastWordChar = lastWord.charAt(lastWord.length() - 1);
		char newWordChar = w.charAt(w.length() - 1);
		
		if(!(lastWordChar == newWordChar)) {
			return true;
		}
		
		return false;
	}
	
	public boolean isOverlap(String w) {
		if(words.contains(w)) {
			return true;
		}
		
		return false;
	}
	
	public boolean isNotExist(String w) {
		// API와 연동해서 단어 체크 코드 추가
		// 단어가 있으면 false, 없으면 true 리턴
		
		return false;
	}
	
	public void addWord(String w) {
		words.add(w);
	}
	
	public String getDefinition() {
		String def = null;
		
		// API와 연동해서 단어 뜻 가져오는 코드 추가
		
		return def;
	}
	
	public void addDeadUser(String nick) {
		deadUsers.add(nick);
	}
	
	public int getDeadUserNum() {
		return deadUsers.size();
	}
	
	public String getDeadUserNick() {
		String dead = "";
		for(int i = 0; i < deadUsers.size(); i++) {
			dead += deadUsers.get(i) + "%";
		}
		
		return dead;
	}
	
	public void resetGame()
	{
		words.clear();
		deadUsers.clear();
	}
}
