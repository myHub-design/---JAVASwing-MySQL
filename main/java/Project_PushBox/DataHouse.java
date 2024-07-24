package Project_PushBox;


import MusicPlayer.*;
import Wanf.*;
import org.example.TopList;

import java.util.ArrayList;

public class DataHouse {
    public static GameJFrame gameJFrame;

    public static SelectFrame selectJFrame;

    public static MainFrame mainJFrame;

    public static SetFrame setJFrame;

    public static TopList topListJFame;

    public static User NowUser;

    public static MusicPlayer music = Musics.clickMusic.get(2);;

    public static ArrayList<Level> getLevels() {
        return LevelDatabaseOperation.ReadLevelDatabase();
    }

    public static void LoadingGame() {
        SetFrame setFrame = new SetFrame();
        setJFrame = setFrame;
        GameJFrame gameFrame = new GameJFrame();
        gameJFrame = gameFrame;
        SelectFrame selectFrame = new SelectFrame(gameFrame);
        selectJFrame = selectFrame;
        gameFrame.setSelectFrame(selectFrame);
        MainFrame mainFrame = new MainFrame();
        mainJFrame = mainFrame;
    }
}
