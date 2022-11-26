package horsetailserver;

import horsetailserver.User;

import java.util.ArrayList;
import java.util.List;

public class RoomManager {
    static List<GameRoom> roomList;

    public static void Init(){
        roomList = new ArrayList<GameRoom>();
    }

    public static GameRoom CreateRoom(){
        GameRoom room = new GameRoom();
        roomList.add(room);
        System.out.println("Room Created!");
        return room;
    }

    public static GameRoom CreateRoom(RecvThread owner){
        GameRoom room = new GameRoom(owner);
        room.SetRoomID("room_" + roomList.size());
        room.SetRoomName("Room");
        roomList.add(room);
        System.out.println("Room Created!");
        return room;
    }

    public static void RemoveRoom(GameRoom room){
        roomList.remove(room);
        System.out.println("Room Deleted!");
    }

    public static void JoinRoom(String roomID, RecvThread user){
        GetRoomList().get(GetRoomIdx(roomID)).EnterRoom(user);
    }

    public static List<GameRoom> GetRoomList(){
        return roomList;
    }

    //return -1: cannot find
    //return 0~ : room index
    public static int GetRoomIdx(String roomID){
        for(int i =0;i< roomList.size();i++){
            if(roomList.get(i).GetRoomID().compareTo(roomID) == 0)
                return i;
        }

        return -1;
    }


}
