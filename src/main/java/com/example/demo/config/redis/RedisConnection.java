/**
 * fshows.com
 * Copyright (C) 2013-2018 All Rights Reserved.
 */
package com.example.demo.config.redis;


import com.example.demo.RedisConfig;
import com.example.demo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 *
 * @author wangyp
 * @version RedisConnection.java, v 0.1 2018-06-18 23:13
 */

public class RedisConnection {

    /** redis通用的连接方式，适用于各种场景*/
    @Autowired
    private RedisTemplate<String, Student> redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisConfig redisConfig;


    public static void main(String[] args) {

        RedisTemplate<String, Student> redis = new RedisTemplate<>();
        redis.opsForValue().set("", new Student());

        /** redis实现系列化 */
        redis.setKeySerializer(new StringRedisSerializer());
        redis.setValueSerializer(new Jackson2JsonRedisSerializer<Student>(Student.class));
        Student student = new Student();
        String key = "redisTemple";
        student.setId(123);
        student.setAge(18);
        student.setGrander("男");
        student.setName("大哥");
        /** 普通的赋值*/
        redis.opsForValue().set(key, student);

        /** 以绑定key的方式从插入value值*/
        BoundListOperations<String, Student> boundListOperations = redis.boundListOps(key);
        boundListOperations.leftPop();
        boundListOperations.leftPush(new Student());
    }


}

