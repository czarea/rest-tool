package com.czarea.rest.exception;

import static org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type.SERVLET;

import com.czarea.rest.vo.Response;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 全局异常处理类
 *
 * @author zhouzx
 */
@RestControllerAdvice
@ConditionalOnClass(HttpServletRequest.class)
@ConditionalOnWebApplication(type = SERVLET)
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(BindException.class)
    public Response<String> bindException(HttpServletRequest req, BindException e) {
        logger.error("bind error", e);
        rpcThrowException(req, e);
        final List<FieldError> fieldErrors = e.getFieldErrors();
        StringBuilder builder = new StringBuilder();
        for (FieldError error : fieldErrors) {
            builder.append(error.getField()).append(":").append(error.getDefaultMessage()).append("；");
        }
        return new Response<>(HttpStatus.BAD_REQUEST.value(), builder.toString());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Response<String> methodNoSupported(HttpServletRequest req, HttpRequestMethodNotSupportedException e) {
        logger.error("method no supported ", e);
        rpcThrowException(req, e);
        String msg = e.getMethod() + "不被允许的请求方法！请使用：" + e.getSupportedHttpMethods();
        return new Response<>(HttpStatus.METHOD_NOT_ALLOWED.value(), msg);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Response<String> constraintViolationException(HttpServletRequest req, ConstraintViolationException e) {
        logger.error("", e);
        rpcThrowException(req, e);
        String builder = e.getConstraintViolations().stream()
            .map(violation -> violation.getPropertyPath().toString() + ":" + violation.getMessage() + "；").collect(Collectors.joining());
        return new Response<>(HttpStatus.BAD_REQUEST.value(), builder);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Response<String> argumentNotValid(HttpServletRequest req, MethodArgumentNotValidException e) {
        logger.error("", e);
        rpcThrowException(req, e);
        BindingResult result = e.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();
        StringBuilder builder = new StringBuilder();
        for (FieldError error : fieldErrors) {
            builder.append(error.getField()).append(":").append(error.getDefaultMessage()).append("；");
        }
        return new Response<>(HttpStatus.BAD_REQUEST.value(), builder.toString());
    }


    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public Response<String> argumentTypeMismatch(HttpServletRequest req, MethodArgumentTypeMismatchException e) {
        logger.error("", e);
        rpcThrowException(req, e);
        String msg = e.getName() + "必须是：" + e.getRequiredType() + "类型！";
        return Response.fail(HttpStatus.BAD_REQUEST.value(), msg);
    }

    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    public Response<String> mediaTypeNotSupported(HttpServletRequest req, HttpMediaTypeNotSupportedException e) {
        logger.error("", e);
        rpcThrowException(req, e);
        String msg = e.getContentType() + "不支持！Content-Type必须是：" + e.getSupportedMediaTypes() + "类型！";
        return Response.fail(HttpStatus.BAD_REQUEST.value(), msg);
    }


    @ExceptionHandler(NoHandlerFoundException.class)
    public Response<String> handle(HttpServletRequest request, NoHandlerFoundException e) {
        logger.error("", e);
        return new Response<>(404, "地址错误！！！" + request.getRequestURI() + "非法访问!");
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Response<String> notReadable(HttpServletRequest req, HttpMessageNotReadableException e) {
        logger.error("", e);
        rpcThrowException(req, e);
        return Response.fail(HttpStatus.BAD_REQUEST, "请求格式有误！" + e.getHttpInputMessage().getHeaders().getContentType());
    }

    @ExceptionHandler(value = Exception.class)
    public Response<String> otherExceptionHandler(HttpServletRequest req, Exception e) {
        logger.error("", e);
        rpcThrowException(req, e);
        Response<String> response = new Response<>();
        if (e instanceof IllegalArgumentException) {
            response.setResult(HttpStatus.BAD_REQUEST.value());
            response.setMsg(e.getMessage());
        } else {
            response.setMsg(ExceptionUtils.getStackTrace(e));
        }
        return response;
    }


    private void rpcThrowException(HttpServletRequest req, Exception e) {

    }

}
