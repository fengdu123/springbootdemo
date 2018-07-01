package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @Author: wangyp
 * @Description:
 * @Date: Created in 20:58 2018/6/3
 * @Modified By:
 */

@Data
@NoArgsConstructor
@Document
public class Student {

    @Id
    private Integer id;

    /** 性别*/
    @Field(value = "mongodbClient")
    private String grander;
    private String name;
    private Integer age;
    private String sex;
    private Double menoy;

}
