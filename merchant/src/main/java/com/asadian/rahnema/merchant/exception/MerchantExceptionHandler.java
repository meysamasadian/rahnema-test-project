package com.asadian.rahnema.merchant.exception;

import com.asadian.rahnema.merchant.dto.MerchantFactory;
import com.asadian.rahnema.merchant.dto.MerchantResultContainer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@EnableWebMvc
@ControllerAdvice
public class MerchantExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected @ResponseBody
    ResponseEntity<MerchantResultContainer> handleBusinessException(Exception ex, WebRequest request) {
        String bodyOfResponse;
        HttpStatus httpStatus;
        if (ex instanceof BusinessException ) {
            bodyOfResponse = ex.getMessage();
            httpStatus = HttpStatus.NOT_ACCEPTABLE;
        } else {
            bodyOfResponse = BusinessException.GATEWAY_INTERNAL_ERROR;
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return handleExceptionInternal(bodyOfResponse, httpStatus);
    }

    private ResponseEntity<MerchantResultContainer> handleExceptionInternal(String bodyOfResponse, HttpStatus notAcceptable) {
        return new ResponseEntity<>(MerchantFactory.getContainer(null, bodyOfResponse), notAcceptable);
    }
}
