package Project_PushBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MVP_results_screen extends JFrame {
    GameJFrame gameJFrame;
    SelectFrame selectFrame;
    public MVP_results_screen(SelectFrame selectFrame) {
        this.selectFrame = selectFrame;
        // 设置窗口标题
        setTitle("MVP Results Screen");

        // 设置窗口大小
        setSize(400, 300);

        // 设置默认关闭操作
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 设置窗口居中
        setLocationRelativeTo(null);

        // 创建主面板
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // 创建标签显示结果信息
        JLabel resultLabel = new JLabel("你胜利了!", JLabel.CENTER);
        resultLabel.setFont(new Font("Serif", Font.BOLD, 24));
        mainPanel.add(resultLabel, BorderLayout.CENTER);

        // 创建按钮
        JButton okButton = new JButton("确定");
        okButton.setFont(new Font("Serif", Font.PLAIN, 18));

        // 创建按钮面板并添加按钮
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // 为按钮添加动作监听器
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 关闭窗口
                dispose();
                selectFrame.setVisible(true);
            }
        });

        // 添加主面板到框架
        add(mainPanel);
    }
}

