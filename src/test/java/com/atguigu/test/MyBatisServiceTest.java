package com.atguigu.test;

import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.pojo.User;
import com.atguigu.mybatisplus.service.UserService;
import com.atguigu.mybatisplus.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**通用的service 接口
 * @author wystart
 * @create 2022-08-03 23:06
 */
//spring测试类写法一
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class MyBatisServiceTest {




    @Autowired
    private UserService userService;

    @Test
    public void testCount(){

        //查询总记录条数
        //SELECT COUNT( * ) FROM user
        long count = userService.count();
        System.out.println("总记录条数" + count);


    }


    @Test
    public void testSaveBatch(){


        //实现新增用户
        ArrayList<User> list = new ArrayList<>();

        // SQL长度有限制，海量数据插入单条SQL无法实行，
        // 因此MP将批量插入放在了通用Service中实现，而不是通用Mapper
        for (int i = 0; i < 100 ; i++) {

            User user = new User();

            user.setName("abc"+i);

            user.setAge(20+i);

            list.add(user);
        }

        //INSERT INTO user ( id, name, age ) VALUES ( ?, ?, ? )
        boolean b = userService.saveBatch(list);

        System.out.println(b);

    }

}
