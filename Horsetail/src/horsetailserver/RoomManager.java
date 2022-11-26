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

    //-1 : cannot found
    //0: full
    //1: success
    public static int JoinRoom(String roomID, RecvThread user){
        GameRoom gr = GetRoomList().get(GetRoomIdx(roomID));
        if(gr == null)
            return -1;

        int r = gr.EnterRoom(user);

        return r;
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
