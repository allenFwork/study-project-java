package com.study.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from user")
    List<User> list();

    @Select("select * from user where user_id = #{userId}")
    User findOne(Integer userId);

    @Insert("insert into user (user_id, host, user, password) values (#{userId},#{host},#{user},#{password})")
    int save(User user);

}

