package com.asadian.rahnema.gateway.dto.treasury;

import java.io.Serializable;

public class TreasuryResultContainer<T> implements Serializable {
    private String message;
    private T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
