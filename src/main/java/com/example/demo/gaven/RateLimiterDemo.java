package com.example.demo.gaven;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wangyp
 * @Description:  Guava 库中的 rateLimiter使用
 * @Date: Created in 0:02 2018/6/5
 * @Modified By:
 */
public class RateLimiterDemo {

    /**
     * RateLimiter和JDK中的Semaphore, 用来限制对资源的访问数
     * @param args
     */
    public static void main(String[] args) {

        ExecutorService service = Executors.newFixedThreadPool(4);
        /**
         * 每秒不超过4个任务被提交
         */
        RateLimiter limiter = RateLimiter.create(4.0);
        /** 请求RateLimiter ,超过规定的资源数，会阻塞线程*/
        Double acquire = limiter.acquire();
        /** 提交任务*/
        service.execute(() -> {
            System.out.println("RateLimiter is Demo");
        });

        /** 非阻塞方法，如果没有达到规定的线程数，就返回false;如果达到规定的数量返回true*/
        System.out.println(limiter.tryAcquire());

        /**
         * 创建具有指定稳定吞吐量的RateLimiter类，传入允许每秒提交的任务数量和准备阶段的时间，
         * 在这段时间RateLimiter会有个缓冲，直到达到它的最大速率（只要有饱和的足够的请求）。
         */
        limiter = RateLimiter.create(4.0, 1000L, TimeUnit.MICROSECONDS);
        /** 稳定的更新RateLimiter速率，设置后，被阻塞的线程不会被唤醒，因此也无法观察到新的速率被设置*/
        limiter.setRate(1000.0);
        double rate = limiter.getRate();
        System.out.println("得到RateLimiter设置的速率 :" + rate);

        /** 获取传入数量的线程，直到达到要求之后才会许可资源通过。*/
        acquire = limiter.acquire(2);
        /** 判断在相应的时间内，是否可以从RateLimiter内获得许可；在规定的时间内没有获得相应的许可，返回false*/
        boolean lean = limiter.tryAcquire(2, TimeUnit.MICROSECONDS);

        /** 最后一定要关闭线程*/
        service.shutdown();

    }
}
