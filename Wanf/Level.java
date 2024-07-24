package Wanf;
//关卡类
public class Level {
    //level记录第几关卡
    private int num = 1;
    //记录该关卡对应的地图编号
    private int map_Id = 1;
    //关卡状态（即解锁和未解锁）
    private boolean state = false;
    //关卡对应的地图
    Map_detail map_detail = null;
    //关卡对应地图的名称
    private String map_name = "";

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    //玩家该关卡的步数
    private int steps = 0;
    //玩家是否通关

    String time;
    private boolean completed = false;
    public Level() {}
    public Level(int num, int map_Id) {
        this.num = num;
        this.map_Id = map_Id;
        map_detail = new Map_detail(map_Id);
    }

    public int getLevel() {
        return num;
    }

    public void setLevel(int level) {
        this.num = level;
    }

    public int getMap_Id() {
        return map_Id;
    }

    public void setMap_Id(int map_Id) {
        this.map_Id = map_Id;
        map_detail = new Map_detail(map_Id);
    }

    public boolean isState() {
        return state;
    }

    public void setState() {
        this.state = !state;
    }

    public Map_detail getMap_detail() {
        return map_detail;
    }

    public void setMap_detail(Map_detail map_detail) {
        this.map_detail = map_detail;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
    public String getMap_name() {
        return map_name;
    }
    public void setMap_name(String map_name) {
        this.map_name = map_name;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }
    public boolean isCompleted() {
        return completed;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
