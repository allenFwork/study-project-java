//package com.study.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class Swagger2Config {
//
//    @Bean
//    public Docket createRestApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.tuling.controller")) //你需要生成文档所在的包
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("springboot利用swagger构建api文档")//文档标题
//                .description("简单优雅的restfun风格，http://blog.csdn.net/saytime") //描述
//                .termsOfServiceUrl("http://blog.csdn.net/saytime")
//                .version("1.0")
//                .build();
//    }
//
//}