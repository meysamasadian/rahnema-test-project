package com.asadian.rahnema.treasury.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Nemesis on 27/10/2017.
 */

public class TreasuryResultContainer<T> {
    @ApiModelProperty(notes = "result message")
    private String message;

    @ApiModelProperty(notes = "result data")
    private T data;

    public TreasuryResultContainer() {
    }

    public TreasuryResultContainer(String message, T data) {
        this.message = message;
        this.data = data;
    }

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
