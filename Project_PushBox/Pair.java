package Project_PushBox;

//存终点坐标
public class Pair {
    int x, y;
    Pair(int x, int y){
        this.x = x;
        this.y = y;
    }
    boolean equals(Pair p){
        return x == p.x && y == p.y;
    }
}
