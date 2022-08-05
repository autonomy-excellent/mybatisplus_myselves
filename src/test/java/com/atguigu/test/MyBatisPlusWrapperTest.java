package com.atguigu.test;

import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.pojo.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.OutputStreamWriter;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Map;

/**
 * @author wystart
 * @create 2022-08-04 22:19
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class MyBatisPlusWrapperTest {




    @Autowired
    public UserMapper userMapper;

    @Test
    public void  test01(){

      //查询用户名包含a，年龄在20到30之间，并且邮箱不为null的用户信息
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        //SELECT uid AS id,user_name AS name,age,email,is_deleted FROM t_user WHERE is_deleted=0 AND (user_name LIKE ? AND age BETWEEN ? AND ? AND email IS NOT NULL)
        queryWrapper.like("user_name","a")
                .between("age",20,30)
                .isNotNull("email");

        List<User> userList = userMapper.selectList(queryWrapper);

        userList.forEach(System.out::println);
    }



    @Test
    public void test02(){

        //按年龄降序查询用户，如果年龄相同则按id升序排列

        //SELECT uid AS id,user_name AS name,age,email,is_deleted FROM t_user WHERE is_deleted=0 ORDER BY age DESC,uid ASC
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.orderByDesc("age")
                .orderByAsc("uid");

        List<User> userList = userMapper.selectList(queryWrapper);

        userList.forEach(System.out::println);


    }

    @Test
    public void test03(){

        //删除email为空的用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();


        //UPDATE t_user SET is_deleted=1 WHERE is_deleted=0 AND (email IS  NULL)
        queryWrapper.isNull("email");

        int result = userMapper.delete(queryWrapper);

        System.out.println("受影响的行数 ： " + result);

    }

    @Test
    public void test04(){


        //将（年龄大于20并且用户名中包含有a）或邮箱为null的用户信息修改
       QueryWrapper<User> queryWrapper = new QueryWrapper<>();

       //UPDATE t_user SET user_name=?, age=?, email=? WHERE is_deleted=0 AND (user_name LIKE ? AND age > ? OR email IS NULL)
       queryWrapper.like("user_name","a")
               .gt("age",20)
               .or()
               .isNull("email");

        User user = new User();
        user.setName("小明");
        user.setEmail("test@atguigu.com");
        int result = userMapper.update(user, queryWrapper);

        System.out.println("受影响的行数：" + result);


    }

    @Test
    public void test05(){

        //将（年龄大于20或邮箱为null）并且用户名中包含有a的用户信息修改
        //lambda表达式内的逻辑优先运算
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        //SELECT uid AS id,user_name AS name,age,email,is_deleted FROM t_user WHERE is_deleted=0 AND (user_name LIKE ? AND (age > ? OR email IS NULL))
        queryWrapper.like("user_name","a")
                .and(i ->i.gt("age",20).or().isNull("email"));

        List<User> userList = userMapper.selectList(queryWrapper);

        userList.forEach(System.out::println);


    }


    @Test
    public void test06(){
        //查询用户信息的username和age字段
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        //SELECT user_name,age FROM t_user WHERE is_deleted=0
        queryWrapper.select("user_name","age");


        List<Map<String, Object>> maps = userMapper.selectMaps(queryWrapper);

        maps.forEach(System.out::println);


    }


    @Test
    public void test07(){


        //查询id小于等于3的用户信息

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        //SELECT uid AS id,user_name AS name,age,email,is_deleted FROM t_user WHERE is_deleted=0 AND (uid IN (select uid from t_user where uid <= 3))
        queryWrapper.inSql("uid","select uid from t_user where uid <= 3");


        List<User> userList = userMapper.selectList(queryWrapper);

        userList.forEach(System.out::println);


    }


    @Test
    public void test08(){


     //将用户名中包含有a并且（年龄大于20或邮箱为null）的用户信息修改
     // 组装set子句以及修改条件
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();

        /*写在一起
        updateWrapper.set("age",18)
                .set("email","user@atguigu.com")
                .like("user_name","a")
                .and(i ->i.gt("age",20).or().isNull("email"));
         */

        //分开写

        //UPDATE t_user SET user_name=?,email=? WHERE is_deleted=0 AND (user_name LIKE ? AND (age > ? OR email IS NULL))
        updateWrapper.like("user_name","a")
                .and(i ->i.gt("age",20).or().isNull("email"));

        updateWrapper.set("user_name","小黑").set("email","abc@atguigu.xom");


        //这里必须要创建User对象，否则无法应用自动填充。如果没有自动填充，可以设置为null
        //UPDATE t_user SET username=?, age=?,email=? WHERE (username LIKE ? AND(age > ? OR email IS NULL))
        //User user = new User();
        //user.setName("张三");
        //int result = userMapper.update(user, updateWrapper);
        //UPDATE t_user SET age=?,email=? WHERE (username LIKE ? AND (age > ? ORemail IS NULL))
        int result = userMapper.update(null, updateWrapper);
        System.out.println("result = " + result);


    }


    @Test
    public void test09(){
        //定义查询条件，有可能为null（用户未输入或未选择）
        String username = null;
        Integer ageBegin = 20;
        Integer ageEnd = 30;

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        //SELECT uid AS id,user_name AS name,age,email,is_deleted FROM t_user WHERE is_deleted=0 AND (age >= ? AND age <= ?)

        //StringUtils.isNotBlank()判断某字符串是否不为空且长度不为0且不由空白符(whitespace)构成
        if(StringUtils.isNotBlank(username)){
            queryWrapper.like("user_name","a");
        }

        if (ageBegin != null){
            queryWrapper.ge("age",ageBegin);
        }

        if (ageEnd != null){
            queryWrapper.le("age",ageEnd);
        }

        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);


    }


    @Test
    public void test10(){
        String username = "a";
        Integer ageBegin = null;
        Integer ageEnd = 30;

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        //SELECT uid AS id,user_name AS name,age,email,is_deleted FROM t_user WHERE is_deleted=0 AND (user_name LIKE ? AND age <= ?)
        queryWrapper.like(StringUtils.isNotBlank(username),"user_name","a")
                .ge(ageBegin != null,"age",ageBegin)
                .le(ageEnd != null,"age",ageEnd);


        List<User> list = userMapper.selectList(queryWrapper);

        list.forEach(System.out::println);


    }

    @Test
    public void test11(){

        String username = "a";
        Integer ageBegin = null;
        Integer ageEnd = 30;

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

        //SELECT uid AS id,user_name AS name,age,email,is_deleted FROM t_user WHERE is_deleted=0 AND (user_name LIKE ? AND age <= ?)
        queryWrapper.like(StringUtils.isNotBlank(username),User::getName,"a")
                .ge(ageBegin != null,User::getAge,ageBegin)
                .le(ageEnd != null,User::getAge,ageEnd);

        List<User> list = userMapper.selectList(queryWrapper);

        list.forEach(System.out::println);
    }

    @Test
    public void test12(){


        //将用户名中包含有a并且（年龄大于20或邮箱为null）的用户信息修改
        // 组装set子句以及修改条件
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();

        /*合在一起
        updateWrapper
                .set(User::getAge, 18)
                .set(User::getEmail, "user@atguigu.com")
                .like(User::getName, "a")
                .and(i -> i.lt(User::getAge, 24).or().isNull(User::getEmail));
                */

        //分开写
        updateWrapper.like(User::getName, "a")
                .and(i -> i.lt(User::getAge, 24).or().isNull(User::getEmail));
        updateWrapper.set(User::getName,"小黑").set(User::getEmail,"abc@atguigu.com");
        User user = new User();
        int result = userMapper.update(user, updateWrapper);
        System.out.println("受影响的行数：" + result);


    }


}
