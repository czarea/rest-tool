package com.czarea.rest.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhouzx
 */
//@Component
public class RestTemplateService {

    private final RestTemplate restTemplate;

    public RestTemplateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * 有泛型的情况下使用
     *
     * @param url 地址
     * @param requestEntity 请求实体
     * @param parameterizedTypeReference 泛型
     * @param <T> 响应类型
     * @return 响应数据
     */
    public <T> T request(String url, HttpMethod method, HttpEntity requestEntity,
        ParameterizedTypeReference<T> parameterizedTypeReference) {
        ResponseEntity<T> responseEntity = restTemplate.exchange(
            url,
            method,
            requestEntity,
            parameterizedTypeReference
        );
        return responseEntity.getBody();
    }
}
