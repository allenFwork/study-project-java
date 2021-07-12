package com.study.controller;

import com.study.entity.Person;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonRestController {

    /**
     * @RequestParam  直接接受前端传入的文本
     * @RequestBody   前端传入的文本直接进行了反序列化，转化成了对应的POJO
     */

    /**
     * @RequestParam String name 表示请求参数中有字符串name，就将其赋值给方法的参数name，
     * @RequestParam(required = false) 表示请求时，可以有name参数，也可以没有，不强制有
     */
    @GetMapping("/person/{id}")
    public Person person(@PathVariable Long id, @RequestParam(required = false) String name) {
        Person person = new Person();
        person.setId(id);
        person.setName(name);
        return person;
    }

    @PostMapping(value = "/person/json/to/properties",
                 consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, // 请求类型：消费类型
                 produces = "application/properties+person"        // 响应类型
    )
    public Person personJsonToProperties(@RequestBody Person person) {
        // @RequestBody 内容是 json
        // 响应内容是 Properties
        return person;
    }

    @PostMapping(value = "/person/properties/to/json",
                 consumes = "application/properties+person",       // 请求类型：消费类型
                 produces = MediaType.APPLICATION_JSON_UTF8_VALUE  // 响应类型
    )
    public Person personPropertiesToJson(@RequestBody Person person) {
        // @RequestBody 内容是 Properties
        // 响应内容是 Json
        return person;
    }

}
