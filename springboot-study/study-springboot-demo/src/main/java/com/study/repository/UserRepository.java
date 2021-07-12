package com.study.repository;

import com.study.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository {

//    private final Map<Long, User> repository = new HashMap<>();

    // 考虑到高并发问题，使用 ConcurrentMap，它是个接口
    private final ConcurrentMap<Long, User> repository = new ConcurrentHashMap<>();

    private final AtomicLong idGenerator = new AtomicLong();

    public Boolean save(User user) {
        // ID 从 1 开始
        long id = idGenerator.incrementAndGet();
        user.setId(id);
        return repository.putIfAbsent(id, user) == null;
    }

    /**
     * 1.集合方法不能返回空
     * 2.集合方法返回尽量
     *
     * 书记：effective java II
     */
    public Collection<User> findAll() {
        return repository.values();
//        return null;
    }

}
