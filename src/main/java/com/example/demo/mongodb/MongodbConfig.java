/**
 * fshows.com
 * Copyright (C) 2013-2018 All Rights Reserved.
 */
package com.example.demo.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Arrays;

/**
 *
 * @author wangyp
 * @version MongodbConfig.java, v 0.1 2018-07-01 14:29
 */

//@Configuration
///** 启动mongodb的repository功能*/
//@EnableMongoRepositories(basePackages = "")
//public class MongodbConfig extends AbstractMongoConfiguration{
//
//    /**
//     * 创建mongodb的客户端
//     * @return
//     */
//    @Override
//    public MongoClient mongoClient() {
//        /** 创建mongdb的凭证*/
//        MongoCredential credential = MongoCredential
//                .createCredential("mongodb.username",
//                        "ordersDB",
//                        "mongodb.password".toCharArray());
//
//        /** 创建mongodb实例*/
//        return new MongoClient(new ServerAddress("localhost", 37017),
//                Arrays.asList(credential));
//    }
//
//    /**
//     *
//     * 指定数据库的名称
//     * @return
//     */
//    @Override
//    protected String getDatabaseName() {
//        return "ordersDB";
//    }
//
//
//}

