package com.asadian.rahnema.merchant.service;

import com.asadian.rahnema.merchant.dto.LoginDto;
import com.asadian.rahnema.merchant.dto.ProductDto;
import com.asadian.rahnema.merchant.dto.PurchaseDto;
import com.asadian.rahnema.merchant.dto.ShopDto;
import com.asadian.rahnema.merchant.dto.gateway.GatewayAccountDto;
import com.asadian.rahnema.merchant.dto.gateway.GatewayTransactionDto;
import com.asadian.rahnema.merchant.exception.BusinessException;
import com.asadian.rahnema.merchant.message.Message;
import com.asadian.rahnema.merchant.model.Purchase;
import com.asadian.rahnema.merchant.model.Shop;
import com.asadian.rahnema.merchant.repository.PurchaseRepository;
import com.asadian.rahnema.merchant.repository.ShopRepository;
import com.asadian.rahnema.merchant.service.gateway.retrofit.callback.GatewayServiceCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Nemesis on 25/10/2017.
 */
@Service
public class MerchantService {
    public static final String SHOP_KEY = "shop";
    public static final String PRODUCT_KEY = "product";
    public static final String PURCHASER_KEY = "purchaser";
    public static final String OTP_KEY = "otp";

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private GatewayServiceCaller serviceCaller;

    public List<ShopDto> shopList() {
        return shopRepository.findAll().stream().map(this::present)
                .collect(Collectors.toList());
    }

    public ShopDto loadShop(String pan) {
        return present(shopRepository.findByPan(pan));
    }

    public String register(ShopDto dto) throws BusinessException {
        Shop shop = new Shop();
        shop.setLogo(dto.getLogo());
        shop.setName(dto.getName());
        shop.setPan(dto.getPan());
        shop.setProducts(dto.getProducts().stream().map(productDto -> {
            Shop.Product product = new Shop.Product();
            product.setName(productDto.getName());
            product.setCode(productDto.getCode());
            product.setPrice(productDto.getPrice());
            return product;
        }).collect(Collectors.toList()));
        GatewayAccountDto accountDto = new GatewayAccountDto();
        accountDto.setFullName(dto.getName());
        accountDto.setPhone(dto.getPan());
        accountDto.setInitAmount(BigDecimal.ZERO);
        serviceCaller.register(accountDto);
        shopRepository.save(shop);
        return Message.SHOP_ACCOUNT_WAS_CREATED_SUCCESSFULLY;
    }

    public Map<String,Object> loadProductInfo(String pan, String code) {
        Map<String,Object> result = new HashMap<>();
        Shop shop = shopRepository.findByPan(pan);
        result.put(SHOP_KEY, present(shop));
        Shop.Product product = shop.getProducts().stream().filter(pro -> pro.getCode().equals(code)).findFirst().get();
        result.put(PRODUCT_KEY, present(product));
        return result;
    }

    private Shop.Product loadProduct(String pan, String code) {
        Shop shop = shopRepository.findByPan(pan);
        Shop.Product product = shop.getProducts().stream().filter(pro -> pro.getCode().equals(code)).findFirst().get();
        return product;
    }


    public Map<String,Object> processLogin(LoginDto dto) throws BusinessException {
        Map<String,Object> result = loadProductInfo(dto.getShop(), dto.getProductCode());
        result.put(PURCHASER_KEY, dto.getPurchaser());
        result.put(OTP_KEY, serviceCaller.login(dto.getPurchaser()));
        return result;
    }

    public String purchase(PurchaseDto purchaseDto) throws BusinessException {
        GatewayTransactionDto transactionDto = new GatewayTransactionDto();
        transactionDto.setSource(purchaseDto.getPurchaserPan());
        transactionDto.setDest(purchaseDto.getShopPan());
        transactionDto.setOtp(purchaseDto.getOtp());
        Shop.Product product = loadProduct(purchaseDto.getShopPan(), purchaseDto.getProductCode());
        transactionDto.setAmount(product.getPrice());
        serviceCaller.transfer(transactionDto);
        Purchase purchase = new Purchase();
        purchase.setPrice(product.getPrice());
        purchase.setProduct(product.getCode());
        purchase.setShop(purchaseDto.getShopPan());
        purchase.setPurchaser(purchaseDto.getPurchaserPan());
        purchaseRepository.save(purchase);
        return Message.PURCHASE_SUCCESSFULLY;
    }

    private ShopDto present(Shop shop) {
        ShopDto dto = new ShopDto();
        dto.setName(shop.getName());
        dto.setPan(shop.getPan());
        dto.setLogo(shop.getLogo());
        dto.setProducts(shop.getProducts().stream().map(this::present).collect(Collectors.toList()));
        return dto;
    }


    private ProductDto present(Shop.Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setName(product.getName());
        productDto.setCode(product.getCode());
        productDto.setPrice(product.getPrice());
        return productDto;
    }
}
