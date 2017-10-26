package com.asadian.rahnema.gateway.dto;

/**
 * Created by Nemesis on 27/10/2017.
 */
public class GatewayResultFactory {
    public static <T> GatewayResultContainer getResult(T data, String message) {
        GatewayResultContainer<T> container = new GatewayResultContainer<>();
        container.setData(data);
        container.setMessage(message);
        return container;
    }
}
