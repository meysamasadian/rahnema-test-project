package com.asadian.rahnema.gateway.config;

import com.asadian.rahnema.gateway.service.treasury.retrofit.callback.TreasuryApiProperties;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class GatewayConfig {

    @Bean
    @Autowired
    public Retrofit retrofit(TreasuryApiProperties properties) {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(properties.getBaseUrl())
            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                    .setLenient()
                    .create()))
            .build();
        return retrofit;
    }
}
