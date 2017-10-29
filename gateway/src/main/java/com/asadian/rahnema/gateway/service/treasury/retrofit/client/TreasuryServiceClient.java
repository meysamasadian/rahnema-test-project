package com.asadian.rahnema.gateway.service.treasury.retrofit.client;

import com.asadian.rahnema.gateway.dto.treasury.TreasuryAccountDto;
import com.asadian.rahnema.gateway.dto.treasury.TreasuryDocumentDto;
import com.asadian.rahnema.gateway.dto.treasury.TreasuryResultContainer;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TreasuryServiceClient {
    @POST("account/register")
    Call<TreasuryResultContainer> register(@Body TreasuryAccountDto dto);

    @POST("account/login/{pan}")
    Call<TreasuryResultContainer> login(@Path("pan") String phoneNumber);

    @POST("document/issue")
    Call<TreasuryResultContainer> issueDocument(@Body TreasuryDocumentDto document);

    @POST("document/reverse/{refId}")
    Call<TreasuryResultContainer> reverseDocument(@Path("refId") String refId);
}
