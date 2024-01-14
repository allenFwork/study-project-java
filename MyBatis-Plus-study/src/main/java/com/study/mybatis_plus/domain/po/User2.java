package com.study.mybatis_plus.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.study.mybatis_plus.enums.UserStatus;
import lombok.Data;

import java.time.LocalDateTime;


// mybatis plus框架的 @TableName 注解表示该实体类映射到哪张表
// autoResultMap默认是false，不开启无法进行类型的自动转化，即使属性上使用了 @TableField(typeHandler = JacksonTypeHandler.class) 也会失败
@TableName(value = "user", autoResultMap = true)
@Data
public class User2 {

    /**
     * 用户id
     */
    @TableId // 表明该字段是表的主键字段
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 注册手机号
     */
    private String phone;

    /**
     * 详细信息
     */
//    private String info;
    @TableField(typeHandler = JacksonTypeHandler.class) // 需要开启配置 @TableName(autoResultMap = true)
    private UserInfo info;

    /**
     * 使用状态（1正常 2冻结）
     */
//    private Integer status;
    @TableField("status")
    private UserStatus status;

    /**
     * 账户余额
     */
    private Integer balance;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
