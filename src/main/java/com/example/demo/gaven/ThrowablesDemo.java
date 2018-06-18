/**
 * fshows.com
 * Copyright (C) 2013-2018 All Rights Reserved.
 */
package
        com.example.demo.gaven;/**
 * @Author: wangyp
 * @Description:
 * @Date: Created in 23:23 2018/6/12
 * @Modified By:
 */

import com.google.common.base.Throwables;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author wangyp
 * @version ThrowablesDemo.java, v 0.1 2018-06-12 23:23
 */
public class ThrowablesDemo {

    public static void main(String[] args) {

        try {
            StringUtils.isNotBlank("");
        }catch(RuntimeException | Error e){

            throw Throwables.propagate(e);
        }
    }
}

