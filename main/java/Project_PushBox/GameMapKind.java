package Project_PushBox;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameMapKind {

    ArrayList<Pair> end_ponint = new ArrayList<>(); // 记录所有目标点的位置
    ArrayList<PointType>[] box_and_player_ponints = new ArrayList[500]; // 记录每一步的箱子和玩家位置
    int hh = -1; // 步数计数器

    // 默认的地图布局，二维数组表示
    // 1 表示墙壁，0 表示空地，2 表示玩家初始位置，3 表示箱子，4 表示目标点
    private int[][] map = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 2, 0, 0, 0, 3, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 4, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    int start_version_map[][]=new int[10][10];

    // 定义各种图片资源，包括墙壁、箱子、目标点、玩家和背景
    private Image wallImage = new ImageIcon("./resources/mossy_stone_bricks.png").getImage();
    private Image boxImage = new ImageIcon("./resources/melon_side.png").getImage();
    private Image goalImage = new ImageIcon("./resources/bee_nest_top.png").getImage();
    private Image playerImage = new ImageIcon("./resources/player_2.png").getImage();
    private Image background = new ImageIcon("./resources/cobblestone.png").getImage();

    // 构造方法，初始化时加载图片资源
    public GameMapKind() {
        loadImages();
    }

    // 加载图片并按比例缩放到指定大小
    private void loadImages() {
        wallImage = wallImage.getScaledInstance(GameJFrame.gameSize, GameJFrame.gameSize, Image.SCALE_SMOOTH);
        boxImage = boxImage.getScaledInstance(GameJFrame.gameSize, GameJFrame.gameSize, Image.SCALE_SMOOTH);
        goalImage = goalImage.getScaledInstance(GameJFrame.gameSize, GameJFrame.gameSize, Image.SCALE_SMOOTH);
        playerImage = playerImage.getScaledInstance(GameJFrame.gameSize, GameJFrame.gameSize, Image.SCALE_SMOOTH);
        background = background.getScaledInstance(GameJFrame.gameSize, GameJFrame.gameSize, Image.SCALE_SMOOTH);
    }

    public  void copyMapToStart_version_map(){
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                start_version_map[i][j] = map[i][j];
            }
        }
    }
    public void copyMapToMap(){
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                map[i][j] = start_version_map[i][j];
            }
        }
    }

    // 设置地图布局
    public void setMap(int[][] map) {
        this.map = map;

        copyMapToStart_version_map();

        SaveEndPoint(); // 保存目标点的位置

        eliminateSixAndSeven();

        addBoxAndPlayerPoint(); // 记录箱子和玩家的位置
    }

    // 获取当前地图布局
    public int[][] getMap() {
        return map;
    }

    // 设置各种图片资源的路径，并加载这些图片
    public void setImage(String wallImagePath, String boxImagePath, String goalImagePath,String backgroundImagePath) {
        this.wallImage = new ImageIcon(wallImagePath).getImage();
        this.boxImage = new ImageIcon(boxImagePath).getImage();
        this.goalImage = new ImageIcon(goalImagePath).getImage();
        this.background = new ImageIcon(backgroundImagePath).getImage();
        loadImages(); // 加载并缩放图片
    }

    // 获取墙壁图片
    public Image getWallImage() {
        return wallImage;
    }

    // 获取箱子图片
    public Image getBoxImage() {
        return boxImage;
    }

    // 获取目标点图片
    public Image getGoalImage() {
        return goalImage;
    }

    // 获取背景图片
    public Image getBackground() {
        return background;
    }

    // 获取玩家图片
    public Image getPlayerImage() {
        return playerImage;
    }

    public void setPlayerImage(String playerImagePath) {
        this.playerImage = new ImageIcon(playerImagePath).getImage();
        loadImages(); // 加载并缩放图片
    }

    // 保存所有目标点的位置
    public void SaveEndPoint() {
        end_ponint = new ArrayList<>();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 4||map[i][j]==6||map[i][j]==7) {
                    Pair pair = new Pair(i, j);
                    end_ponint.add(pair);
                }
            }
        }
    }

    // 判断是否所有箱子都在目标点上
    public boolean judgVictory() {
        int sum = 0;
        for (int i = 0; i < end_ponint.size(); i++) {
            Pair pair = end_ponint.get(i);
            if (map[pair.x][pair.y] == 3) sum++;
        }
        return sum == end_ponint.size();
    }

    // 重置目标点
    public void resetEndPoint() {
        for (int i = 0; i < end_ponint.size(); i++) {
            Pair pair = end_ponint.get(i);
            if (map[pair.x][pair.y] == 0) map[pair.x][pair.y] = 4;
        }
    }

    // 记录当前箱子和玩家的位置
    public void addBoxAndPlayerPoint() {
        ArrayList<PointType> box_and_player_ponint = new ArrayList<>();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 2 || map[i][j] == 3) {
                    PointType pointType = new PointType(i, j, map[i][j]);
                    box_and_player_ponint.add(pointType);
                }
            }
        }
        box_and_player_ponints[++hh] = box_and_player_ponint;
    }

    // 撤销上一步操作
    public void back(Player player) {
        if (hh > 0) {
            ArrayList<PointType> box_and_player_ponint = box_and_player_ponints[hh];
            hh--;
            ArrayList<PointType> box_and_player_ponint1 = box_and_player_ponints[hh];
            for (int i = 0; i < box_and_player_ponint.size(); i++) {
                PointType pointType = box_and_player_ponint.get(i);
                map[pointType.x][pointType.y] = 0;
            }
            for (int i = 0; i < box_and_player_ponint1.size(); i++) {
                PointType pointType = box_and_player_ponint1.get(i);
                map[pointType.x][pointType.y] = pointType.getType();
                if (pointType.getType() == 2) {
                    player.setPlayerX(pointType.x);
                    player.setPlayerY(pointType.y);
                }
            }
        }
    }
    //重开
    public void repaly(){
        //地图初始化
        copyMapToMap();
        eliminateSixAndSeven();
        //栈初始化
        hh=-1;
        box_and_player_ponints=new ArrayList[200];
        addBoxAndPlayerPoint();
    }
    public void eliminateSixAndSeven() {
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                if(map[i][j]==6)map[i][j]=2;
                if(map[i][j]==7)map[i][j]=3;
            }
        }
    }

    public int getHh() {
        return hh;
    }

    public void setHh(int hh) {
        this.hh = hh;
    }
}
