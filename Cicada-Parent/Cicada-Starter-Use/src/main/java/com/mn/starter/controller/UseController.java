package com.mn.starter.controller;

import com.mn.starter.StarterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UseController
{
    @Autowired
    private StarterService starterService;

    @GetMapping("hello/starter")
    public String test()
    {
        String config = starterService.getConfig();
        starterService.print();
        return config;
    }
}
