package org.example;

import Wanf.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ManageMySQL {
    static String formName = "jdbc:mysql://localhost:3306/data_1";
    static String mysqlUser = "root";
    static String mysqlPassword = "123456";

    // 静态公共方法，用于向MySQL数据库中插入用户数据
    static public boolean insertMySQL(User user)  {
        // 从User对象中获取数据
        int id = user.getUser_id();
        String password = user.getPassword();
        String name = user.getName();
        String gender = user.getGender();
        int age = user.getAge();;

        Connection conn = null;  // 声明数据库连接对象
        PreparedStatement ps = null;  // 声明预处理语句对象

        try {
            // 加载驱动（通常可省略，因为使用Maven会自动加载驱动）
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 连接数据库，使用JDBC URL、用户名和密码
            conn = DriverManager.getConnection( formName, mysqlUser, mysqlPassword);

            // 定义插入数据的SQL语句
            String insertSQL = "INSERT INTO users (user_id, username ,password, age, gender) VALUES (?, ?, ?, ?, ?)";

            // 创建PreparedStatement对象，用于执行预处理的SQL语句
            ps = conn.prepareStatement(insertSQL);

            // 设置SQL语句中的参数值
            ps.setInt(1, id);  // 设置第一个参数，id
            ps.setString(2, password);  // 设置第二个参数，password
            ps.setString(3, name);  // 设置第三个参数，name
            ps.setInt(5, age);  // 设置第五个参数，age
            ps.setString(4, gender);  // 设置第四个参数，gender


            // 执行插入操作，并获取受影响的行数
            int rowsAffected = ps.executeUpdate();

            // 输出受影响的行数（可选）
            System.out.println("插入数据成功，受影响的行数: " + rowsAffected);

        } catch (ClassNotFoundException e) {
            // 处理找不到驱动类的异常
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            // 处理SQL异常
            e.printStackTrace();
        } finally {
            // 关闭声明和连接对象
            try {
                if (ps != null) ps.close();  // 关闭PreparedStatement对象
                if (conn != null) conn.close();  // 关闭Connection对象
            } catch (SQLException e) {
                // 处理关闭过程中可能发生的异常
                e.printStackTrace();
            }
        }
        return true;  // 返回true，表示插入操作成功
    }

    // 静态公共方法，用于从MySQL数据库查询User对象
    static public User queryMySQL(int id) {
        Connection conn = null;  // 声明数据库连接对象
        PreparedStatement ps = null;  // 声明预处理语句对象
        ResultSet rs = null;  // 声明结果集对象

        try {
            // 加载MySQL JDBC驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 使用DriverManager获取数据库连接
            conn = DriverManager.getConnection(formName, mysqlUser, mysqlPassword);
            // 准备SQL查询语句
            String query = "SELECT * FROM users WHERE user_id = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            // 执行查询并获取结果集
            rs = ps.executeQuery();
            // 遍历结果集
            if (rs.next()) {
                // 获取当前记录的其他字段
                String password = rs.getString("password");
                String queryName = rs.getString("name");
                String queryGender = rs.getString("gender");
                int queryAge = rs.getInt("age");
                String queryCreateTime = rs.getString("createTime");
                int queryGameDate = rs.getInt("gameDate");
                // 创建User对象并返回
                User user = new User(id, queryName ,password);
                return user;
            }
        } catch (ClassNotFoundException e) {
            // 捕获并处理ClassNotFoundException
            throw new RuntimeException(e);
        } catch (SQLException e) {
            // 捕获并处理SQLException
            throw new RuntimeException(e);
        } finally {
            // 关闭结果集、声明和连接对象
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                // 捕获并处理关闭资源时的SQLException
                e.printStackTrace();
            }
        }
        // 如果未找到符合条件的记录，返回null
        return null;
    }


    // 静态公共方法，用于从MySQL数据库删除用户数据
    static public boolean deleteMySQL(int id, String password) {
        Connection conn = null;  // 声明数据库连接对象
        PreparedStatement ps = null;  // 声明预处理语句对象

        try {
            // 加载MySQL JDBC驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 使用DriverManager获取数据库连接
            conn = DriverManager.getConnection( formName, mysqlUser, mysqlPassword);

            // 定义删除数据的SQL语句
            String deleteSQL = "DELETE FROM users WHERE id = ? AND password = ?";

            // 创建PreparedStatement对象，用于执行预处理的SQL语句
            ps = conn.prepareStatement(deleteSQL);

            // 设置SQL语句中的参数值
            ps.setInt(1, id);  // 设置第一个参数，id
            ps.setString(2, password);  // 设置第二个参数，password

            // 执行删除操作，并获取受影响的行数
            int rowsAffected = ps.executeUpdate();

            // 输出受影响的行数（可选）
            System.out.println("删除数据成功，受影响的行数: " + rowsAffected);

            // 返回true，表示删除操作成功
            return rowsAffected > 0;

        } catch (ClassNotFoundException e) {
            // 处理找不到驱动类的异常
            e.printStackTrace();
        } catch (SQLException e) {
            // 处理SQL异常
            e.printStackTrace();
        } finally {
            // 关闭声明和连接对象
            try {
                if (ps != null) ps.close();  // 关闭PreparedStatement对象
                if (conn != null) conn.close();  // 关闭Connection对象
            } catch (SQLException e) {
                // 处理关闭过程中可能发生的异常
                e.printStackTrace();
            }
        }
        // 返回false，表示删除操作失败
        return false;
    }


    // 静态公共方法，用于批量插入用户数据到MySQL数据库
    static public void insertMultipleUsers(int numberOfUsers) throws Exception {
        Connection conn = null;  // 声明数据库连接对象
        PreparedStatement ps = null;  // 声明预处理语句对象

        try {
            // 加载MySQL JDBC驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 使用DriverManager获取数据库连接
            conn = DriverManager.getConnection( formName, mysqlUser, mysqlPassword);

            // 定义插入数据的SQL语句
            String insertSQL = "INSERT INTO users (id, password, name, gender, age) VALUES (?, ?, ?, ?, ?)";
            // 创建PreparedStatement对象，用于执行预处理的SQL语句
            ps = conn.prepareStatement(insertSQL);

            // 创建随机数生成器
            Random random = new Random();

            for (int i = 1; i <= numberOfUsers; i++) {
                // 生成随机用户数据
                int id = i;
                String password = "password" + i;
                String name = "User" + i;
                String gender = random.nextBoolean() ? "Male" : "Female";
                int age = 18 + random.nextInt(50);
                String createTime = "2024-07-10 12:00:00";
                int gameDate = random.nextInt(100);

                // 设置SQL语句中的参数值
                ps.setInt(1, id);  // 设置第一个参数，id
                ps.setString(2, password);  // 设置第二个参数，password
                ps.setString(3, name);  // 设置第三个参数，name
                ps.setString(4, gender);  // 设置第四个参数，gender
                ps.setInt(5, age);  // 设置第五个参数，age
                // 执行插入操作
                ps.addBatch();
            }

            // 执行批量插入操作
            int[] rowsAffected = ps.executeBatch();

            // 输出受影响的行数（可选）
            System.out.println("插入数据成功，受影响的行数: " + rowsAffected.length);

        } catch (ClassNotFoundException e) {
            // 处理找不到驱动类的异常
            e.printStackTrace();
        } catch (SQLException e) {
            // 处理SQL异常
            e.printStackTrace();
        } finally {
            // 关闭声明和连接对象
            try {
                if (ps != null) ps.close();  // 关闭PreparedStatement对象
                if (conn != null) conn.close();  // 关闭Connection对象
            } catch (SQLException e) {
                // 处理关闭过程中可能发生的异常
                e.printStackTrace();
            }
        }
    }

    static public int[] getNumberOfPasses(int userId) {
        Connection conn = null;  // 声明数据库连接对象
        PreparedStatement ps = null;  // 声明预处理语句对象
        ResultSet rs = null;  // 声明结果集对象
        List<Integer> numberOfPassesList = new ArrayList<>();  // 用于存储numberOfPasses的List

        try {
            // 加载MySQL JDBC驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 使用DriverManager获取数据库连接
            conn = DriverManager.getConnection( formName, mysqlUser, mysqlPassword);

            // 准备SQL查询语句
            String querySQL = "SELECT numberOfPasses FROM gameDate WHERE id = ?";
            ps = conn.prepareStatement(querySQL);
            ps.setInt(1, userId);  // 设置用户ID参数

            // 执行查询并获取结果集
            rs = ps.executeQuery();
            if (rs.next()) {
                // 获取numberOfPasses字段的值
                String numberOfPassesStr = rs.getString("numberOfPasses");
                // 将numberOfPasses字段的值拆分成单个数字，并添加到List中
                if (numberOfPassesStr != null && !numberOfPassesStr.isEmpty()) {
                    String[] numberOfPassesArray = numberOfPassesStr.split(",");
                    for (String number : numberOfPassesArray) {
                        numberOfPassesList.add(Integer.parseInt(number));
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            // 捕获并处理异常
            e.printStackTrace();
        } finally {
            // 关闭结果集、声明和连接对象
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                // 捕获并处理关闭资源时的异常
                e.printStackTrace();
            }
        }

        // 将List转换为int[]并返回
        int[] numberOfPassesArray = new int[numberOfPassesList.size()];
        for (int i = 0; i < numberOfPassesList.size(); i++) {
            numberOfPassesArray[i] = numberOfPassesList.get(i);
        }
        return numberOfPassesArray;
    }

    static public boolean insertNumberOfPasses(int id, int[] arr) {
        PreparedStatement ps = null;
        Connection conn=null;
        try {
            // 加载MySQL JDBC驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 使用DriverManager获取数据库连接
            conn = DriverManager.getConnection( formName, mysqlUser, mysqlPassword);

            // 插入数据
            // 准备SQL插入语句
            String insertSQL = "INSERT INTO gameDate (id,numberOfPasses) VALUES (?,?)";
            ps = conn.prepareStatement(insertSQL);
            // 执行插入操作


            // 将整数数组转换为逗号分隔的字符串
            StringBuilder passesStr = new StringBuilder();
            for (int i = 0; i < arr.length; i++) {
                passesStr.append(arr[i]);
                if (i < arr.length - 1) {
                    passesStr.append(",");
                }
            }
            // 设置SQL语句中的参数值
            ps.setInt(1, id);
            ps.setString(2, passesStr.toString());
            // 执行插入操作
            ps.executeUpdate();
            // 关闭连接
            conn.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭PreparedStatement对象
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    // 方法：根据id更新整数数组到gameDate表的numberOfPasses字段中
    static public boolean updateNumberOfPasses(int id, int[] arr) {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            // 加载MySQL JDBC驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 使用DriverManager获取数据库连接
            conn = DriverManager.getConnection( formName, mysqlUser, mysqlPassword);

            // 准备SQL更新语句
            String updateSQL = "UPDATE gameDate SET numberOfPasses = ? WHERE id = ?";
            ps = conn.prepareStatement(updateSQL);

            // 将整数数组转换为逗号分隔的字符串
            StringBuilder passesStr = new StringBuilder();
            for (int i = 0; i < arr.length; i++) {
                passesStr.append(arr[i]);
                if (i < arr.length - 1) {
                    passesStr.append(",");
                }
            }

            // 设置SQL语句中的参数值
            ps.setString(1, passesStr.toString());
            ps.setInt(2, id);
            ps.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // 关闭PreparedStatement对象和数据库连接
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
    // 方法：根据id删除gameDate表中的数据
    static public boolean deleteNumberOfPasses(int id) {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            // 加载MySQL JDBC驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 使用DriverManager获取数据库连接
            conn = DriverManager.getConnection( formName, mysqlUser, mysqlPassword);

            // 准备SQL删除语句
            String deleteSQL = "DELETE FROM gameDate WHERE id = ?";
            ps = conn.prepareStatement(deleteSQL);

            // 设置SQL语句中的参数值
            ps.setInt(1, id);

            // 执行删除操作
            ps.executeUpdate();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // 关闭PreparedStatement对象和数据库连接
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    // 静态公共方法，用于从MySQL数据库查询User对象
    static public LinkedList<User> AllMySQL() {
        Connection conn = null;  // 声明数据库连接对象
        PreparedStatement ps = null;  // 声明预处理语句对象
        ResultSet rs = null;  // 声明结果集对象
       LinkedList<User> userList = new LinkedList<>();

        try {
            // 加载MySQL JDBC驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 使用DriverManager获取数据库连接
            conn = DriverManager.getConnection(formName, mysqlUser, mysqlPassword);
            // 准备SQL查询语句
            String query = "SELECT * FROM users ";
            ps = conn.prepareStatement(query);
          //  ps.setInt(1, id);
            // 执行查询并获取结果集
            rs = ps.executeQuery();
            // 遍历结果集
           while(rs.next()){
               // 获取当前记录的其他字段
               int id=rs.getInt("id");
               String password = rs.getString("password");
               String queryName = rs.getString("name");
               String queryGender = rs.getString("gender");
               int queryAge = rs.getInt("age");
//               String queryCreateTime = rs.getString("createTime");
//               int queryGameDate = rs.getInt("gameDate");
               // 创建User对象并返回
               User user = new User(id, queryName, password);
               userList.add(user);
           }
        } catch (ClassNotFoundException e) {
            // 捕获并处理ClassNotFoundException
            throw new RuntimeException(e);
        } catch (SQLException e) {
            // 捕获并处理SQLException
            throw new RuntimeException(e);
        } finally {
            // 关闭结果集、声明和连接对象
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                // 捕获并处理关闭资源时的SQLException
                e.printStackTrace();
            }
        }
        // 如果未找到符合条件的记录，返回null
        return userList;
    }
}

