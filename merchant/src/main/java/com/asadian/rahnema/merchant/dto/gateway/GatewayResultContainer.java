package com.asadian.rahnema.merchant.dto.gateway;

/**
 * Created by Nemesis on 27/10/2017.
 */
public class GatewayResultContainer<T> {
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
