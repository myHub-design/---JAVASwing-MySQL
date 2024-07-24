package MusicPlayer;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LoadMusicPlayer extends MusicPlayer {
    private final Lock lock = new ReentrantLock();
    private static int number = 2;
    private static float volume = -20f;

    @Override
    public float getVolume() {
        return LoadMusicPlayer.volume;
    }

    public static void setVolume(float volume) {
        LoadMusicPlayer.volume = volume;
    }

    @Override
    public void run() {
        lock.lock();
        MusicOperation.OncePlay(this,lock);
    }

    public LoadMusicPlayer() {

    }

    public LoadMusicPlayer(String fileName) {
        super(fileName);
    }
    public void changeMusic(){//这一模块是否真的有用
        if(this.isRunning()) {
            this.changeState();
            this.setFileName(Musics.LoadMusic.get(number).getFileName());
            this.changeState();
            new Thread(this).start();//播放音乐
            number++;
            if (number > 2) {
                number -= 2;
            }
        }
    }
}
