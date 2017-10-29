package com.asadian.rahnema.merchant.service.gateway.retrofit.client;


import com.asadian.rahnema.merchant.dto.gateway.GatewayAccountDto;
import com.asadian.rahnema.merchant.dto.gateway.GatewayResultContainer;
import com.asadian.rahnema.merchant.dto.gateway.GatewayTransactionDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface GatewayServiceClient {
    @POST("account/login/{pan}")
    Call<GatewayResultContainer> login(@Path("pan") String pan);

    @POST("account/register")
    Call<GatewayResultContainer> register(@Body GatewayAccountDto accountDto);

    @POST("account/transfer")
    Call<GatewayResultContainer> transfer(@Body GatewayTransactionDto transactionDto);

    @GET("account/list/{pan}")
    Call<GatewayResultContainer> list(@Path("pan") String pan);
}
