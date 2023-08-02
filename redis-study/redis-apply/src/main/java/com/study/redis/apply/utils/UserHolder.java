package com.study.redis.apply.utils;

import com.study.redis.apply.dto.UserDTO;

public class UserHolder {

    private static final ThreadLocal<UserDTO> theadLocal = new ThreadLocal<>();

    public static void saveUser(UserDTO user){
        theadLocal.set(user);
    }

    public static UserDTO getUser(){
        return theadLocal.get();
    }

    public static void removeUser(){
        theadLocal.remove();
    }

}
