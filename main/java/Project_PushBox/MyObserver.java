package Project_PushBox;
import Wanf.Level;
import Wanf.UserRecordDatabase;
import org.example.TopList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;

public class MyObserver implements Observer {
    ///GameMapKind gameMapKind;
    SelectFrame selectFrame;
    GameJFrame gameJFrame;
    @Override
    public void update(Observable o, Object arg) {
        int  currentLevelIndex=gameJFrame.getCurrentLevelIndex();
        GamePane gamePane=(GamePane) gameJFrame.getMainPanel().getComponent(currentLevelIndex);
        Level level=new Level();

        level.setNum(currentLevelIndex+1);


        level.setSteps(gamePane.getGameMapKind().getHh());

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedNow = now.format(formatter);

        level.setTime(formattedNow);

        level.setCompleted(true);


        UserRecordDatabase.InsertUserLevelRecord(DataHouse.NowUser,level);
        System.out.println(currentLevelIndex);
//        gameJFrame.setVisible(false);
        TopList topList = new TopList(UserRecordDatabase.GetUserRecord_User(DataHouse.NowUser));
        topList.setVisible(true);
    }


    public MyObserver(SelectFrame selectFrame){
        this.selectFrame = selectFrame;
    }

    public GameJFrame getGameJFrame() {
        return gameJFrame;
    }

    public void setGameJFrame(GameJFrame gameJFrame) {
        this.gameJFrame = gameJFrame;
    }


    public SelectFrame getSelectFrame() {
        return selectFrame;
    }

    public void setSelectFrame(SelectFrame selectFrame) {
        this.selectFrame = selectFrame;
    }
}