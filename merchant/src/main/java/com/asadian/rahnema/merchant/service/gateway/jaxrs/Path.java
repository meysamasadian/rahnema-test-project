package com.asadian.rahnema.merchant.service.gateway.jaxrs;

/**
 * Created by rahnema on 9/6/2017.
 */
@Deprecated
public enum Path {
    REGISTER("/account/register/"),
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
