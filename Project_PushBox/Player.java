package Project_PushBox;

public class Player {
    // 玩家当前位置的X坐标
    private int playerX = 0;
    // 玩家当前位置的Y坐标
    private int playerY = 0;
    // 目标点的数量
    private int goalNum = 0;
    // 玩家移动的步数
    private int steps = 0;
    // 玩家是否在目标位置
    private boolean inGoal = false;

    // 默认构造方法
    public Player() {
    }

    // 获取玩家的X坐标
    public int getPlayerX() {
        return playerX;
    }

    // 设置玩家的X坐标
    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    // 获取玩家的Y坐标
    public int getPlayerY() {
        return playerY;
    }

    // 设置玩家的Y坐标
    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }

    // 判断玩家是否在目标位置
    public boolean isInGoal() {
        return inGoal;
    }

    // 设置玩家是否在目标位置
    public void setInGoal(boolean inGoal) {
        this.inGoal = inGoal;
    }

    // 查找玩家在地图上的初始位置，并将该位置设置为空地
    public void findPlayer(int[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 2 || map[i][j] == 6) {
                    playerX = i;
                    playerY = j;
                }
            }
        }
    }

    // 移动玩家的位置，并处理推动箱子的逻辑
    public boolean movePlayer(int x, int y, int[][] map) {
        steps++;
        map[playerX][playerY] = 0;
        int newX = playerX + x;
        int newY = playerY + y;

        // 如果目标位置是墙壁，则无法移动
        if (map[newX][newY] == 1) {
            map[playerX][playerY] = 2;
            return false;
        }
        // 如果目标位置是箱子，则尝试推动箱子
        if (map[newX][newY] == 3) {
            int newBoxX = newX + x;
            int newBoxY = newY + y;
            // 如果箱子前面的位置不是墙壁、另一个箱子或用户，则可以移动
            if (map[newBoxX][newBoxY] != 1 && map[newBoxX][newBoxY] != 2 && map[newBoxX][newBoxY] != 3) {
                map[newBoxX][newBoxY] = 3; // 移动箱子
                map[newX][newY] = 0; // 将箱子原来的位置设置为空地
            } else {
                map[playerX][playerY] = 2;
                return false; // 无法推动箱子，终止移动
            }
        }
        // 更新玩家的位置
        playerX = newX;
        playerY = newY;
        map[newX][newY] = 2;
        return true;
    }
}
