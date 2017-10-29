package com.asadian.rahnema.merchant.service.gateway.retrofit.callback;

import com.asadian.rahnema.merchant.dto.gateway.GatewayAccountDto;
import com.asadian.rahnema.merchant.dto.gateway.GatewayErrorContainer;
import com.asadian.rahnema.merchant.dto.gateway.GatewayResultContainer;
import com.asadian.rahnema.merchant.dto.gateway.GatewayTransactionDto;
import com.asadian.rahnema.merchant.exception.BusinessException;
import com.asadian.rahnema.merchant.service.gateway.retrofit.client.GatewayServiceClient;
import okhttp3.ResponseBody;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import retrofit2.*;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@SuppressWarnings("Duplicates")
@Component
public class GatewayServiceCaller {
    private static final Log LOGGER = LogFactory.getLog(GatewayServiceCaller.class);
    @Autowired
    private Retrofit retrofit;



    public GatewayResultContainer login(String pan) throws BusinessException {
        try {
            final BlockingQueue<GatewayResultContainer> queue = loginCall(pan);
            GatewayResultContainer container = queue.take();
            if (container instanceof GatewayErrorContainer) {
                throw new BusinessException(container.getMessage());
            }
            return container;
        } catch (InterruptedException e) {
            LOGGER.warn(e);
            throw new BusinessException(BusinessException.GATEWAY_PROCESS_NOT_COMPLETE);
        }
    }

    public GatewayResultContainer register(GatewayAccountDto accountDto) throws BusinessException {
        try {
            final BlockingQueue<GatewayResultContainer> queue = registerCall(accountDto);
            GatewayResultContainer container = queue.take();
            if (container instanceof GatewayErrorContainer) {
                throw new BusinessException(container.getMessage());
            }
            return container;
        } catch (InterruptedException e) {
            LOGGER.warn(e);
            throw new BusinessException(BusinessException.GATEWAY_PROCESS_NOT_COMPLETE);
        }
    }

    public GatewayResultContainer transfer(GatewayTransactionDto transactionDto) throws BusinessException {
        try {
            final BlockingQueue<GatewayResultContainer> queue = transferCall(transactionDto);
            GatewayResultContainer container = queue.take();
            if (container instanceof GatewayErrorContainer) {
                throw new BusinessException(container.getMessage());
            }
            return container;
        } catch (InterruptedException e) {
            LOGGER.warn(e);
            throw new BusinessException(BusinessException.GATEWAY_PROCESS_NOT_COMPLETE);
        }
    }

    public GatewayResultContainer list(String pan) throws BusinessException {
        try {
            final BlockingQueue<GatewayResultContainer> queue = listCall(pan);
            GatewayResultContainer container = queue.take();
            if (container instanceof GatewayErrorContainer) {
                throw new BusinessException(container.getMessage());
            }
            return container;
        } catch (InterruptedException e) {
            LOGGER.warn(e);
            throw new BusinessException(BusinessException.GATEWAY_PROCESS_NOT_COMPLETE);
        }
    }

    private BlockingQueue<GatewayResultContainer> loginCall(String phoneNumber) {
        final BlockingQueue<GatewayResultContainer> result = new ArrayBlockingQueue<>(1);
        GatewayServiceClient client = initClient();
        Call<GatewayResultContainer> call = client.login(phoneNumber);
        return enqueue(result, call);
    }

    private BlockingQueue<GatewayResultContainer> registerCall(GatewayAccountDto accountDto) {
        final BlockingQueue<GatewayResultContainer> result = new ArrayBlockingQueue<>(1);
        GatewayServiceClient client = initClient();
        Call<GatewayResultContainer> call = client.register(accountDto);
        return enqueue(result, call);
    }

    private BlockingQueue<GatewayResultContainer> transferCall(GatewayTransactionDto documentDto) {
        final BlockingQueue<GatewayResultContainer> result = new ArrayBlockingQueue<>(1);
        GatewayServiceClient client = initClient();
        Call<GatewayResultContainer> call = client.transfer(documentDto);
        return enqueue(result, call);
    }

    private BlockingQueue<GatewayResultContainer> listCall(String refId) {
        final BlockingQueue<GatewayResultContainer> result = new ArrayBlockingQueue<>(1);
        GatewayServiceClient client = initClient();
        Call<GatewayResultContainer> call = client.list(refId);
        return enqueue(result, call);
    }

    private BlockingQueue<GatewayResultContainer> enqueue(BlockingQueue<GatewayResultContainer> result,
                                                           Call<GatewayResultContainer> call) {
        call.enqueue(new Callback<GatewayResultContainer>() {
            @Override
            public void onResponse(Call<GatewayResultContainer> call, Response<GatewayResultContainer> response) {
                if (response.isSuccessful()) {
                    LOGGER.info("response received successfully!");
                    result.add(response.body());
                } else {
                    LOGGER.warn("response received with error!");
                    Converter<ResponseBody, GatewayErrorContainer> errorConverter =
                            retrofit.responseBodyConverter(GatewayErrorContainer.class, new Annotation[0]);
                    GatewayErrorContainer error = null;
                    try {
                        error = errorConverter.convert(response.errorBody());
                    } catch (IOException e) {
                        LOGGER.warn(e);
                    }
                    result.add(error);
                }
            }
            @Override
            public void onFailure(Call<GatewayResultContainer> call, Throwable t) {
                LOGGER.warn(t);
            }
        });
        return result;
    }



    private GatewayServiceClient initClient() {
        return retrofit.create(GatewayServiceClient.class);
    }
}
