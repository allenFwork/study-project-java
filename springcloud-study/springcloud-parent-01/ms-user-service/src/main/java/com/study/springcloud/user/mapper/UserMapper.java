package com.study.springcloud.user.mapper;

import com.study.springcloud.user.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    
    @Select("select * from tb_user where id = #{id}")
    User findById(@Param("id") Long id);
}