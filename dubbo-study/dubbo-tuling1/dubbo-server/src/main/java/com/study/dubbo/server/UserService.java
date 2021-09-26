package com.study.dubbo.server;

import com.study.dubbo.server.Bean.UserVo;

/**
 * @author Tommy
 * Created by Tommy on 2018/11/18
 **/
public interface UserService {
    UserVo getUser(Integer id);
}
