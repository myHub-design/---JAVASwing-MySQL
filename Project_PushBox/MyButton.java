package Project_PushBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyButton extends JButton {
    public Image ButtonImage_unClick = new ImageIcon("./resources/diamond_block.png").getImage();
    public Image ButtonImage_Clicked = new ImageIcon("./resources/clicked.png").getImage();
    Font ButtonFont = new Font("楷体", Font.BOLD, 14);
    public Timer timer = new Timer(500, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt) {
            setIcon(new ImageIcon(ButtonImage_unClick));
        }
    });
    public MyButton(String title) {
        super(title);
        makeButtonTransparent(this);
        setSize(GameJFrame.gameSize * 2 , GameJFrame.gameSize);
        ButtonImage_unClick = ButtonImage_unClick.getScaledInstance(getSize().width, getSize().height, Image.SCALE_SMOOTH);
        ButtonImage_Clicked = ButtonImage_Clicked.getScaledInstance(getSize().width, getSize().height, Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(ButtonImage_unClick));
        setContentAreaFilled(false);
        setFont(ButtonFont);
        setHorizontalTextPosition(SwingConstants.CENTER);
    }
    public MyButton(String title, int x, int y, int width, int height) {
        setFont(ButtonFont);
        setText(title);
        ChangePositionAndSize(x, y, width, height);
    }

    public void ChangePositionAndSize(int x, int y, int width, int height) {
        makeButtonTransparent(this);
        setBounds(x, y, width, height);
        ButtonImage_unClick = ButtonImage_unClick.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(ButtonImage_unClick));
        setContentAreaFilled(false);
        setFont(ButtonFont);
        setHorizontalTextPosition(SwingConstants.CENTER);
        setIcon(new ImageIcon(ButtonImage_unClick));
    }

    private void makeButtonTransparent(MyButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setForeground(Color.BLACK);
    }

    public void setSpecial() {
        setIcon(new ImageIcon(ButtonImage_Clicked));
        timer.setRepeats(false); // Only execute once
        timer.start();
    }

}