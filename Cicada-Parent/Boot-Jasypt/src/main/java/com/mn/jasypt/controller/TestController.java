package com.mn.jasypt.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController
{
    @Value("${base.password}")
    String password;

    @GetMapping("get/password")
    public String test()
    {
        return password;
    }
}
