package com.common.spark;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Created by zhoucg on 2019-01-30.
 */
public class PeopleInfoFileGenerator {

    public static void main(String[] args){
        File file = new File("F:\\PeopleInfo.txt");

        try {
            Random random = new Random();//生成随机数
            FileWriter fileWriter = new FileWriter(file);//新建一个文件
            for (int i=1;i<=1000000;i++){   //生成10万个数字
                int height = random.nextInt(220);
                if (height < 50) {
                    height = height + 50;
                }
                String  gender = getRandomGender(); //性别方法
                if (height < 100 && gender == "M") {
                    height = height + 100;
                }
                if (height < 100 && gender == "F") {
                    height = height + 40;
                }
                fileWriter.write( i + " " + getRandomGender() + " " + height); //文件格式：ID 性别 身高
                fileWriter.write(System.getProperty("line.separator"));
            }
            fileWriter.flush();
            fileWriter.close();
            System.out.println("People Information File generated successfully.");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static String getRandomGender(){ //构建一个随机生成性别方法
        Random random = new Random();
        int randomNum = random.nextInt(2) + 1;
        if( randomNum % 2 == 0){
            return "M";
        }else{
            return "F";
        }
    }
}
