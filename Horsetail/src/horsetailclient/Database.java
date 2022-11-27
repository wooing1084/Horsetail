package horsetailclient;

import java.awt.Toolkit;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Database {
	Connection con = null;
	Statement stmt = null;
	String url = "jdbc:mysql://localhost/dbteamproject?serverTimezone=Asia/Seoul"; // dbstudy 스키마
	String user = "root";
	String passwd = "6412"; // 본인이 설정한 root 계정의 비밀번호를 입력하면 된다.

	Database() { // 데이터베이스에 연결한다.
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, passwd);
			stmt = con.createStatement();
			System.out.println("MySQL 서버 연동 성공");
		} catch (Exception e) {
			System.out.println("MySQL 서버 연동 실패 > " + e.toString());
		}
	}

	/* 로그인 정보를 확인 */
	boolean logincheck(String _i, String _p) {
		boolean flag = false;

		String id = _i;
		String pw = _p;

		try {
			String use = "USE net;";
			stmt.execute(use);

			String checkingStr = "SELECT password FROM userTable WHERE id='" + id + "'";
			ResultSet result = stmt.executeQuery(checkingStr);

			int count = 0;
			while (result.next()) {
				if (pw.equals(result.getString("password"))) {
					flag = true;
					System.out.println("로그인 성공");
				}

				else {
					flag = false;
					System.out.println("로그인 실패");
				}
				count++;
			}
		} catch (Exception e) {
			flag = false;
			System.out.println("로그인 실패 > " + e.toString());
		}

		return flag;
	}

	/* 회원가입 시에 id가 중복인지 아닌지 체크 */
	boolean joinCheck(String _i, String _p) {
		boolean flag = true;

		String id = _i;
		String pw = _p;

		try {
			String use = "USE net;";
			stmt.execute(use);

			String checkingStr = "SELECT id FROM userTable WHERE id='" + id + "'";
			ResultSet result = stmt.executeQuery(checkingStr);

			while (result.next()) {
				if (id.equals(result.getString("id"))) {
					flag = false;
					JOptionPane.showMessageDialog(null, "회원가입에 실패하였습니다\n아이디 중복입니다.");
					break;
				}
			}

			if (flag == false) {
				return flag;
			} else {
				String insertStr = "INSERT INTO userTable VALUES('" + id + "', '" + pw + "')";
				stmt.executeUpdate(insertStr);
				System.out.println("회원가입 성공");
			}

		} catch (SQLException e) {
			flag = false;
			System.out.println("회원가입 실패 > " + e.toString());
		}
		return flag;
	}

	ArrayList<String> infoRoom() { // 방 정보 가져오기

		ArrayList<String> str = new ArrayList();
		int i = 0;
		try {
			String use = "USE net;";
			stmt.execute(use);

			String query = "SELECT * FROM room;";
			ResultSet result = stmt.executeQuery(query);

			while (result.next()) {
				str.add(result.getString(1) + "                                  " + result.getInt(2));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	// 랭킹 데이터 확인
	ArrayList<String> checkScore() {
		ArrayList<String> str = new ArrayList();
		try {
			String use = "USE net;";
			stmt.execute(use);

			String query = "SELECT id, score FROM userTable ORDER BY score DESC";
			ResultSet result = stmt.executeQuery(query);

			int i = 1;
			while (result.next()) {
				if (i == 1 || i==2 || i==3) {
					str.add("                                           "+i + "등:          " + result.getString(1) + "          " + result.getString(2));
					i++;
				} else {
					str.add("                                                 "+i + "등:          " + result.getString(1) + "          " + result.getString(2));
					i++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(str);
		return str;
	}

	String CheckMyInfo(String _i) { // 내 정보 가져오기

		String str = new String();
		int i = 0;
		try {
			String use = "USE net;";
			stmt.execute(use);

			String query = "SELECT * FROM userTable WHERE id =\"" + _i + "\"";
			ResultSet result = stmt.executeQuery(query);

			while (result.next()) {
				str = (result.getString(1) + "      " + result.getInt(2) + "      " + result.getInt(3));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
}
