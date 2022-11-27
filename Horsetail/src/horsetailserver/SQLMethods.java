package horsetailserver;

import java.sql.*;

public class SQLMethods {
    public static Connection con;

    public static void Init() {
        Connection connection =  null;
        connection =  null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost/HorseTail";

            String user = "root", passwd = "6412";

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
    	
    	String q1 = "select id from user order by desc";
        ResultSet rs = ExecuteQuery(q1);
        
    	try {
    		int cnt = 0;
    		while(rs.next() || cnt > 10) {
    			String temp = rs.getString(1) + "%";
    			ranking += temp;
    			cnt++;
    		}
    	}
    	catch(SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return ranking;
    }
}
