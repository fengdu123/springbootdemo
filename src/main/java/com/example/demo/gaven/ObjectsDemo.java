package com.example.demo.gaven;


import com.google.common.base.Objects;

/**
 * @Author: wangyp
 * @Description:  Guava Object方法的使用
 * @Date: Created in 0:30 2018/6/5
 * @Modified By:
 */
public class ObjectsDemo {

    /**
     * Guava的这个功能比较坑爹，和Util包中的Object差不多。
     * @param args
     */

    public static void main(String[] args) {

        /**
         * 判断两个对象是否相等
         */
        System.out.println(Objects.equal(123, null));
        System.out.println(Objects.hashCode("a"));
        System.out.println(Objects.equal(null, 123));

    }
}
