package horsetailserver.MultiroomTest;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

//클라이언트 I/O를 동시에 진행할 수 있도록 스레드 처리하면 됨

public class TestClient {
    static String IP = "192.168.131.1";
    static int PORT = 37101;

    static String roomID = "";

    public TestClient(){
        Socket socket = null;

        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(IP, PORT));

            OutputStreamWriter outputStream = new OutputStreamWriter(socket.getOutputStream());

            PrintWriter writer = new PrintWriter(outputStream);

            Scanner scanner = new Scanner(System.in);
            Thread reader = new ClientReader(socket,this);
            reader.start();

            while(true){
                System.out.println("Enter a request:");
                String req = scanner.nextLine();

                writer.println(req);
                writer.flush();
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        TestClient t = new TestClient();
    }
}

