package com.study.webflux;

import com.study.entity.User;
import com.study.UserRepository;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class UserHandler {

    private final UserRepository userRepository;

    public UserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<ServerResponse> saveUser(ServerRequest serverRequest) {
        // 在 Spring Web MVC  中使用 @RequestBody
        // 在 Spring Web Flux 中使用 ServerRequest
        // Mono<User> 类似于 Optional<User>
        Mono<User> userMono = serverRequest.bodyToMono(User.class);
        // map 相当于 转换工作
        Mono<Boolean> booleanMono = userMono.map(userRepository::saveUser);
        return ServerResponse.ok().body(booleanMono, Boolean.class);
    }

}
