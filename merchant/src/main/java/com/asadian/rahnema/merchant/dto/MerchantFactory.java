package com.asadian.rahnema.merchant.dto;

/**
 * Created by Nemesis on 25/10/2017.
 */
public class MerchantFactory {
    public static <T> MerchantResultContainer<T> getContainer(T data, String view) {
        MerchantResultContainer container = new MerchantResultContainer();
        container.setData(data);
        container.setView(view);
        return container;
    }
}
