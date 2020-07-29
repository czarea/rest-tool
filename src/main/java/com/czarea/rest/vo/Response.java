package com.czarea.rest.vo;

import com.alibaba.fastjson.annotation.JSONField;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

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

    @JSONField(name = "Result")
    protected int result = 200;

    @JSONField(name = "Msg")
    protected String msg = "success";

    @JSONField(name = "Data")
    protected T data;


    public Response(T data) {
        this.data = data;
    }

    public static <T> Response<T> data(T data) {
        return new Response<>(data);
    }

    public Response(int code, String msg) {
        this.result = code;
        this.msg = msg;
    }

    public Response(int code, String msg, T data) {
        this.result = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Response<T> fail(int code, String msg) {
        return new Response<>(code, msg, null);
    }

    public static <T> Response<T> fail(HttpStatus status, String msg) {
        return new Response<>(status.value(), msg, null);
    }

    public void setHttpStatus(HttpStatus status) {
        Assert.notNull(status, "http status not allow null");
        this.result = status.value();
    }


    @JSONField(serialize = false, deserialize = false)
    public boolean isSuccess() {
        return this.result == 200;
    }

}
