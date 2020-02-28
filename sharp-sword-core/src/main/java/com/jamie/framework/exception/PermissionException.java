package com.jamie.framework.exception;

/**
 * @author lizheng
 * @date: 13:16 2020/02/28
 * @Description: PermissionException
 */
public class PermissionException extends RuntimeException {
    public PermissionException() {
        super();
    }

    public PermissionException(String message) {
        super(message);
    }
}
