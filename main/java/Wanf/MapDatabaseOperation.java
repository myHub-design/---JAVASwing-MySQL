package Wanf;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

//地图的数据据库的操作
public class MapDatabaseOperation {
    //查询地图数据库来初始化地图
    public static int[][] QueryMapDatabase(int mapId) {
        Connection con = Utiljdbk.getConnection();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT row_index, col_index, value FROM map_data WHERE map_id = " + mapId;
            ResultSet rs = stmt.executeQuery(sql);
            Map<Integer, Map<Integer,Integer>> datamap = new HashMap<>();
            //如果没有该地图编号就直接返回空
            boolean flag = false;
            while (rs.next()) {
                flag = true;
                int row = rs.getInt("row_index");
                int col = rs.getInt("col_index");
                int value = rs.getInt("value");
                datamap.putIfAbsent(row, new HashMap<>());
                datamap.get(row).put(col, value);
            }
            if(!flag) return null;
            //将地图的二维数组存起来
            int rows = datamap.keySet().stream().max(Integer::compareTo).orElse(0) + 1;
            int cols = datamap.values().stream().flatMap(m -> m.keySet().stream()).max(Integer::compareTo).orElse(0) + 1;
            int[][] mapData = new int[rows][cols];
            for(Map.Entry<Integer, Map<Integer,Integer>> entry : datamap.entrySet()) {
                int row = entry.getKey();
                for(Map.Entry<Integer,Integer> cell : entry.getValue().entrySet()) {
                    int col = cell.getKey();
                    int value = cell.getValue();
                    mapData[row][col] = value;
                }
            }
            Utiljdbk.close(con, stmt, rs);
            return mapData;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    //将地图的数据写入数据库 管理员的操作
    public static void WriteMapDatabase(int mapId, int[][] mapData) {
        Connection con = Utiljdbk.getConnection();
        String insertQuery = "INSERT INTO map_data (map_id, row_index, col_index, value) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement stmt = con.prepareStatement(insertQuery);
            for (int row = 0; row < mapData.length; row++) {
                for (int col = 0; col < mapData[row].length; col++) {
                    stmt.setInt(1, mapId);
                    stmt.setInt(2, row);
                    stmt.setInt(3, col);
                    stmt.setInt(4, mapData[row][col]);
                    stmt.executeUpdate();
                }
            }
            Utiljdbk.close(con, stmt, null);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //删除该地图
    public static void DeleteMapDatabase(int mapId) {
        Connection con = Utiljdbk.getConnection();
        String deleteQuery = "DELETE FROM map_data WHERE map_id = ?";
        try {
            PreparedStatement pstmt = con.prepareStatement(deleteQuery);
            pstmt.setInt(1, mapId);
            pstmt.executeUpdate();
            Utiljdbk.close(con, pstmt, null);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //修改该地图
    public static void Update_Map_Database(int mapId, int[][] newMapData) {
        DeleteMapDatabase(mapId);
        WriteMapDatabase(mapId, newMapData);
    }
}
