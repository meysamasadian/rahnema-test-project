package com.asadian.rahnema.gateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @RequestMapping("/")
    public String welcome() {
        return "Welcome to Gateway!";
    }
}
