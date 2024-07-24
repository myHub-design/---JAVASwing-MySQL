package Project_PushBox;

import javax.swing.*;
import java.awt.*;

public class GamePass extends JDialog{
    JPanel pane = new JPanel();
    JLabel label = new JLabel(" 分数:114514");
    MyButton pass = new MyButton("你过关");
    public GamePass() {
        pane.setLayout(null);
        setPreferredSize(new Dimension(300, 550));
        label.setBounds(100,400, 100 , 50 );
        pass.ChangePositionAndSize(100,450, 100, 50);
        pass.addActionListener(e -> {
            dispose();
        });
        pane.add(pass);
        pane.add(label);
        add(pane);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
