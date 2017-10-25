package com.asadian.rahnema.treasury.dto;

public class DocumentContainerFactory {
    public static DocumentContainer newOkInstance(String refId, String message) {
        DocumentContainer container = new DocumentContainer();
        container.setRefId(refId);
        container.setMessage(message);
        container.setOk(true);
        return container;
    }

    public static DocumentContainer newBadInstance(String message) {
        DocumentContainer container = new DocumentContainer();
        container.setRefId(null);
        container.setMessage(message);
        container.setOk(false);
        return container;
    }
}
