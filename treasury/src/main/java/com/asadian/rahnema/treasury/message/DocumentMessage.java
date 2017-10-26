package com.asadian.rahnema.treasury.message;

/**
 * Created by Nemesis on 27/10/2017.
 */
public interface DocumentMessage {
    String DOCUMENT_GENERATED = "مبلغ {0} از حساب {1} به حساب {2} با موفقیت انتقال یافت";
    String DOCUMENT_REVERSED = "سند با شماره پیگیری {0} با موفقیت برگشت داده شد";
}
