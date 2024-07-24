package MusicPlayer;

import java.util.HashMap;

public class Musics {
    public static HashMap<Integer,BackgroundMusicPlayer> backgroundMusic = new HashMap<>();
    public static HashMap<Integer,ClickMusicPlayer> clickMusic = new HashMap<>();
    public static HashMap<Integer, LoadMusicPlayer> LoadMusic = new HashMap<>();
    public static void initMusic(){
        for(int i = 1;i <= 5;i++){
            BackgroundMusicPlayer bgMusic = new BackgroundMusicPlayer("MusicResource/BackgroundSong" + i + ".wav");
            backgroundMusic.put(i,bgMusic);
        }
        for(int i = 1;i <= 2;i++){
            ClickMusicPlayer clm = new ClickMusicPlayer("MusicResource/ClickMusic" + i + ".wav");
            clickMusic.put(i,clm);
        }
        for(int i = 1;i <= 2;i++){
            LoadMusicPlayer load = new LoadMusicPlayer("MusicResource/LoadMusic" + i + ".wav");
            LoadMusic.put(i,load);
        }
    }
}
