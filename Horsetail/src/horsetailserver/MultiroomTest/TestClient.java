package horsetailserver.MultiroomTest;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

//클라이언트 I/O를 동시에 진행할 수 있도록 스레드 처리하면 됨

public class TestClient {
    static String IP = "192.168.131.1";
    static int PORT = 37101;

    static String roomID;


    public static void main(String[] args){
        Socket socket = null;


        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(IP, PORT));

            InputStreamReader inputStream = new InputStreamReader(socket.getInputStream());
            OutputStreamWriter outputStream = new OutputStreamWriter(socket.getOutputStream());

            BufferedReader reader = new BufferedReader(inputStream);
            PrintWriter writer = new PrintWriter(outputStream);

            Scanner scanner = new Scanner(System.in);

            while(true){
                System.out.println("Enter a request:");
                String req = scanner.nextLine();

                writer.println(req);
                writer.flush();

                String res = reader.readLine();
                System.out.println(res);

                String[] ress = res.split("/");

                if(ress[0].compareTo("151") == 0 || ress[0].compareTo("161") == 0) {
                    roomID = ress[1];
                }

            }



        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
