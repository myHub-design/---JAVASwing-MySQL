package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LevelTopList extends JFrame {
    private BackgroundPanel panel; // 使用 BackgroundPanel
    private JPanel checkBoxPanel = new JPanel();
    private JScrollPane checkBoxScrollPane; // 滚动面板

    private ArrayList<JCheckBox> checkBoxes = new ArrayList<>();

    JTable table = new JTable();//排行榜组件

    JLabel levelTopList = new JLabel("排行榜");

    JPanel topListPanel = new JPanel();
    public LevelTopList() {

        setTitle("JTable Example");
        setSize(600, 400); // 调整窗口大小
        setLayout(null); // 设置为绝对布局

        // 使用带有背景图片的自定义面板
        panel = new BackgroundPanel("/img_7.png"); // 替换为您的图片路径
        panel.setLayout(null);

        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));
        checkBoxPanel.setOpaque(false); // 设置复选框面板为透明


        // 根据游戏记录创建复选框
        for (int i = 0; i < 25; i++) {
            JCheckBox checkBox = new JCheckBox("关卡" + i);
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

        JScrollPane tableScrollPane = new JScrollPane(table);
        topListPanel.setLayout(new BoxLayout(topListPanel, BoxLayout.Y_AXIS));
        topListPanel.add(levelTopList);
        topListPanel.add(tableScrollPane);
        topListPanel.setBounds(365, 5, 240, getHeight() - 20); // 确保面板高度减小一些，以避免遮挡
        topListPanel.setOpaque(false); // 设置面板为透明

       panel.add(checkBoxScrollPane);
        panel.add(topListPanel);
        panel.setBounds(0,0,getWidth(),getHeight());

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true); // 确保窗口可见
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

                int index = getComponentIndex(selectedCheckBox, checkBoxPanel);
                resetTable(index + 1, index + 1);
            }
        }
    }

    private void resetTable(int map_id, int index) {
        System.out.println("map_id: " + map_id);

        String[][] topList = Tool.levelDataForId(map_id);
        if(topList == null) {
            topList=new String[1][4];
            topList[1][0]="暂无";
            topList[1][1]="暂无";
            topList[1][2]="暂无";
            topList[1][3]="暂无";

        }

        // 表格列名称
        String[] columnNames = {"名字", "步数", "得分", "排名"};
        // 创建表格模型
        DefaultTableModel model = new DefaultTableModel(topList, columnNames);

        // 只更新表格的模型
        table.setModel(model);
        table.setOpaque(false); // 设置表格为透明
        table.setBackground(new Color(0, 0, 0, 0)); // 设置表格背景色为透明
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LevelTopList());
    }
}
