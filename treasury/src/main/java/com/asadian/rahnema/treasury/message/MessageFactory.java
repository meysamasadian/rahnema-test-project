package com.asadian.rahnema.treasury.message;

import java.text.MessageFormat;

/**
 * Created by Nemesis on 27/10/2017.
 */
public class MessageFactory {
    public static String message(String message, String... args) {
        return MessageFormat.format(message, args);
    }
}
