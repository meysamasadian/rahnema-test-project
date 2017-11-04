package com.asadian.rahnema.gateway.controller;


import com.asadian.rahnema.gateway.dto.AccountDto;
import com.asadian.rahnema.gateway.dto.GatewayResultContainer;
import com.asadian.rahnema.gateway.dto.GatewayResultFactory;
import com.asadian.rahnema.gateway.dto.TransactionDto;
import com.asadian.rahnema.gateway.dto.treasury.TreasuryResultContainer;
import com.asadian.rahnema.gateway.exception.BusinessException;
import com.asadian.rahnema.gateway.message.Message;
import com.asadian.rahnema.gateway.message.MessageFactory;
import com.asadian.rahnema.gateway.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by rahnema on 9/6/2017.
 */
@RestController
@RequestMapping("/account")
@Api(value = "Gateway API" , description = "A Gateway layer to treasury connection")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/register", method = RequestMethod.POST )
    @ResponseBody
    @ApiOperation(value = "Register new account", response = GatewayResultContainer.class, produces = "application/json")
    public Object register(@RequestBody AccountDto accountDto) throws BusinessException {
        String message = accountService.register(accountDto);
        return GatewayResultFactory.getResult(accountDto, message);
    }

    @RequestMapping(value = "/login/{pan}", method = RequestMethod.POST )
    @ResponseBody
    @ApiOperation(value = "Account login", response = GatewayResultContainer.class, produces = "application/json")
    public Object login(@PathVariable String pan) throws BusinessException {
        final TreasuryResultContainer login = accountService.login(pan);
        return GatewayResultFactory.getResult(login.getData(), login.getMessage());
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.POST )
    @ResponseBody
    @ApiOperation(value = "transfer credit", response = GatewayResultContainer.class, produces = "application/json")
    public Object transfer(@RequestBody TransactionDto dto) throws BusinessException {
        TransactionDto result = accountService.transfer(dto);
        return GatewayResultFactory.getResult(result, MessageFactory.message(Message.TRANSACTION_GENERATED,
                String.valueOf(dto.getAmount()), dto.getSource(), dto.getDest()));
    }

    @RequestMapping(value = "/list/{phone}", method = RequestMethod.GET )
    @ResponseBody
    @ApiOperation(value = "List of transactions", response = GatewayResultContainer.class, produces = "application/json")
    public GatewayResultContainer transactions(@PathVariable String phone) {
        return GatewayResultFactory.getResult(accountService.list(phone), Message.SUCCESSFULLY_OPERATION);
    }
}
