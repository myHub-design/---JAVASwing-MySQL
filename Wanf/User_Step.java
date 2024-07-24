package Wanf;

//玩家分数类
public class User_Step {
    private String username;
    private int steps;
    public User_Step(String username, int steps) {
        this.username = username;
        this.steps = steps;
    }
    public User_Step() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }
}
