
package org.example;

import Project_PushBox.DataHouse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ChatAppPanel extends JPanel {
    // 定义界面组件
    private JTextField userNameField; // 用户名输入框
    private JTextArea messageArea; // 消息输入区域
    private JPanel chatHistoryPanel; // 聊天记录显示面板
    private JButton sendButton; // 发送按钮
    private JButton refreshButton; // 刷新按钮
    private JScrollPane scrollPane; // 滚动面板
    private JCheckBox autoRefreshCheckBox; // 自动刷新复选框
    private Timer autoRefreshTimer; // 自动刷新计时器
    private int id; // 这个界面对应地图编号

    private List<ChatMessage> chatHistory; // 聊天记录列表

    public ChatAppPanel(int map_num) {
        // 初始化聊天记录
        chatHistory = MySQL_use.readChatMessage(map_num);
        id = map_num;

        // 初始化界面组件
        userNameField = new JTextField(DataHouse.NowUser.getName(), 10); // 初始化用户名输入框，默认值为 "Mike"
        userNameField.setEditable(false); // 设置用户名输入框不可编辑
        messageArea = new JTextArea(3, 40); // 初始化消息输入区域
        chatHistoryPanel = new JPanel(); // 初始化聊天记录面板
        chatHistoryPanel.setLayout(new BoxLayout(chatHistoryPanel, BoxLayout.Y_AXIS)); // 设置聊天记录面板的布局为垂直排列
        scrollPane = new JScrollPane(chatHistoryPanel); // 包装聊天记录面板的滚动面板

        sendButton = new JButton("发送"); // 初始化发送按钮
        refreshButton = new JButton("刷新"); // 初始化刷新按钮
        autoRefreshCheckBox = new JCheckBox("自动刷新"); // 初始化自动刷新复选框

        // 使用 GridBagLayout 布局
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // 设置聊天记录面板的位置和属性
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(scrollPane, gbc); // 添加滚动面板

        // 设置输入面板的位置和属性
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.8;
        gbc.weighty = 0.0;

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(new JLabel("玩家名:"), BorderLayout.WEST); // 添加用户名标签
        inputPanel.add(userNameField, BorderLayout.CENTER); // 添加用户名输入框
        inputPanel.add(new JScrollPane(messageArea), BorderLayout.SOUTH); // 添加消息输入区域

        add(inputPanel, gbc);

        // 设置按钮面板的位置和属性
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.2;

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout()); // 设置按钮面板为流布局
        buttonPanel.add(sendButton); // 添加发送按钮
        buttonPanel.add(refreshButton); // 添加刷新按钮
        buttonPanel.add(autoRefreshCheckBox); // 添加自动刷新复选框

        add(buttonPanel, gbc);

        // 为发送按钮添加事件监听器
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        // 为刷新按钮添加事件监听器
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshChatHistory();
                // 滑杆自动到达最底部
                JScrollBar vertical = scrollPane.getVerticalScrollBar();
                vertical.setValue(vertical.getMaximum());
            }
        });

        // 初始化时加载聊天记录
        refreshChatHistory();

        // 初始化自动刷新计时器
        autoRefreshTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (autoRefreshCheckBox.isSelected()) {
                    refreshButton.doClick(); // 触发刷新按钮点击事件
                }
            }
        });
        autoRefreshTimer.start();

        // 添加自动刷新复选框事件监听器
        autoRefreshCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!autoRefreshCheckBox.isSelected()) {
                    autoRefreshTimer.stop(); // 取消选中时停止自动刷新计时器
                } else {
                    autoRefreshTimer.start(); // 选中时启动自动刷新计时器
                }
            }
        });
    }

    // 发送消息的方法
    private void sendMessage() {
        String userName = userNameField.getText(); // 获取用户名
        String message = messageArea.getText(); // 获取消息内容

        ChatMessage chatMessage = new ChatMessage(userName, message, 0, id); // 创建聊天消息对象

        MySQL_use.writeChatMessage(chatMessage);

        chatHistory.add(chatMessage); // 将消息添加到聊天记录列表中

        messageArea.setText(""); // 清空消息输入区域
        refreshChatHistory(); // 刷新聊天记录
        // 滑杆自动到达最底部
        JScrollBar vertical = scrollPane.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }

    // 刷新聊天记录的方法
    private void refreshChatHistory() {
        chatHistoryPanel.removeAll(); // 清空聊天记录面板
        chatHistory = MySQL_use.readChatMessage(id);
        for (ChatMessage chatMessage : chatHistory) {
            JPanel messagePanel = new JPanel(); // 创建消息面板
            messagePanel.setLayout(new BorderLayout());
            JLabel messageLabel = new JLabel(formatMessage(chatMessage.toString())); // 创建消息标签并格式化消息
            JButton likeButton = new JButton("点赞 (" + chatMessage.getLikes() + ")"); // 创建点赞按钮
            likeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    chatMessage.like(); // 点赞数加一
                    MySQL_use.updateChatMessage(chatMessage); // 更新点赞数到数据库
                    likeButton.setText("Like (" + chatMessage.getLikes() + ")");
                    //chatHistory.remove(chatMessage);
                    //refreshChatHistory(); // 刷新聊天记录
                    // 滑杆自动到达最底部
                    JScrollBar vertical = scrollPane.getVerticalScrollBar();
                    vertical.setValue(vertical.getMaximum());
                }
            });

            messagePanel.add(messageLabel, BorderLayout.CENTER); // 将消息标签添加到消息面板中
            messagePanel.add(likeButton, BorderLayout.EAST); // 将点赞按钮添加到消息面板中
            chatHistoryPanel.add(messagePanel); // 将消息面板添加到聊天记录面板中
        }
        chatHistoryPanel.revalidate(); // 重新验证聊天记录面板
        chatHistoryPanel.repaint(); // 重新绘制聊天记录面板
    }

    // 格式化消息内容，每隔30个字符插入换行符
    private String formatMessage(String message) {
        StringBuilder formattedMessage = new StringBuilder();
        int length = message.length();
        for (int i = 0; i < length; i++) {
            formattedMessage.append(message.charAt(i));
            if ((i + 1) % 45 == 0 && (i + 1) < length) {
                formattedMessage.append("<br>");
            }
        }
        return "<html>" + formattedMessage.toString() + "</html>";
    }
}

class ChatMessage {
    private int id;
    private String userName; // 用户名
    private String message; // 消息内容
    private int likes; // 点赞数
    private int map_num; // 地图编号

    public ChatMessage(String userName, String message, int likes, int map_num) {
        this.userName = userName;
        this.message = message;
        this.likes = likes;
        this.map_num = map_num;
    }

    public void like() {
        this.likes++; // 点赞数加一
    }

    public int getLikes() {
        return likes; // 获取点赞数
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getMap_num() {
        return map_num;
    }

    public void setMap_num(int map_num) {
        this.map_num = map_num;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("%s： %s", userName, message); // 返回格式化的消息内容
    }
}
