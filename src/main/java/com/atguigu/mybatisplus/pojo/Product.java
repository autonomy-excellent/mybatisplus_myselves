package com.atguigu.mybatisplus.pojo;

import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

/**
 * @author wystart
 * @create 2022-08-05 10:26
 */@Data
public class Product {


    private Long id;
    private String name;
    private Integer price;

    @Version
    private Integer version;

}
