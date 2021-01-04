package com.sharp.sword.login;

import com.sharp.sword.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * LoginFailDataBean
 *
 * @author lizheng
 * @version 1.0
 * @date 2020/3/13 16:03
 */
@Data
@AllArgsConstructor
public class LoginFailDataBean {
    private ErrorCode failEnum;
    private Object data;
}
