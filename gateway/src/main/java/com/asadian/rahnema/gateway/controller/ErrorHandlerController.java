package com.asadian.rahnema.gateway.controller;

import com.asadian.rahnema.gateway.dto.AccountDto;
import com.asadian.rahnema.gateway.dto.treasury.TreasuryResultContainer;
import com.asadian.rahnema.gateway.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ErrorHandlerController {

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public TreasuryResultContainer<AccountDto> businessException(Exception e, WebRequest req) {
        return null;
    }
}
