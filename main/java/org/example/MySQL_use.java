package org.example;


import java.sql.*;
import java.util.ArrayList;

public class MySQL_use {

    // 获取连接对象
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/data_1", "root", "123456");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    // 关闭数据库
    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 从数据库读取聊天消息
    public static ArrayList<ChatMessage> readChatMessage(int map_num) {
        ArrayList<ChatMessage> chatMessages = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT id, userName, message, likes, map_num FROM chatmessage";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int mapNum = rs.getInt("map_num");
                if (mapNum != map_num) continue;
                int id = rs.getInt("id");
                String userName = rs.getString("userName");
                String message = rs.getString("message");
                int likes = rs.getInt("likes");
                ChatMessage chatMessage = new ChatMessage(userName, message, likes, mapNum);
                chatMessage.setId(id);
                chatMessages.add(chatMessage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, stmt, rs);
        }

        return chatMessages;
    }

    // 将聊天消息写入数据库
    public static void writeChatMessage(ChatMessage chatMessage) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            String sql = "INSERT INTO chatmessage (userName, message, likes, map_num) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, chatMessage.getUserName());
            pstmt.setString(2, chatMessage.getMessage());
            pstmt.setInt(3, chatMessage.getLikes());
            pstmt.setInt(4, chatMessage.getMap_num());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, null);
        }
    }

    // 更新聊天消息
    public static void updateChatMessage(ChatMessage chatMessage) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            String sql = "UPDATE chatmessage SET userName = ?, message = ?, likes = ?, map_num = ? WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, chatMessage.getUserName());
            pstmt.setString(2, chatMessage.getMessage());
            pstmt.setInt(3, chatMessage.getLikes());
            pstmt.setInt(4, chatMessage.getMap_num());
            pstmt.setInt(5, chatMessage.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, null);
        }
    }
}