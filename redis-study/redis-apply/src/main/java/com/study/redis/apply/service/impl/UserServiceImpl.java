package com.study.redis.apply.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.redis.apply.dto.LoginFormDTO;
import com.study.redis.apply.dto.Result;
import com.study.redis.apply.dto.UserDTO;
import com.study.redis.apply.entity.User;
import com.study.redis.apply.mapper.UserMapper;
import com.study.redis.apply.service.IUserService;
import com.study.redis.apply.utils.RegexUtils;
import com.study.redis.apply.utils.SystemConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.study.redis.apply.utils.RedisConstants.*;

/**
 * 服务实现类
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result sendCode(String phone, HttpSession session) {
        // 1.校验手机号
        boolean flag = RegexUtils.isPhoneInvalid(phone);
        // 2.如果手机号不合法, 返回错误信息
        if (flag == true) {
            return Result.fail("手机号格式错误！");
        }
        // 3.手机号合法, 生成验证码
        String code = RandomUtil.randomNumbers(6); // 利用 hutool工具包的方法生成随机六位数的验证码

        // 4.保存验证码
        // session.setAttribute("code", code); // 为了解决Session数据共享问题，使用redis进行缓存，不用session缓存
        // redis中缓存字符串类型，并且设置缓存的手机号验证码对应的有效时间，不让其一直存储着消耗内存
        stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY + phone, code, 2, TimeUnit.MINUTES);

        // 5.发送验证码: 调用第三方平台发送验证(不实现,模拟)
        log.debug("发送短信验证码，验证码: {}", code);
        System.out.println("验证码：" + code);
        // 6.返回 OK
        return Result.ok();
    }

    @Override
    public Result login(LoginFormDTO loginForm, HttpSession session) {
        // 1.校验手机号
        String phone = loginForm.getPhone();
        boolean phoneFlag = RegexUtils.isPhoneInvalid(phone);
        if (phoneFlag) {
            return Result.fail("手机号格式错误！");
        }
        // 2.校验验证码
        String code = loginForm.getCode();
        // Object cacheCode = session.getAttribute("code");
        // 获取验证码操作从原来的session中，换为redis中
        String cacheCode = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY + phone);
        if (cacheCode == null || !cacheCode.toString().equals(code)) {
            // 3.手机号验证码不一致, 报错
            return Result.fail("验证码错误");
        }

        // 4.手机号验证码一致, 根据手机号查询用户 select * from tb_user where phone = ?
        User user = query().eq("phone", phone).one(); // Mybatis Plus 的使用
        // query().eq("phone", phone).list(); // Mybatis Plus 的使用: 查询出来是集合

        // 5.判断用户是否存在
        if (user == null) {
            // 6.不存在该用户, 则创建新用户并保存
            user = createUserWithPhone(phone);
        }

        // 7.保存用户信息到session中
        // session.setAttribute("user", user); // 查询出来的用户信息太全了，会出现安全问题，所以进行部分影藏
        // 通过 BeanUtil的copyProperties方法 从user对象创建UserDTO对象
        // session.setAttribute("user", BeanUtil.copyProperties(user, UserDTO.class));
        // 7.保存用户信息到redis中（解决session数据共享失败问题）
        // TODO 7.1 随机生成token，作为登录令牌
        String token = UUID.randomUUID().toString(true); // 通过 hutool 提供的UUID工具类直接生成
        // TODO 7.2 将User对象转化为HashMap存储
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        Map<String, Object> userMap = BeanUtil.beanToMap(userDTO,
                new HashMap<>(),                    // 创建一个Map,将UserDTO对应的属性和值放到Map中的key-value中
                CopyOptions.create()                // 创建了一个CopyOptions对象，
                        .setIgnoreNullValue(true)   // CopyOptions设置: 如果value值为Null那么直接忽略，设置为空字符串作为值
                        // 主要目的是处理掉UserDTO中Long类型的值，将其变为了String类型
                        .setFieldValueEditor((field, fieldValue) -> fieldValue.toString()) // 函数式编程，对所有属性的值进行获取该对象的字符串值：
        );
        // TODO 7.3 存储
        stringRedisTemplate.opsForHash().putAll(LOGIN_USER_KEY + token, userMap);
        // 7.4 设置有效期: token设置为30分钟有效期，即默认登录30分钟
        stringRedisTemplate.expire(LOGIN_USER_KEY + token, LOGIN_USER_TTL, TimeUnit.SECONDS);
        // TODO 7.5 返回token
        return Result.ok(token);

//        // 此处必须返回ok对应的信息，
//        // 因为前端通过其值作为判断条件，是否会进入用户登录校验（用户登录成功后本地存储了相关信息作为已登陆的判断标识）
//        return Result.ok();
    }

    /**
     * 创建用户, 并存储到数据库中
     *
     * @param phone 手机号
     * @return
     */
    private User createUserWithPhone(String phone) {
        // 1.创建用户
        User user = new User();
        user.setPhone(phone);
        user.setNickName(SystemConstants.USER_NICK_NAME_PREFIX + RandomUtil.randomString(10));
        // 2.保存用户：mybatis plus提供的 save方法
        save(user);
        return user;
    }

}
