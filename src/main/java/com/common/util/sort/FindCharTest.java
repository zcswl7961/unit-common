package com.common.util.sort;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zhoucg
 * @date: 2019-08-15
 */
public class FindCharTest {

    public static void main(String[] args) {
        String s="abcdefghijklba";
        System.out.println(findCharMost(s));
    }
    public static char findCharMost(String s) {
        //记录字符串中的字符
        List<Character> list=new ArrayList<>();
        //记录对应字符出现的次数
        List<Integer> list2=new ArrayList<>();
        //因为题目要求最先达到次数，那么我就倒着来遍历字符串
        //那么记录顺序的list中就有字符串的顺序了
        for(int i=s.length()-1;i>=0;i--){
            if(!list.contains(s.charAt(i))) {
                //如果字符没有出现过,那么add到list中
                list.add(s.charAt(i));
                //同时在list2对应位置赋值为1
                list2.add(1);
            }else {
                //如果字符出现过，那么找到其对应的index
                int index=list.indexOf(s.charAt(i));
                //在list2对应位置将次数++
                list2.set(index,list2.get(index)+1);
            }
        }
        int max=list2.get(list2.size()-1);
        int j=list2.size()-1;
        //记录顺序的list中是按后到的字符放在前面的顺序
        //所以我们只要倒着遍历找到最大值就可以了
        for(int i=list2.size()-2;i>=0;i--){
            if(list2.get(i)>max){
                max=list2.get(i);
                j=i;
            }
        }
        return list.get(j);
    }

}
