package com.asadian.rahnema.treasury.controller;


import com.asadian.rahnema.treasury.dto.AccountDto;
import com.asadian.rahnema.treasury.dto.TreasuryResultContainer;
import com.asadian.rahnema.treasury.dto.TreasuryResultFactory;
import com.asadian.rahnema.treasury.exception.BusinessException;
import com.asadian.rahnema.treasury.message.AccountMessage;
import com.asadian.rahnema.treasury.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

/**
 * Created by rahnema on 9/6/2017.
 */
@RestController
@RequestMapping("/api/account")
@Api(value="account api", description="All operations of account that provided in this api")
public class AccountController {
    @Autowired
    private AccountService accountService;


    @ApiOperation(value = "register new account", response = TreasuryResultContainer.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @RequestMapping(value = "/register", method = RequestMethod.POST )
    @ResponseBody
    public Object register(@RequestBody AccountDto accountDto) throws BusinessException {
            AccountDto dto = accountService.register(accountDto);
            return TreasuryResultFactory.getResult(dto, AccountMessage.ACCOUNT_WAS_CREATED);
    }

    @RequestMapping(value = "/login/{pan}", method = RequestMethod.POST )
    @ResponseBody
    @ApiOperation(value = "login point", response = TreasuryResultContainer.class, produces = "application/json")
    public Object login(@PathVariable String pan) throws BusinessException {
        return TreasuryResultFactory.getResult(accountService.login(pan), AccountMessage.OTP_WAS_GENERATED);
    }


    @RequestMapping(value = "/balance/{pan}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "get last balance", response = TreasuryResultContainer.class, produces = "application/json")
    public Object balance(@PathVariable String pan) throws BusinessException {
        return TreasuryResultFactory.getResult(accountService.load(pan), AccountMessage.SUCCESSFULLY_OPERATION);
    }

}
