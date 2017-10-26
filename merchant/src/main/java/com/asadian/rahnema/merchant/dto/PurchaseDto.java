package com.asadian.rahnema.merchant.dto;

/**
 * Created by Nemesis on 26/10/2017.
 */
public class PurchaseDto {
    private String shopPan;
    private String productCode;
    private String purchaserPan;
    private String otp;

    public String getShopPan() {
        return shopPan;
    }

    public void setShopPan(String shopPan) {
        this.shopPan = shopPan;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getPurchaserPan() {
        return purchaserPan;
    }

    public void setPurchaserPan(String purchaserPan) {
        this.purchaserPan = purchaserPan;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
