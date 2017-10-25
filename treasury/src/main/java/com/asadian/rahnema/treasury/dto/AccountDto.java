package com.asadian.rahnema.treasury.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by rahnema on 9/6/2017.
 */
public class AccountDto implements Serializable {
    private BigDecimal balance;
    private String pan;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }
}
