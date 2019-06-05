package com.czarea.rest.vo;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 响应类
 *
 * @author zhouzx
 */
@Data
@NoArgsConstructor
public class Response<T> implements Serializable {

    private static final long serialVersionUID = -1574275447878553270L;

    public static final Response<Void> SUCCESS = new Response<>(200, "success");
    public static final Response<Void> NO_LOGIN = new Response<>(HttpStatus.NON_AUTHORITATIVE_INFORMATION.value(), "Non-Authoritative Information");

    protected int code = 200;

    protected String msg = "success";

    protected T data;

    public Response(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Response(T data) {
        this.data = data;
    }
}
