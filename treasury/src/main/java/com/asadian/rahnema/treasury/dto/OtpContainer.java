package com.asadian.rahnema.treasury.dto;

import java.io.Serializable;

public class OtpContainer implements Serializable {
    private String code;
    private long expire;

    public OtpContainer(String code, long expire) {
        this.code = code;
        this.expire = expire;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }
}
