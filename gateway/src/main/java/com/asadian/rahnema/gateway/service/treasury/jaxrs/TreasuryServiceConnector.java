package com.asadian.rahnema.gateway.service.treasury.jaxrs;


import com.asadian.rahnema.gateway.dto.treasury.TreasuryAccountDto;
import com.asadian.rahnema.gateway.dto.treasury.TreasuryDocumentDto;
import com.asadian.rahnema.gateway.dto.treasury.TreasuryResultContainer;
import com.asadian.rahnema.gateway.exception.BusinessException;

/**
 * Created by rahnema on 9/6/2017.
 */
@Deprecated
public interface TreasuryServiceConnector {
    TreasuryResultContainer register(TreasuryAccountDto dto) throws BusinessException;
    TreasuryResultContainer login(String phoneNumber) throws BusinessException;
    TreasuryResultContainer issueDocument(TreasuryDocumentDto dto) throws BusinessException;
    TreasuryResultContainer reverseDocument(String refId) throws BusinessException;
}
