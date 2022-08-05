package com.atguigu.mybatisplus.mapper;

import com.atguigu.mybatisplus.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author wystart
 * @create 2022-08-03 16:35
 */
public interface UserMapper extends BaseMapper<User> {


    //继承BaseMapper后，不需要写这样的条件语句了
    // /*
    // 查询所有用户信息
    //  */
    // List<User>  getAllUser();


    //自定义功能
    //根据id查询用户信息，输出为map集合
    Map<String, Object> selectMapById(long id);


    /**
     * 根据年龄查询用户列表，分页显示
     * @param page 分页对象，xml中可以从里面进行取值，传递参数 Page 即自动分页，必须放在第一位
     * @param age 年龄
     * @return
     */
    IPage<User> selectPageVo(@Param("page") Page<User> page , @Param("age") Integer age);

}



