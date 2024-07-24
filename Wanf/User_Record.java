package Wanf;

public class User_Record {
    private User user;
    private Level level;
    public User_Record(User user, Level level) {
        this.user = user;
        this.level = level;
    }
    public User_Record(){}
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
