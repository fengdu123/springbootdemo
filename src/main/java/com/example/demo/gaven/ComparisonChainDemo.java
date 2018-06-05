package com.example.demo.gaven;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wangyp
 * @Description:  Guava 库中 ComparisonChain实现对象之前的相互比较
 * @Date: Created in 11:19 2018/6/5
 * @Modified By:
 */
public class ComparisonChainDemo {

    /**
     * 使用ComparisonChain类会比Java自带的compare/compareTo优雅，使用起来更加简洁，也不容易犯错。
     *
     * @param args
     */
    public static void main(String[] args){

    }
}

@Data
@NoArgsConstructor
class Student{

    private int id;
    private int age;
    private String greader;
    private Double money;

    public int compareTo(Student student){

        return ComparisonChain.start()
                .compare(this.id, student.id)
                .compare(this.age, student.age)
                .compare(this.greader.length(), student.greader.length())
                .result();
    }
}

