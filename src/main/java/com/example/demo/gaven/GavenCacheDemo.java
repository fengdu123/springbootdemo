package com.example.demo.gaven;


import com.example.demo.model.Student;
import com.google.common.cache.*;
import com.google.common.graph.Graph;
import com.google.common.graph.ImmutableGraph;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wangyp
 * @Description:  Gaven cache缓存机制
 * @Date: Created in 20:53 2018/6/3
 * @Modified By:
 */
public class GavenCacheDemo {

    /**
     * Guava Cache与ConcurrentMap有一些相似的地方，但也不完成相同。最基本的区别是ConcurrentMap会一直保存所有添加的元素，
     * 直到显式地移除。相对地，Guava Cache为了限制内存占用，通常都设定为自动回收元素。在某些场景下，尽管LoadingCache 不回收元素，
     * 它也是很有用的，因为它会自动加载缓存。
     *
     * Guava Cache适用场景：
     * 1.你愿意牺牲一些空间来提升运行速度。
     * 2.你预料到某些键会被查询次数在一次以上。
     * 3.缓存中存放的数据总量不会超过内存总量。
     *
     * 何时使用CacheLoader和Callable 缓存实例。
     *     有没有合理的默认方法来加载或计算与键关联的值。如果有，就使用CacheLoader方法，如果没有就使用Callable实例来计算。
     *
     *
     * 从LoadingCache查询常规方法是get(key),这个方法要么返回缓存中的值，要么就是利用CacheLoader向缓存原子加载新值。
     * 由于LoadingCache可能会存在异常，如果你定义的LoadingCache没有申明任何异常，那么可以使用LoadingCache的getUnchecked(key)
     * 查询缓存。
     * 注意：如果你申明了异常，这里就不能使用getUncheck()方法来获得返回值。
     *
     * LoadingCache获取返回值的方法：get(key)\getUnchecked(key)\getAll()
     *     getAll()方法默认情况下会循环缓存中的所有值，对于不在缓存中的键，会调用CacheLoader()中的load()单条的去加载缓存，在这种情况下，
     *     CacheLoader.loadAll()批量加载会比单个加载效果好很多。getAll()的效果也会好很多。
     *
     * LoadingCache其他方法：
     * 1.ImmutableMap<K, V> getAllPresent(Iterable<?> keys) 一次获得多个键的缓存值
     * 2.put和putAll方法向缓存中添加一个或者多个缓存项
     * 3.invalidate 和 invalidateAll方法从缓存中移除缓存项
     * 4.asMap()方法获得缓存数据的ConcurrentMap<K, V>快照
     * 5.cleanUp()清空缓存
     * 6.refresh(Key) 刷新缓存，即重新取缓存数据，更新缓存
     *
     * 缓存回收机制：
     * 1.基于容器的回收，规定缓存项的数目不超过规定的数目。CacheBuilder.maximumSize(long) ,可以指定一个权重函数，在CacheBuilder.weigth(Weight)
     *   指定一个权重函数，并且用CacheBuilder.maximumWeigth(long)指定最大值。
     * 2.设置时间定时回收缓存：
     *   Cache.expireAfterAccess(long, TimeUnit)：缓存项在给定时间内没有被读/写访问，则回收。请注意这种缓存的回收顺序和基于大小回收一样。
     *   Cache.expireAfterWrite(long, TimeUnit)：缓存项在给定时间内没有被写访问（创建或覆盖），则回收。如果认为缓存数据总是在固定
     *   时候后变得陈旧不可用，这种回收方式是可取的。
     * 一般常用的方法如上：
     *
     * 刷新缓存的方法：
     * 1.LoadingCache.refresh(K)，刷新表示为键加入新值，整个过程可以是异步的。在刷新操作进行是，缓存仍然可以向其他线程返回旧值，
     *   而不像回收操作，读缓存的线程必须等待新值加载完成。
     * 2.CacheBuilder.refreshAfterWrite(long, TimeUnit),可以为缓存设置刷新时间，缓存项只有在检索到LoadingCache.refresh(K)方法，是
     *   才会重新加载；否则就算到了时间，也不会刷新缓存。
     * @param args
     * @throws InterruptedException
     * @throws ExecutionException
     */

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        //缓存接口这里是LoadingCache，LoadingCache在缓存项不存在时可以自动加载缓存
        LoadingCache<Integer,Student> studentCache
                //CacheBuilder的构造函数是私有的，只能通过其静态方法newBuilder()来获得CacheBuilder的实例
                = CacheBuilder.newBuilder()
            //设置并发级别为8，并发级别是指可以同时写缓存的线程数
            //refreshAfterWrite(3, TimeUnit.HOURS)// 给定时间内没有被读/写访问，则回收。
                .concurrencyLevel(8)
                //设置写缓存后8秒钟过期
                .expireAfterWrite(8, TimeUnit.SECONDS)
                //设置缓存容器的初始容量为10
                .initialCapacity(10)
                //设置缓存最大容量为100，超过100之后就会按照LRU最近虽少使用算法来移除缓存项
                .maximumSize(100)
                //设置要统计缓存的命中率
                .recordStats()
                //设置缓存的移除通知
                .removalListener(new RemovalListener<Object, Object>() {
                    @Override
                    public void onRemoval(RemovalNotification<Object, Object> notification) {
                        System.out.println(notification.getKey() + " was removed, cause is " + notification.getCause());
                    }
                })
                //build方法中可以指定CacheLoader，在缓存不存在时通过CacheLoader的实现自动加载缓存
                ///** 当本地缓存命没有中时，调用load方法获取结果并将结果缓存 **/　　
                .build(
                new CacheLoader<Integer, Student>() {
                    @Override
                    public Student load(Integer key) throws Exception {
                        System.out.println("load student " + key);
                        Student student = new Student();
                        student.setId(key);
                        student.setName("name " + key);
                        return student;
                    }
                }
        );

        for (int i=0;i<20;i++) {
            //从缓存中得到数据，由于我们没有设置过缓存，所以需要通过CacheLoader加载缓存数据
            Student student = studentCache.get(1);
            System.out.println(student);
            //休眠1秒
            TimeUnit.SECONDS.sleep(1);
        }

        System.out.println("cache stats:");
        //最后打印缓存的命中率等 情况
        System.out.println(studentCache.stats().toString());

        /**
         * 所有的Guava Cache都支持get(K, Callable<V>)方法，这个方法返回缓存中相应的方法。或者直接返回Callable运行后的
         * 结果。
         * 显式插入：Cache.put(key, value) 方法可以直接向缓存中插入值，但是这会覆盖之前已经存在的值。
         */

        /** Callable实现缓存*/
        Cache<String, String > cache = CacheBuilder.newBuilder().maximumSize(1000)
                .weigher(new Weigher<String, String>() {
                    /**
                     * 设置缓存的权重，以及最大权重值。
                     */
                    @Override
                    public int weigh(String s1, String s2) {
                        return s1.length() - s2.length();
                    }
                }).maximumWeight(1000).build();
        String resultVal = cache.get("jerry", new Callable<String>() {
            @Override
            public String call() {
                String strProValue="hello "+"jerry"+"!";
                return strProValue;
            }
        });
        System.out.println("jerry value : " + resultVal);

        resultVal = cache.get("peida", new Callable<String>() {
            @Override
            public String call() {
                String strProValue="hello "+"peida"+"!";
                return strProValue;
            }
        });
        System.out.println("peida value : " + resultVal);

    }
}
