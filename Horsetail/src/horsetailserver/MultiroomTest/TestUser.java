package horsetailserver.MultiroomTest;

import horsetailserver.Protocol;

import java.io.*;
import java.net.Socket;

public class TestUser extends Thread{
    private String _userID;
    private String _userName;
    private Socket _socket;

    private InputStreamReader inputStream = null;
    private OutputStreamWriter outputStream = null;

    public TestUser(Socket socket, String userID, String userName){
        _socket = socket;
        _userID = userID;
        _userName = userName;

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

    public void run(){
        BufferedReader reader = new BufferedReader(inputStream);

        //request protocol
        //req num/roomID/msg
        while(true){
            System.out.println(_userID+ "cycle:");
            try {
                String req = reader.readLine();
                System.out.println(_userID + ":" + req);

                String[] reqs = req.split("/");

                if(reqs[0].compareTo("150") == 0){
                    GameRoom gr = RoomManager.CreateRom(this);
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


            } catch (IOException e) {
               e.printStackTrace();
            }


        }


    }



}
