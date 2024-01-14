package com.study.mybatis_plus.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.study.mybatis_plus.domain.po.Address;
import com.study.mybatis_plus.domain.po.User2;
import com.study.mybatis_plus.service.IAddressService;
import com.study.mybatis_plus.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class UserMapper2Test {

    @Autowired
    private UserMapper2 userMapper2;

    @Test
    void testInsert() {
        User2 user = new User2();
        user.setId(5L);
        user.setUsername("Lucy");
        user.setPassword("123");
        user.setPhone("18688990011");
        user.setBalance(200);
//        user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper2.insert(user);
    }

    @Test
    void testSelectById() {
        User2 user = userMapper2.selectById(5L);
        System.out.println("user = " + user);
    }

    @Test
    void testQueryByIds() {
        List<User2> users = userMapper2.selectBatchIds(List.of(1L, 2L, 3L, 4L));
        users.forEach(System.out::println);
    }

    @Test
    void testUpdateById() {
        User2 user = new User2();
        user.setId(5L);
        user.setBalance(20000);
        userMapper2.updateById(user);
    }

    @Test
    void testDeleteUser() {
        userMapper2.deleteById(5L);
    }

    @Test
    void testQuery() {
        User2 user2 = userMapper2.queryById(5L);
        System.out.println(user2);
    }

    @Test
    void testQueryWrapper() {
        // 1.构建查询条件 where name like "%o%" AND balance >= 1000
        QueryWrapper<User2> queryWrapper = new QueryWrapper<User2>()
                .select("id", "username", "info", "balance")
                .like("username", "o")
                .ge("balance", 1000);
        // 2.查询数据
        List<User2> user2List = userMapper2.selectList(queryWrapper);
        user2List.forEach(System.out::println);
    }

    @Test
    void testUpdateByQueryWrapper() {
        // 1.构建查询条件 where name = "Jack"
        QueryWrapper<User2> wrapper = new QueryWrapper<User2>().eq("username", "Jack");
        // 2.更新数据，user中非null字段都会作为set语句
        User2 user = new User2();
        user.setBalance(2000);
        userMapper2.update(user, wrapper);
    }

    @Test
    void testUpdateWrapper() {
        // UPDATE user SET balance = balance - 200 WHERE id in (1, 2, 4)
        List<Long> ids = List.of(1L, 2L, 4L);
        // 1.生成SQL
        UpdateWrapper<User2> updateWrapper = new UpdateWrapper<User2>()
                .setSql("balance = balance - 200")      // SET balance = balance - 200
                .in("id", ids);                  // WHERE id in (1, 2, 4)
        // 2.更新，注意第一个参数可以给null，也就是不填更新字段和数据，基于UpdateWrapper中的setSQL来更新
        userMapper2.update(null, updateWrapper);
    }

    @Test
    void testLambdaQueryWrapper() {
        // 1.构建条件 WHERE username LIKE "%o%" AND balance >= 1000
        LambdaQueryWrapper<User2> lambdaQueryWrapper = new LambdaQueryWrapper<User2>()
                .select(User2::getId, User2::getUsername, User2::getInfo, User2::getBalance)
                .like(User2::getUsername, "o")
                .ge(User2::getBalance, 1000);
        // 2.查询
        List<User2> user2List = userMapper2.selectList(lambdaQueryWrapper);
        user2List.forEach(System.out::println);
    }

    @Test
    void testCustomWrapper() {
        // UPDATE user SET balance = balance - 200 WHERE id in (1, 2, 4)
        // 1.准备自定义查询条件
        List ids = List.of(1L, 2L, 4L);
        QueryWrapper<User2> queryWrapper = new QueryWrapper<User2>().in("id", ids);
        // 2.调用mapper的自定义方法，直接传递Wrapper
        userMapper2.deductBalanceByIds(200, queryWrapper);
    }

    @Test
    void testCustomWrapper2() {
        // 1.准备自定义查询条件
        List ids = List.of(1L, 2L, 4L);
        QueryWrapper<User2> queryWrapper = new QueryWrapper<User2>()
                .in("u.id", ids)
                .eq("a.city", "北京");
        // 2.调用mapper的自定义方法，直接传递Wrapper
        List<User2> user2List = userMapper2.queryUserByWrapper(queryWrapper);
        user2List.forEach(System.out::println);
    }

    private User2 buildUser(int i) {
        User2 user = new User2();
        user.setUsername("user_" + i);
        user.setPassword("123");
        user.setPhone("" + (18688190000L + i));
        user.setBalance(2000);
//        user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(user.getCreateTime());
        return user;
    }

    /**
     * 测试单条单条的插入10万数据到数据库耗时情况
     */
    @Test
    void testSaveOneByOne() {
        long b = System.currentTimeMillis();
        for (int i = 1; i <= 100000; i++) { // 插入10万条数据
            userMapper2.insert(buildUser(i));
        }
        long e = System.currentTimeMillis();
        System.out.println("耗时：" + (e - b));
    }

    @Autowired
    private IUserService userService;

    /**
     * 测试批量插入10万数据到数据库耗时情况
     */
    @Test
    void testSaveBatch() {
        // 准备10万条数据
        List<User2> list = new ArrayList<>(1000);
        long b = System.currentTimeMillis();
        for (int i = 1; i <= 100000; i++) {
            list.add(buildUser(i));
            // 每1000条批量插入一次
            if (i % 1000 == 0) {
                userService.saveBatch(list);
                list.clear();
            }
        }
        long e = System.currentTimeMillis();
        System.out.println("耗时：" + (e - b));
    }

    @Test
    void testGet() {
        User2 user = Db.getById(1L, User2.class);
        System.out.println(user);
    }

    @Test
    void testList() {
        List<User2> user2List = Db.listByIds(List.of(1L, 2L, 3L), User2.class);
        user2List.forEach(System.out::println);
        user2List = Db.lambdaQuery(User2.class)
                .like(User2::getUsername, "o")
                .ge(User2::getBalance, 1000)
                .list();
        user2List.forEach(System.out::println);
    }

    @Test
    void testDbUpdate() {
        Db.lambdaUpdate(User2.class)
                .set(User2::getBalance, 2000)
                .eq(User2::getUsername, "Rose")
                .update();
    }

    @Autowired
    IAddressService addressService;

    @Test
    void testDeleteByLogic() {
        // 删除方法与以前没有区别
        addressService.removeById(59L);
    }

    @Test
    void testQueryAddress() {
        // 测试添加了逻辑删除字段后，查询的语句变化
        List<Address> list = addressService.list();
        list.forEach(System.out::println);
    }

    @Test
    void testPageQuery() {
        // 1.分页查询，new Page()的两个参数分别是：页码、每页大小
        Page<User2> page = userService.page(new Page<>(2, 2));
        // 2.总条数
        System.out.println(page.getTotal());
        // 3.总页数
        System.out.println(page.getPages());
        // 4.数据
        List<User2> records = page.getRecords();
        records.forEach(System.out::println);
    }

    @Test
    void testPageQuery2() {
        int pageNo = 1, pageSize = 5;
        // 分页参数
        Page<User2> page = Page.of(pageNo, pageSize);
        // 排序参数, 通过OrderItem来指定
        page.addOrder(new OrderItem("balance", false));

        page = userService.page(page);
        page.getRecords().forEach(System.out::println);
    }

}