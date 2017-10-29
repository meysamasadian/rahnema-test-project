package com.asadian.rahnema.gateway.service.treasury.jaxrs;

import com.asadian.rahnema.gateway.dto.treasury.TreasuryAccountDto;
import com.asadian.rahnema.gateway.dto.treasury.TreasuryDocumentDto;
import com.asadian.rahnema.gateway.dto.treasury.TreasuryResultContainer;
import com.asadian.rahnema.gateway.exception.BusinessException;
import org.springframework.stereotype.Service;

/**
 * Created by rahnema on 9/6/2017.
 */
@Deprecated
@Service
public class TreasuryServiceConnectorImpl implements TreasuryServiceConnector {
    @Override
    public TreasuryResultContainer register(TreasuryAccountDto dto) throws BusinessException {
        return (TreasuryResultContainer) ConnectionUtils.request(Path.REGISTER_ACCOUNT, TreasuryResultContainer.class, null, dto);
    }

    @Override
    public TreasuryResultContainer login(String phoneNumber) throws BusinessException {
        return (TreasuryResultContainer) ConnectionUtils.request(Path.LOGIN, TreasuryResultContainer.class, phoneNumber, null);
    }

    @Override
    public TreasuryResultContainer issueDocument(TreasuryDocumentDto dto) throws BusinessException {
        return (TreasuryResultContainer) ConnectionUtils.transact(Path.ISSUE_DOCUMENT, TreasuryResultContainer.class, null, dto);
    }

    @Override
    public TreasuryResultContainer reverseDocument(String refId) throws BusinessException {
        return (TreasuryResultContainer) ConnectionUtils.transact(Path.REVERSE_DOCUMENT, TreasuryResultContainer.class, refId, null);
    }

}
