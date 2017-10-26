package com.asadian.rahnema.merchant.controller;

import com.asadian.rahnema.merchant.dto.MerchantFactory;
import com.asadian.rahnema.merchant.dto.MerchantResultContainer;
import com.asadian.rahnema.merchant.dto.PurchaseDto;
import com.asadian.rahnema.merchant.exception.BusinessException;
import com.asadian.rahnema.merchant.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.ws.rs.core.Response;

/**
 * Created by Nemesis on 25/10/2017.
 */
@RestController
public class MerchantController {
    @Autowired
    private MerchantService service;

    @RequestMapping("/shops")
    public MerchantResultContainer shopLists() {
        return MerchantFactory.getContainer(service.shopList(),"show-shops");
    }

    @RequestMapping("/products/{shopPan}")
    public MerchantResultContainer products(@PathVariable String shopPan) {
        return MerchantFactory.getContainer(service.loadShop(shopPan),"show-products");
    }

    @RequestMapping("/products/{shopPan}/product/{productCode}")
    public MerchantResultContainer product(@PathVariable String shopPan, @PathVariable String productCode) {
        return MerchantFactory.getContainer(service.loadProductInfo(shopPan,productCode),"show-product");
    }

    @RequestMapping("/products/{shopPan}/product/{productCode}/choose")
    public MerchantResultContainer choose(@PathVariable String shopPan, @PathVariable String productCode) {
        return MerchantFactory.getContainer(service.loadProductInfo(shopPan,productCode),"show-login");
    }

    @RequestMapping("/products/{shopPan}/product/{productCode}/login")
    public Response login(@PathVariable String shopPan, @PathVariable String productCode, @RequestBody String purchaserPan) {
        try {
            return Response.ok().entity(MerchantFactory.getContainer(service.processLogin(purchaserPan, shopPan, productCode),"show-otp")).build();
        } catch (BusinessException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @RequestMapping("/purchase")
    public Response purchase(PurchaseDto purchaseDto) {
        try {
            return Response.ok().entity(MerchantFactory.getContainer(service.purchase(purchaseDto),"show-result")).build();
        } catch (BusinessException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
