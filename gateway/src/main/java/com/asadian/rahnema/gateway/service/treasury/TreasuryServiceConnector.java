package com.asadian.rahnema.gateway.service.treasury;


import com.asadian.rahnema.gateway.dto.treasury.TreasuryAccountDto;
import com.asadian.rahnema.gateway.dto.treasury.TreasuryDocumentContainer;
import com.asadian.rahnema.gateway.dto.treasury.TreasuryDocumentDto;
import com.asadian.rahnema.gateway.exception.BusinessException;

/**
 * Created by rahnema on 9/6/2017.
 */
public interface TreasuryServiceConnector {
    String register(TreasuryAccountDto dto) throws BusinessException;
    String login(String phoneNumber) throws BusinessException;
    TreasuryDocumentContainer issueDocument(TreasuryDocumentDto dto);
    TreasuryDocumentContainer reverseDocument(String refId);
}
