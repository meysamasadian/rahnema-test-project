package com.asadian.rahnema.merchant.service.gateway;


import com.asadian.rahnema.merchant.dto.gateway.GatewayTransactionDto;
import com.asadian.rahnema.merchant.exception.BusinessException;

import java.util.List;

/**
 * Created by rahnema on 9/6/2017.
 */
public interface GatewayServiceConnector {
    String login(String phoneNumber) throws BusinessException;
    String transfer(GatewayTransactionDto dto) throws BusinessException;
    List<GatewayTransactionDto> transactions(String refId) throws BusinessException;
}
