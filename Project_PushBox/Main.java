package Project_PushBox;

import Effect.FadeEffectDemo;
import MusicPlayer.Musics;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Musics.initMusic();
        new Thread(Musics.LoadMusic.get(2)).start();//播放音乐
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FadeEffectDemo();
            }
        });
    }
}

