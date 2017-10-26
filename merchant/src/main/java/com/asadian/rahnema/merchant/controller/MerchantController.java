package com.asadian.rahnema.merchant.controller;

import com.asadian.rahnema.merchant.dto.*;
import com.asadian.rahnema.merchant.exception.BusinessException;
import com.asadian.rahnema.merchant.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nemesis on 25/10/2017.
 */
@RestController
@RequestMapping("/shops")
public class MerchantController {
    @Autowired
    private MerchantService service;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public MerchantResultContainer shopLists() {
        return MerchantFactory.getContainer(service.shopList(),"show-shops");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Object registerShop(@RequestBody ShopDto dto) {
        try {
            return MerchantFactory.getContainer(service.register(dto),"show-shops");
        } catch (BusinessException e) {
            e.printStackTrace();
            return new ResponseEntity<>(MerchantFactory.getContainer("",e.getMessage()), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @RequestMapping(value = "/products/{shopPan}", method = RequestMethod.GET)
    public MerchantResultContainer products(@PathVariable String shopPan) {
        return MerchantFactory.getContainer(service.loadShop(shopPan),"show-products");
    }

    @RequestMapping(value = "/products/{shopPan}/product/{productCode}", method = RequestMethod.GET)
    public MerchantResultContainer product(@PathVariable String shopPan, @PathVariable String productCode) {
        return MerchantFactory.getContainer(service.loadProductInfo(shopPan,productCode),"show-product");
    }

    @RequestMapping(value = "/products/{shopPan}/product/{productCode}/choose", method = RequestMethod.GET)
    public MerchantResultContainer choose(@PathVariable String shopPan, @PathVariable String productCode) {
        return MerchantFactory.getContainer(service.loadProductInfo(shopPan,productCode),"show-login");
    }

    @RequestMapping(value = "/login" , method = RequestMethod.POST)
    public Object login(@RequestBody LoginDto dto) {
        try {
            return MerchantFactory.getContainer(service.processLogin(dto),"show-otp");
        } catch (BusinessException e) {
            e.printStackTrace();
            return new ResponseEntity<>(MerchantFactory.getContainer("",e.getMessage()), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @RequestMapping(value = "/purchase", method = RequestMethod.POST)
    public Object purchase(@RequestBody PurchaseDto purchaseDto) {
        try {
            return MerchantFactory.getContainer(service.purchase(purchaseDto),"show-result");
        } catch (BusinessException e) {
            e.printStackTrace();
            return new ResponseEntity<>(MerchantFactory.getContainer("",e.getMessage()), HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
