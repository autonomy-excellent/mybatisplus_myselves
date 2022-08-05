package com.atguigu.mybatisplus.pojo;

import com.atguigu.mybatisplus.enums.SexEnum;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * @author wystart
 * @create 2022-08-03 16:22
 */@Data //lombok注解

//设置实体类对应的表名
//@TableName("t_user")
public class User {

     //将属性所对应的字段指定为主键
    // @TableId注解的value属性用于指定主键的字段
    // @TableId注解的type属性设置主键生成策略
    // @TableId(value = "uid", type = IdType.AUTO)
    @TableId("uid")
    private Long id;


    //指定属性所对应的字段名
    @TableField("user_name")
    private String name;

    private int age;

    private String email;

    private SexEnum sex;

    @TableLogic
    private int isDeleted;

    public User(Long id, String name, int age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }


    public User() {
    }
}
