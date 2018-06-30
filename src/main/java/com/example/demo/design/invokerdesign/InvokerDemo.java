/**
 * fshows.com
 * Copyright (C) 2013-2018 All Rights Reserved.
 */
package com.example.demo.design.invokerdesign;


import com.google.common.collect.Lists;

import java.util.ArrayList;

/**
 * 设计模式之命令模式
 *
 * @author wangyp
 * @version InvokerDemo.java, v 0.1 2018-06-25 23:36
 */
public class InvokerDemo {

    /**
     * 命令模式（Command Pattern）:将一个请求封装为对象，从而让我们可以对不同的请求进行参数化；队请求排队或者记录日志，
     * 以及支持可撤销的操作，命令模式是一个对象模式，其别名为动作模式或者事务模式。
     *
     * 在命令模式中的角色：
     * 1.Command（抽象命令类）：抽象命令类一般是一个抽象类或接口，在其中声明了用于执行请求的execute()等方法，
     *   通过这些方法可以调用请求接收者的相关操作。
     * 2.ConcreteCommand（具体命令类）：具体命令类是抽象命令类的子类，实现了在抽象命令类中声明的方法，它对应具体的接收者对象，
     *   将接收者对象的动作绑定其中。在实现execute()方法时，将调用接收者对象的相关操作(Action)。
     * 3.Invoker（调用者）：调用者即请求发送者，它通过命令对象来执行请求。一个调用者并不需要在设计时确定其接收者，
     *   因此它只与抽象命令类之间存在关联关系。在程序运行时可以将一个具体命令对象注入其中，再调用具体命令对象的
     *   execute()方法，从而实现间接调用请求接收者的相关操作。
     * 4.Receiver（接收者）：接收者执行与请求相关的操作，它具体实现对请求的业务处理。
     *
     * 命令模式的本质是对请求进行的封装，一个请求对应一个命令，将发出命令的责任和执行命令的责任分割开来。每一个命令都是
     * 一个操作，请求的一方发出请求要求执行一个操作，接受的一方收到请求，立即执行操作。命令模式允许请求的一方和接收的一方
     * 独立开来，使得请求的一方不必知道接收请求的一方的接口，更不必知道请求如何被接收、操作是否被执行、何时被执行，以及是
     * 怎么被执行的。
     *
     * 命令模式的优点：
     * 1.更加松散的耦合性。使得发出命令的一方不必知道接受命令的一方接口是如何实现的。
     * 2.更加动态的控制，命令模式把请求封装起来，可以动态的对它进行日志化、队列化、参数化等操作，可以动态的进行管理。
     * 3.更好的扩展性，很容易的就可以扩展命令，只需要实现命令的接口实现这个命令就可以。
     *
     * 流程图
     * client ---> invoker ---> command <--- concreteCommand ---> receiver
     *
     *
     * 命令模式url:https://www.cnblogs.com/lfxiao/p/6825644.html
     * 命令模式扩展：https://www.cnblogs.com/JsonShare/p/7206607.html
     */
}


/**
 * 抽象模式中的命令
 */
abstract class Command{
    public abstract void execute();
}

/**
 * 调用者
 */
class Invoker{

    private Command command;

    //构造注入
    public Invoker(Command command){
        this.command = command;
    }

    //设值注入
    public void setCommand(Command command){
        this.command = command;
    }

    /**
     * 业务方法，用于调用命令类的execute()
     */
    public void call(){
        command.execute();
    }
}

/**
 * 具体的命令类实现抽象命令类
 */
class ConcreteCommand extends Command{

    /**
     * 维持一个对请求接受者的调用
     */
    private ReceiverDemo receiverDemo;

    /**
     * 调用请求接受者中的业务方法
     */
    @Override
    public void execute() {
        receiverDemo.action();
    }
}


/**
 * 请求接受者
 */
class ReceiverDemo {

    /**
     * 用于执行和请求相关的操作
     */
    public void action() {
        //具体操作
    }
}


//-----------------------------命令模式中的队列模式---------------------------------------------------

/**
 * 定义一个对象，用来盛装队列中的Command
 * 命令队列的实现方式多种多样，最常见的就是增加一个CommandQueue类，由该类负责存储多个命令对象，不同的命令对象
 * 可以对应不同的请求接受者。
 */
class CommandQueue{

    /**
     * 定义一个ArrayList集合装命令
     */
    private ArrayList<Command> queue = Lists.newArrayList();

    private void addCommand(Command command){
        queue.add(command);
    }

    public void removeCommand(Command command){
        queue.remove(command);
    }

    /**
     * 循环调用每一个命令的execute()
     */
    public void execute(){
        queue.stream().forEach(i -> i.execute());
    }

}

/**
 * 请求发送者（调用者）也要针对CommandQueue编程
 */
class InvokerQueue{

    private CommandQueue commandQueue; //维持一个CommandQueue对象的引用

    //构造注入
    public InvokerQueue(CommandQueue commandQueue) {
        this. commandQueue = commandQueue;
    }

    //设值注入
    public void setCommandQueue(CommandQueue commandQueue) {
        this.commandQueue = commandQueue;
    }

    //调用CommandQueue类的execute()方法
    public void call() {
        commandQueue.execute();
    }

}

