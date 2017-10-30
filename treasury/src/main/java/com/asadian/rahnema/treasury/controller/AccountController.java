package com.asadian.rahnema.treasury.controller;


import com.asadian.rahnema.treasury.dto.AccountDto;
import com.asadian.rahnema.treasury.dto.TreasuryResultFactory;
import com.asadian.rahnema.treasury.exception.BusinessException;
import com.asadian.rahnema.treasury.message.AccountMessage;
import com.asadian.rahnema.treasury.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public Object register(@RequestBody AccountDto accountDto) throws BusinessException {
            AccountDto dto = accountService.register(accountDto);
            return TreasuryResultFactory.getResult(dto, AccountMessage.ACCOUNT_WAS_CREATED);
    }

    @RequestMapping(value = "/login/{pan}", method = RequestMethod.POST )
    @ResponseBody
    public Object login(@PathVariable String pan) throws BusinessException {
        return TreasuryResultFactory.getResult(accountService.login(pan), AccountMessage.OTP_WAS_GENERATED);
    }


    @RequestMapping(value = "/balance/{pan}", method = RequestMethod.GET )
    @ResponseBody
    public Object balance(@PathVariable String pan) throws BusinessException {
        return TreasuryResultFactory.getResult(accountService.load(pan), AccountMessage.SUCCESSFULLY_OPERATION);
    }

}
