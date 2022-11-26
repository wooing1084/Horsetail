package horsetailserver.MultiroomTest;

import horsetailserver.User;

import java.util.ArrayList;
import java.util.List;

public class GameRoom {
    private String _roomID;
    private TestUser _owner;
    private String _roomName;
    private List<TestUser> _userList;

    public GameRoom(){
        _userList = new ArrayList<TestUser>();
    }

    public GameRoom(TestUser owner){
        _owner = owner;
        _userList = new ArrayList<TestUser>();
        _userList.add(owner);
    }

    public void EnterRoom(TestUser user){
        _userList.add(user);
    }

    public void ExitRoom(TestUser user){
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
    public  TestUser GetOWner(){
        return _owner;
    }
    public String GetRoomName(){
        return _roomName;
    }
    public List<TestUser> GetUserList(){
        return _userList;
    }

    //Setter
    public void SetRoomID(String id){
        _roomID = id;
    }
    public void SetRoomOwner(TestUser user){
        _owner = user;
    }
    public void SetRoomName(String name){
        _roomName = name;
    }



}
