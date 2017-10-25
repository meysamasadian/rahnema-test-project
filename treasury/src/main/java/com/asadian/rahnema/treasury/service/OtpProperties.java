package com.asadian.rahnema.treasury.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("otp")
public class OtpProperties {
    private long expireDuration;
    private int length;

    public long getExpireDuration() {
        return expireDuration;
    }

    public void setExpireDuration(long expireDuration) {
        this.expireDuration = expireDuration;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
