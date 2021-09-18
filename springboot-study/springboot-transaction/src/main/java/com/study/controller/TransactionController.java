package com.study.controller;

import com.study.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @RequestMapping("/test1")
    public void test1() {
        transactionService.test1();
    }

    @RequestMapping("/test2")
    public void test2() {
        transactionService.test2();
    }
    @RequestMapping("/test3")
    public void test3() {
        transactionService.test3();
    }

    @RequestMapping("/test4")
    public void test4() {
        transactionService.test4();
    }
}
