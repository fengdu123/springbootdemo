/**
 * fshows.com
 * Copyright (C) 2013-2018 All Rights Reserved.
 */
package com.example.demo.mongodb;

import com.example.demo.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 *
 * @author wangyp
 * @version MongodbDemo.java, v 0.1 2018-07-01 15:13
 */

//public interface MongodbDemo extends MongoRepository<String, Student>{
//
//    /**
//     * mongodbDemo这个类继承了MongoRepository的一些方法
//     */
//
//    /**
//     * 根据性别查找符合条件的student
//     * @param mongodbClient
//     * @return
//     */
//    @Query("{'Student': 'mongodbClient is sex', 'id' > 1}")
//    List<Student> findByMongodbClient(String mongodbClient);
//
//    /**
//     * 根据姓名查找所有符合条件的student
//     * @param name
//     * @return
//     */
//    List<Student> findStudentLike(String name);
//
//
//}
//
