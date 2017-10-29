package com.asadian.rahnema.gateway.service.treasury.retrofit.callback;

import com.asadian.rahnema.gateway.dto.treasury.TreasuryAccountDto;
import com.asadian.rahnema.gateway.dto.treasury.TreasuryDocumentDto;
import com.asadian.rahnema.gateway.dto.treasury.TreasuryErrorContainer;
import com.asadian.rahnema.gateway.dto.treasury.TreasuryResultContainer;
import com.asadian.rahnema.gateway.exception.BusinessException;
import com.asadian.rahnema.gateway.service.treasury.retrofit.client.TreasuryServiceClient;
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
public class TreasuryAccountServiceCaller {
    private static final Log LOGGER = LogFactory.getLog(TreasuryAccountServiceCaller.class);

    @Autowired
    private Retrofit retrofit;

    public TreasuryResultContainer login(String phoneNumber) throws BusinessException {
        try {
            final BlockingQueue<TreasuryResultContainer> queue = loginCall(phoneNumber);
            TreasuryResultContainer container = queue.take();
            if (container instanceof TreasuryErrorContainer) {
                throw new BusinessException(container.getMessage());
            }
            return container;
        } catch (InterruptedException e) {
            LOGGER.warn(e);
            throw new BusinessException(BusinessException.TREASURY_PROCESS_NOT_COMPLETE);
        }
    }

    public TreasuryResultContainer register(TreasuryAccountDto accountDto) throws BusinessException {
        try {
            final BlockingQueue<TreasuryResultContainer> queue = registerCall(accountDto);
            TreasuryResultContainer container = queue.take();
            if (container instanceof TreasuryErrorContainer) {
                throw new BusinessException(container.getMessage());
            }
            return container;
        } catch (InterruptedException e) {
            LOGGER.warn(e);
            throw new BusinessException(BusinessException.TREASURY_PROCESS_NOT_COMPLETE);
        }
    }

    public TreasuryResultContainer issueDocument(TreasuryDocumentDto documentDto) throws BusinessException {
        try {
            final BlockingQueue<TreasuryResultContainer> queue = issueDocumentCall(documentDto);
            TreasuryResultContainer container = queue.take();
            if (container instanceof TreasuryErrorContainer) {
                throw new BusinessException(container.getMessage());
            }
            return container;
        } catch (InterruptedException e) {
            LOGGER.warn(e);
            throw new BusinessException(BusinessException.TREASURY_PROCESS_NOT_COMPLETE);
        }
    }

    public TreasuryResultContainer reverseDocument(String refId) throws BusinessException {
        try {
            final BlockingQueue<TreasuryResultContainer> queue = reverseDocumentCall(refId);
            TreasuryResultContainer container = queue.take();
            if (container instanceof TreasuryErrorContainer) {
                throw new BusinessException(container.getMessage());
            }
            return container;
        } catch (InterruptedException e) {
            LOGGER.warn(e);
            throw new BusinessException(BusinessException.TREASURY_PROCESS_NOT_COMPLETE);
        }
    }

    private BlockingQueue<TreasuryResultContainer> loginCall(String phoneNumber) {
        final BlockingQueue<TreasuryResultContainer> result = new ArrayBlockingQueue<>(1);
        TreasuryServiceClient client = initClient();
        Call<TreasuryResultContainer> call = client.login(phoneNumber);
        return enqueue(result, call);
    }

    private BlockingQueue<TreasuryResultContainer> registerCall(TreasuryAccountDto accountDto) {
        final BlockingQueue<TreasuryResultContainer> result = new ArrayBlockingQueue<>(1);
        TreasuryServiceClient client = initClient();
        Call<TreasuryResultContainer> call = client.register(accountDto);
        return enqueue(result, call);
    }

    private BlockingQueue<TreasuryResultContainer> issueDocumentCall(TreasuryDocumentDto documentDto) {
        final BlockingQueue<TreasuryResultContainer> result = new ArrayBlockingQueue<>(1);
        TreasuryServiceClient client = initClient();
        Call<TreasuryResultContainer> call = client.issueDocument(documentDto);
        return enqueue(result, call);
    }

    private BlockingQueue<TreasuryResultContainer> reverseDocumentCall(String refId) {
        final BlockingQueue<TreasuryResultContainer> result = new ArrayBlockingQueue<>(1);
        TreasuryServiceClient client = initClient();
        Call<TreasuryResultContainer> call = client.reverseDocument(refId);
        return enqueue(result, call);
    }

    private BlockingQueue<TreasuryResultContainer> enqueue(BlockingQueue<TreasuryResultContainer> result,
                                                           Call<TreasuryResultContainer> call) {
        call.enqueue(new Callback<TreasuryResultContainer>() {
            @Override
            public void onResponse(Call<TreasuryResultContainer> call, Response<TreasuryResultContainer> response) {
                if (response.isSuccessful()) {
                    LOGGER.info("response received successfully!");
                    result.add(response.body());
                } else {
                    LOGGER.warn("response received with error!");
                    Converter<ResponseBody, TreasuryErrorContainer> errorConverter =
                            retrofit.responseBodyConverter(TreasuryErrorContainer.class, new Annotation[0]);
                    TreasuryErrorContainer error = null;
                    try {
                        error = errorConverter.convert(response.errorBody());
                    } catch (IOException e) {
                        LOGGER.warn(e);
                    }
                    result.add(error);
                }
            }

            @Override
            public void onFailure(Call<TreasuryResultContainer> call, Throwable t) {
                LOGGER.warn(t);
            }
        });
        return result;
    }

    private TreasuryServiceClient initClient() {
        return retrofit.create(TreasuryServiceClient.class);
    }
}
