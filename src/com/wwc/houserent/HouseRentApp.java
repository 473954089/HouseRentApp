package com.wwc.houserent;
import com.wwc.houserent.view.HouseView;

/**
 * 房屋出租系统（整体上类似于零钱通项目，但多了“分层模式”的思想
 * 后续完善内容：
 * 1.数组扩容
 * 2.修改modifyHouse()方法，封装到Service层
 */
public class HouseRentApp {
    public static void main(String[] args) {
        //创建HouseView对象，并且显示主菜单，是整个程序的入口
        new HouseView().mainMenu();//此处用匿名对象即可，因为new完对象之后就直接调用方法进行循环
        System.out.println("你退出了房屋出租系统");
    }
}
