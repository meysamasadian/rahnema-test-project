package com.asadian.rahnema.merchant.service.gateway.jaxrs;


import com.asadian.rahnema.merchant.dto.gateway.GatewayResultContainer;
import com.asadian.rahnema.merchant.exception.BusinessException;
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

@SuppressWarnings("Duplicates")
@Deprecated
public class ConnectionUtils {


    private static final String BASE_URL = "http://localhost:8082/gateway";
    private static final int CONNECT_TIMEOUT = 30000;
    private static final int READ_TIMEOUT = 30000;

    static Object requestGet(Path path, Class clazz, String pathVariable) throws BusinessException {
        return connectGet(path, pathVariable, clazz);
    }

    static Object requestPost(Path path, Class clazz, String pathVariable, Object requestData) throws BusinessException {
        Entity entity = Entity.entity(requestData, MediaType.APPLICATION_JSON);
        return connectPost(path, entity, pathVariable, clazz);
    }

    static Object transact(Path path, Class clazz, String pathVariable, Object requestData) throws BusinessException {
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
            GatewayResultContainer container = (GatewayResultContainer)response.readEntity(t);
            throw new BusinessException(container.getMessage());
        }
        return response.readEntity(t);
    }


    private static<T> T connectPost(Path path, Entity entity, String pathVariable, Class<T> t) throws BusinessException {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(urlCreator(path,pathVariable));
        Response response;
        response = entity != null ? target.request().
                accept(MediaType.APPLICATION_JSON).
                post(entity) : target.request().
                post(null);
        if (response.getStatus() != 200) {
            GatewayResultContainer container = (GatewayResultContainer)response.readEntity(t);
            throw new BusinessException(container.getMessage());
        }
        return response.readEntity(t);
    }

    private static<T> T connectGet(Path path, String pathVariable, Class<T> t) throws BusinessException {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(urlCreator(path,pathVariable));
        Response response;
        response = target.request().
                accept(MediaType.APPLICATION_JSON).get();
        if (response.getStatus() != 200) {
            GatewayResultContainer container = (GatewayResultContainer)response.readEntity(t);
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
