package org.example;

import Wanf.User_Record;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TopList extends JFrame {
    private BackgroundPanel panel; // 使用 BackgroundPanel
    private JPanel checkBoxPanel = new JPanel();
    private JScrollPane checkBoxScrollPane; // 滚动面板
    public ArrayList<User_Record> userRecords; // 登录用户的游戏记录

    JTable table = new JTable();

    JPanel myJLabel = new JPanel();

    JLabel gameDateLabel = new JLabel("游戏数据");
    JLabel first_stepsJLabel = new JLabel("第一名的步数");
    JLabel your_stepsJLabel = new JLabel("你的步数");
    JLabel your_ranksJLabel = new JLabel("你的排名");
    JLabel yout_numbersJLabel = new JLabel("你的得分");
    JLabel map_idLabel = new JLabel("关卡ID");

    JLabel levelTopList = new JLabel("排行榜");

    JPanel topListPanel = new JPanel();

    private ArrayList<JCheckBox> checkBoxes = new ArrayList<>();

    public TopList(ArrayList<User_Record> userRecords) {


        this.userRecords = userRecords;
        userRecords.sort(comparator);
        setTitle("JTable Example");
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 400); // 调整窗口大小
        setLayout(null); // 设置为绝对布局

        // 使用带有背景图片的自定义面板
        panel = new BackgroundPanel("/img_7.png"); // 替换为您的图片路径
        panel.setLayout(null);

        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));
        checkBoxPanel.setOpaque(false); // 设置复选框面板为透明


        // 根据游戏记录创建复选框
        for (int i = 0; i < userRecords.size(); i++) {
            User_Record userRecord = userRecords.get(userRecords.size()-1-i);
            JCheckBox checkBox = new JCheckBox("关卡" + userRecord.getLevel().getMap_Id() + "_" + userRecord.getLevel().getTime());
            checkBox.setOpaque(false); // 设置复选框为透明
            checkBox.setBackground(new Color(0, 0, 0, 0)); // 设置复选框背景色为透明
            checkBox.addActionListener(new CheckBoxActionListener()); // 为复选框添加事件监听器
            checkBoxes.add(checkBox);
            checkBoxPanel.add(checkBox);
        }

        // 为复选框面板创建滚动面板
        checkBoxScrollPane = new JScrollPane(checkBoxPanel);
        checkBoxScrollPane.setBounds(5, 5, 200, getHeight() - 20); // 设置滚动面板的位置和大小
        checkBoxScrollPane.setOpaque(false); // 设置滚动面板为透明
        checkBoxScrollPane.getViewport().setOpaque(false); // 设置滚动面板视图为透明

        myJLabel.setLayout(new BoxLayout(myJLabel, BoxLayout.Y_AXIS));
        myJLabel.add(map_idLabel);
        myJLabel.add(gameDateLabel);
        myJLabel.add(first_stepsJLabel);
        myJLabel.add(your_stepsJLabel);
        myJLabel.add(your_ranksJLabel);
        myJLabel.add(yout_numbersJLabel);
        myJLabel.setBounds(210, 5, 150, getHeight()); // 设置标签组的位置和大小
        myJLabel.setOpaque(false); // 设置标签面板为透明

        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setOpaque(false); // 设置表格滚动面板为透明
        tableScrollPane.getViewport().setOpaque(false); // 设置表格滚动面板视图为透明
        tableScrollPane.setBounds(365, 5, 240, getHeight());

        topListPanel.setLayout(new BoxLayout(topListPanel, BoxLayout.Y_AXIS));
        topListPanel.add(levelTopList);
        topListPanel.add(tableScrollPane);
        topListPanel.setBounds(365, 5, 240, getHeight());
        topListPanel.setOpaque(false); // 设置面板为透明

        panel.add(checkBoxScrollPane);
        panel.add(myJLabel);
        panel.add(topListPanel);
        panel.setBounds(0, 0, getWidth(), getHeight()); // 设置主面板的位置和大小

        add(panel);
        setLocationRelativeTo(null);
        setVisible(false);
    }

    // 复选框的事件监听器类
    class CheckBoxActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JCheckBox selectedCheckBox = (JCheckBox) e.getSource(); // 获取事件源（复选框）
            if (selectedCheckBox.isSelected()) { // 检查复选框是否被选中
                // 取消选择其他所有复选框
                for (JCheckBox checkBox : checkBoxes) {
                    if (checkBox != selectedCheckBox) {
                        checkBox.setSelected(false);
                    }
                }

                int index = getComponentIndex(selectedCheckBox, checkBoxPanel);//这个指引表示选中复选框的位置
                User_Record user_record = userRecords.get(userRecords.size() -index-1);
                int map_id = user_record.getLevel().getMap_Id();

                resetTable(map_id, index);
            }
        }
    }

    // 方法来获取组件的索引
    public static int getComponentIndex(Component component, Container container) {
        for (int i = 0; i < container.getComponentCount(); i++) {
            if (container.getComponent(i) == component) {

                return i;
            }
        }
        return -1; // 如果没有找到，返回-1
    }

    private void resetTable(int map_id, int index) {
        // index=

        System.out.println("map_id: " + map_id);

        String[][] topList = Tool.levelDataForId(map_id);
        int first_steps = Integer.parseInt(topList[0][1]);

        // 表格列名称
        String[] columnNames = {"名字", "步数", "得分", "排名"};
        // 创建表格模型
        DefaultTableModel model = new DefaultTableModel(topList, columnNames);

        // 只更新表格的模型
        table.setModel(model);
        table.setOpaque(false); // 设置表格为透明
        table.setBackground(new Color(0, 0, 0, 0)); // 设置表格背景色为透明

        first_stepsJLabel.setText("第一名步数: " + first_steps);
        your_stepsJLabel.setText("你的步数: " + userRecords.get(userRecords.size()-index-1).getLevel().getSteps());
        map_idLabel.setText("地图 ID: " + map_id);

        String steps = String.valueOf(userRecords.get(userRecords.size()-index-1).getLevel().getSteps());
        for (int i = 0; i < topList.length; i++) {
            if (topList[i][0].equals(userRecords.get(userRecords.size()-index-1).getUser().getName()) && topList[i][1].equals(steps)) {
                your_ranksJLabel.setText("你的排名: " + (i + 1));
                yout_numbersJLabel.setText("你的得分: " + topList[i][2]);
                break;
            }
        }

    }


    Comparator<User_Record> comparator = new Comparator<User_Record>() {
        @Override
        public int compare(User_Record o1, User_Record o2) {
            Pattern pattern = Pattern.compile("/(\\d+)-(\\d)(\\d)-(\\d)(\\d)\\s(\\d)(\\d):(\\d)(\\d):(\\d)(\\d)/gm");
            Matcher matcher = pattern.matcher(o1.getLevel().getTime());
            Matcher matcher1 = pattern.matcher(o2.getLevel().getTime());
            if (matcher.find() && matcher1.find()) {
                int year = Integer.parseInt(matcher.group(1));
                int year1 = Integer.parseInt(matcher1.group(1));
                if (year != year1) return year1 - year;
                char month = matcher.group(2).charAt(0);
                char month1 = matcher1.group(2).charAt(0);
                if (month != month1) return month1 - month;
                char month2 = matcher.group(3).charAt(0);
                char month21 = matcher1.group(3).charAt(0);
                if (month2 != month21) return month21 - month2;
                char day = matcher.group(4).charAt(0);
                char day1 = matcher1.group(4).charAt(0);
                if (day != day1) return day1 - day;
                char day2 = matcher.group(5).charAt(0);
                char day21 = matcher1.group(5).charAt(0);
                if (day2 != day21) return day21 - day2;
                char hour = matcher.group(6).charAt(0);
                char hour1 = matcher1.group(6).charAt(0);
                if (hour != hour1) return hour1 - hour;
                char hour2 = matcher.group(7).charAt(0);
                char hour21 = matcher1.group(7).charAt(0);
                if (hour2 != hour21) return hour21 - hour2;
                char min = matcher.group(8).charAt(0);
                char min1 = matcher1.group(8).charAt(0);
                if (min != min1) return min1 - min;
                char min2 = matcher.group(9).charAt(0);
                char min21 = matcher1.group(9).charAt(0);
                if (min2 != min21) return min21 - min2;
                char sec = matcher.group(10).charAt(0);
                char sec1 = matcher1.group(10).charAt(0);
                if (sec != sec1) return sec1 - sec;
                char sec2 = matcher.group(11).charAt(0);
                char sec21 = matcher1.group(11).charAt(0);
                if (sec2 != sec21) return sec21 - sec2;
            }
            return 0;
        }
    };

}
