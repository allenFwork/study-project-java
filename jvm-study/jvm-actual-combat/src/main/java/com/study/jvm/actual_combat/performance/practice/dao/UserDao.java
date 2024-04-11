package com.study.jvm.actual_combat.performance.practice.dao;


import com.study.jvm.actual_combat.performance.practice.entity.UserDetails;

import java.util.List;

public interface UserDao {
    List<UserDetails> findUsers();
}
