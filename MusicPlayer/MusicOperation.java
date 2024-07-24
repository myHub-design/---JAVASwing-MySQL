package MusicPlayer;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.locks.Lock;

public class MusicOperation {
    public synchronized static void LoopingPlay(MusicPlayer player){
        try {
            // 打开音频输入流
            Clip bgm = AudioSystem.getClip();
            InputStream is = MusicOperation.class.getClassLoader().getResourceAsStream(player.getFileName());
            AudioInputStream ais = AudioSystem.getAudioInputStream(is);

            bgm.open(ais);
            setVolume(bgm, player.getVolume());
            looping(player, bgm, ais);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void OncePlay(MusicPlayer player, Lock lock){
        try {
            // 打开音频输入流
            Clip bgm = AudioSystem.getClip();
            InputStream is = MusicOperation.class.getClassLoader().getResourceAsStream(player.getFileName());
            AudioInputStream ais = AudioSystem.getAudioInputStream(is);

            bgm.open(ais);
            setVolume(bgm, player.getVolume());
            bgm.start();

            while (bgm.isRunning()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (!bgm.isActive()) {
                    bgm.stop();
                    bgm.close();
                    ais.close();
                }
            }
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private static void setVolume(Clip clip, float volume) {
        // 检查并设置支持的音量控件
        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(volume);
        } else if (clip.isControlSupported(FloatControl.Type.VOLUME)) {
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.VOLUME);
            volumeControl.setValue(volume);
        } else {
            System.out.println("Volume control not supported");
        }
    }

    public static void looping(MusicPlayer player, Clip bgm, AudioInputStream ais) {
        while (player.isRunning()) {
            if (!bgm.isActive()) {
                bgm.setFramePosition(0);
                bgm.start();
            }
        }
        bgm.stop();
        bgm.close();
        try {
            ais.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
