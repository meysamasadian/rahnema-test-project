package com.asadian.rahnema.treasury.service;

import com.asadian.rahnema.treasury.dto.DocumentDto;
import com.asadian.rahnema.treasury.exception.BusinessException;
import com.asadian.rahnema.treasury.message.MessageFactory;
import com.asadian.rahnema.treasury.model.Account;
import com.asadian.rahnema.treasury.model.Document;
import com.asadian.rahnema.treasury.repositories.AccountRepo;
import com.asadian.rahnema.treasury.repositories.DocumentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by rahnema on 9/6/2017.
 */
@Service
public class DocumentService {

    private AccountService accountService;

    private DocumentRepo documentRepo;

    private AccountRepo accountRepo;

    @Autowired
    public DocumentService(AccountService accountService, DocumentRepo documentRepo, AccountRepo accountRepo) {
        this.accountService = accountService;
        this.documentRepo = documentRepo;
        this.accountRepo = accountRepo;
    }

    public String issueDocument(DocumentDto dto) throws BusinessException {
        Account source = accountRepo.findByPan(dto.getSource());
        if (source == null) {
            throw new BusinessException(MessageFactory.message(BusinessException.ACCOUNT_NOT_FOUND, dto.getSource()));
        }
        Account dest = accountRepo.findByPan(dto.getDest());
        if (dest == null) {
            throw new BusinessException(MessageFactory.message(BusinessException.ACCOUNT_NOT_FOUND, dto.getDest()));
        }
        BigDecimal amount = dto.getAmount();
        if (amount.compareTo(source.getBalance()) > 0) {
            throw new BusinessException(MessageFactory.message(BusinessException.NOT_ENOUGH_MONEY, source.getPan()));
        }

        accountService.decreaseBalance(accountService.present(source),amount);
        accountService.increaseBalance(accountService.present(dest),amount);
        Account sourceOrigin = accountRepo.findByPan(source.getPan());
        Account destOrigin = accountRepo.findByPan(dest.getPan());

        Document document = new Document();
        document.setAmount(amount);
        document.setSource(sourceOrigin.getPan());
        document.setDest(destOrigin.getPan());
        Date date = new Date();
        document.setDate(date.toString());
        document.setRefId(generateDocumentRefId());
        documentRepo.save(document);
        return document.getRefId();
    }

    private String generateDocumentRefId() {
        return "DOC_"+System.currentTimeMillis();
    }

    public String reverseDocument(String refId) throws BusinessException {
        Document document = documentRepo.findByRefId(refId);
        if (document != null) {
            DocumentDto documentDto = new DocumentDto();
            documentDto.setAmount(document.getAmount());
            documentDto.setSource(document.getDest());
            documentDto.setDest(document.getSource());
            return issueDocument(documentDto);
        }
        throw new BusinessException(BusinessException.DOCUMENT_NOT_EXISTS);
    }

}
