package org.example;

import javax.swing.*;
import java.awt.*;

public class TabbedPaneExample extends JFrame {
    public TabbedPaneExample() {
        setTitle("TabbedPane Example");
        setSize(700, 500);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();


       for(int i=1;i<=25;i++){
           ChatAppPanel chatAppPanel = new ChatAppPanel(i);
           tabbedPane.addTab("关卡"+i, chatAppPanel);
       }


        add(tabbedPane);
        setVisible(true);
    }
    public static void main(String[] args) {
        new TabbedPaneExample();
    }
}
