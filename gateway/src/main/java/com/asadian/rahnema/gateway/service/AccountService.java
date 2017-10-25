package com.asadian.rahnema.gateway.service;

import com.asadian.rahnema.gateway.dto.AccountDto;
import com.asadian.rahnema.gateway.dto.TransactionDto;
import com.asadian.rahnema.gateway.dto.treasury.TreasuryAccountDto;
import com.asadian.rahnema.gateway.dto.treasury.TreasuryDocumentContainer;
import com.asadian.rahnema.gateway.dto.treasury.TreasuryDocumentDto;
import com.asadian.rahnema.gateway.exception.BusinessException;
import com.asadian.rahnema.gateway.model.Account;
import com.asadian.rahnema.gateway.model.Transaction;
import com.asadian.rahnema.gateway.repository.AccountRepository;
import com.asadian.rahnema.gateway.repository.TransactionRepository;
import com.asadian.rahnema.gateway.service.treasury.TreasuryServiceConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.ws.rs.ProcessingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rahnema on 9/6/2017.
 */
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TreasuryServiceConnector connector;

    @Autowired
    private GatewayProperties properties;

    public void register(AccountDto accountDto) throws BusinessException {
        try {
            accountRepository.save(convert(accountDto));
            connector.register(getTreasuryAccount(accountDto, accountDto.getInitAmount()));
        } catch (BusinessException e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }

    private TreasuryAccountDto getTreasuryAccount(AccountDto accountDto, BigDecimal initBalance) {
        TreasuryAccountDto dto = new TreasuryAccountDto();
        dto.setPan(accountDto.getPhone());
        dto.setBalance(initBalance);
        return dto;
    }

    private TreasuryAccountDto getTreasuryAccount(AccountDto accountDto) {
        TreasuryAccountDto dto = new TreasuryAccountDto();
        dto.setPan(accountDto.getPhone());
        return dto;
    }

    public String login(String phoneNumber) throws BusinessException {
        return connector.login(phoneNumber);
    }

    public void transfer(TransactionDto transactionDto) throws BusinessException {
        TreasuryDocumentContainer partOne;
        try {
           partOne = connector.issueDocument(generatePartOneDocument(transactionDto));
        } catch (ProcessingException pe) {
            pe.printStackTrace();
            throw new BusinessException(BusinessException.TIMEOUT_FROM_SOURCE_ACCOUNT);
        }
        if (!partOne.isOk()) {
            throw new BusinessException(partOne.getMessage());
        }

        String channelOtp = login(properties.getPan());
        transactionDto.setOtp(channelOtp);
        TreasuryDocumentContainer partTwo;
        try {
           partTwo = connector.issueDocument(generatePartTwoDocument(transactionDto));
        } catch (ProcessingException pe) {
            pe.printStackTrace();
            reverse(partOne.getRefId());
            throw new BusinessException(BusinessException.TIMEOUT_FROM_TARGET_ACCOUNT);
        }

        if (!partTwo.isOk()) {
            reverse(partOne.getRefId());
            throw new BusinessException(partTwo.getMessage());
        }
        Transaction transaction = convert(transactionDto);
        transaction.setPartOneRefId(partOne.getRefId());
        transaction.setPartTwoRefId(partTwo.getRefId());
        transaction.setRefId(generateRefId());
        transactionRepository.save(transaction);
    }

    private String generateRefId() {
        return "TX_"+System.currentTimeMillis();
    }

    private TreasuryDocumentDto generatePartTwoDocument(TransactionDto transactionDto) {
        TreasuryDocumentDto treasuryDocumentDto = new TreasuryDocumentDto();
        treasuryDocumentDto.setAmount(transactionDto.getAmount());
        treasuryDocumentDto.setSource(properties.getPan());
        treasuryDocumentDto.setDest(transactionDto.getDest());
        treasuryDocumentDto.setOtp(transactionDto.getOtp());
        return treasuryDocumentDto;
    }

    private TreasuryDocumentDto generatePartOneDocument(TransactionDto transactionDto) {
        TreasuryDocumentDto treasuryDocumentDto = new TreasuryDocumentDto();
        treasuryDocumentDto.setAmount(transactionDto.getAmount());
        treasuryDocumentDto.setSource(transactionDto.getSource());
        treasuryDocumentDto.setDest(properties.getPan());
        treasuryDocumentDto.setOtp(transactionDto.getOtp());
        return treasuryDocumentDto;
    }

    private TreasuryDocumentDto getTreasuryDocumentDto(TransactionDto transactionDto) {
        TreasuryDocumentDto dto = new TreasuryDocumentDto();
        dto.setAmount(transactionDto.getAmount());
        dto.setSource(transactionDto.getSource());
        dto.setDest(transactionDto.getSource());
        return dto;
    }

    private void reverse(String refId) {
        connector.reverseDocument(refId);
    }


    public List<TransactionDto> list(String phone) {

        return transactionRepository.findAllBySourceOrDest(phone,phone).stream()
                .map(this::present).collect(Collectors.toList());
    }

    public AccountDto present(Account account) {
        AccountDto dto = new AccountDto();
        dto.setFullName(account.getFullName());
        dto.setPhone(account.getPhone());
        return dto;
    }

    private TransactionDto present(Transaction transaction) {
        TransactionDto dto = new TransactionDto();
        dto.setDate(transaction.getDate());
        dto.setAmount(transaction.getAmount());
        dto.setDest(transaction.getDest());
        dto.setSource(transaction.getSource());
        return dto;
    }

    private Account convert(AccountDto dto) {
        Account account = new Account();
        account.setFullName(dto.getFullName());
        account.setPhone(dto.getPhone());
        return account;
    }

    private Transaction convert(TransactionDto dto) {
        Transaction transaction = new Transaction();
        transaction.setDate(new Date().toString());
        transaction.setAmount(dto.getAmount());
        transaction.setSource(dto.getSource());
        transaction.setDest(dto.getDest());
        return transaction;
    }
}
