package org.example;

import Project_PushBox.DataHouse;
import Wanf.Level;
import Wanf.User;
import Wanf.UserRecordDatabase;
import Wanf.User_Record;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

public class Tool {
    static public User_Record getFirstUserRecord(int map_num) {
        Level level=new Level();
        level.setNum(map_num);
        ArrayList<User_Record> userRecords=UserRecordDatabase.GetUserRecord_Level(level);
        if(userRecords.size()==0)return null;
        Comparator<User_Record>  comparator=new Comparator<User_Record>() {
            public int compare(User_Record o1, User_Record o2) {
                return o1.getLevel().getSteps()-o2.getLevel().getSteps();
            }
        };
        userRecords.sort(comparator);
        //if(userRecords.size()==0)return null;
        return userRecords.get(0);
    }


    static  public String[][] levelDataForId(int map_num){
         Level level=new Level();
         level.setNum(map_num);
         ArrayList<User_Record> userRecords=UserRecordDatabase.GetUserRecord_Level(level);
         if(userRecords.size()==0)return null;
        Comparator<User_Record>  comparator=new Comparator<User_Record>() {
            public int compare(User_Record o1, User_Record o2) {
                return o1.getLevel().getSteps()-o2.getLevel().getSteps();
            }
        };
        userRecords.sort(comparator);

        String[][] topList=new String[userRecords.size()][4];

        int first_step=userRecords.get(0).getLevel().getSteps();
        for(int i=0;i<userRecords.size();i++){

            topList[i][0]=userRecords.get(i).getUser().getName();
            topList[i][1]=String.valueOf(userRecords.get(i).getLevel().getSteps());
            topList[i][2]=String.valueOf(100*first_step/userRecords.get(i).getLevel().getSteps());
            topList[i][3]=String.valueOf((i+1));
            System.out.println(topList[i][0]+" "+topList[i][1]+" "+topList[i][2]);
        }
        return topList;
    }
}
