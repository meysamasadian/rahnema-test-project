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
public class AccountController {
    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/register", method = RequestMethod.POST )
    @ResponseBody
    public Object register(@RequestBody AccountDto accountDto) throws BusinessException {
        String message = accountService.register(accountDto);
        return GatewayResultFactory.getResult(accountDto, message);
    }

    @RequestMapping(value = "/login/{pan}", method = RequestMethod.POST )
    @ResponseBody
    public Object login(@PathVariable String pan) throws BusinessException {
        final TreasuryResultContainer login = accountService.login(pan);
        return GatewayResultFactory.getResult(login.getData(), login.getMessage());
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.POST )
    @ResponseBody
    public Object transfer(@RequestBody TransactionDto dto) throws BusinessException {
        TransactionDto result = accountService.transfer(dto);
        return GatewayResultFactory.getResult(result, MessageFactory.message(Message.TRANSACTION_GENERATED,
                String.valueOf(dto.getAmount()), dto.getSource(), dto.getDest()));
    }

    @RequestMapping(value = "/list/{phone}", method = RequestMethod.GET )
    @ResponseBody
    public GatewayResultContainer transactions(@PathVariable String phone) {
        return GatewayResultFactory.getResult(accountService.list(phone), Message.SUCCESSFULLY_OPERATION);
    }
}
