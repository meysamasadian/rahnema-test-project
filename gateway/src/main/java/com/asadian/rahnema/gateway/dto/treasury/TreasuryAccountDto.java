package com.asadian.rahnema.gateway.dto.treasury;

import java.math.BigDecimal;

/**
 * Created by rahnema on 9/6/2017.
 */
public class TreasuryAccountDto {
    private long id;
    private BigDecimal balance;
    private String pan;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
