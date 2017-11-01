package com.asadian.rahnema.treasury.service;

import com.asadian.rahnema.treasury.dto.AccountDto;
import com.asadian.rahnema.treasury.exception.BusinessException;
import com.asadian.rahnema.treasury.model.Account;
import com.asadian.rahnema.treasury.repositories.AccountRepo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.test.context.junit4.SpringRunner;
import java.math.BigDecimal;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {

    private static final Log LOGGER = LogFactory.getLog(AccountServiceTest.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepo accountRepo;

    private String samplePan;


    @Before
    public void initialize() {
        samplePan = "123456789";
    }

    @Test
    public void testAll() {
        testRegister();
        testLoad();
        testLogin();
        testDelete();
    }

    private void testRegister() {
        AccountDto accountDto = new AccountDto();
        accountDto.setPan(samplePan);
        accountDto.setBalance(new BigDecimal(2000));
        try {
            accountService.register(accountDto);
        } catch (BusinessException e) {
            LOGGER.warn(e);
        }
        Account account = accountRepo.findByPan(samplePan);
        assertNotNull(account);
    }


    private void testLoad() {
        AccountDto account = null;
        try {
            account = accountService.load(samplePan);
        } catch (BusinessException e) {
            LOGGER.error(e);
            assert true;
        }
        assertNotNull(account);
    }


    private void testLogin() {
        try {
            String otp = accountService.login(samplePan);
            assert otp == null || otp.equals("");
        } catch (BusinessException e) {
            LOGGER.error(e);
            assert true;
        }
    }

    private void testDelete() {
        try {
            accountService.drop(samplePan);
        } catch (BusinessException e) {
            LOGGER.error(e);
            assert true;
        }
        Account account = accountRepo.findByPan(samplePan);
        assertNull(account);
    }


}