package Wanf;

//箱子的目标点
public class Target_point extends Container{
    //目标点状态，判断是否有箱子
    private boolean state = false;
    public Target_point(){}
    public Target_point(int x, int y){
        this.x = x;
        this.y = y;
    }
    //用于箱子离开或者到达目标点的状态改变
    public void updateState() {
        this.state = !state;
    }
}
