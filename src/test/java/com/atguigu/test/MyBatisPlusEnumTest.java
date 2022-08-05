package com.atguigu.test;

import com.atguigu.mybatisplus.enums.SexEnum;
import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author wystart
 * @create 2022-08-05 11:06
 */@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class MyBatisPlusEnumTest {

     @Autowired
    private UserMapper userMapper;

     @Test
     public void testSexEnum(){

         User user = new User();
         user.setName("Enum");
         user.setAge(20);

         //设置性别信息为枚举项，会将@EnumValue注解所标识的属性值存储到数据库
         // INSERT INTO t_user ( user_name, age, sex, is_deleted ) VALUES ( ?, ?, ?, ? )
         user.setSex(SexEnum.MALE);

         userMapper.insert(user);
     }




}
