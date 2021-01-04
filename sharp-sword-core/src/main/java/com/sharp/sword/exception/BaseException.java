package com.sharp.sword.exception;

import com.sharp.sword.enums.IErrorCodeEnum;

/**
 * @author lizheng
 */
public class BaseException extends RuntimeException {

    private int status = 200;

    private int code = 200;

    private String msg;

    public BaseException(String message, int status) {
        super(message);
        this.status = status;
        this.code = status;
        this.msg = message;
    }
    public BaseException(IErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getErrDesc());
        this.status = errorCodeEnum.getErrCode();
        this.code = status;
        this.msg = errorCodeEnum.getErrDesc();
    }



    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
