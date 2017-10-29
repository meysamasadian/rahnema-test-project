package com.asadian.rahnema.gateway.service.treasury.jaxrs;

/**
 * Created by rahnema on 9/6/2017.
 */
@Deprecated
public enum Path {
    REGISTER_ACCOUNT("/account/register"),
    LOGIN("/account/login/"),
    ISSUE_DOCUMENT("/document/issue/"),
    REVERSE_DOCUMENT("/document/reverse/"),;

    private String path;

    Path(String path) {
        this.path = path;
    }


    @Override
    public String toString() {
        return path;
    }
}
