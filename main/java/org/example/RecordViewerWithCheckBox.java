package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 这个类创建一个包含复选框的记录查看器
public class RecordViewerWithCheckBox {

    private JFrame frame; // 主窗口
    private JPanel panel; // 主面板
    private JPanel checkBoxPanel; // 复选框面板
    private JScrollPane scrollPane; // 滚动面板
    private static JTextArea detailArea; // 用于显示详细信息的文本区域

    // 构造函数
    public RecordViewerWithCheckBox() {
        frame = new JFrame("Record Viewer with CheckBoxes"); // 创建主窗口并设置标题
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置关闭操作
        frame.setSize(600, 400); // 设置窗口大小

        panel = new JPanel(); // 创建主面板
        panel.setLayout(new BorderLayout()); // 设置主面板为边界布局

        // 创建复选框面板并设置为垂直排列
        checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));

        // 创建示例记录的复选框并添加到复选框面板
        for (int i = 1; i <= 30; i++) {
            JCheckBox checkBox = new JCheckBox("Record ookkjnjkhhbjgghhbhgghjhggygughgh" + i); // 创建复选框
            checkBox.addActionListener(new CheckBoxActionListener()); // 为复选框添加事件监听器
            checkBoxPanel.add(checkBox); // 将复选框添加到复选框面板
        }

        // 将复选框面板放入滚动面板中
        scrollPane = new JScrollPane(checkBoxPanel);
        panel.add(scrollPane, BorderLayout.WEST); // 将滚动面板添加到主面板的左侧

        // 创建用于显示详细信息的文本区域，并放入滚动面板中
        detailArea = new JTextArea();
        detailArea.setEditable(false); // 设置文本区域为不可编辑
        JScrollPane detailScrollPane = new JScrollPane(detailArea);
        panel.add(detailScrollPane, BorderLayout.CENTER); // 将滚动面板添加到主面板的中央

        // 创建按钮并添加事件监听器
        JButton showButton = new JButton("Show Selected Records");
        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSelectedRecords(); // 按钮点击时显示选中的记录
            }
        });
        panel.add(showButton, BorderLayout.SOUTH); // 将按钮添加到主面板的底部

        frame.add(panel); // 将主面板添加到主窗口
        frame.setVisible(true); // 显示主窗口
    }

    // 显示选中的记录
    private void showSelectedRecords() {
        StringBuilder selectedRecords = new StringBuilder("Selected Records:\n"); // 创建字符串构建器用于存储选中的记录
        Component[] components = checkBoxPanel.getComponents(); // 获取复选框面板中的所有组件
        for (Component component : components) {
            if (component instanceof JCheckBox) { // 检查组件是否为复选框
                JCheckBox checkBox = (JCheckBox) component;
                if (checkBox.isSelected()) { // 检查复选框是否被选中
                    selectedRecords.append(checkBox.getText()).append("\n"); // 将选中的复选框文本添加到字符串构建器中
                }
            }
        }
        detailArea.setText(selectedRecords.toString()); // 将选中的记录显示在文本区域中
    }

    // 复选框的事件监听器类
    class CheckBoxActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JCheckBox checkBox = (JCheckBox) e.getSource(); // 获取事件源（复选框）
            if (checkBox.isSelected()) { // 检查复选框是否被选中
                detailArea.append(checkBox.getText() + " selected\n"); // 在文本区域中显示选中的复选框
            } else {
                detailArea.append(checkBox.getText() + " deselected\n"); // 在文本区域中显示取消选中的复选框
            }
        }
    }

    // 主方法，程序的入口
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RecordViewerWithCheckBox(); // 创建并显示记录查看器窗口
            }
        });
    }
}
