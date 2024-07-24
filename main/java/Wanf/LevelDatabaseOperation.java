package Wanf;
import java.sql.*;
import java.util.ArrayList;

//关卡的数据库操作
public class LevelDatabaseOperation {
    //增加关卡（增加关卡再去绑定相应的地图）(管理员操作)
    public static void WriteLevelDatabase(Level level){
        Connection con = Utiljdbk.getConnection();
        String sql = "insert into level(num,map_id,state,level_name) values(?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, level.getNum());
            ps.setInt(2,level.getMap_Id());
            ps.setInt(3,level.isState() ? 1 : 0);
            ps.setString(4, level.getMap_name());
            ps.executeUpdate();
            Utiljdbk.close(con, ps, null);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    //查询所有关卡 并将所有关卡信息保存起来
    public static ArrayList<Level> ReadLevelDatabase(){
        ArrayList<Level> levels = new ArrayList<>();
        Connection con = Utiljdbk.getConnection();
        try {
            Statement stmt = con.createStatement();
            String sql = "select * from level";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                int num = rs.getInt("num");
                int map_id = rs.getInt("map_id");
                int state = rs.getInt("State");
                String level_name = rs.getString("level_name");
                //如果没有该地图编号 就删除该关卡在数据库的信息
                if(MapDatabaseOperation.QueryMapDatabase(map_id) == null){
                    DeleteLevelDatabase(num);
                    continue;
                }
                Level level = new Level(num,map_id);
                //每查询一个关卡就将关卡里面的地图数据保存到关卡里面
                level.getMap_detail().primarySetMap();
                if(state == 1) level.setState();
                level.setMap_name(level_name);
                levels.add(level);
            }
            Utiljdbk.close(con, stmt,rs);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return levels;
    }
    //删除关卡(管理员操作)
    public static void DeleteLevelDatabase(int num){
        Level level = new Level();
        level.setNum(num);
        UserProgressDatabase.deleteUserProgressData_level(level);
        Connection con = Utiljdbk.getConnection();
        String sql = "DELETE FROM level WHERE num = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, num);
            ps.executeUpdate();
            Utiljdbk.close(con, ps, null);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    //修改关卡信息
    public static void UpdateLevelDatabase(int num, Level level2){
        DeleteLevelDatabase(num);
        WriteLevelDatabase(level2);
    }
    //加载单个关卡信息
    public static Level GetLevelDatabase(int num){
        Level level = new Level();
        level.setNum(num);
        Connection con = Utiljdbk.getConnection();
        try {
            String sql = "select * from level where num = " + num;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                level.setMap_Id(rs.getInt("map_id"));
                level.getMap_detail().primarySetMap();
            }
            Utiljdbk.close(con, stmt, rs);
            return level;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
