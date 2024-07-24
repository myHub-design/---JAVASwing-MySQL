package Wanf;

//地图容量类 存放角色，箱子，和箱子的目标点信息
public abstract class Container {
    int x;
    int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
