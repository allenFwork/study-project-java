package com.study.config;

import com.study.entity.User;
import com.study.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Configuration
public class WebFluxConfiguration {

    @Bean
    @Autowired
    public RouterFunction<ServerResponse> routerFunctionUsers(UserRepository userRepository) {
        Collection<User> users = userRepository.findAll();
        Flux<User> userFlux = Flux.fromIterable(users);
//        Mono<Collection<User>> mono = Mono.just(users);
        return RouterFunctions.route(RequestPredicates.path("/users"),
                                request -> ServerResponse.ok().body(userFlux, User.class));
    }
}
