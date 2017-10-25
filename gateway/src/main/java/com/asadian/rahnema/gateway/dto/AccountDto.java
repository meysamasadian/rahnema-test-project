package com.asadian.rahnema.gateway.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by rahnema on 9/6/2017.
 */
public class AccountDto implements Serializable {
    private String fullName;
    private String phone;
    private BigDecimal initAmount;

    public BigDecimal getInitAmount() {
        return initAmount;
    }

    public void setInitAmount(BigDecimal initAmount) {
        this.initAmount = initAmount;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
