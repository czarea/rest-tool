package com.czarea.rest.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplateHandler;

/**
 * @author zhouzx
 */
@Component
public class MyUriTemplateHandler implements UriTemplateHandler {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public URI expand(String uri, Map<String, ?> params) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(uri);
        for (Map.Entry<String, ?> entry : params.entrySet()) {
            String firstKey = entry.getKey();
            if (entry.getValue() instanceof Map) {
                Map values = (Map) entry.getValue();
                values.forEach((k, v) -> {
                    if (v != null) {
                        uriComponentsBuilder.queryParam(firstKey + "." + k, v);
                    }
                });
            } else {
                if (entry.getValue() != null) {
                    uriComponentsBuilder.queryParam(firstKey, entry.getValue());
                }
            }
        }
        uri = uriComponentsBuilder.build().toUriString();
        return URI.create(uri);
    }

    @Override
    public URI expand(String uri, Object... params) {
        if (params.length != 0 && params[0] != null) {
            Map map = mapper.convertValue(params[0], Map.class);
            return expand(uri, map);
        } else {
            return URI.create(uri);
        }
    }


}
