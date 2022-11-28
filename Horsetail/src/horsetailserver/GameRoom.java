/*
 * Name : Game.java
 * Author : 이준형
 * Description : 서버 레벨에서 게임을 관리하는 클래스
 */

package horsetailserver;
import Util.Protocol;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameRoom {
	private String _roomID = null;
	private RecvThread _owner = null;
	private String _roomName = null;
	private ArrayList<RecvThread> _userList;
	private int startTime;

	private Game game;

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

	//0 : 시작 실패
	//1 : 게임 시작
	//-1: 이미 게임중
	public int GameStart(RecvThread u){
		//방장이 아님
		if(u.GetUser().getId().compareTo(_owner.GetUser().getId()) != 0)
		{
			u.SendMessage(Protocol.STARTGAME_NO);
			return 0;
		}

		//게임이 이미 진행중임
		if(game != null)
			return -1;
		
		//방 인원이 너무 적음
		if(isTooSmallUser() == true) {
			u.SendMessage(Protocol.TOOSMALLUSER);
			return 0;
		}

		startTime = 3;
		BroadCast(Protocol.GAMEREADY);
		Runnable r = new Runnable() {
			@Override
			public void run() {
				BroadCast(Protocol.NOWTIME + "//" + startTime);
				startTime--;
			}
		};

		ScheduledExecutorService timerService = Executors.newSingleThreadScheduledExecutor();
		timerService.scheduleAtFixedRate(r, 0, 1, TimeUnit.SECONDS);

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		timerService.shutdown();
		while(startTime > 1){

		}


		//스레드 생성 및 start후 게임시작 알림 broadcast
		game = new Game(this);
		Thread thread = game;
		thread.start();
		BroadCast(Protocol.STARTGAME_OK);

		return 1;
	}

	public void EnterWordToGame(String w, RecvThread u){
		if(game == null)
			return;

		game.EnterWord(w, u);
	}

	public void EndGame(){
		game = null;
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
	
	public boolean isTooSmallUser() {
		if(_userList.size() <= 1) {
			return true;
		}
		return false;
	}
	

	

	

}
