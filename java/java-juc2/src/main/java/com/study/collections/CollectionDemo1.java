package com.study.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class CollectionDemo1 {

    public static void main(String[] args) {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            User user = new User(i, "User" + i);
            list.add(user);
        }
        // 遍历集合
        Iterator<User> it = list.iterator();
        while (it.hasNext()) {
            User user = it.next();
            if ("User6".equals(user.getName()))
                // 删除操作，报异常：
                // Exception in thread "main" java.util.ConcurrentModificationException
                list.remove(user);
        }
    }

}
