package com.asadian.rahnema.treasury.controller;


import com.asadian.rahnema.treasury.dto.AccountDto;
import com.asadian.rahnema.treasury.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

/**
 * Created by rahnema on 9/6/2017.
 */
@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/register", method = RequestMethod.POST )
    @ResponseBody
    public String register(@RequestBody AccountDto accountDto) {
        accountService.register(accountDto);
        return accountDto.getPan() + " was registered successfully!";
    }

    @RequestMapping(value = "/login/{pan}", method = RequestMethod.POST )
    @ResponseBody
    public String login(@PathVariable String pan) {
        return accountService.login(pan);
    }


    @RequestMapping(value = "/balance/{pan}", method = RequestMethod.GET )
    @ResponseBody
    public AccountDto balance(@PathVariable String pan) {
        return accountService.load(pan);
    }

}
