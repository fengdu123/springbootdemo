package com.example.demo.gaven;


import com.google.common.base.Optional;


/**
 * @Author: wangyp
 * @Description: Guava 库中的Optional使用
 * @Date: Created in 12:38 2018/6/5
 * @Modified By:
 */
public class OptionalDemo {

    /**
     * Guava用Optional<T>表示可能为null的T类型引用。
     * 所用Optional可以让人主动地思考引用为null的情况。
     *
     * Optional可以运用在判断这个对象或者这个值是否为空的情况，运用 Optional.fromNullable(对象或者值)，Optional.isPresent()
     * 判断得到的值是否为true，为true表示此实例不为空。
     * @param args
     */
    public static void main(String[] args) {
        Optional<Integer> optional = Optional.of(5);
        System.out.println(optional.isPresent());
        System.out.println(optional.get());

        /** 如果直接赋值null的话，会抛出异常，因底层调用了 Preconditions.checkNotNull(T reference) 这个方法，检查是否为空*/
//        optional = Optional.of(null);
        /** 创建一个缺失的Optional 的实例，也就是创建一个为null的对象*/
//        optional = Optional.absent();
        /** 创建指定引用的Optional实例，如果插入的是一个null值，他不会抛出异常，可以用Optional.isPresent() 查看师傅包含有null值*/
        optional = Optional.fromNullable(5);
        /** 如果Optional 包含的T实例不为null，则返回true;若T实例为null，返回false*/
        System.out.println(optional.isPresent());
        /** 该T实例必须不为空，对包含null的Optional实例调用get()会抛出一个IllegalStateException异常 */
//        System.out.println(optional.get());
        /** Optional.or(T) 若Optional包换输入的值，则输出Optional的值，如果不包含就直接输出输入的值 */
        System.out.println(optional.or(6));

        /**
         * 返回Optional实例中包含的非空T实例，如果Optional中包含的是空值，返回null，逆操作是fromNullable()
         */
        System.out.println(optional.orNull());

        /**
         * 返回一个不可修改的Set，该Set中包含Optional实例中包含的所有非空存在的T实例，且在该Set中，每个T实例都是单态，
         * 如果Optional中没有非空存在的T实例，返回的将是一个空的不可修改的Set
         * */
        System.out.println(optional.asSet());

    }
}
