package Wanf;

import Project_PushBox.DataHouse;

import java.util.ArrayList;
import java.util.Scanner;

import static Wanf.MapDatabaseOperation.DeleteMapDatabase;

public class Test {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
       for(int i=1;i<=18;i++){
           Level level = new Level();
           level.setMap_name("kind1");
           level.setNum(i);
           level.setMap_Id(i);
           LevelDatabaseOperation.WriteLevelDatabase(level);
       }
    }
}
