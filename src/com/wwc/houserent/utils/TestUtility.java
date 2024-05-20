package com.wwc.houserent.utils;

/**
 * 这是一个工具测试类，用于测试各工具的用法
 * （一个类中只有一个main方法，一个项目中可以用多个main方法，通过main方法的调用关系串联起其他的类）
 * （main方法是程序的起点，同样的类，起点不同，调用顺序不同，所得到的结果也不同）
 */
public class TestUtility {
    public static void main(String[] args) {

        //这是一个测试类，使用完毕，就可以删除了
        //要求输入一个字符串，长度最大为3
        String s1 = Utility.readString(3);
        System.out.println("s1="+s1);

        //要求输入一个字符串，长度最大为10，如果用户直接回车，就给一个默认值
        //把该方法当做一个api使用即可
        //默认值：WWC
        String s2 = Utility.readString(10, "WWC");
        System.out.println("s2="+s2);


    }
}
