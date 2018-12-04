package com.SpringBootSwaggerDemo.demo;

/**
 *
 * @author zhoucg
 * @date 2018-11-30
 */
public class ForTest {

    public static void main(String[] args) {
        int a=0;
        LABEL:for(;;) {
            for(int i =0;i<12;i++) {
                if(i==11) {
                    a = i;
                    break LABEL;
                }
            }
        }
        System.out.println(a);
    }
}
