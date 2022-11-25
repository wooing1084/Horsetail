package horsetailserver.MultiroomTest;

import horsetailserver.Game;
import horsetailserver.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class TestServer {
    private ServerSocket welSoc = null;

    private ArrayList<TestUser> allUserList;

    public TestServer() {
        RoomManager.Init();
        try {
            welSoc = new ServerSocket(37101);
            System.out.println("Server is online IP: "+ getServerIp());

            allUserList = new ArrayList<TestUser>();

            while(true)
            {
                System.out.println("Wait");
                Socket conSoc = welSoc.accept();
                System.out.println("Connect");

                TestUser thread = new TestUser(conSoc, "ID" + allUserList.size(), "Name_ID" + allUserList.size());
                thread.start();
                allUserList.add(thread);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                welSoc.close();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getServerIp() {

        InetAddress local = null;
        try {
            local = InetAddress.getLocalHost();
        }
        catch ( UnknownHostException e ) {
            e.printStackTrace();
        }

        if( local == null ) {
            return "";
        }
        else {
            String ip = local.getHostAddress();
            return ip;
        }

    }

    public static void main(String[] args) {
        new TestServer();
    }
}
