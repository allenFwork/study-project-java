package com.study.springcloud.user.web;

import com.study.springcloud.user.config.PatternProperties;
import com.study.springcloud.user.pojo.User;
import com.study.springcloud.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RefreshScope
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${pattern.dateformat}")
    private String dateformat;

    @Autowired
    private PatternProperties patternProperties;

    @GetMapping("now")
    public String now() {
        System.out.println("从nacos服务器上读取的配置信息：pattern.dateformat: " + dateformat);
        System.out.println("从nacos服务器上读取的配置信息：patternProperties的pattern.dateformat: " + patternProperties.getDateformat());
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateformat));
    }

    /**
     * 路径： /user/110
     *
     * @param id 用户id
     * @return 用户
     */
    @GetMapping("/{id}")
    public User queryById(@PathVariable("id") Long id) {
        return userService.queryById(id);
    }
}
