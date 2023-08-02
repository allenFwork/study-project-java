package com.study.redis.client.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 新建的一个User对象用来测试Redis存储对象效果
 */
@Data
@NoArgsConstructor  // 新建一个无参构造方法
@AllArgsConstructor // 新建一个有参构造方法
public class User {

    private String name;
    private Integer age;

}
