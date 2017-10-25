package com.asadian.rahnema.gateway.model;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

/**
 * Created by rahnema on 9/6/2017.
 */

public class Transaction {
    @Id
    private long id;
    private BigDecimal amount;
    private String date;
    private String partOneRefId;
    private String partTwoRefId;
    private String refId;
    private String source;
    private String dest;

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getPartOneRefId() {
        return partOneRefId;
    }

    public void setPartOneRefId(String partOneRefId) {
        this.partOneRefId = partOneRefId;
    }

    public String getPartTwoRefId() {
        return partTwoRefId;
    }

    public void setPartTwoRefId(String partTwoRefId) {
        this.partTwoRefId = partTwoRefId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }
}
