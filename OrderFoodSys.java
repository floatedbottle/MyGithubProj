package com.zjp;
import java.util.Scanner;
/**
 * Created by zhaojieping on 2019/11/28.
 * @author zhaojp.
 *
 */
//吃货联盟订餐系统
public class OrderFoodSys {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String[] dishName = {"红烧排骨","鱼香肉丝","时令蔬菜"}; //菜名
        double[] prices = {38.0,20.0,10.0};  //金额
        int[] praiseNums = new int[3];  //点赞数

        String[] names = new String[4];
        String[] disMsg = new String[4];   //菜品名称+订单份数
        int[] times = new int[4];
        String[] adresses = new String[4];
        double[] sumPrices = new double[4];
        int[] states = new int[4];    //0:已预订    1：已完成

        //初始化两个订单信息
        names[0] = "张三";
        disMsg[0] = "红烧排骨 2份";
        times[0] = 10;
        adresses[0] = "知春路251号";
        sumPrices[0] = 70;    //餐费>50免配送费，否则配送费6元
        states[0] = 0;

        names[1] = "李四";
        disMsg[1] = "鱼香肉丝 1份";
        times[1] = 13;
        adresses[1] = "知春路512号";
        sumPrices[1] = 26;    //餐费>50免配送费，否则配送费6元
        states[1] = 1;



        System.out.println("欢迎使用”吃货联盟订餐系统“");
        int num = -1;
        boolean flag = false;

