package com.zcswl.pattern.builder;

import com.mysql.jdbc.Driver;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;


/**
 * @author zhoucg
 * @date 2020-07-11 10:12
 */
public class Test {


    public static void main(String[] args) {
        ComputerDirector director=new ComputerDirector();//1
        ComputerBuilder builder=new MacComputerBuilder("I5处理器","三星125");//2
        director.makeComputer(builder);//3
        Computer macComputer=builder.getComputer();//4
        System.out.println("mac computer:"+macComputer.toString());


        ComputerBuilder lenovoBuilder = new LenovoComputerBuilder("I7处理器","海力士222");
        director.makeComputer(lenovoBuilder);
        Computer lenovoComputer=lenovoBuilder.getComputer();
        System.out.println("lenovo computer:"+lenovoComputer.toString());

        /**
         * 典型的建造者设计模式
         */
        DataSourceBuilder.create().type(HikariDataSource.class)
                .url(String.format("jdbc:mysql://%s:%s/%s?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=UTF-8", "127.0.0.1", 3306, "nacos-confg"))
                .driverClassName(Driver.class.getName())
                .username("root")
                .password("123456")
                .build();

    }
}
