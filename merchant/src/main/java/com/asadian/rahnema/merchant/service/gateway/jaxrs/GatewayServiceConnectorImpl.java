package com.asadian.rahnema.merchant.service.gateway.jaxrs;


import com.asadian.rahnema.merchant.dto.gateway.GatewayAccountDto;
import com.asadian.rahnema.merchant.dto.gateway.GatewayResultContainer;
import com.asadian.rahnema.merchant.dto.gateway.GatewayTransactionDto;
import com.asadian.rahnema.merchant.exception.BusinessException;
import org.springframework.stereotype.Service;

/**
 * Created by rahnema on 9/6/2017.
 */
@Deprecated
@Service
public class GatewayServiceConnectorImpl implements GatewayServiceConnector {

    @Override
    public GatewayResultContainer register(GatewayAccountDto accountDto) throws BusinessException {
        return (GatewayResultContainer) ConnectionUtils.requestPost(Path.REGISTER, GatewayResultContainer.class, null, accountDto);
    }

    @Override
    public GatewayResultContainer login(String phoneNumber) throws BusinessException {
        return (GatewayResultContainer) ConnectionUtils.requestPost(Path.LOGIN, GatewayResultContainer.class, phoneNumber, null);
    }

    @Override
    public GatewayResultContainer transfer(GatewayTransactionDto dto) throws BusinessException {
        return (GatewayResultContainer) ConnectionUtils.transact(Path.TRANSFER, GatewayResultContainer.class, null, dto);
    }

    @Override
    public GatewayResultContainer transactions(String pan) throws BusinessException {
        return (GatewayResultContainer) ConnectionUtils.requestGet(Path.LIST, GatewayResultContainer.class ,pan);
    }
}
