package com.asadian.rahnema.treasury.exception;

/**
 * Created by rahnema on 9/6/2017.
 */
public class BusinessException extends Exception {
    public static final String NOT_ENOUGH_MONEY = "حساب {0} موجودی کافی ندارد";
    public static final String DOCUMENT_NOT_EXISTS = "سندی با این مشخصات وجود ندارد";
    public static final String OTP_IS_NOT_VALID = "یکبار رمز اشتباه است";
    public static final String ACCOUNT_NOT_FOUND = "حسابی با شماره {0} یافت نشد";
    public static final String INTERNAL_ERROR = "این حساب قبلا در سیستم ایجاد شده است";
    public static final String ACCOUNT_IS_DUPLICATE = "به علت برخی خطاهای داخلی ادامه عملیات امکان پذیر نیست";

    public BusinessException(String message) {
        super(message);
    }
}
