package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wangyp
 * @Description:
 * @Date: Created in 20:58 2018/6/3
 * @Modified By:
 */

@Data
@NoArgsConstructor
public class Student {

    private Integer id;
    private String grander;
    private String name;
    private Integer age;
    private String sex;
    private Double menoy;

}
