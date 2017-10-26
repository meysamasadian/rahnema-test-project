package com.asadian.rahnema.merchant.dto;

/**
 * Created by Nemesis on 27/10/2017.
 */
public class LoginDto {
    private String purchaser;
    private String shop;
    private String productCode;

    public String getPurchaser() {
        return purchaser;
    }

    public void setPurchaser(String purchaser) {
        this.purchaser = purchaser;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}
