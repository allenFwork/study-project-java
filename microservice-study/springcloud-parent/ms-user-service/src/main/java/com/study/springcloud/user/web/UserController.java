package com.study.springcloud.user.web;

import com.study.springcloud.user.config.PatternProperties;
import com.study.springcloud.user.pojo.User;
import com.study.springcloud.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
    public String now(HttpServletRequest request) { // spring mvc 获取Request，使用 HttpServletRequest 类型
        // 测试 nacos的配置管理功能
        System.out.println("从nacos服务器上读取的配置信息：pattern.dateformat: " + dateformat);
        System.out.println("从nacos服务器上读取的配置信息：patternProperties的pattern.dateformat: " + patternProperties.getDateformat());
        // 测试网关服务的 网关过滤器(路由过滤器/默认过滤器) 和 全局过滤器
        System.out.println("请求头Header中添加Truth:  " + request.getHeaders("Truth").nextElement());
        System.out.println("请求头Header中添加Truth2: " + request.getHeaders("Truth2").nextElement());
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateformat));
    }

    @GetMapping("env")
    public PatternProperties env() {
        return patternProperties;
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
