package com.jamie.framework.exception;

import com.jamie.framework.util.api.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lizheng
 * @date: 11:16 2020/02/01
 * @Description: GlobalExceptionHandler
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
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
}
