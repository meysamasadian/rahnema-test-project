package com.asadian.rahnema.gateway.exception;

import com.asadian.rahnema.gateway.dto.GatewayResultContainer;
import com.asadian.rahnema.gateway.dto.GatewayResultFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GatewayExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected @ResponseBody
    ResponseEntity<GatewayResultContainer> handleBusinessException(Exception ex, WebRequest request) {
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

    private ResponseEntity<GatewayResultContainer> handleExceptionInternal(String bodyOfResponse, HttpStatus notAcceptable) {
        return new ResponseEntity<>(GatewayResultFactory.getResult(null, bodyOfResponse), notAcceptable);
    }
}
