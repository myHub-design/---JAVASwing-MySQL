package Project_PushBox;

import MusicPlayer.Musics;

import javax.swing.*;

public class MainFrame extends JFrame {
    private MainPanel mainPanel = new MainPanel();

    public MainFrame() {
        new Thread(Musics.backgroundMusic.get(2)).start();
        add(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
