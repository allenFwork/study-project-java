package com.study.mybatis_plus.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.study.mybatis_plus.domain.dto.PageDTO;
import com.study.mybatis_plus.domain.po.Address;
import com.study.mybatis_plus.domain.po.User2;
import com.study.mybatis_plus.domain.query.UserQuery;
import com.study.mybatis_plus.domain.vo.AddressVO;
import com.study.mybatis_plus.domain.vo.UserVO;
import com.study.mybatis_plus.enums.UserStatus;
import com.study.mybatis_plus.mapper.UserMapper2;
import com.study.mybatis_plus.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper2, User2> implements IUserService {

    @Override
    public void deductBalance(Long id, Integer money) {
        // 1.查询用户
        User2 user = getById(id);
        // 2.判断用户状态
        if (user == null || user.getStatus() == UserStatus.FREEZE) {
            throw new RuntimeException("用户状态异常");
        }
        // 3.判断用户余额
        if (user.getBalance() < money) {
            throw new RuntimeException("用户余额不足");
        }
        // 4.扣减余额（改造） update tb_user set balance = balance - ?
        // baseMapper.deductMoneyById(id, money);
        int remainBalance = user.getBalance() - money;
        lambdaUpdate()
                .set(User2::getBalance, remainBalance)                      // 更新余额
                .set(remainBalance == 0, User2::getStatus, 2)  // 动态判断，是否更新 status
                .eq(User2::getId, id)
                .eq(User2::getBalance, user.getBalance())                   // 乐观锁(防止多线程已经有更新该数据，判断值是否相等)
                .update();                                                  // 执行更新
    }

    @Override
    public UserVO queryUserAndAddressById(Long userId) {
        // 1.查询用户
        User2 user = getById(userId);
        if (user == null) {
            return null;
        }
        // 2.查询收货地址 (在查询地址时，我们采用了Db的静态方法，因此避免了注入AddressService，减少了循环依赖的风险)
        List<Address> addresses = Db.lambdaQuery(Address.class).eq(Address::getUserId, userId).list();
        // 3.封装VO
        // 3.1.转User的PO为VO
        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
        // 3.2.转地址VO
        if (CollUtil.isNotEmpty(addresses)) {
            List<AddressVO> addressVOS = BeanUtil.copyToList(addresses, AddressVO.class);
            userVO.setAddresses(addressVOS);
        }
        return userVO;
    }

    @Override
    public List<UserVO> queryUserAndAddressByIds(List<Long> ids) {
        // 1.查询用户
        List<User2> userList = listByIds(ids);
        if (CollUtil.isEmpty(userList)) {
            return Collections.emptyList();
        }
        // 2.查询地址
        // 2.1.获取用户id集合
        List<Long> userIds = userList.stream().map(User2::getId).collect(Collectors.toList());
        // 2.2.根据用户id查询地址
        List<Address> addresses = Db.lambdaQuery(Address.class).in(Address::getUserId, userIds).list();
        // 2.3.转换地址VO
        List<AddressVO> addressVOList = BeanUtil.copyToList(addresses, AddressVO.class);
        // 2.4.用户地址集合分组处理，相同用户的放入一个集合（组）中
        Map<Long, List<AddressVO>> addressMap = new HashMap<>(0);
        if (CollUtil.isNotEmpty(addressVOList)) {
            addressMap = addressVOList.stream().collect(Collectors.groupingBy(AddressVO::getUserId));
        }
        // 3.转换VO返回
        List<UserVO> list = new ArrayList<>(userList.size());
        for (User2 user : userList) {
            // 3.1.转换User的PO为VO
            UserVO vo = BeanUtil.copyProperties(user, UserVO.class);
            list.add(vo);
            // 3.2.转换地址VO
            vo.setAddresses(addressMap.get(user.getId()));
        }
        return list;
    }

    @Override
    public PageDTO<UserVO> queryUsersPage(UserQuery query) {
//        // 1.构建条件
//        // 1.1.分页条件
//        Page<User2> page = Page.of(query.getPageNo(), query.getPageSize());
//        // 1.2.排序条件
//        if (query.getSortBy() == null) {
//            page.addOrder(new OrderItem("update_time", false));
//        } else {
//            page.addOrder(new OrderItem(query.getSortBy(), query.getIsAsc()));
//        }

        // 替换上面的分页查询语句
        Page<User2> page = query.toMpPageDefaultSortByUpdateTimeDesc();

        // 2.查询
        page(page);

//        // 3.数据非空校验
//        List<User2> records = page.getRecords();
//        if (records == null || records.isEmpty()) {
//            // 无数据，返回空结果
//            return new PageDTO<>(page.getTotal(), page.getPages(), Collections.emptyList());
//        }
//        // 4.有数据，转换
//        List<UserVO> userVOList = BeanUtil.copyToList(records, UserVO.class);
//        // 5.封装返回
//        return new PageDTO<>(page.getTotal(), page.getPages(), userVOList);

        // 3.封装返回 (stream中map方法中的处理逻辑)
        return PageDTO.of(page, user -> {
            // 拷贝属性到VO
            UserVO vo = BeanUtil.copyProperties(user, UserVO.class);
            // 用户名脱敏
            String username = vo.getUsername();
            vo.setUsername(username.substring(0, username.length() - 2) + "**");
            return vo;
        });
    }

}
