package org.example;

import Project_PushBox.DataHouse;
import Wanf.User;
import Wanf.UserDatabaseOperation;

public class LogOnAndSignUp {
    User user;
//登录
    public User logOn(int id, String password) {
        user = UserDatabaseOperation.getUserInformation(id);
        if (user == null) return null;
        if (user.getPassword().equals(password)) {
            DataHouse.NowUser = user;
            return user;
        }
        else return null;

    }
// 注册
    public boolean signUp(User user) {
        User user1 = UserDatabaseOperation.getUserInformation(user.getUser_id());
        if (user1 == null) {
            UserDatabaseOperation.registerUserInformation(user);
            return true;
        } else return false;
    }

    public void AdministratorLogin(int id, String password) {

    }
}
