package com.asadian.rahnema.merchant.dto;

import java.util.List;

/**
 * Created by Nemesis on 25/10/2017.
 */
public class ShopDto {
    private String pan;
    private String name;
    private String logo;
    private List<ProductDto> products;


    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }
}
