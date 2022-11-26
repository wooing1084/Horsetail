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
	
	//연결된 클라이언트 리스트(로그인하지 않았을 수도 있는 상태)
	private static ArrayList<RecvThread> connectionList;
	//로그인 유저 정보를 위한 리스트
	private static ArrayList<User> loginedUsers;

	public static void RemoveUser(RecvThread user){

		user.Close();
		loginedUsers.remove((user.GetUser()));
		connectionList.remove(user);
	}

	public static void AddUser(User user){
		loginedUsers.add(user);
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
		SQLMethods.Init();
		
		try {
			welSoc = new ServerSocket(37101);
			System.out.println("Server is online IP:" +getServerIp());
			
			connectionList = new ArrayList<RecvThread>();
			loginedUsers = new ArrayList<User>();
			
			while(true)
			{
				Socket conSoc = welSoc.accept();
				
				RecvThread thread = new RecvThread(conSoc);
				thread.start();
				connectionList.add(thread);
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
