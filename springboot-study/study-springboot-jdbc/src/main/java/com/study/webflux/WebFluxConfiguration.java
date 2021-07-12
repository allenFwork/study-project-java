//package com.study.webflux;
//
//import com.study.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.reactive.function.server.RouterFunction;
//import org.springframework.web.reactive.function.server.RouterFunctions;
//import org.springframework.web.reactive.function.server.ServerResponse;
//
//import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
//
//@Configuration
//public class WebFluxConfiguration {
//
////    @Autowired
////    private UserRepository userRepository;
//
//    @Bean
//    @Autowired
//    public RouterFunction<ServerResponse> saveUser(UserRepository userRepository) {
////        return route(POST("web/flux/user/save"), userRepository::saveUser);
////        return RouterFunctions.route(RequestPredicates.path("web/flux/user/save"),
////                request -> ServerResponse.ok().body(userFlux, User.class));
//        return RouterFunctions.route(POST("web/flux/user/save"),
//                userRepository::saveUser);
//    }
//
//}
