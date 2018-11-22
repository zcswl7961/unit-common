package com.SpringBootSwaggerDemo.demo;

/**
 * Created by zhoucg on 2018-10-26.
 */
public class LamdbaTest {


    public static void main(String[] args) {
        Runnable runnable = () ->{
            try{
                System.out.println(1);
            } catch ( Exception e){
                e.getMessage();
            }
        };
        Thread thread = new Thread(runnable);
    }
}
