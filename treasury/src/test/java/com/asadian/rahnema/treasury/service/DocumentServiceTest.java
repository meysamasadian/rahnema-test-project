package com.asadian.rahnema.treasury.service;

import com.asadian.rahnema.treasury.dto.AccountDto;
import com.asadian.rahnema.treasury.dto.DocumentDto;
import com.asadian.rahnema.treasury.exception.BusinessException;
import com.asadian.rahnema.treasury.model.Document;
import com.asadian.rahnema.treasury.repositories.AccountRepo;
import com.asadian.rahnema.treasury.repositories.DocumentRepo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DocumentServiceTest {

    private static final Log LOGGER = LogFactory.getLog(DocumentServiceTest.class);

    @Autowired
    private DocumentService documentService;

    @Autowired
    private DocumentRepo documentRepo;

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private AccountService accountService;

    private AccountDto sourceAccount;
    private AccountDto destAccount;
    private DocumentDto document;

    @Before
    public void initialize() {
        sourceAccount = new AccountDto();
        sourceAccount.setPan("123");
        sourceAccount.setBalance(new BigDecimal(100));

        destAccount = new AccountDto();
        destAccount.setPan("456");
        destAccount.setBalance(BigDecimal.ZERO);

        document = new DocumentDto();
        document.setAmount(sourceAccount.getBalance());
        document.setSource(sourceAccount.getPan());
        document.setDest(destAccount.getPan());
    }


    @Test
    public void testAll() {
        String refId = testIssue();
        testReverse(refId);
    }


    @After
    public void cleanup() {
        documentRepo.deleteAll();
        accountRepo.deleteAll();
    }


    private String testIssue() {
        AccountDto srcDto = null;
        AccountDto destDto = null;
        String otp = null;
        try {
            srcDto = accountService.register(sourceAccount);
            destDto = accountService.register(destAccount);
        } catch (BusinessException e) {
            LOGGER.error(e);
        }

        assertNotNull(srcDto);
        assertNotNull(destDto);

        try {
            otp = accountService.login(sourceAccount.getPan());
        } catch (BusinessException e) {
            LOGGER.error(e);
        }
        assertNotNull(otp);

        document.setOtp(otp);
        String refId = null;
        try {
            refId = documentService.issueDocument(document);
        } catch (BusinessException e) {
            LOGGER.error(e);
        }
        assertNotNull(refId);
        return refId;
    }

    private void testReverse(String refId) {
        try {
            documentService.reverseDocument(refId);
        } catch (BusinessException e) {
            LOGGER.error(e);
            assert true;
        }
    }
}
