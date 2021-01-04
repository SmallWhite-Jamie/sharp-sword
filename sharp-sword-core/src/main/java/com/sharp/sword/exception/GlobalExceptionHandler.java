package com.sharp.sword.exception;

import com.sharp.sword.util.api.ApiCode;
import com.sharp.sword.util.api.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.MimeType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lizheng
 * @date: 11:16 2020/02/01
 * @Description: GlobalExceptionHandler
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResult baseExceptionHandler(BaseException ex) {
        log.error(ex.getMessage(), ex);
        return ApiResult.result(ex.getCode(), ex.getMessage(), null);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResult exceptionHandler(Exception e, HttpServletRequest request){
        log.error("Global Exception：", e);
        ApiResult<Map<String, Object>> result = new ApiResult<>();
        result.setCode(500);
        result.setMsg("Global Exceptions");
        result.setTime(new Date());
        Map<String, Object> data = new HashMap<>(2);
        data.put("path", request.getRequestURI());
        data.put("desc", e.getMessage());
        result.setData(data);
        return result;
    }
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult handleError(MissingServletRequestParameterException e) {
        log.error("缺少请求参数{}", e.getMessage());
        String message = String.format("缺少必要的请求参数: %s", e.getParameterName());
        return ApiResult.fail(ApiCode.PARAM_MISS_EXCEPTION, message);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult handleError(MethodArgumentTypeMismatchException e) {
        log.error("请求参数格式错误{}", e.getMessage());
        String message = String.format("请求参数格式错误: %s", e.getName());
        return ApiResult.fail(ApiCode.PARAMETER_PARSE_EXCEPTION, message);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult handleError(MethodArgumentNotValidException e) {
        log.error("参数验证失败{}", e.getMessage());
        return this.handleError(e.getBindingResult());
    }

    @ExceptionHandler({BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult handleError(BindException e) {
        log.error("参数绑定失败{}", e.getMessage());
        return this.handleError(e.getBindingResult());
    }

    private ApiResult handleError(BindingResult result) {
        FieldError error = result.getFieldError();
        return ApiResult.fail(ApiCode.PARAMETER_EXCEPTION, error.getDefaultMessage());
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult handleError(ConstraintViolationException e) {
        log.error("参数验证失败{}", e.getMessage());
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        String path = ((PathImpl) violation.getPropertyPath()).getLeafNode().getName();
        String message = String.format("%s:%s", path, violation.getMessage());
        return ApiResult.fail(ApiCode.PARAMETER_EXCEPTION, message);
    }

    @ExceptionHandler({NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResult handleError(NoHandlerFoundException e) {
        log.error("404没找到请求:{}", e.getMessage());
        return ApiResult.fail(ApiCode.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult handleError(HttpMessageNotReadableException e) {
        log.error("消息不能读取:{}", e.getMessage());
        return ApiResult.fail(ApiCode.MSG_NOT_READABLE, e.getMessage());
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ApiResult handleError(HttpRequestMethodNotSupportedException e) {
        log.error("不支持当前请求方法:{}", e.getMessage());
        return ApiResult.fail(ApiCode.METHOD_NOT_SUPPORTED, e.getMessage());
    }

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ApiResult handleError(HttpMediaTypeNotSupportedException e) {
        log.error("不支持当前媒体类型:{}", e.getMessage());
        return ApiResult.fail(ApiCode.MEDIA_TYPE_NOT_SUPPORTED, e.getMessage());
    }

    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ApiResult handleError(HttpMediaTypeNotAcceptableException e) {
        String message = e.getMessage() + " " + Optional.of(e.getSupportedMediaTypes()).orElse(new ArrayList<>()).stream().map(MimeType::toString).collect(Collectors.joining(","));
        log.error("不接受的媒体类型:{}", message);
        return ApiResult.fail(ApiCode.MEDIA_TYPE_NOT_SUPPORTED, message);
    }
}
