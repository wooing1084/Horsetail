package horsetailserver;

import java.sql.*;

public class SQLMethods {
    public static Connection con;

    public static void Init() {
        Connection connection =  null;
        connection =  null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost/horsetail";
            String user = "root", passwd = "dong1084@";
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
        }
        return result;
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
    public static int SignUp(String id, String pw, String name){
        String q1 = "insert into user values(\"" + id + "\", \""  + pw + "\", \"" + name+ "\", 1000, 0, 0);";
        return ExecuteUpdate(q1);
    }

    public static User LogIn(String id, String pw){
        String q1 = "search * from user where id = \"" + id + "\" and pw = \"" + pw + "\";";
        ResultSet rs = ExecuteQuery(q1);

        User user = new User();
        try {
            if(rs.next()){
                user.setId(rs.getString(0));
                user.setPw(rs.getString(1));
                user.setNick(rs.getString(2));
                user.setRating(rs.getInt(3));
                user.setWins(rs.getInt(4));
                user.setLoses(rs.getInt(5));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

}
