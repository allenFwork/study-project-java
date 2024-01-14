package com.study.mybatis_plus.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.study.mybatis_plus.domain.dto.PageDTO;
import com.study.mybatis_plus.domain.dto.UserFormDTO;
import com.study.mybatis_plus.domain.po.User2;
import com.study.mybatis_plus.domain.query.UserQuery;
import com.study.mybatis_plus.domain.vo.UserVO;
import com.study.mybatis_plus.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "用户管理接口")
@RequiredArgsConstructor // 只将必须的成员变量放入构造方法中
@RestController
@RequestMapping("users")
public class UserController {

    /*
        spring框架推荐使用构造方法注入对象，不推荐使用 @Autowired注解，
        可以使用final修饰成员对象，那么构造方法中必须要有该属性
     */
    private final IUserService userService;

    @PostMapping
    @ApiOperation("新增用户")
    public void saveUser(@RequestBody UserFormDTO userFormDTO) {
        // 1.转换DTO为PO
        User2 user = BeanUtil.copyProperties(userFormDTO, User2.class);
        // 2.新增
        userService.save(user);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除用户")
    public void removeUserById(@PathVariable("id") Long userId) {
        userService.removeById(userId);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询用户")
    public UserVO queryUserById(@PathVariable("id") Long userId) {
//        // 1.查询用户
//        User2 user = userService.getById(userId);
//        // 2.处理vo
//        return BeanUtil.copyProperties(user, UserVO.class);

        // 基于自定义service方法查询（需求变更）
        return userService.queryUserAndAddressById(userId);
    }

    @GetMapping
    @ApiOperation("根据id集合查询用户")
    public List<UserVO> queryUserByIds(@RequestParam("ids") List<Long> ids) {
//        // 1.查询用户
//        List<User2> users = userService.listByIds(ids);
//        // 2.处理vo
//        return BeanUtil.copyToList(users, UserVO.class);
        // 基于自定义service方法查询（需求变更）
        return userService.queryUserAndAddressByIds(ids);
    }

    @PutMapping("{id}/deduction/{money}")
    @ApiOperation("扣减用户余额")
    public void deductBalance(@PathVariable("id") Long id, @PathVariable("money") Integer money) {
        userService.deductBalance(id, money);
    }

    @GetMapping("/list")
    @ApiOperation("根据条件查询用户")
    public List<UserVO> queryUsers(UserQuery query) {
        // 1.组织条件
        String username = query.getName();
        Integer status = query.getStatus();
        Integer minBalance = query.getMinBalance();
        Integer maxBalance = query.getMaxBalance();

        // 方法一：
        LambdaQueryWrapper<User2> wrapper = new LambdaQueryWrapper<User2>()
                .like(username != null, User2::getUsername, username)
                .eq(status != null, User2::getStatus, status)
                .ge(minBalance != null, User2::getBalance, minBalance)   // ge 大于等于
                .le(maxBalance != null, User2::getBalance, maxBalance);  // le 小于等于
        // 2.查询用户
        List<User2> list = userService.list(wrapper);

        // 方法二：lambdaQuery的链式编程
        // 2.查询用户
        list = userService.lambdaQuery().like(username != null, User2::getUsername, username)
                .eq(status != null, User2::getStatus, status)
                .ge(minBalance != null, User2::getBalance, minBalance)
                .le(maxBalance != null, User2::getBalance, maxBalance)
                .list();// 执行查询，返回集合结果

        // 3.处理vo
        return BeanUtil.copyToList(list, UserVO.class);
    }

    @ApiOperation("用户分页查询")
    @GetMapping("/page")
    public PageDTO<UserVO> queryUsersPage(UserQuery query) {
        return userService.queryUsersPage(query);
    }
}