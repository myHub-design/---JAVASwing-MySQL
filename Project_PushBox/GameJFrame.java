package Project_PushBox;

import MusicPlayer.Musics;
import Wanf.Level;
import Wanf.User_Record;
import org.example.Tool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameJFrame extends JFrame {
    public static int gameSize = 50; //界面大小
    private ArrayList<GamePane> gamePanes = new ArrayList<>();//存储所有布局
    private JPanel mainPanel;
    private CardLayout cardLayout = new CardLayout();
    private MyButton button_menu = new MyButton("返回主菜单");
    private MyButton button_next = new MyButton("下一关");
    private MyButton button_return = new MyButton("回退");
    private MyButton button_replay = new MyButton("重玩");
    private int currentLevelIndex = 0;// 当前关卡索引
    private SelectFrame selectFrame;
    private MyObserver myObserver=new MyObserver(selectFrame);

    public GameJFrame() {
        runGame();
        myObserver.setGameJFrame(this);
        myObserver.setSelectFrame(selectFrame);
    }

    public void runGame() {
        setTitle("Sokoban Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(10 *gameSize, 10 * gameSize);

        mainPanel = new JPanel(cardLayout);
        loadMaps();
        add(mainPanel);

        JPanel controlPanel = new JPanel();//创建管理布局
        controlPanel.setBackground(Color.CYAN);
        button_replay.ChangePositionAndSize(gameSize * 6, 10 * gameSize, gameSize, gameSize);
        button_return.ChangePositionAndSize(gameSize * 3, 10 * gameSize, gameSize, gameSize);
        button_menu.ChangePositionAndSize(0, 10 * gameSize, gameSize * 2, gameSize);
        button_next.ChangePositionAndSize(gameSize * 7, 10 * gameSize, gameSize * 2, gameSize);
        setButtonEvent_next();
        setButtonEvent_menu();
        setButtonEvent_replay();
        setButtonEvent_return();
        controlPanel.add(button_menu);
        controlPanel.add(button_replay);
        controlPanel.add(button_return);
        controlPanel.add(button_next);
        add(controlPanel, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
        setVisible(false);
    }
//加载地图，创建每一个地图面板
    public void loadMaps() {
        int i = 0;
        for (Level level : DataHouse.getLevels()) {
            GamePane gamePane = new GamePane(level.getMap_name(), level.getMap_detail().getMapgrounp(), level.getMap_Id());
            gamePanes.add(gamePane);
            gamePane.getMyObservable().addObserver(myObserver);
            mainPanel.add(gamePane, "" + i);
            i++;
        }
    }
//点击进入下一关
    public void setButtonEvent_next() {
        button_next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(DataHouse.music).start();
                currentLevelIndex = (currentLevelIndex + 1) % mainPanel.getComponentCount();
                cardLayout.show(mainPanel, "" + currentLevelIndex);

                GamePane gamePane=(GamePane) getMainPanel().getComponent(currentLevelIndex);
                JLabel jLabel=gamePane.getFirst_steps_Label();

                User_Record user_record= Tool.getFirstUserRecord(currentLevelIndex+1);
                if(user_record!=null){
                    jLabel.setText("最佳记录:"+user_record.getUser().getName()+"-"+"关卡"+(currentLevelIndex+1)+"-"+user_record.getLevel().getSteps());
                }
                else jLabel.setText("关卡"+(currentLevelIndex+1)+"暂无通关玩家");

               // System.out.println(currentLevelIndex);

                mainPanel.getComponent(currentLevelIndex).requestFocusInWindow();
            }
        });
    }
//返回选关界面
    public void setButtonEvent_menu() {
        button_menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(DataHouse.music).start();
                Musics.backgroundMusic.get(3).changeState();
                new Thread(Musics.backgroundMusic.get(2)).start();
                setVisible(false);
                selectFrame.setVisible(true);
            }
        });
    }
    // 为重玩按钮设置事件，点击重玩当前关卡
    public void setButtonEvent_replay() {
        button_replay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(DataHouse.music).start();
                //cardLayout.show(mainPanel, "" + currentLevelIndex); // 显示当前关卡
                GamePane gamePane = (GamePane) mainPanel.getComponent(currentLevelIndex); // 获取当前关卡的面板
                gamePane.getGameMapKind().repaly();
                gamePane.getPlayer().findPlayer(gamePane.getGameMapKind().getMap());
                gamePane.stepsLabel.setText("Steps: " + gamePane.getGameMapKind().getHh());
                gamePane.revalidate();
                gamePane.repaint();
                mainPanel.getComponent(currentLevelIndex).requestFocusInWindow(); // 请求焦点
            }
        });
    }

    // 为返回按钮设置事件，点击返回当前关卡
    public void setButtonEvent_return() {
        button_return.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(DataHouse.music).start();
                cardLayout.show(mainPanel, "" + currentLevelIndex); // 显示当前关卡
                mainPanel.getComponent(currentLevelIndex).requestFocusInWindow(); // 请求焦点
                GamePane x = (GamePane) mainPanel.getComponent(currentLevelIndex); // 获取当前关卡的面板
                x.getGameMapKind().back(x.getPlayer()); // 还原玩家位置
                x.getGameMapKind().resetEndPoint(); // 重置终点位置
                x.stepsLabel.setText("Steps: " + x.getGameMapKind().getHh());
                x.revalidate();
                x.repaint();
                x.repaint(); // 重绘面板
                x.requestFocusInWindow(); // 请求焦点
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public int getCurrentLevelIndex() {
        return currentLevelIndex;
    }

    public ArrayList<GamePane> getGamePanes() {
        return gamePanes;
    }

    public void setSelectFrame(SelectFrame selectFrame) {
        this.selectFrame = selectFrame;
    }

    public void setCurrentLevelIndex(int currentLevelIndex) {
        this.currentLevelIndex = currentLevelIndex;
        myObserver.setSelectFrame(selectFrame);
    }

}
