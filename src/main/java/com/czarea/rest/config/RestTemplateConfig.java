package com.czarea.rest.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhouzx
 */
@Configuration
public class RestTemplateConfig {

    private final ObjectProvider<HttpMessageConverters> messageConverters;

    public RestTemplateConfig(
        ObjectProvider<HttpMessageConverters> messageConverters) {
        this.messageConverters = messageConverters;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    @Bean
    public RestTemplateBuilder restTemplateBuilder(HttpClientRequestFactory httpClientRequestFactory) {
        HttpMessageConverters converters = this.messageConverters.getIfUnique();
        return new RestTemplateBuilder()
            .rootUri("http://localhost:8080")
            .uriTemplateHandler(new MyUriTemplateHandler())
            //.errorHandler(myResponseErrorHandler)
            .requestFactory(httpClientRequestFactory)
            .messageConverters(converters.getConverters())
            .additionalInterceptors(new RestHttpRequestInterceptor());
    }

}
