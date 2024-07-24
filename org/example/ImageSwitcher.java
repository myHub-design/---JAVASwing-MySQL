package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class ImageSwitcher extends JFrame {

    private JLabel imageLabel;
    private JButton switchButton;
    private ImageIcon image1;
    private ImageIcon image2;
    private boolean isImage1Displayed;
    private Timer timer;
    private int xPosition;

    public ImageSwitcher() {
        // 使用相对路径加载图片
        URL img1URL = ImageSwitcher.class.getResource("/img_2.png");
        URL img2URL = ImageSwitcher.class.getResource("/img_3.png");
        if (img1URL != null && img2URL != null) {
            image1 = new ImageIcon(img1URL);
            image2 = new ImageIcon(img2URL);
        } else {
            System.err.println("Image files not found!");
            System.exit(1);
        }
        isImage1Displayed = true;

        // 初始化组件
        imageLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (isImage1Displayed) {
                    g.drawImage(image2.getImage(), 0, 0, this); // 先绘制底层图片
                    g.drawImage(image1.getImage(), xPosition, 0, this); // 再绘制上层图片
                } else {
                    g.drawImage(image1.getImage(), 0, 0, this); // 先绘制底层图片
                    g.drawImage(image2.getImage(), xPosition, 0, this); // 再绘制上层图片
                }
            }
        };

        switchButton = new JButton("Switch Image");

        // 设置布局
        setLayout(new BorderLayout());
        add(imageLabel, BorderLayout.CENTER);
        add(switchButton, BorderLayout.SOUTH);

        // 初始化计时器和xPosition
        xPosition = getWidth();

        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xPosition -= 20;
                if (xPosition <= 0) {
                    xPosition = 0;
                    timer.stop();
                }
                imageLabel.repaint();
            }
        });

        // 添加按钮点击事件监听器
        switchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isImage1Displayed = !isImage1Displayed;
                xPosition = getWidth();
                timer.start();
            }
        });

        // 设置窗体属性
        setTitle("Image Switcher");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void run() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ImageSwitcher().setVisible(true);
            }
        });
    }

}