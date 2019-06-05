package com.czarea.rest.config;

import java.util.function.Supplier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;

/**
 * @author zhouzx
 */
@Component
public class HttpClientRequestFactory implements Supplier<ClientHttpRequestFactory> {

    private final HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory;

    public HttpClientRequestFactory(CloseableHttpClient httpClient) {
        httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpComponentsClientHttpRequestFactory.setHttpClient(httpClient);
    }

    @Override
    public ClientHttpRequestFactory get() {
        return httpComponentsClientHttpRequestFactory;
    }
}
