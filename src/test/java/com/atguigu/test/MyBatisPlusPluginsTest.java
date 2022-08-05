package com.atguigu.test;

import com.atguigu.mybatisplus.mapper.ProductMapper;
import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.pojo.Product;
import com.atguigu.mybatisplus.pojo.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author wystart
 * @create 2022-08-05 9:34
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class MyBatisPlusPluginsTest {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProductMapper productMapper;


    @Test
    public void testProduct01(){
        //小李查询价格
        Product productLi = productMapper.selectById(1);

        System.out.println("小李查询的商品价格：" + productLi.getPrice());

        //小王查询价格
        Product productWang = productMapper.selectById(1);

        System.out.println("小王查询的商品价格：" + productWang.getPrice());


        //小李将商品价格+50
        productLi.setPrice(productLi.getPrice() + 50);
        productMapper.updateById(productLi);

        //小王将商品价格-30
        productWang.setPrice(productWang.getPrice() - 30);
        int result = productMapper.updateById(productWang);

        if (result == 0){

            //操作失败，重试
            Product productNew = productMapper.selectById(1);

            productNew.setPrice(productNew.getPrice() - 30);

            productMapper.updateById(productNew);


        }

        //老板查询商品价格
        Product productBoss = productMapper.selectById(1);

        System.out.println("老板查询的商品价格为：" + productBoss.getPrice());

    }






    @Test
    public void testPage(){
        //设置分页参数
        Page<User> page = new Page<>(2,3);

        userMapper.selectPage(page,null);

        List<User> list = page.getRecords();
        list.forEach(System.out::println);

        System.out.println("每页显示的条数："+page.getSize());
        System.out.println("总记录数："+page.getTotal());
        System.out.println("总页数："+page.getPages());
        System.out.println( "当前页:"  + page.getCurrent());
        System.out.println("是否有下一页:" + page.hasNext());
        System.out.println("是否有上一页:" + page.hasPrevious());


    }


    @Test
    public void testPageVo(){

        Page<User> page = new Page<>(1,5);//第一页索引为1，不是0
        userMapper.selectPageVo(page,20);
        List<User> list = page.getRecords();
        list.forEach(System.out::println);

        System.out.println("每页显示的条数："+page.getSize());
        System.out.println("总记录数："+page.getTotal());
        System.out.println("总页数："+page.getPages());
        System.out.println( "当前页:"  + page.getCurrent());
        System.out.println("是否有下一页:" + page.hasNext());
        System.out.println("是否有上一页:" + page.hasPrevious());





    }









}
