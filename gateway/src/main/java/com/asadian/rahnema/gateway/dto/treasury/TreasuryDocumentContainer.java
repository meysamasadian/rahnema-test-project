package com.asadian.rahnema.gateway.dto.treasury;

import java.io.Serializable;

public class TreasuryDocumentContainer implements Serializable {
    private String message;
    private boolean ok;
    private String refId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }
}
