package Project_PushBox;

public class GameMapFactory {
    public static GameMapKind createGameMapKind(String kind) {
        GameMapKind gameMapKind = null;
        if (kind.equals("kind1")) {
            gameMapKind = new GameMap_1();
        }
        else if (kind.equals("kind2")) {
            gameMapKind = new GameMap_2();
        }
        else if (kind.equals("kind3")) {
            gameMapKind = new GameMap_3();
        }
        else if (kind.equals("kind4")) {
            gameMapKind = new GameMap_4();
        }
        return gameMapKind;
    }
}
