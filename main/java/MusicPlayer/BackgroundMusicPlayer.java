package MusicPlayer;


public class BackgroundMusicPlayer extends MusicPlayer {
    public static int number = 2;
    private static float volume = -20f;

    public static void setVolume(float volume) {
        BackgroundMusicPlayer.volume = volume;
    }

    @Override
    public void run(){
        MusicOperation.LoopingPlay(this);
    }

    public BackgroundMusicPlayer() {
    }
    public float getVolume(){
        return BackgroundMusicPlayer.volume;
    };
    public BackgroundMusicPlayer(String fileName) {
        super(fileName);
    }

    public void changeMusic() {//这一模块是否真的有用
        if(this.isRunning()) {
            this.changeState();
            this.setFileName(Musics.backgroundMusic.get(number).getFileName());
            this.changeState();
            new Thread(this).start();//播放音乐
            number++;
            if (number > 5) {
                number -= 5;
            }
        }
    }
}
