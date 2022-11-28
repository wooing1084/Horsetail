package horsetailserver;

import Util.Protocol;

import java.sql.*;

public class SQLMethods {
    public static Connection con;

    public static void Init() {
        Connection connection =  null;
        connection =  null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost/HorseTail";


            String user = "root", passwd = "dltkdtjs01";

            connection = DriverManager.getConnection(url, user, passwd);
        }catch (ClassNotFoundException e){
            e.printStackTrace();

        }
        catch (SQLException e){
            e.printStackTrace();
        }

        con = connection;
    }

    public static Connection GetCon()
    {
        return con;
    }

    public static ResultSet ExecuteQuery(String q1){
        ResultSet result = null;
        try{
            Statement stmt = con.createStatement();
            result = stmt.executeQuery(q1);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return result;
        }
    }

    public static int ExecuteUpdate(String q1){
        int result = 0;
        try{
            Statement stmt = con.createStatement();
            result = stmt.executeUpdate(q1);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return result;
    }


    //0: Failed
    //1:Success
    //-1:error
    public static int SignUp(String id, String pw){
        String q1 = "insert into user values(\"" + id + "\", \""  + pw + "\", NULL, 1000, 0, 0);";
        return ExecuteUpdate(q1);
    }

    public static User LogIn(String id, String pw){
        String q1 = "select * from user where id = \"" + id + "\" and pw = \"" + pw + "\";";
        ResultSet rs = ExecuteQuery(q1);
        User user = null;

        try {
            if(!rs.next()){
            	return null;
            }
            else {
            	user = new User();
            	user.setId(rs.getString(1));
                user.setPw(rs.getString(2));
                user.setNick(rs.getString(3));
                user.setRating(rs.getInt(4));
                user.setWins(rs.getInt(5));
                user.setLoses(rs.getInt(6));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public static String getRankingID() {
    	String ranking = "";

    	String q1 = "select id from user order by rating desc";
        ResultSet rs = ExecuteQuery(q1);

    	try {
    		int cnt = 0;
    		while(rs.next() && cnt < 10) {
                if(cnt == 0)
                    ranking = Protocol.RANKING_OK + "//";
    			String temp = rs.getString(1) + "%";
    			ranking += temp;
    			cnt++;
    		}
    	}
    	catch(SQLException e) {
    		e.printStackTrace();
            ranking = Protocol.RANKING_NO;
    	}

    	return ranking;
    }

    public static String GetStat(String u_id){
        String result = "";

        String q1 = "select id, name, rating, wins, loss from user where id = \"" + u_id + "\";";
        ResultSet rs = ExecuteQuery(q1);

        try {
            if(rs.next()){
                result = Protocol.STAT_OK + "//" + rs.getString(1) + "%"+ rs.getString(2) +
                        "%" + rs.getString(3) + "%" + rs.getString(4) + "%" + rs.getString(5);
            }
            else{
                result = Protocol.STAT_NO;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result = Protocol.STAT_NO;
        }

        return result;

    }
}
