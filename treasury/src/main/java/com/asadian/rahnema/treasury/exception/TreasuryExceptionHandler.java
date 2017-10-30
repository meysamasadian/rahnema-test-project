package com.asadian.rahnema.treasury.exception;

import com.asadian.rahnema.treasury.dto.TreasuryResultContainer;
import com.asadian.rahnema.treasury.dto.TreasuryResultFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@ControllerAdvice
public class TreasuryExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected @ResponseBody ResponseEntity<TreasuryResultContainer> handleBusinessException(Exception ex, WebRequest request) {
        String bodyOfResponse;
        HttpStatus httpStatus;
        if (ex instanceof BusinessException ) {
            bodyOfResponse = ex.getMessage();
            httpStatus = HttpStatus.NOT_ACCEPTABLE;
        } else {
            bodyOfResponse = BusinessException.INTERNAL_ERROR;
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return handleExceptionInternal(bodyOfResponse, httpStatus);
    }

    private ResponseEntity<TreasuryResultContainer> handleExceptionInternal(String bodyOfResponse, HttpStatus notAcceptable) {
        return new ResponseEntity<>(TreasuryResultFactory.getResult(null, bodyOfResponse), notAcceptable);
    }
}
