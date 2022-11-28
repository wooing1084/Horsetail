package horsetailserver.MultiroomTest;

import Util.Protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientReader extends Thread {
    Socket socket = null;
    InputStreamReader inputStream = null;
    BufferedReader reader = null;
    TestClient client = null;
    public ClientReader(Socket _socket, TestClient _client){
        socket = _socket;
        try {
            inputStream = new InputStreamReader(socket.getInputStream());
            reader = new BufferedReader(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        client = _client;
    }

    public void run(){
        while(true){
            String res = "";
            try {
                res = reader.readLine();

                if(res == null)
                    continue;
                if(res.compareTo("") == 0)
                    continue;




                String[] ress = res.split("//");

                if(ress[0].compareTo(Protocol.NOWTIME) != 0)
                    System.out.println(res);
                else
                {
                    if(Integer.parseInt(ress[1]) >= 20)
                        System.out.println(res);
                }

                if(ress[0].compareTo("151") == 0 || ress[0].compareTo("161") == 0) {
                    client.roomID = ress[1];
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
