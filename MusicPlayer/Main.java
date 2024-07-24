package MusicPlayer;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {//测试程序
        Scanner sc = new Scanner(System.in);
        Musics.initMusic();//音乐初始化
        BackgroundMusicPlayer.setVolume(6f);//设置背景音乐音量
        MusicPlayer music = Musics.backgroundMusic.get(1);//获取对应音乐
        new Thread(music).start();//播放音乐
        int i = sc.nextInt();//用于模仿主线程中的部分函数
        music.changeState();
        while(true);//用于模仿主线程
        //模块介绍见本包中的txt文档
    }
}
