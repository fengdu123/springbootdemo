/**
 * fshows.com
 * Copyright (C) 2013-2018 All Rights Reserved.
 */
package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * redis客户端配置
 *
 * @author wangyp
 * @version RedisConfig.java, v 0.1 2018-06-18 22:56
 */

@Configuration
public class RedisConfig {


    /**
     * redis client 客户端连接方式
     *
     * @return
     */
//    @Bean
//    public RedisConnectionFactory redisConnfig() {
//
//        /**
//         * redis连接池：
//         * JedisConnectionFactory()
//         * LettuceClusterConnection()
//         *
//         */
//        JedisConnectionFactory jedis = new JedisConnectionFactory();
//        jedis.setHostName("localhost");
//        jedis.setPort(6379);
//        jedis.setPassword("123456");
//        return jedis;
//    }


}

