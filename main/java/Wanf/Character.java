package Wanf;

public class Character extends Container{
    public Character(){}
    public Character(int x,int y){
        this.x = x;
        this.y = y;
    }
    //此种情况是横坐标为x
    //玩家向上移动
    public int Move_Up(int y){
        return y + 1;
    }
    //玩家向下移动
    public int Move_Down(int y){
        return y - 1;
    }
    //玩家向左移动
    public int Move_Left(int x){
        return x - 1;
    }
    //玩家向右移动
    public int Move_Right(int x){
        return x + 1;
    }
}
