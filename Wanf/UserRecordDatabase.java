package Wanf;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


//用户记录类
    public class UserRecordDatabase {
        //根据用户id和关卡id来写入信息
        public static void InsertUserLevelRecord(User user, Level level) {
            Connection con = Utiljdbk.getConnection();
            String sql = "INSERT INTO user_records(user_id,username,num,completed,steps,time) VALUES(?,?,?,?,?,?)";
            try {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1,user.getUser_id());
                ps.setString(2, user.getName());
                ps.setInt(3,level.getNum());
                ps.setInt(4,level.isCompleted() ? 1 : 0);
                ps.setInt(5,level.getSteps());
                ps.setString(6,level.getTime());
                ps.executeUpdate();
                Utiljdbk.close(con, ps, null);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        //根据用户来查询用户记录，主菜单
        public static ArrayList<User_Record> GetUserRecord_User(User user) {
            ArrayList<User_Record> userRecords = new ArrayList<>();
            Connection con = Utiljdbk.getConnection();
            String sql = "SELECT * FROM user_records WHERE user_id=?";
            try {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1,user.getUser_id());
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    Level level1 = new Level();
                    level1.setMap_Id(rs.getInt("num"));
                    level1.setCompleted(rs.getInt("completed") == 1 ? true : false);
                    level1.setSteps(rs.getInt("steps"));
                    level1.setTime(rs.getString("time"));
                    userRecords.add(new User_Record(user,level1));
                }
                Utiljdbk.close(con, ps, rs);
                return userRecords;
            }catch (SQLException e){
                e.printStackTrace();
            }
            return null;
        }
        //根据关卡来查询记录,关卡页面
        public static ArrayList<User_Record> GetUserRecord_Level(Level level) {
            ArrayList<User_Record> userRecords = new ArrayList<>();
            Connection con = Utiljdbk.getConnection();
            String sql = "SELECT * FROM user_records WHERE num=?";
            try {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1,level.getNum());
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    if(rs.getInt("steps")!=0) {
                       User user1 = new User();
                        user1.setUser_id(rs.getInt("user_id"));
                        user1.setName(rs.getString("username"));
                        Level level1 = new Level();
                        int steps=rs.getInt("steps");
                        //level.setNum(level.getNum());
                        level1.setSteps(steps);
                        level1.setTime(rs.getString("time"));
                        userRecords.add(new User_Record(user1,level1));
                    }
                }
                Utiljdbk.close(con, ps, rs);
                return userRecords;
            }catch (SQLException e){
                e.printStackTrace();
            }
            return null;
        }
    }
