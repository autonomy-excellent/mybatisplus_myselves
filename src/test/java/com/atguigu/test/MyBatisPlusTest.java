package com.atguigu.test;

import com.alibaba.druid.sql.visitor.SQLASTOutputVisitor;
import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wystart
 * @create 2022-08-03 17:21
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class MyBatisPlusTest {


    /*
        没有使用mybatis_plus
        @Test
        public void testMyBatis(){
            ApplicationContext ac = new
                    ClassPathXmlApplicationContext("applicationContext.xml");
            UserMapper mapper = ac.getBean(UserMapper.class);
            mapper.getAllUser().forEach(user -> System.out.println(user));
        }

     */
    @Autowired
    private UserMapper userMapper;




    /*---------------------------------使用MyBatisPlus之后-------------------------------------*/
    @Test
    public void testMyBatisPlus(){
         //查询所有用户信息
        // SELECT id,name,age,email FROM user
        System.out.println(userMapper.selectList(null));
    }


    /*--------------------------------------------普通的CRUD-----------------------------------------------*/
    @Test
    public void testInsert(){
        // User user = new User(219, "张三", 23, "zhangsan@atguigu.com");
        User user = new User(4L,"王五",25,"wangwu@atguigu.com");
        // INSERT INTO user ( id, name, age, email ) VALUES ( ?, ?, ?, ? )
        int result = userMapper.insert(user);

        System.out.println("受影响的行数" + result);


    }

    @Test
    public void testDelete(){

        /*
        //① 通过id删除用户

        //DELETE FROM user WHERE id=?
        int result = userMapper.deleteById(6);
        System.out.println("受影响的行数" + result);



         */
        //② 通过id批量删除用户记录


        List<Long> list = Arrays.asList(1L, 2L, 3L);

        //DELETE FROM user WHERE id IN ( ? , ? , ? )
        int result = userMapper.deleteBatchIds(list);
        System.out.println("受影响的行数" + result);


        /*

        //通过map集合删除

        HashMap<String, Object> map = new HashMap<>();
        map.put("user_name","王五");
        map.put("age",22);

        //DELETE FROM user WHERE name = ? AND age = ?
        int result = userMapper.deleteByMap(map);
        System.out.println("受影响的行数" + result);

        */

    }


    @Test
    public void testUpdate(){
        User user = new User(4L, "李四", 22, "lisi@atguigu.com");

        //UPDATE user SET name=?, age=?, email=? WHERE id=?
        int result = userMapper.updateById(user);

        System.out.println("受影响的行数" + result);

    }

    @Test
    public void testSelect(){
        //① 根据id查询用户
        //SELECT id,name,age,email FROM user WHERE id=?
        /*
        User user = userMapper.selectById(1L);

        System.out.println("user = " + user);
        */


        //②根据多个id批量查询

        List<Long> list = Arrays.asList(1L, 2L, 3L);


        //SELECT id,name,age,email FROM user WHERE id IN ( ? , ? , ? )
        List<User> userList = userMapper.selectBatchIds(list);

        for (User user : userList) {
            System.out.println("user1 = " + user);
        }





        // //③ 根据map条件进行查询
        // Map<String, Object> map =  new HashMap<>();
        // map.put("name","李四");
        // map.put("age",22);
        //
        // //SELECT id,name,age,email FROM user WHERE name = ? AND age = ?
        // List<User> userList = userMapper.selectByMap(map);
        //
        // userList.forEach(System.out::println);


    }

    //自定义查询条件
    @Test
    public void  selectMapById(){

        //select id,name,age,email from user where id = ?
        Map<String, Object> map = userMapper.selectMapById(4L);

        System.out.println(map);


    }


























    }








