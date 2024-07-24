package Project_PushBox;

import java.util.Observable;

public class MyObservable extends Observable {
    public GameMapKind gameMapKind;
    public MyObservable(GameMapKind gameMapKind) {
        this.gameMapKind = gameMapKind;
    }

    public GameMapKind getGameMapKind() {
        return gameMapKind;
    }

    public void setGameMapKind(GameMapKind gameMapKind) {
        this.gameMapKind = gameMapKind;
        setChanged();
        notifyObservers();
    }

    public MyObservable() {
    }
}