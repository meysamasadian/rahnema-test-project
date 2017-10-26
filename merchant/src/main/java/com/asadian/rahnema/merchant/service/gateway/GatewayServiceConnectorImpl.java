package com.asadian.rahnema.merchant.service.gateway;


import com.asadian.rahnema.merchant.dto.gateway.GatewayTransactionDto;
import com.asadian.rahnema.merchant.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by rahnema on 9/6/2017.
 */
@Service
public class GatewayServiceConnectorImpl implements GatewayServiceConnector {
    @Override
    public String login(String phoneNumber) throws BusinessException {
        return (String) ConnectionUtils.requestPost(Path.LOGIN, String.class, phoneNumber, null);
    }

    @Override
    public String transfer(GatewayTransactionDto dto) throws BusinessException {
        return (String) ConnectionUtils.transact(Path.TRANSFER, String.class, null, dto);
    }

    @Override
    public List<GatewayTransactionDto> transactions(String pan) throws BusinessException {
        return (List<GatewayTransactionDto>) ConnectionUtils.requestGet(Path.LIST, List.class ,pan);
    }
}
