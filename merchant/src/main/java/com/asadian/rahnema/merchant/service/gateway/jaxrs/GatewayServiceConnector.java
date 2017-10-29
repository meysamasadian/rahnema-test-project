package com.asadian.rahnema.merchant.service.gateway.jaxrs;


import com.asadian.rahnema.merchant.dto.gateway.GatewayAccountDto;
import com.asadian.rahnema.merchant.dto.gateway.GatewayResultContainer;
import com.asadian.rahnema.merchant.dto.gateway.GatewayTransactionDto;
import com.asadian.rahnema.merchant.exception.BusinessException;


/**
 * Created by rahnema on 9/6/2017.
 */
@Deprecated
public interface GatewayServiceConnector {
    GatewayResultContainer register(GatewayAccountDto accountDto) throws BusinessException;
    GatewayResultContainer login(String phoneNumber) throws BusinessException;
    GatewayResultContainer transfer(GatewayTransactionDto dto) throws BusinessException;
    GatewayResultContainer transactions(String refId) throws BusinessException;
}
