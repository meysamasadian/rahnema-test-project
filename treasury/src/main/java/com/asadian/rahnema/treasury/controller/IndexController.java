package com.asadian.rahnema.treasury.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @RequestMapping("/")
    public String welcome() {
        return "welcome to treasury app!";
    }

    @RequestMapping("/hi")
    public String welcome1() {
        return "{\"message\":\"welcome to treasury app!\"}";
    }
}
