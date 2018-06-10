/**
 * fshows.com
 * Copyright (C) 2013-2018 All Rights Reserved.
 */
package
        com.example.demo.gaven;/**
 * @Author: wangyp
 * @Description:  Monitor guava 的使用，
 * @Date: Created in 23:31 2018/6/10
 * @Modified By:
 */

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Monitor;


import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 *
 * @author wangyp
 * @version MonitorDemo.java, v 0.1 2018-06-10 23:31
 */
public class MonitorDemo {

    /**
     * Monitor guava 作为ReentrantLock的替代，比ReentrantLock使用起来更加方便，可读性强。
     * 该Monitor对多线程的操作是安全的。
     * Monitor 是一个支持任意布尔条件的同步抽象
     *
     * 1.public abstract static class Guard：一个标识线程是否等待的布尔条件，Guard类总是与单一的Monitor相关联，
     * Monitor可以在任意时间从任意占用Monitor的线程检查Guard，这样代码的编写将不在关心Guard是否被检查的频率。
     * 2.public abstract boolean isSatisfied()：Guard内部提供的抽象方法，isSatisfied()，当被关联的Monitor被占用时，
     * Guard的此方法会被调用，该方法的实现必须取决于被关联Monitor保护的状态，并且状态不可修改。
     *
     * Monitor几个常用的方法：
     * 1.enter()：进入到当前Monitor，无限期阻塞，等待锁。(这里没有Guard)
     * 2.enter(long time, TimeUnit unit)：进入到当前Monitor，最多阻塞给定的时间，返回是否进入Monitor。
     * 3.tryEnter()：如果可以的话立即进入Monitor，不阻塞，返回是否进入Monitor。
     * 4.enterWhen(Guard guard)：进入当前Monitor，等待Guard的isSatisfied()为true后，继续往下执行 ，但可能会被打断;
     *   为false，会阻塞。
     * 5.enterIf(Guard guard)：如果Guard的isSatisfied()为true，进入当前Monitor。等待获得锁(这里会等待获取锁)，
     *   不需要等待Guard satisfied。
     * 6.tryEnterIf(Guard guard)：如果Guard的isSatisfied()为true并且可以的话立即进入Monitor，不等待获取锁(这里不
     *   等待获取锁)，也不等待Guard satisfied。
     * 感觉  enterWhen enterIf的用的区别就是前者无返回值，后者返回Boolean值。
     *
     * 在使用Monitor布尔值时，记得用try finally，不管如何在程序的结尾都会要释放锁
     *
     * 每次只允许一个线程进入，其他线程想进入，需要等待线程的leave()方法。
     */

    private static final int MAX_SIZE = 10;
    private Monitor monitor = new Monitor();
    private List<String> list = Lists.newArrayList();

    /** 使用Monitor 必须是继承Guard，重写isSatisfied()方法,定义一个什么时候应该阻塞线程 */
    Monitor.Guard listBelowCapacity = new Monitor.Guard(monitor) {
                @Override
                public boolean isSatisfied() {
                    return list.size() < MAX_SIZE;
                }
            };

    public void addToListWait(String item) throws InterruptedException {
        // 超过MAX_SIZE， 会锁死（阻塞）
        this.monitor.enterWhen(listBelowCapacity);
        try {
            list.add(item);
            System.out.println("添加元素[" + item + "]成功，当前List.size=" + list.size() + "～");
        } finally {
            // 确保线程会退出Monitor锁,最后记得要释放锁
            monitor.leave();
        }
    }
    public void addToListSkipWait(String item) throws InterruptedException {
        // 超过MAX_SIZE， 会锁死
        //this.monitor.enterWhen(listBelowCapacity);

        // 超过返回false  不会锁死
        Boolean isOK = monitor.tryEnterIf(listBelowCapacity);
        System.out.println("Thread[" + Thread.currentThread().getName() + "] item=" + item + ",获取令牌：isOK=" + isOK);
        if (isOK) {
            try {
                list.add(item);
                System.out.println("添加元素[" + item + "]成功，当前List.size=" + list.size() + "～");
            } finally { // 确保线程会退出Monitor锁
                monitor.leave();
            }
        }
    }

    public static void main(String[] args) {
        final MonitorDemo monitorDemo = new MonitorDemo();
        /** 定义一个固定大小的额线程池*/
        ExecutorService service = Executors.newFixedThreadPool(5);
        service.submit(() -> {
            for (int j = 0; j < 10 ; j++) {
                try {
                    // monitorDemo.addToListWait(count + "------------------>" + Thread.currentThread().getName());
                    monitorDemo.addToListSkipWait(j + "------------------>" + Thread.currentThread().getName());
                    Thread.sleep(100L);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });


        // 等待所有线程执行完毕
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("---------------------------- 长长的分割线 ---------------------------");
        monitorDemo.list.stream().forEach(i -> System.out.println(i));
//        Iterator iteratorStringList = monitorDemo.list.iterator();
//        while (iteratorStringList.hasNext()) {
//            System.out.println(iteratorStringList.next());
//        }
        /** 最后一定要记得关掉线程池*/
        service.shutdown();
    }
}

