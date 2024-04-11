package com.study.jvm.actual_combat.performance.practice.service;


import com.study.jvm.actual_combat.performance.practice.entity.User;
import com.study.jvm.actual_combat.performance.practice.entity.UserDetails;

import java.util.List;

public interface UserService {
    List<UserDetails> getUserDetails();

    List<User> getUsers();
}
