package com.asadian.rahnema.merchant.exception;

public class BusinessException extends Exception {
    public static final String GATEWAY_PROCESS_NOT_COMPLETE = "gateway process not complete!";
    public static final String GATEWAY_INTERNAL_ERROR = "به علت برخی خطاهای داخلی ادامه عملیات امکان پذیر نیست";
    public BusinessException(String message) {
        super(message);
    }
}
