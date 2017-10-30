package com.asadian.rahnema.gateway.exception;

public class BusinessException extends Exception {
    public static final String TREASURY_PROCESS_NOT_COMPLETE = "treasury process not complete";
    public static final String TIMEOUT_FROM_SOURCE_ACCOUNT = "timeout from source account";
    public static final String TIMEOUT_FROM_TARGET_ACCOUNT = "timeout from target account";
    public static final String INTERNAL_ERROR = "به علت برخی خطاهای داخلی ادامه عملیات امکان پذیر نیست";
    public BusinessException(String message) {
        super(message);
    }
}
