package com.wwc.houserent.view;

import com.wwc.houserent.domain.House;
import com.wwc.houserent.service.HouseService;
import com.wwc.houserent.utils.Utility;

/**
 * 1.显示界面
 * 2.接收用户的输入
 * 3.调用HouseService完成对房屋信息的各种操作
 */
public class HouseView {

    //控制显示菜单
    private boolean loop = true;
    //接收用户选择
    private char key = ' ';
    //HouseService需要先new对象才可以调方法，此处用一个全局变量接收
    //设置数组的大小为10
    private HouseService houseService = new HouseService(10);

    //完成退出确认
    public void exit() {
        if (Utility.readConfirmSelection() == 'Y') {//善于利用已的类进行开发
            loop = false;
        }
    }

    //modifyHouse()接收用户输入id，调用modify方法，修改房屋
    public void modifyHouse() {

        //显示修改界面
        System.out.println("=============修改房屋信息=============");
        System.out.print("请选择待修改房屋编号（-1退出）");
        int updateid = Utility.readInt();
        if (updateid == -1) {
            System.out.println("=============放弃修改房屋信息=============");
            return;
        }

        //根据输入的updateid，查找对象
        House modifyHouse = houseService.findById(updateid);//查找出要修改的房子
        if (modifyHouse == null) {//后面还要进行大量操作则用过关斩将数据校验法，如果选择有限，则用if-else
            System.out.println("=============修改的房屋编号信息不存在=============");
            return;
        }

        //给出修改界面,此处还是将接收数据统一交回HouseService层处理，而不在HouseView层处理
        //HouseView层处理，HouseService层只负责用户数据的接收
        System.out.print("姓名(" + modifyHouse.getName() + ")：");
        String name = Utility.readString(8, "");
        System.out.print("电话(" + modifyHouse.getPhone() + ")：");
        String phone = Utility.readString(12, "");
        System.out.print("地址(" + modifyHouse.getAddress() + ")：");
        String address = Utility.readString(16, "");
        System.out.print("月租(" + modifyHouse.getRent() + ")：");
        int rent = Utility.readInt(-1);
        System.out.print("状态(" + modifyHouse.getState() + ")：");
        String state = Utility.readString(3);

        //将修改内容封装成一个House对象，调用HouseService中的modify方法，然后传入修改前与修改后的House对象
        House newHouse = new House(modifyHouse.getId(), name, phone, address, rent, state);
        if (houseService.modify(newHouse)) {
            System.out.println("=============修改完成=============");
            return;
        }
        System.out.println("=============修改失败=============");
    }

    //编写delHouse()接收用户输入id，调用delete方法，删除房屋
    public void delHouse() {

        System.out.println("=============删除房屋=============");
        System.out.print("请选择待删除房屋编号（-1退出）");
        int delId = Utility.readInt();//接受用户输入id
        if (delId == -1) {//过关斩将数据校验法
            System.out.println("=============放弃删除房屋信息=============");
            return;
        }
        //注意该方法本身就有循环判断逻辑，必须输入Y/N,否则不能出循环，不分区大小写（已做处理）
        if (Utility.readConfirmSelection() == 'Y') {//判断输入为'Y'，则调用delete方法进行删除
            if (houseService.del(delId)) {
                System.out.println("=============删除房屋信息成功=============");
            } else {
                System.out.println("=============房屋编号不存在，删除失败=============");
            }
        } else {
            System.out.println("=============放弃删除房屋信息=============");
        }
    }


    //编写findHouse()接收用户输入id，调用find方法，对房屋进行查找
    public void findHouse() {

        System.out.println("=============查找房屋=============");
        System.out.print("请输入你要查找的id：");

        //调用HouseService中的findById方法，传入查找id
        House findHouse = houseService.findById(Utility.readInt());
        if (findHouse != null) {//判断是否查找成功，如果成功，打印输出
            System.out.println(findHouse);
            return;
        } else {
            System.out.println("=============查找房屋信息id不存在=============");
        }
    }

    //编写addHouse()接收输入，创建House对象，调用add方法
    public void addHouse() {

        //接收用户输入的添加房屋信息（命令行的缺点就是不能把全部要输入的内容一起同时展示出去）
        System.out.println("=============添加房屋=============");
        System.out.print("姓名： ");
        String name = Utility.readString(8);
        System.out.print("电话： ");
        String phone = Utility.readString(12);
        System.out.print("地址： ");
        String address = Utility.readString(16);
        System.out.print("月租： ");
        int rent = Utility.readInt();
        System.out.print("状态： ");
        String state = Utility.readString(3);

        //将接收到的房屋数据，封装成一个新的House对象
        //id是系统分配的，用户不能输入,这里可以设置默认值为0
        House newHouse = new House(0, name, phone, address, rent, state);

        //调用业务层方法
        if (houseService.add(newHouse)) {
            System.out.println("=============添加房屋成功=============");
        } else {
            System.out.println("=============添加房屋失败=============");
        }

    }

    //编写listHouse()显示房屋列表
    public void listHouse() {
        System.out.println("=============房屋列表=============");
        System.out.println("编号\t\t房主\t\t电话\t\t地址\t\t月租\t\t状态（未出租/已出租）");
        House[] houses = houseService.list();//通过业务层得到所有房屋信息，在页面层进行数据展示
        //遍历houses数组输出数据
        for (int i = 0; i < houses.length; i++) {
            //过关斩将数据校验法，过滤掉空数据;
            //因为此处都是输出完有数据的，再输出空数据，因此可以用break，否则用continue。
            if (houses[i] == null) break;
            System.out.println(houses[i]);
        }
        System.out.println("=============房屋列表显示完毕=============");
    }

    //显示主菜单
    public void mainMenu() {

        //持续不断进行的系统必然是包裹在循环体中的
        do {
            System.out.println("=============房屋出租系统菜单=============");
            System.out.println("\t\t\t1 新 增 房 源");
            System.out.println("\t\t\t2 查 找 房 源");
            System.out.println("\t\t\t3 删 除 房 屋 信 息");
            System.out.println("\t\t\t4 修 改 房 屋 信 息");
            System.out.println("\t\t\t5 房 屋 列 表");
            System.out.println("\t\t\t6 退      出");
            System.out.println("请输入你的选择（1-6）：");
            key = Utility.readChar();
            //使用switch判断用户的选择并进行执行判断
            switch (key) {
                case '1':
                    //System.out.println("新 增");//化繁为简，可以先用简单sout输出替代具体方法的调用,逐步完善
                    addHouse();
                    break;
                case '2':
                    findHouse();
                    break;
                case '3':
                    delHouse();
                    break;
                case '4':
                    modifyHouse();
                    break;
                case '5':
                    listHouse();
                    break;
                case '6':
                    exit();
                    break;
            }
        } while (loop);
    }
}
