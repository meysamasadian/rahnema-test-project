package com.asadian.rahnema.gateway.controller;


import com.asadian.rahnema.gateway.dto.AccountDto;
import com.asadian.rahnema.gateway.dto.TransactionDto;
import com.asadian.rahnema.gateway.exception.BusinessException;
import com.asadian.rahnema.gateway.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Response register(@RequestBody AccountDto accountDto) {
        try {
            accountService.register(accountDto);
            return Response.ok().entity(accountDto.getFullName() + " was registered successfully!").build();
        } catch (BusinessException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }

    }

    @RequestMapping(value = "/login/{pan}", method = RequestMethod.POST )
    @ResponseBody
    public Response login(@PathVariable String pan) {
        try {
            return Response.ok().entity(accountService.login(pan)).build();
        } catch (BusinessException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.POST )
    @ResponseBody
    public Response transfer(@RequestBody TransactionDto dto) {
        try {
            accountService.transfer(dto);
            return Response.ok().entity("money was transfer successfully!").build();
        } catch (BusinessException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @RequestMapping(value = "/list/{phone}", method = RequestMethod.GET )
    @ResponseBody
    public List<TransactionDto> transactions(@PathVariable String phone) {
        return accountService.list(phone);
    }
}
