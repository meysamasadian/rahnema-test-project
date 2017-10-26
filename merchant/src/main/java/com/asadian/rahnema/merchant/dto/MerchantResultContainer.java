package com.asadian.rahnema.merchant.dto;

/**
 * Created by Nemesis on 25/10/2017.
 */
public class MerchantResultContainer<T> {
    private String view;
    private T data;

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
