package Wanf;

import java.util.ArrayList;

public class Map_detail {
    //地图的长度
    private int length;
    //地图的宽度
    private int width;
    //地图大小与分布设计
    private int mapgrounp[][];
    //地图里面的角色
    Character character = new Character();
    //地图里面的箱子信息
    ArrayList<Chest> chests = new ArrayList<>();
    //地图里面的目标点信息
    ArrayList<Target_point> target_points = new ArrayList<>();
    //地图编号 不同关卡有不同的地图 即根据不同的地图编号来获取地图
    private int id;
    public Map_detail() {}
    public Map_detail(int id, int length, int width) {
        this.length = length;
        this.width = width;
        this.id = id;
        //地图大小
        mapgrounp = new int[length][width];
    }
    public Map_detail(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public int[][] getMapgrounp() {
        return mapgrounp;
    }
    public void setMapgrounp(int[][] mapgrounp) {
        this.mapgrounp = mapgrounp;
    }
    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }

    public Character getCharacter() {
        return character;
    }

    public ArrayList<Chest> getChests() {
        return chests;
    }

    public ArrayList<Target_point> getTarget_points() {
        return target_points;
    }

    //初始化地图，地图数据从数据库中读取
    public void primarySetMap(){
        mapgrounp = MapDatabaseOperation.QueryMapDatabase(id);
        //获取出的地图的大小
        this.length = mapgrounp.length;
        this.width = mapgrounp[0].length;
        //获取地图数据以后设置角色的原始位置以及箱子的原始位置和目标点的位置坐标
        setPrimaryCharacterPoint();
        setPrimaryChestPoint();
        setTargetPoints();
    }
    //初始化玩家的位置
    public void setPrimaryCharacterPoint(){
        for(int i = 0; i < mapgrounp.length; i++){
            for(int j = 0; j < mapgrounp[i].length; j++){
                if(mapgrounp[i][j] == 2){
                    character.setX(i);
                    character.setY(j);
                    break;
                }
            }
        }
    }
    //初始化箱子的位置信息
    public void setPrimaryChestPoint(){
        for(int i = 0; i < mapgrounp.length; i++){
            for(int j = 0; j < mapgrounp[i].length; j++){
                if(mapgrounp[i][j] == 3){
                chests.add(new Chest(i, j));
                }
            }
        }
    }
    //设置目标位置
    public void setTargetPoints(){
        for(int i = 0; i < mapgrounp.length; i++){
            for(int j = 0; j < mapgrounp[i].length; j++){
                if(mapgrounp[i][j] == 4){
                    target_points.add(new Target_point(i,j));
                }
            }
        }
    }
}
