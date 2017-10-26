package com.asadian.rahnema.treasury.service;


import com.asadian.rahnema.treasury.dto.AccountDto;
import com.asadian.rahnema.treasury.dto.OtpContainer;
import com.asadian.rahnema.treasury.exception.BusinessException;
import com.asadian.rahnema.treasury.message.MessageFactory;
import com.asadian.rahnema.treasury.model.Account;
import com.asadian.rahnema.treasury.repositories.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

/**
 * Created by rahnema on 9/6/2017.
 */
@Service
public class AccountService {
    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private Cache otpCache;

    @Autowired
    private OtpProperties properties;

    public void validate(Account account, String otp) throws BusinessException {
        Cache.ValueWrapper wrapper = otpCache.get(account.getPan());

        OtpContainer container =  wrapper != null ? (OtpContainer)wrapper.get() : null;
        if (container != null) {
            if (!container.getCode().equals(otp)) {
                throw new BusinessException(BusinessException.OTP_IS_NOT_VALID);
            }
        } else {
            throw new BusinessException(BusinessException.OTP_IS_NOT_VALID);
        }
    }

    public String login(String pan) throws BusinessException {
        Account account = accountRepo.findByPan(pan);
        if (account != null) {
            Cache.ValueWrapper wrapper = otpCache.get(pan);

            OtpContainer container = wrapper != null ? (OtpContainer) wrapper.get() : null;
            if (container != null) {
                if (container.getExpire() > System.currentTimeMillis()) {
                    return container.getCode();
                }
            }
            otpCache.put(pan, generateOtp());
            return ((OtpContainer) otpCache.get(pan).get()).getCode();
        }
        throw new BusinessException(MessageFactory.message(BusinessException.ACCOUNT_NOT_FOUND, pan));
    }


    private OtpContainer generateOtp() {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        int i = 0;
        while(i < properties.getLength()) {
            builder.append(random.nextInt(9));
            i++;
        }
        return new OtpContainer(builder.toString(),
                System.currentTimeMillis() + properties.getExpireDuration());
    }

    public AccountDto register(AccountDto dto) throws BusinessException {
        Account account = accountRepo.findByPan(dto.getPan());
        if (account != null) {
            throw new BusinessException(BusinessException.ACCOUNT_IS_DUPLICATE);
        }
        accountRepo.save(convert(dto));
        return present(accountRepo.findByPan(dto.getPan()));
    }

    public AccountDto load(String pan) throws BusinessException {
        Account account = accountRepo.findByPan(pan);
        if (account != null) {
            return present(account);
        } else {
            throw new BusinessException(MessageFactory.message(BusinessException.ACCOUNT_NOT_FOUND, pan));
        }
    }


    public void increaseBalance(AccountDto dto, BigDecimal amount) {
        Account account = accountRepo.findByPan(dto.getPan());
        account.setBalance(account.getBalance().add(amount));
        accountRepo.save(account);
    }

    public void decreaseBalance(AccountDto dto, BigDecimal amount) {
        Account account = accountRepo.findByPan(dto.getPan());
        account.setBalance(account.getBalance().subtract(amount));
        accountRepo.save(account);
    }


    public Account convert(AccountDto accountDto) {
        Account account = new Account();
        account.setPan(accountDto.getPan());
        account.setBalance(accountDto.getBalance());
        return account;
    }

    public AccountDto present(Account account) {
        AccountDto accountDto = new AccountDto();
        accountDto.setPan(account.getPan());
        accountDto.setBalance(account.getBalance());
        return accountDto;
    }
}
