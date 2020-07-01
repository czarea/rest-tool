package com.czarea.rest.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * @author zhouzx
 */
public class MyFastJsonHttpMessageConverter extends FastJsonHttpMessageConverter {

    @Override
    protected boolean supports(Class<?> clazz) {
        if (clazz == null) {
            return true;
        }
        return clazz.getPackage().getName().contains("com.czarea.rest");
    }
}
