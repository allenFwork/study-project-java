//package com.study.config;
//
//import com.study.http.message.PropertiesPersonHttpMessageConverter;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import java.util.List;
//
//@Configuration
//public class MyMvcConfig implements WebMvcConfigurer {
//
////    // 通过这个方法添加转换器不生效
////    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//////        converters.add(new MappingJackson2HttpMessageConverter());
////        // 打印红色的字体
////        System.err.println("converters :" + converters);
////    }
//
//    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//
//        /*
//         * HttpMessageConverter的默认顺序：
//         * org.springframework.http.converter.ByteArrayHttpMessageConverter,
//         * org.springframework.http.converter.StringHttpMessageConverter,
//         * org.springframework.http.converter.StringHttpMessageConverter,
//         * org.springframework.http.converter.ResourceHttpMessageConverter,
//         * org.springframework.http.converter.ResourceRegionHttpMessageConverter,
//         * org.springframework.http.converter.xml.SourceHttpMessageConverter,
//         * org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter,
//         * org.springframework.http.converter.json.MappingJackson2HttpMessageConverter,
//         * org.springframework.http.converter.json.MappingJackson2HttpMessageConverter,
//         * org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter,
//         * org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter
//         */
//
//        // 将转换顺序的集合中的第一个设置为xml格式，那么默认会返回xml的格式
//        converters.set(0, new MappingJackson2XmlHttpMessageConverter());
////        converters.add(new MappingJackson2HttpMessageConverter());
//        // 打印红色的字体
//        System.err.println("converters :" + converters);
//        // 添加自定义的信息转化规则类型
//        converters.add(new PropertiesPersonHttpMessageConverter());
//    }
//
//}
