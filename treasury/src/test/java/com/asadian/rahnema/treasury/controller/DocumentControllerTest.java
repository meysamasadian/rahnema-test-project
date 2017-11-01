package com.asadian.rahnema.treasury.controller;

import com.asadian.rahnema.treasury.dto.AccountDto;
import com.asadian.rahnema.treasury.dto.DocumentDto;
import com.asadian.rahnema.treasury.model.Account;
import com.asadian.rahnema.treasury.repositories.AccountRepo;
import com.asadian.rahnema.treasury.repositories.DocumentRepo;
import com.asadian.rahnema.treasury.service.AccountService;
import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DocumentControllerTest {
    private static final Gson GSON = new Gson();

    @Autowired
    WebApplicationContext context;

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private DocumentRepo documentRepo;

    @Autowired
    private AccountService accountService;

    private Account mockSourceAccount;

    private Account mockDestinationAccount;

    private MockMvc mvc;

    @Before
    public void initTests() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        createMockData();
    }

    private void createMockData() {
        mockSourceAccount = new Account();
        mockSourceAccount.setBalance(new BigDecimal(200));
        mockSourceAccount.setPan("123456789");
        accountRepo.save(mockSourceAccount);

        mockDestinationAccount = new Account();
        mockDestinationAccount.setBalance(BigDecimal.ZERO);
        mockDestinationAccount.setPan("987654321");
        accountRepo.save(mockDestinationAccount);
    }

    @Test
    public void testIssuance() throws Exception {
        String otp = accountService.login(mockSourceAccount.getPan());

        DocumentDto documentDto = new DocumentDto();
        documentDto.setSource(mockSourceAccount.getPan());
        documentDto.setDest(mockDestinationAccount.getPan());
        documentDto.setAmount(mockSourceAccount.getBalance());
        documentDto.setOtp(otp);

        mvc.perform(post("/document/issue")
                .contentType(MediaType.APPLICATION_JSON)
                .content(GSON.toJson(documentDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.refId", notNullValue()));
    }

    @After
    public void cleanup() {
        accountRepo.deleteAll();
        documentRepo.deleteAll();
    }
}
