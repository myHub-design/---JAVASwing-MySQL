package Project_PushBox;

import Wanf.UserRecordDatabase;
import org.example.Main;
import org.example.TabbedPaneExample;
import org.example.TopList;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class MainPanel extends JLayeredPane{
    private ImageIcon icon = new ImageIcon("./resources/img_8.png");
    private MyButton button_start = new MyButton("开始游戏");
    private MyButton button_rank_= new MyButton("查看记录");
    private MyButton button_chat=new MyButton("关卡社区");
    private MyButton button_set = new MyButton("设置");
    private JLabel topic = new JLabel(" 军兵推箱子");

    public MainPanel() {
        setLayout(null);

        setPreferredSize(new Dimension(500, 500));
        button_start.ChangePositionAndSize(200, 150, 100, 50);
        button_rank_.ChangePositionAndSize(200, 220, 100, 50);
        button_set.ChangePositionAndSize(200, 290, 100, 50);
        button_chat.ChangePositionAndSize(200, 360, 100, 50);

        topic.setBounds(175, 70, 150, 70);

        topic.setFont(new Font("黑体", Font.BOLD, 24));

        setButton_rankEvent();
        setButton_startEvent();
        setButton_setEvent();
        setButton_chatEvent();

        add(button_start);
        add(button_rank_);
        add(button_set);
        add(button_chat);
        add(topic);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (icon != null) {
            g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }

    public void setButton_startEvent() {
        button_start.addActionListener(e -> {
            new Thread(DataHouse.music).start();
            DataHouse.selectJFrame.setVisible(true);
            DataHouse.mainJFrame.setVisible(false);
        });
    }

    public void setButton_rankEvent() {
        button_rank_.addActionListener(e -> {
            new Thread(DataHouse.music).start();
            TopList topList = new TopList(UserRecordDatabase.GetUserRecord_User(DataHouse.NowUser));
            topList.setVisible(true);
        });
    }

    public void setButton_setEvent() {
        button_set.addActionListener(e -> {
            new Thread(DataHouse.music).start();
            DataHouse.mainJFrame.setVisible(false);
            DataHouse.setJFrame.setVisible(true);
        });
    }
    public void setButton_chatEvent() {
        button_chat.addActionListener(e -> {
            new Thread(DataHouse.music).start();
            TabbedPaneExample tabbedPane = new TabbedPaneExample();
            tabbedPane.setVisible(true);
        });
    }
}
