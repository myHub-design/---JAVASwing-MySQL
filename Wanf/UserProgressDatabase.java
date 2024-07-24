package Wanf;
import java.sql.*;
import java.util.ArrayList;

//用户进度表的写入与查询
public class UserProgressDatabase {

    //初始化玩家界面
    public static void setPrimaryUserProgressData(User user) {
        Connection con = Utiljdbk.getConnection();
        String insertProgressQuery = "INSERT INTO user_progress (user_id, num, completed, steps,state) VALUES (?, ?, ?, ?,?)";
        try {
            PreparedStatement ps = null;
            //将系统中所有的关卡信息读取出来
            ArrayList<Level> levels= LevelDatabaseOperation.ReadLevelDatabase();
            for (Level level : levels) {
                ps = con.prepareStatement(insertProgressQuery);
                ps.setInt(1,user.getUser_id());
                ps.setInt(2,level.getNum());
                //初始化的时候初始步数为0，完成状态为否
                ps.setInt(3,0);
                ps.setInt(4,0);
                ps.setInt(5,level.isState()?1:0);
                ps.executeUpdate();
            }
            Utiljdbk.close(con, ps,null);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //玩家点击登录操作后的关卡信息加载（有则读取，无则初始化）
    public static ArrayList<Level> getUserProgressData(User user) {
        ArrayList<Level> levels = new ArrayList<>();
        Connection con = Utiljdbk.getConnection();
        try {
            String selectProgressQuery = "SELECT * FROM user_progress WHERE user_id = " + user.getUser_id();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(selectProgressQuery);
            //如果没有该用户的信息就是注册的用户 就要初始化
            boolean flag = false;
            while (resultSet.next()) {
                flag = true;
                Level level = LevelDatabaseOperation.GetLevelDatabase(resultSet.getInt("num"));
                if(resultSet.getInt("state") == 1) level.setState();
                level.setCompleted(resultSet.getInt("completed") == 1);
                level.setSteps(resultSet.getInt("steps"));
                levels.add(level);
            }
            if(!flag) setPrimaryUserProgressData(user);
            Utiljdbk.close(con,statement,resultSet);
            return levels;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    //玩家每通过或者对关卡进行游戏后的数据保存
    public static void updateUserProgressData(User user,Level level,boolean completed) {
        Connection con = Utiljdbk.getConnection();
        String updateProgressQuery = "UPDATE user_progress SET completed = ?, steps = ?, state = ? WHERE user_id = ? AND num = ?";
        try {
            PreparedStatement ps = con.prepareStatement(updateProgressQuery);
            ps.setInt(1, completed ? 1 : 0);
            ps.setInt(2, level.getSteps());
            ps.setInt(3,level.isState() ? 1 : 0);
            ps.setInt(4, user.getUser_id());
            ps.setInt(5, level.getNum());
            ps.executeUpdate();
            Utiljdbk.close(con,ps,null);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    //用户数据库删除(用户删除)
    public static void deleteUserProgressData_user(User user) {
        Connection con = Utiljdbk.getConnection();
        String deleteProgressQuery = "DELETE FROM user_progress WHERE user_id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(deleteProgressQuery);
            ps.setInt(1, user.getUser_id());
            ps.executeUpdate();
            Utiljdbk.close(con,ps,null);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    //用户数据库表删除(关卡删除)
    public static void deleteUserProgressData_level(Level level) {
        Connection con = Utiljdbk.getConnection();
        String deleteProgressQuery = "DELETE FROM user_progress WHERE num = ?";
        try {
            PreparedStatement ps = con.prepareStatement(deleteProgressQuery);
            ps.setInt(1, level.getNum());
            ps.executeUpdate();
            Utiljdbk.close(con,ps,null);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
