package com.asadian.rahnema.gateway.service;

import com.asadian.rahnema.gateway.dto.AccountDto;
import com.asadian.rahnema.gateway.dto.TransactionDto;
import com.asadian.rahnema.gateway.dto.treasury.TreasuryAccountDto;
import com.asadian.rahnema.gateway.dto.treasury.TreasuryDocumentDto;
import com.asadian.rahnema.gateway.dto.treasury.TreasuryResultContainer;
import com.asadian.rahnema.gateway.exception.BusinessException;
import com.asadian.rahnema.gateway.model.Account;
import com.asadian.rahnema.gateway.model.Transaction;
import com.asadian.rahnema.gateway.repository.AccountRepository;
import com.asadian.rahnema.gateway.repository.TransactionRepository;
import com.asadian.rahnema.gateway.service.treasury.jaxrs.TreasuryServiceConnector;
import com.asadian.rahnema.gateway.service.treasury.retrofit.callback.TreasuryAccountServiceCaller;
import com.asadian.rahnema.gateway.service.treasury.retrofit.client.TreasuryServiceClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.ProcessingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

/**
 * Created by rahnema on 9/6/2017.
 */
@Service
public class AccountService {
    private static final Log LOGGER = LogFactory.getLog(AccountService.class);
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private GatewayProperties properties;

    @Autowired
    private TreasuryAccountServiceCaller serviceCaller;

    public String register(AccountDto accountDto) throws BusinessException {
        try {
            TreasuryResultContainer container =
                    serviceCaller.register(getTreasuryAccount(accountDto, accountDto.getInitAmount()));
            accountRepository.save(convert(accountDto));
            return container.getMessage();
        } catch (BusinessException e) {
            LOGGER.warn(e);
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

    public TreasuryResultContainer login(String phoneNumber) throws BusinessException {
        TreasuryResultContainer login = serviceCaller.login(phoneNumber);
        return login;
    }

    public TransactionDto transfer(TransactionDto transactionDto) throws BusinessException {
        TreasuryResultContainer partOne;
        try {
           partOne = serviceCaller.issueDocument(generatePartOneDocument(transactionDto));
        } catch (ProcessingException pe) {
            LOGGER.warn(pe);
            throw new BusinessException(BusinessException.TIMEOUT_FROM_SOURCE_ACCOUNT);
        }
        String channelOtp = (String) login(properties.getPan()).getData();
        transactionDto.setOtp(channelOtp);
        TreasuryResultContainer partTwo;
        try {
           partTwo = serviceCaller.issueDocument(generatePartTwoDocument(transactionDto));
        } catch (ProcessingException pe) {
            LOGGER.warn(pe);
            reverse(((String) partOne.getData()));
            throw new BusinessException(BusinessException.TIMEOUT_FROM_TARGET_ACCOUNT);
        } catch (BusinessException bx) {
            reverse((String)partOne.getData());
            throw new BusinessException(bx.getMessage());
        }
        Transaction transaction = convert(transactionDto);
        transaction.setPartOneRefId((String)partOne.getData());
        transaction.setPartTwoRefId((String)partTwo.getData());
        transaction.setRefId(generateRefId());
        transactionRepository.save(transaction);
        transactionDto.setOtp(null);
        transactionDto.setDate(transaction.getDate());
        transactionDto.setTx(transaction.getRefId());
        return transactionDto;
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

    private void reverse(String refId) throws BusinessException {
        serviceCaller.reverseDocument(refId);
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
        dto.setTx(transaction.getRefId());
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
