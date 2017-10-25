package com.asadian.rahnema.gateway.controller;


import com.asadian.rahnema.gateway.dto.AccountDto;
import com.asadian.rahnema.gateway.dto.TransactionDto;
import com.asadian.rahnema.gateway.exception.BusinessException;
import com.asadian.rahnema.gateway.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        try {
            accountService.register(accountDto);
            return accountDto.getFullName() + " was registered successfully!";
        } catch (BusinessException e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    @RequestMapping(value = "/login/{pan}", method = RequestMethod.POST )
    @ResponseBody
    public String login(@PathVariable String pan) {
        try {
            return accountService.login(pan);
        } catch (BusinessException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.POST )
    @ResponseBody
    public String transfer(@RequestBody TransactionDto dto) {
        try {
            accountService.transfer(dto);
            return "money was transfer successfully!";
        } catch (BusinessException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @RequestMapping(value = "/list/{phone}", method = RequestMethod.GET )
    @ResponseBody
    public List<TransactionDto> transfer(@PathVariable String phone) {
        return accountService.list(phone);
    }
}
