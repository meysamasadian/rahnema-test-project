package com.asadian.rahnema.gateway.service.treasury;

import com.asadian.rahnema.gateway.dto.treasury.TreasuryAccountDto;
import com.asadian.rahnema.gateway.dto.treasury.TreasuryDocumentContainer;
import com.asadian.rahnema.gateway.dto.treasury.TreasuryDocumentDto;
import com.asadian.rahnema.gateway.exception.BusinessException;
import org.springframework.stereotype.Service;

/**
 * Created by rahnema on 9/6/2017.
 */
@Service
public class TreasuryServiceConnectorImpl implements TreasuryServiceConnector {
    @Override
    public String register(TreasuryAccountDto dto) throws BusinessException {
        return (String) ConnectionUtils.request(Path.REGISTER_ACCOUNT, String.class, null, dto);
    }

    @Override
    public String login(String phoneNumber) throws BusinessException {
        return (String) ConnectionUtils.request(Path.LOGIN, String.class, phoneNumber, null);
    }

    @Override
    public TreasuryDocumentContainer issueDocument(TreasuryDocumentDto dto)  {
        return (TreasuryDocumentContainer) ConnectionUtils.transact(Path.ISSUE_DOCUMENT, TreasuryDocumentContainer.class, null, dto);
    }

    @Override
    public TreasuryDocumentContainer reverseDocument(String refId) {
        return (TreasuryDocumentContainer) ConnectionUtils.transact(Path.REVERSE_DOCUMENT, TreasuryDocumentContainer.class, refId, null);
    }

}
