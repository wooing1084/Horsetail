/*
 * Name : Server.java
 * Author : 이준형
 * Description : 서버를 실행하는 메인 클래스
 */

package horsetailserver;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
	
	private ServerSocket welSoc = null;
	
	private static ArrayList<RecvThread> allUserList;

	public static void RemoveUser(RecvThread user){
		user.Close();
		allUserList.remove(user);
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

	public Server() {
		RoomManager.Init();
		
		try {
			welSoc = new ServerSocket(37101);
			System.out.println("Server is online IP:" +getServerIp());
			
			allUserList = new ArrayList<RecvThread>();
			
			while(true)
			{
				Socket conSoc = welSoc.accept();
				
				RecvThread thread = new RecvThread(conSoc);
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
