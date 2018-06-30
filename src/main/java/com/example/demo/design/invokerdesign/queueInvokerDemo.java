/**
 * fshows.com
 * Copyright (C) 2013-2018 All Rights Reserved.
 */
package com.example.demo.design.invokerdesign;

import com.google.common.collect.Lists;

import java.util.ArrayList;

/**
 * 命令模式中的队列模式实现
 *
 * @author wangyp
 * @version queueInvokerDemo.java, v 0.1 2018-06-30 11:19
 */
public class queueInvokerDemo {



    public static void main(String[] args) {
        //1.创建接受者
        javaReceiver javaReceiver = new javaReceiver();
        phpReceiver phpReceiver = new phpReceiver();

        //2.创建命令对象
        javaCommand javaCommand = new javaCommand(javaReceiver, "编写java后端代码");
        phpCommand phpCommand = new phpCommand(phpReceiver, "编写php专属代码");

        //3.创建命令队列
        InvokerQueueManger manger = new InvokerQueueManger();

        //4.加入命令对象
        manger.addCommand(javaCommand);
        manger.addCommand(phpCommand);

        //5.创建请求者，并把命令加进去
        Manger mangerInvoker = new Manger(manger);

        //6.最后调用请求者的运行方法
        mangerInvoker.call();

    }


}


/**
 * 抽象的命令类
 */
abstract class QueueCommand{

    //执行方法
    public abstract void execute();
}

/**
 * 具体的接受者--java程序员
 */
class javaReceiver{

    /**
     * 具体的执行方法
     * @param task
     */
    public void call(String task){
        System.out.printf("java程序员要执行的操作是 %s", task);
        System.out.println();
    }

}

/**
 * 具体的接受者---php程序员
 */
class phpReceiver{

    /**
     * 具体的执行方法
     * @param task
     */
    public void call(String task){
        System.out.printf("php程序员要执行的操作 %s", task);
        System.out.println();
    }

}

/**
 * 具体的java程序员实现的抽象命令
 */
class javaCommand extends Command{

    private javaReceiver javaReceiver;

    private String task;

    public javaCommand(javaReceiver javaReceiver, String task){
        this.javaReceiver = javaReceiver;
        this.task = task;
    }

    @Override
    public void execute() {
        //通常会转调接收者对象的相应方法，让接收者来真正执行功能
        javaReceiver.call(this.task);
    }
}

/**
 * 具体的命令--phpCommand
 */
class phpCommand extends Command{

    private phpReceiver phpReceiver;

    private String task;

    public phpCommand(phpReceiver phpReceiver, String task){
        this.phpReceiver = phpReceiver;
        this.task = task;
    }

    @Override
    public void execute() {
        phpReceiver.call(this.task);
    }
}

/**
 * CommandQueue命令队列类
 */
class InvokerQueueManger {

    private ArrayList<Command> commands = Lists.newArrayList();

    public void addCommand(Command command){
        commands.add(command);
    }

    public void removeCommand(Command command){
        commands.remove(command);
    }

    /**
     * 循环调用每一个命令对象的execute()方法
     */
    public void execute(){
        commands.stream().forEach(r -> r.execute());
    }
}


/**
 * 请求发送者类，针对CommandQueue提供方便
 */
class Manger{

    private InvokerQueueManger manger;

    /**
     * 构造注入
     * @param manger
     */
    public Manger(InvokerQueueManger manger){
        this.manger = manger;
    }

    /**
     * 设值注入
     * @param manger
     */
    public void setManger(InvokerQueueManger manger){
        this.manger = manger;
    }

    /**
     * 调用InvokerQueueManger类的execute()方法
     */
    public void call(){
        manger.execute();
    }

}


