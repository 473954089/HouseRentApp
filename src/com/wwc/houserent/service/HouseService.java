package com.wwc.houserent.service;

import com.wwc.houserent.domain.House;

/**
 * HouseService.java<=>类[业务层]：处理业务数据
 * //定义House[],保存House对象
 * 1.响应HouseView的调用
 * 2.完成对房屋信息的各种操作（增删改查c[create]r[read]u[update]d[delete]
 */
public class HouseService {

    //用于存放House对象，对数据的crud主要是业务层要做的事
    private House[] houses;
    //记录当前有多少个房屋信息，默认初始化信息为1，即默认有一条数据
    private int houseNums = 1;
    //两个变量区分开id和元素个数，记录当前的id增长，默认现在id是1，即默认有一条数据
    private int idCounter = 1;

    //构造器，用于初始化房屋数据
    public HouseService(int size) {
        //通过接受size参数，动态指定houses数组的大小，而不考虑从外面传入创建好的数组
        this.houses = new House[size];//当创建HouseService对象，指定数组大小
        //配合测试列表信息，初始化一个House对象
        houses[0] = new House(1, "jack", "112", "海淀区", 2000, "未出租");
        //houses[1] = new House(2, "John", "114", "朝阳区", 2500, "未出租");
    }

    ////modify方法，根据用户传入newHouse修改房屋数组
    public boolean modify(House newHouse) {

        int index=-1;
        for (int i = 0; i < houseNums; i++) {
            if (newHouse.getId() == houses[i].getId()) {
                houses[i] = newHouse;
                return true;
            }
        }
        return false;
    }

    //del方法，根据用户传入id查询房屋数组，删除id一致的房屋对象
    public boolean del(int delId) {

        //此处要分离，查找下标与删除内容的操作，因为删除中间房屋，后面的还要补上
        //一个代码块完成一件事情
        //应当先找到要删除的房屋信息对应的下标
        //下标不等于房屋编号
        int index = -1;
        //仅做下标查找操作
        for (int i = 0; i < houseNums; i++) {
            if (delId == houses[i].getId()) {//要删除的房屋(id),是数组下标为id元素
                index = i;
                break;
            }
        }

        //过关斩将数据校验法
        if (index == -1) {//说明delId在数组中不存在
            return false;
        }

        //从被删处，开始往后向上缩进
        //此处i的末端取值为houseNums-1，因为最后一个值的已经不需要做上缩操作，后面直接置空就行，否则会空指针异常
        for (int i = index; i < houseNums - 1; i++) {
            houses[i] = houses[i + 1];
        }
        //置空当前数组元素的最后一个，房屋数量少一个
        houses[--houseNums] = null;
        return true;

    }


    //findById方法，从名字上进行区分，因为后面可能还有其他查找方法，根据用户传入id查询房屋数组，返回id一致的房屋对象
    public House findById(int id) {
        for (int i = 0; i < houseNums; i++) {
            if (id == houses[i].getId()) {
                return houses[i];
            }
        }
        return null;
    }

    //list方法，返回houses
    public House[] list() {
        return houses;
    }

    //add方法，添加新对象，返回boolean
    public boolean add(House newHouse) {

        //判断是否还可以继续添加新数据，此处暂时先不考虑数组扩容的问题，后续可添加
        if (houseNums == houses.length) {//数组已满，不能在添加
            System.out.println("数组已满，不能在添加");
            return false;
        }//过关斩将数据校验法

        //把newHouse对象加入到最新的最后（个数刚好与下标吻合）,增加了一个空房
        houses[houseNums++] = newHouse;
        //设定一个id自增长机制,然后更新newHouse的id
        newHouse.setId(++idCounter);
        return true;
    }
}
