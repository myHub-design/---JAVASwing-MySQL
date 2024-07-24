package Project_PushBox;


import MusicPlayer.BackgroundMusicPlayer;
import MusicPlayer.Musics;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSliderUI;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SetFrame extends JFrame {
    private ImageIcon icon= new ImageIcon("./resources/img_8.png");
    private String[] mapItems = {"经典", "冰川", "地狱", "森林"};
    private String[] playerItems = {"皮肤1", "鸭子", "魔王", "冷冻怪", "王军兵", "黄色果冻", "经典"};
    private JLabel label_changeThem = new JLabel("选择关卡外观:");
    private JLabel label_player = new JLabel("选择玩家皮肤:");
    private JLabel label_music = new JLabel("音量调节");
    private JComboBox<String> comboBox_changeThem = new JComboBox<>(mapItems);
    private JComboBox<String> comboBox_play = new JComboBox<>(playerItems);
    private MyButton button_return = new MyButton("返回");
    private JSlider slider = new JSlider(JSlider.HORIZONTAL, -80, 6, -40);

    JLayeredPane setPane  = new JLayeredPane() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(new Color(255, 255, 255, 0)); // 设置背景为完全透明
            g.fillRect(0, 0, getWidth(), getHeight());
            g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    };

    public SetFrame() {
        setPane.setLayout(null);
        setPane.setPreferredSize(new Dimension(500, 500));

        label_music.setBounds(20, 300, 180, 50);
        slider.setBounds(150, 300, 300, 50);
        slider.setMajorTickSpacing(10); // 设置主刻度间隔为10
        slider.setMinorTickSpacing(1);  // 设置次刻度间隔为1
        slider.setPaintTicks(false);     // 显示刻度
        slider.setPaintLabels(false);// 显示标签
        slider.setOpaque(false);

        label_changeThem.setFont(new Font("楷体", Font.BOLD, 24));
        label_player.setFont(new Font("楷体", Font.BOLD, 24));
        label_music.setFont(new Font("楷体", Font.BOLD, 24));

        label_changeThem.setBounds(20, 100, 180, 50);
        comboBox_changeThem.setBounds(200, 100, 200, 50);

        label_player.setBounds(20, 200, 180, 50);
        comboBox_play.setBounds(200, 200, 200, 50);

        button_return.ChangePositionAndSize(200, 450, 100, 50);

        setComboBox_changeThemEvent();
        setComboBox_playEvent();
        setButton_returnEvent();
        setSlider_musicEvent();

        setPane.add(comboBox_changeThem);
        setPane.add(label_changeThem);
        setPane.add(comboBox_play);
        setPane.add(label_player);
        setPane.add(button_return);
        setPane.add(label_music);
        setPane.add(slider);

        setPane.setOpaque(false);
        add(setPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(false);
    }

    private void setSlider_musicEvent() {
        // 设置自定义 UI 使其透明
        slider.setUI(new BasicSliderUI(slider) {
            @Override
            public void paintTrack(Graphics g) {
                // 绘制透明的轨道
                Graphics2D g2d = (Graphics2D) g;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)); // 设置透明度为 50%
                super.paintTrack(g2d);
            }

            @Override
            public void paintThumb(Graphics g) {
                // 绘制透明的滑块
                Graphics2D g2d = (Graphics2D) g;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)); // 设置透明度为 50%
                super.paintThumb(g2d);
            }
        });
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                BackgroundMusicPlayer.setVolume((float)slider.getValue());
            }
        });
    }

    private void setComboBox_changeThemEvent() {
        comboBox_changeThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取选中的选项
                String selectedItem = (String) comboBox_changeThem.getSelectedItem();
                for (GamePane gamePane: DataHouse.gameJFrame.getGamePanes()) {
                    GameMapKind gameMapKind = gamePane.getGameMapKind();
                    if (selectedItem.equals("经典")) {
                        gameMapKind.setImage("./resources/mossy_stone_bricks.png",
                                "./resources/melon_side.png",
                                "./resources/bee_nest_top.png",
                                "./resources/cobblestone.png");
                    }
                    else if (selectedItem.equals("冰川")) {
                        gameMapKind.setImage("./resources/prismarine_bricks.png",
                                "./resources/diamond_block.png",
                                "./resources/beacon.png",
                                "./resources/ice.png");
                    }
                    else if (selectedItem.equals("地狱")) {
                        gameMapKind.setImage("./resources/nether_bricks.png",
                                "./resources/gold_block.png",
                                "./resources/ancient_debris_top.png",
                                "./resources/netherrack.png");
                    }
                    else if (selectedItem.equals("森林")) {
                        gameMapKind.setImage("./resources/stripped_oak_log_top.png",
                                "./resources/emerald_block.png",
                                "./resources/bamboo_trapdoor.png",
                                "./resources/moss_block.png");
                    }
                }
            }
        });
    }

    private void setComboBox_playEvent() {
        comboBox_play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String) comboBox_play.getSelectedItem();
                for (GamePane gamePane: DataHouse.gameJFrame.getGamePanes()) {
                    GameMapKind gameMapKind = gamePane.getGameMapKind();
                    if (selectedItem.equals("皮肤1")) {
                        gameMapKind.setPlayerImage("./resources/player_1.png");
                    }
                    else if (selectedItem.equals("鸭子")) {
                        gameMapKind.setPlayerImage("./resources/player_2.png");
                    }
                    else if (selectedItem.equals("魔王")) {
                        gameMapKind.setPlayerImage("./resources/player_3.png");
                    }
                    else if (selectedItem.equals("冷冻怪")) {
                        gameMapKind.setPlayerImage("./resources/player_4.png");
                    }
                    else if (selectedItem.equals("王军兵")) {
                        gameMapKind.setPlayerImage("./resources/player_5.png");
                    }
                    else if (selectedItem.equals("黄色果冻")) {
                        gameMapKind.setPlayerImage("./resources/player_6.png");
                    }
                    else if (selectedItem.equals("经典")) {
                        gameMapKind.setPlayerImage("./resources/player_7.png");
                    }
                }
            }
        });
    }

    public void setButton_returnEvent() {
        button_return.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(DataHouse.music).start();
                DataHouse.mainJFrame.setVisible(true);
                setVisible(false);
            }
        });
    }
}
