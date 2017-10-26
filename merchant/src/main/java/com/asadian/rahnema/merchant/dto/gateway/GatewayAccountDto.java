package com.asadian.rahnema.merchant.dto.gateway;

import java.math.BigDecimal;

/**
 * Created by Nemesis on 26/10/2017.
 */
public class GatewayAccountDto {
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
