package Effect;

import MusicPlayer.*;
import org.example.JavaSwingLogOnAndSignUp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FadeEffectDemo {
    private JFrame frame;
    private FadePanel fadePanel;
    private Timer fadeInTimer;
    private Timer fadeOutTimer;
    private Timer musicTimer;
    private float alpha = 0f; // 初始透明度为0，表示完全透明
    private boolean isFirstImage = true;

    public FadeEffectDemo() {
        // 加载图片
        ImageIcon imageIcon1 = new ImageIcon("./resources/start.png");
        ImageIcon imageIcon2 = new ImageIcon("./resources/img_3.png");
        Image image1 = imageIcon1.getImage();
        Image image2 = imageIcon2.getImage();

        // 创建淡入淡出面板
        fadePanel = new FadePanel();


        // 创建JFrame
        frame = new JFrame("Fade Effect Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600); // 你可以调整这个大小
        frame.setLayout(new CardLayout());
        frame.add(fadePanel, "FadePanel");
        // frame.add(gifPlayer, "GifPlayer");
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        // 创建定时器控制淡入效果
        fadeInTimer = new Timer(50, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                alpha += 0.05f;
                if (alpha > 1f) {
                    alpha = 1f;
                    fadeInTimer.stop();
                    if (isFirstImage) {
                        fadeOutTimer.start(); // 开始淡出效果
                    }
                    else {
                        onAnimationEnd();
                        // ((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "GifPlayer");
                    }
                    }
                fadePanel.setAlpha(alpha);
            }
        });

        // 创建定时器控制淡出效果
        fadeOutTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alpha -= 0.05f;
                if (alpha < 0f) {
                    alpha = 0f;
                    fadeOutTimer.stop();
                    isFirstImage = false;
                    fadePanel.setImage(image2);
                    fadeInTimer.start(); // 开始第二张图片的淡入效果
                }
                fadePanel.setAlpha(alpha);
            }
        });

        // 设置第一张图片并开始淡入效果
        fadePanel.setImage(image1);
        fadeInTimer.start();
    }

    private void onAnimationEnd(){

        // 显示按钮
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Thread(Musics.backgroundMusic.get(5)).start();
                JavaSwingLogOnAndSignUp.select();
                frame.dispose(); // 释放资源
            }
        });
    }
}
