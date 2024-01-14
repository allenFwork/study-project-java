package com.study.mybatis_plus.mapper;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.study.mybatis_plus.domain.po.User2;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

/**
 * 基于 MyBatis Plus 框架实现的Mapper接口
 * 该接口继承 com.baomidou.mybatisplus.core.mapper.BaseMapper 接口，那么就有了许多默认方法
 */
public interface UserMapper2 extends BaseMapper<User2> {

    User2 queryById(@Param("id") Long id);

    // Constants.WRAPPER 就是字符串 “ew”
    @Update("update user set balance = balance - #{money} ${ew.customSqlSegment}")
    void deductBalanceByIds(@Param("money") int money, @Param(Constants.WRAPPER) QueryWrapper<User2> queryWrapper);

//    @Select("select u.* from user u inner join address a on u.id = a.user_id ${ew.customSqlSegment}")
    List<User2> queryUserByWrapper(@Param("ew") QueryWrapper<User2> queryWrapper);

    @Update("UPDATE user SET balance = balance - #{money} WHERE id = #{id}")
    void deductMoneyById(@Param("id") Long id, @Param("money") Integer money);
}
