package com.asadian.rahnema.treasury.dto;

/**
 * Created by Nemesis on 27/10/2017.
 */
public class TreasuryResultFactory {
    public static <T> TreasuryResultContainer getResult(T data, String message) {
        return new TreasuryResultContainer<>(message, data);
    }
}
