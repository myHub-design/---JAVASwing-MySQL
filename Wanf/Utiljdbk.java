package Wanf;
import java.sql.*;
public class Utiljdbk {
    //获取连接对象
    public static Connection getConnection(){
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/data_1", "root", "123456");
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        return conn;
    }
    //关闭资源
    public static void close(Connection conn, Statement stmt, ResultSet rs){
        try{
            if(rs != null){
                rs.close();
            }
            if(stmt != null){
                stmt.close();
            }
            if(conn != null){
                conn.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static int insertDelAndUpdate(String sql,Object [] obj){
        Connection conn = getConnection();
        PreparedStatement ps = null;
        int i = 0;
        try{
            ps = conn.prepareStatement(sql);
            for (int j = 0; j < obj.length; j++) {
                ps.setObject(j + 1,obj[j]);
            }
            i = ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        close(conn, ps, null);
        return i;
    }
}
