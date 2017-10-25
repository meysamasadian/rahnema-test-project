package com.asadian.rahnema.treasury.exception;

/**
 * Created by rahnema on 9/6/2017.
 */
public class BusinessException extends Exception {
    public static final String NOT_ENOUGH_MONEY = "not enough money";
    public static final String DOCUMENT_NOT_EXISTS = "document not exists";
    public static final String OTP_IS_NOT_VALID = "otp is not valid";

    public BusinessException(String message) {
        super(message);
    }
}
