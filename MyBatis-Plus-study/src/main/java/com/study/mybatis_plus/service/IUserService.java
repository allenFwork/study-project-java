package com.study.mybatis_plus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.mybatis_plus.domain.dto.PageDTO;
import com.study.mybatis_plus.domain.po.User2;
import com.study.mybatis_plus.domain.query.UserQuery;
import com.study.mybatis_plus.domain.vo.UserVO;

import java.util.List;

public interface IUserService extends IService<User2> {

    // 拓展自定义方法
    void deductBalance(Long id, Integer money);

    UserVO queryUserAndAddressById(Long userId);

    List<UserVO> queryUserAndAddressByIds(List<Long> ids);

    PageDTO<UserVO> queryUsersPage(UserQuery query);
}
