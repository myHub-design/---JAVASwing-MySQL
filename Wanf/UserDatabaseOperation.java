package Wanf;
import java.sql.*;

//用户表
public class UserDatabaseOperation {
    //用户的注册
    public static void registerUserInformation(User user) {
        Connection con = Utiljdbk.getConnection();
        String sql = "insert into users(user_id,username,password,age,gender) values(?,?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,user.getUser_id());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getAge());
            ps.setString(5, user.getGender());
            ps.executeUpdate();
            //用户注册后就初始化用户的关卡等信息
            UserProgressDatabase.setPrimaryUserProgressData(user);
            Utiljdbk.close(con, ps, null);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    //用户的注销
    public static void deleteUserInformation(User user) {
        //对应的用户进度也删除
        UserProgressDatabase.deleteUserProgressData_user(user);
        Connection con = Utiljdbk.getConnection();
        String sql = "delete from users where user_id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,user.getUser_id());
            ps.executeUpdate();
            Utiljdbk.close(con, ps, null);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    //用户信息的读取(点击登录就读取)
    public static User getUserInformation(int user_id) {
        Connection con = Utiljdbk.getConnection();
        try {
            Statement stmt = con.createStatement();
            String sql = "select * from users where user_id=" + user_id;
            ResultSet rs = stmt.executeQuery(sql);
            User user = new User();
            if (rs.next()) {
                user.setUser_id(user_id);
                user.setName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setAge(rs.getInt("age"));
                user.setGender(rs.getString("gender"));
                Utiljdbk.close(con, stmt, rs);
                return user;
            }
            return null;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    //用户登录的判断
    public static boolean TfLoginJudgment(int user_id,String password) {
        Connection con = Utiljdbk.getConnection();
        String sql = "select * from users WHERE user_id = "+ user_id +" AND password = " + password;
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (!rs.next()) return false;
            Utiljdbk.close(con, stmt, rs);
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
