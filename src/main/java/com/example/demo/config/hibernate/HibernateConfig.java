/**
 * fshows.com
 * Copyright (C) 2013-2018 All Rights Reserved.
 */
package com.example.demo.config.hibernate;

import com.example.demo.model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangyp
 * @version HibernateConfig.java, v 0.1 2018-07-02 20:04
 */

/**
 * Repository的作用：
 * 1.用于标记数据访问组件，即DAO组件
 * 2.spring在启动的时候会扫描Repository类所涵盖的包。
 * 3.获取平台相关异常，然后使用spring统一非检查型异常的形式重新抛出。
 *
 * */
//@Repository
public class HibernateConfig {

//    private SessionFactory sessionFactory;
//
//    /**
//     * 注入sessionFactory
//     */
//    public HibernateConfig(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }
//
//    /**
//     * 从sessionFactory中获取当前session
//     */
//    private Session currentSession() {
//
//        return sessionFactory.getCurrentSession();
//    }
//
//    public long count() {
//        return findAll().size();
//    }
//
//    /**
//     * 使用当前session
//     */
//    public Student save(Student student) {
//        Serializable id = currentSession().save(student);
//
//        return new Student();
//    }
//
//    public Student findOne(long id){
//        return currentSession().get(Student.class, id);
//    }
//
//    public Student findByUsername(String username){
//        return (Student) currentSession().createCriteria(Student.class)
//                .add(Restrictions.eq("username", username));
//    }
//
//    public List<Student> findAll() {
//        return (List<Student>) currentSession()
//                .createCriteria(Student.class).list();
//    }


}

