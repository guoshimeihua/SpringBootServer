package com.dodonew.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Bruce on 2017/10/26.
 */
@RestController
public class HelloController {
    @RequestMapping(value = "/hello")
    public String hello() {
        return "hello world";
    }
}
