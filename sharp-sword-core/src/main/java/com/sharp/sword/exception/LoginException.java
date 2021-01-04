package com.sharp.sword.exception;

/**
 * LoginException
 *
 * @author lizheng
 * @version 1.0
 * @date 2020/3/13 17:00
 */
public class LoginException extends RuntimeException  {
    public LoginException() {
    }

    public LoginException(String message) {
        super(message);
    }
}
