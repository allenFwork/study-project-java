package com.study.dao;

import com.study.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TransactionDao {

    @Select("select * from user")
    List<User> selectAll();

    @Insert("insert into user (id, name, power) values (#{id}, #{name}, #{power})")
    int insert(User user);

    @Update("update user set power = #{power} , name = #{name} where id = #{id}")
    int update(User user);

}
