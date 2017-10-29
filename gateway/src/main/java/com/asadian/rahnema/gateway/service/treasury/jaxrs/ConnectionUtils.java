package com.asadian.rahnema.gateway.service.treasury.jaxrs;

import com.asadian.rahnema.gateway.dto.treasury.TreasuryResultContainer;
import com.asadian.rahnema.gateway.exception.BusinessException;
import org.glassfish.jersey.client.ClientProperties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by rahnema on 9/6/2017.
 */
@Deprecated
public class ConnectionUtils {


    private static final String BASE_URL = "http://localhost:8081/treasury";
    private static final int CONNECT_TIMEOUT = 30000;
    private static final int READ_TIMEOUT = 30000;


    public static Object request(Path path, Class clazz ,String pathVariable ,Object requestData) throws BusinessException {
        Entity entity = Entity.entity(requestData, MediaType.APPLICATION_JSON);
        return connect(path, entity, pathVariable, clazz);
    }

    public static Object transact(Path path, Class clazz ,String pathVariable ,Object requestData) throws BusinessException {
        Entity entity = Entity.entity(requestData, MediaType.APPLICATION_JSON);
        return transact(path, entity, pathVariable, clazz);
    }

    private static<T> T transact(Path path, Entity entity, String pathVariable, Class<T> t) throws BusinessException {
        Client client = ClientBuilder.newClient();
        client.property(ClientProperties.CONNECT_TIMEOUT,
                CONNECT_TIMEOUT);
        client.property(ClientProperties.READ_TIMEOUT,
                READ_TIMEOUT);
        WebTarget target = client.target(urlCreator(path,pathVariable));
        Response response = entity != null ? target.request().
                accept(MediaType.APPLICATION_JSON).
                post(entity) : target.request().
                post(null);
        if (response.getStatus() != 200) {
            TreasuryResultContainer container = (TreasuryResultContainer)response.readEntity(t);
            throw new BusinessException(container.getMessage());
        }
        return response.readEntity(t);
    }


    private static<T> T connect(Path path, Entity entity, String pathVariable, Class<T> t) throws BusinessException {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(urlCreator(path,pathVariable));
        Response response = null;
        response = entity != null ? target.request().
                accept(MediaType.APPLICATION_JSON).
                post(entity) : target.request().
                post(null);
        if (response.getStatus() != 200) {
            TreasuryResultContainer container = (TreasuryResultContainer)response.readEntity(t);
            throw new BusinessException(container.getMessage());
        }
        return response.readEntity(t);
    }

    private static String urlCreator(Path path,String pathVariable) {
        String raw = BASE_URL.concat(path.toString());
        String result = pathVariable != null ? raw.concat(pathVariable) : raw;
        return result;
    }
}
