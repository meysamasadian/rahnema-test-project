package com.asadian.rahnema.gateway.message;

import java.text.MessageFormat;

/**
 * Created by Nemesis on 27/10/2017.
 */
public class MessageFactory {
    public static String message(String pattern, String... args) {
        return MessageFormat.format(pattern, args);
    }
}
