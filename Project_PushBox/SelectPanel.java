package Project_PushBox;

import MusicPlayer.Musics;
import Wanf.Level;
import Wanf.User_Record;
import org.example.Main;
import org.example.Tool;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;

public class SelectPanel extends JLayeredPane {
    private ArrayList<MyButton> myButtons = new ArrayList<>();
    private MyButton button_next = new MyButton("下一页");
    private MyButton button_last = new MyButton("上一页");
    private MyButton button_return = new MyButton("返回");
    private ImageIcon icon= new ImageIcon("./resources/img_8.png");

    public SelectPanel() {
        setLayout(null);

        setPreferredSize(new Dimension(500, 500));
        button_last.ChangePositionAndSize(0, 450, 100, 50);
        button_next.ChangePositionAndSize(400, 450, 100, 50);
        button_return.ChangePositionAndSize(200, 450, 100, 50);

        setButton_returnEvent();

        add(button_last, new Integer(1)); //设置上层
        add(button_next, new Integer(1));
        add(button_return, new Integer(1));
    }

    public void addButtons(int count, int pageIndex) {
        int x = 50, y = 50, i = 0;
        for (y = 25; y < 425; y += 100) {
            for (x = 25; x <= 450; x += 100) {
                i++;
                MyButton myButton = new MyButton("" + (i + pageIndex * 20));
                myButton.ChangePositionAndSize(x,y, 50, 50);
                myButtons.add(myButton);
                add(myButton, new Integer(1));
                if (i == count) {
                    return;
                }
            }
        }
    }

    public void setButtonEvent(MyButton myButton, GameJFrame gameJFrame, SelectFrame selectFrame,int levelIndex) {
        myButton.addActionListener(e -> {
            new Thread(DataHouse.music).start();
            Musics.backgroundMusic.get(2).changeState();
            new Thread(Musics.backgroundMusic.get(3)).start();

            gameJFrame.getCardLayout().show(gameJFrame.getMainPanel(), ""+ levelIndex);
            gameJFrame.getMainPanel().getComponent(levelIndex).requestFocusInWindow();

            GamePane gamePane=(GamePane) gameJFrame.getMainPanel().getComponent(levelIndex);
            JLabel jLabel=gamePane.getFirst_steps_Label();

            User_Record user_record= Tool.getFirstUserRecord(levelIndex+1);
            if(user_record!=null){
                jLabel.setText("最佳记录:"+user_record.getUser().getName()+"-"+"关卡"+(levelIndex+1)+"-"+user_record.getLevel().getSteps());
            }
            else jLabel.setText("关卡"+(levelIndex+1)+"-"+"暂无通关玩家");

            gameJFrame.setVisible(true);
            selectFrame.setVisible(false);
            gameJFrame.setCurrentLevelIndex(levelIndex);
        });
    }

    public ArrayList<MyButton> getMyButtons() {
        return myButtons;
    }

    public void setButton_nextEvent(SelectFrame selectFrame) {
        button_next.addActionListener(e -> {
            new Thread(DataHouse.music).start();
            selectFrame.pageIndex = (selectFrame.pageIndex + 1) % selectFrame.getSelectPanels().size();
            selectFrame.getCardLayout().show(selectFrame.getMainPanel(), "" + selectFrame.pageIndex);
        });
    }

    public void setButton_lastEvent(SelectFrame selectFrame) {
        button_last.addActionListener(e -> {
            new Thread(DataHouse.music).start();
            selectFrame.pageIndex = (selectFrame.pageIndex - 1);
            if (selectFrame.pageIndex < 0) {
                selectFrame.pageIndex += selectFrame.getSelectPanels().size();
            }
            selectFrame.getCardLayout().show(selectFrame.getMainPanel(), "" + selectFrame.pageIndex);
        });
    }

    public void setButton_returnEvent() {
        button_return.addActionListener(e -> {
            new Thread(DataHouse.music).start();
            DataHouse.mainJFrame.setVisible(true);
            DataHouse.selectJFrame.setVisible(false);
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (icon != null) {
            g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }

}
