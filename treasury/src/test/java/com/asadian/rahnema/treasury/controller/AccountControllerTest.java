package com.asadian.rahnema.treasury.controller;

import com.asadian.rahnema.treasury.dto.AccountDto;
import com.asadian.rahnema.treasury.model.Account;
import com.asadian.rahnema.treasury.repositories.AccountRepo;
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
public class AccountControllerTest {
    private static final Gson GSON = new Gson();
    @Autowired
    WebApplicationContext context;

    @Autowired
    private AccountRepo accountRepo;

    private Account mockAccountLogin;

    private AccountDto mockAccountRegister;

    private MockMvc mvc;

    @Before
    public void initTests() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        createMockData();
    }

    private void createMockData() {
        mockAccountLogin = new Account();
        mockAccountLogin.setBalance(BigDecimal.ZERO);
        mockAccountLogin.setPan("123456789");
        accountRepo.save(mockAccountLogin);

        mockAccountRegister = new AccountDto();
        mockAccountRegister.setPan("987654321");
        mockAccountRegister.setBalance(new BigDecimal(200));
    }

    @Test
    public void registerTest() throws Exception {
        String json = GSON.toJson(mockAccountRegister);
        this.mvc.perform(post("/api/account/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", notNullValue()));
    }

    @Test
    public void loginTest() throws Exception {
        this.mvc.perform(post("/api/account/login/".concat(mockAccountLogin.getPan()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", notNullValue()));
    }

    @After
    public void cleanup() {
        accountRepo.deleteAll();
    }
}