        do {
            System.out.println("************************************");
            System.out.println("1.我要订餐");
            System.out.println("2.查看餐袋");
            System.out.println("3.签收订单");
            System.out.println("4.删除订单");
            System.out.println("5.我要点赞");
            System.out.println("6.退出系统");
            System.out.println("************************************");
            System.out.print("请选择：");
            int choose = sc.nextInt();
            switch (choose){
                case 1:
                    System.out.println("************我要订餐************");
                    //订餐前提示订单未满，booblean变量来标识订单是否满了的结果，满了false不能订餐，否则能订餐
                    boolean isAdd = false;
                    for (int i = 0; i < names.length; i++) {
                        if (names[i]==null){
                            isAdd = true;
                            System.out.print("请输入订餐人姓名：");
                            String name = sc.next();
                            //循环输出菜品信息
                            System.out.println("序号\t菜名\t单价");
                            for (int j = 0; j < dishName.length; j++) {
                                String praise = (praiseNums[j]==0)?" ":praiseNums[j]+"赞";
                                System.out.println((j+1)+"\t"+dishName[j]+"\t"+prices[j]+"\t"+praise);
                            }
                            //菜品编号的输入及判断
                            System.out.print("请输入您要点的菜品编号");
                            int no = sc.nextInt();
                            while (no<1||no>dishName.length){
                                System.out.print("本店没有这个菜品，请重新选择：");
                                no = sc.nextInt();
                            }
                            //份数
                            System.out.print("请输入您要得份数");
                            int number = sc.nextInt();
                            //送餐时间输入及判断
                            System.out.print("请输入送餐时间（时间只能是在10-20之间的整点）");
                            int time = sc.nextInt();
                            while (time<10||time>20){
                                System.out.print("您输入的送餐时间有误，请输入10-20之间的整数");
                                time = sc.nextInt();
                            }
                            //地址
                            System.out.print("请输入送餐地址");
                            String address = sc.next();
                            //给用户看订单信息
                            System.out.println("订餐成功！");
                            String dishInfo = dishName[no-1]+" "+number+"份";
                            System.out.println("您订的是："+dishInfo);
                            System.out.println("送餐时间："+time+"点");
                            //餐费  单价*份数+配送费
                            //餐费
                            double disprice = prices[no-1]*number;
                            //求配送费
                            double peisong = (prices[no-1]*number>50)?0:6;
                            double sumPrice = disprice+peisong;
                            System.out.println("餐费："+disprice+"元，送餐费："+peisong+"元，总计："+sumPrice+"元。");

                            //把订餐信息添加到订单信息
                            names[i] = name;
                            disMsg[i] = dishInfo;
                            times[i] = time;
                            adresses[i] = address;
                            sumPrices[i] = sumPrice;
                            //跳出此次循环
                            break;
                        }
                    }
                    if (!isAdd){
                        System.out.println("对不起，您的餐带已满！");
                    }

                    break;
                case 2:
                    System.out.println("\n************查看餐袋************");
                    System.out.println("序号\t订餐人\t订餐菜品\t\t配送时间\t配送地址\t\t订餐金额\t订单状态");
                    for (int i = 0; i < names.length; i++) {
                        if (names[i]!=null){
                            String time = times[i]+"点";
                            String state = (states[i]==0)?"已预订":"已完成";
                            System.out.println((i+1)+"\t"+names[i]+"\t\t"+disMsg[i]+"\t"+time+"\t\t"+adresses[i]+"\t"+sumPrices[i]+"\t"+state);

                        }
                    }
                    break;
                case 3:
                    System.out.println("************签收订单************");
                    System.out.print("请选择您要签收的订单序号：");
                    int signNo = sc.nextInt();
                    boolean isSign = false;
                    for (int i = 0; i < names.length; i++) {
                        //条件： 有此订单&&订单状态为0已预订&&并且用户输入的订单编号我能找到（i==signNo-1）
                        if (names[i] != null && states[i] == 0 && i==signNo-1){
                            isSign = true;
                            states[i] = 1;
                            System.out.println("订单签收成功！");
                        }else if (names[i] != null && states[i] == 1 && i==signNo-1){
                            //有订单信息 所以isSign为true 并且订单信息我能找到
                            isSign = true;
                            //但是订单状态是已经完成，所以不能签收
                            System.out.println("您选择的订单已经完成签收，不能再次签收！");
                        }
                    }
                        if (!isSign){
                            System.out.println("您选择的订单不存在!");
                        }
                    break;
                case 4:
                    System.out.println("************删除订单************");


                    System.out.print("请选择您要删除的订单序号：");
                    int delNo = sc.nextInt();
                    boolean isDel = false;
                    for (int i = 0; i < names.length; i++) {
                        //条件： 有此订单&&订单状态为0已预订&&并且用户输入的订单编号我能找到（i==signNo-1）
                        if (names[i] != null && states[i] == 0 && i==delNo-1){
                            isDel = true;
                            System.out.println("您选择的订单还未签收，不能删除！");
                        }else if (names[i] != null && states[i] == 1 && i==delNo-1){
                            //有订单信息 所以isSign为true 并且订单信息我能找到
                            isDel = true;
                            //但是订单状态是已经完成，所以可以删除
                            //先找到删除订单的位置下标  i ，把i后面的元素依次往前移动，最后一个元素要置空
                            for (int j = 0; j < names.length-1; j++) {
                                names[j] = names[j+1];
                                disMsg[j] = disMsg[j+1];
                                times[j] = times[j+1];
                                adresses[j] = adresses[j+1];
                                sumPrices[j] = sumPrices[j+1];
                                states[j] = states[j+1];
                            }
                            //最后一个元素要置空
                            names[names.length-1] = null;
                            disMsg[names.length-1] = null;
                            times[names.length-1] = 0;
                            adresses[names.length-1] = null;
                            sumPrices[names.length-1] = 0;
                            states[names.length-1] = 0;
                            System.out.println("删除订单成功！");
                        }
                    }
                    if (!isDel){
                        System.out.println("您要删除的订单不存在!");
                    }
                    break;
                case 5:
                    System.out.println("************我要点赞************");
                    //循环输出菜品信息
                    System.out.println("序号\t菜名\t单价");
                    for (int j = 0; j < dishName.length; j++) {
                        String praise = (praiseNums[j]==0)?" ":praiseNums[j]+"赞";
                        System.out.println((j+1)+"\t"+dishName[j]+"\t"+prices[j]+"\t"+praise);
                    }
                    System.out.print("请输入您要点赞的餐品序号");
                    int praiseNo = sc.nextInt();
                    while (praiseNo<1||praiseNo>dishName.length){
                        System.out.print("本店没有这个菜品，无法点赞，请重新输入菜品序号");
                        praiseNo = sc.nextInt();
                    }
                    praiseNums[praiseNo-1]++;
                    break;
                case 6:
                    flag = true;
                    break;
                default:
                    flag = true;
                    break;
            }
            if (!flag){
                System.out.println("请输入0返回");
                num=sc.nextInt();
            }else {
                break;
            }
        }while (num==0);
        System.out.println("谢谢使用。欢迎下次再来！");
    }
}
