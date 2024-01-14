package com.study.mybatis_plus.mapper;


import com.study.mybatis_plus.domain.po.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    void saveUser(User user);

    void deleteUser(Long id);

    void updateUser(User user);

    User queryUserById(@Param("id") Long id);

    List<User> queryUserByIds(@Param("ids") List<Long> ids);
}
