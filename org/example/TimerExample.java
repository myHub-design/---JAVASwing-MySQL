package org.example;

import java.util.Timer;
import java.util.TimerTask;

public class TimerExample {
    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                triggerMethod();
            }
        };

        // 安排任务从现在开始每隔2秒执行一次
        timer.scheduleAtFixedRate(task, 0, 2000);
    }

    // 每隔2秒触发的方法
    public static void triggerMethod() {
        System.out.println("Method triggered at: " + System.currentTimeMillis());
        // 在这里添加需要执行的逻辑
    }
}
