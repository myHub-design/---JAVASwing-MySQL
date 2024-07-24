package Wanf;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LevelPlayerSteps {
    //获取每一关每个玩家的步数
    public static ArrayList<User_Step> inquiryplaySteps(Level level) {
        Connection con = Utiljdbk.getConnection();
        ArrayList<User_Step> user_steps = new ArrayList<>();
        String sql = "select * from user_progress where num=" + level.getNum();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                User_Step user_step = new User_Step();
                user_step.setUsername(rs.getString("username"));
                user_step.setSteps(rs.getInt("steps"));
                //当该关卡被完成时玩家信息传上去
                if(rs.getInt("completed") == 1) user_steps.add(user_step);
            }
            Utiljdbk.close(con,st,rs);
            return user_steps;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
