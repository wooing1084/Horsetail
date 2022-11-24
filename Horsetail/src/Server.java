/*
 * Name : Server.java
 * Author : 이준형
 * Description : 서버를 실행하는 메인 클래스
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
	
	private ServerSocket welSoc = null;
	
	private ArrayList<RecvThread> allUserList;
	
	public Server() {
		Game g = new Game();
		
		try {
			welSoc = new ServerSocket(37101);
			System.out.println("Server is online");
			
			allUserList = new ArrayList<RecvThread>();
			
			while(true)
			{
				Socket conSoc = welSoc.accept();
				
				RecvThread thread = new RecvThread(conSoc, allUserList, g);
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
	
	public static void main(String[] args) {
		new Server();
	}
}
