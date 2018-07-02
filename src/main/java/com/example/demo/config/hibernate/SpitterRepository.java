///**
// * fshows.com
// * Copyright (C) 2013-2018 All Rights Reserved.
// */
//package com.example.demo.config.hibernate;
//
//import com.example.demo.model.Student;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.orm.jpa.vendor.Database;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.PersistenceContext;
//import javax.persistence.PersistenceUnit;
//
//
///**
// * 基于jpa实现spring自动化配置Hibernate
// * @author wangyp
// * @version SpitterRepository.java, v 0.1 2018-07-02 21:31
// */
//@Repository
///** 加入事务 是entityManager成为线程安全的*/
//@Transactional
//public class SpitterRepository {
//
//    /**
//     * 使用Hibernate-JpaVendorAdapter作为JPA的实现
//     * 使用起来比较简单，只需要简单配置就可以。
//     * 同时Hibernate支持多种数据库  可以通过Datebase选择相应的数据库
//     * @return
//     */
////    @Bean
////    public JpaVendorAdapter jpaVendorAdapter(){
////        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
////        adapter.setDatabase(Database.MYSQL);
////        /** 是否展示sql*/
////        adapter.setShowSql(true);
////        adapter.setGenerateDdl(false);
////        adapter.setDatabasePlatform("org.hibernate.dialect.MySQL");
////        return adapter;
////
////    }
//
//
//    /**
//     * 创建不依赖spring的jpa
//     */
//
//
//    /** 注入entityManagerFactory*/
////    @PersistenceUnit
//    /** 并不会真正注入entityManager，另外这两个都不是spring的注解，而是
//     * JPA规范的。*/
//    @PersistenceContext
//    private EntityManagerFactory emf;
//
//
//    /** 创建并使用entity Manager*/
//    public void addStudent(Student student){
//        emf.createEntityManager().persist(student);
//    }
//
//    public Student getStudentById(long id){
//        return emf.createEntityManager().find(Student.class, id);
//    }
//
//    public void saveStudent(Student student){
//        emf.createEntityManager().merge(student);
//    }
//
//
//}
//
