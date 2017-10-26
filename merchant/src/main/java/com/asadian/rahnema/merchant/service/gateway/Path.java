package com.asadian.rahnema.merchant.service.gateway;

/**
 * Created by rahnema on 9/6/2017.
 */
public enum Path {
    LOGIN("/account/login/"),
    TRANSFER("/account/transfer/"),
    LIST("/account/list/"),;

    private String path;

    Path(String path) {
        this.path = path;
    }


    @Override
    public String toString() {
        return path;
    }
}
