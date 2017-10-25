package com.asadian.rahnema.gateway.model;

import org.springframework.data.annotation.Id;

/**
 * Created by rahnema on 9/6/2017.
 */

public class Account {
    @Id
    private String id;
    private String fullName;
    private String phone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
