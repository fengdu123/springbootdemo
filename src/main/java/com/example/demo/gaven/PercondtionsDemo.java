/**
 * fshows.com
 * Copyright (C) 2013-2018 All Rights Reserved.
 */
package
        com.example.demo.gaven;/**
 * @Author: wangyp
 * @Description:
 * @Date: Created in 15:11 2018/6/10
 * @Modified By:
 */

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.Map;

/**
 * @author wangyp
 * @version PercondtionsDemo.java, v 0.1 2018-06-10 15:11
 */
public class PercondtionsDemo {

    /**
     * Preconditions 是guava库用于检验代码校验方法
     *
     * @param args
     */

    public static void main(String[] args) {

        /**
         * Preconditions.checkArgument()用于校验表达式是否正确，一般用户方法中的参数校验,并使用占位符返回错误信息。
         * 当表达式为错误的时候，会抛出异常,这个时候需要使用trycatch去接这个异常
         */
        try {
            Preconditions.checkArgument(StringUtils.equals("a", "b"), "a和b是不相等");
        } catch (IllegalArgumentException e) {
            /** 使用apache common 包中的ExceptionUtils，输出异常的详细信息*/
            System.out.println(ExceptionUtils.getMessage(e));
        }

        /**
         * Preconditions.checkNotNull()用于检验对象是否为空，并使用占位符返回错误信息
         * 当表达式为false的时候，会抛出空指针异常，这时需要使用trycatch一下
         * */
        Map<String, String> params = null;
        try {
            Preconditions.checkNotNull(params, "此时的map还是空的");
        } catch (NullPointerException e ){
            System.out.println(ExceptionUtils.getMessage(e));
        }

        /** 校验表达式是否为真，一般用作校验方法返回是否为真。*/
        try {
            Preconditions.checkState(StringUtils.equals("a", "b"), "%s is wrong", "dhalkhdkas");
        }catch (IllegalStateException e) {
            System.out.println(ExceptionUtils.getMessage(e));
        }
    }
}



