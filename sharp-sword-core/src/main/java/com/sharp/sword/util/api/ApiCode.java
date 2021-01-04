/*
 * Copyright 2019-2029 geekidea(https://github.com/geekidea)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sharp.sword.util.api;

/**
 * <p>
 * REST API 响应码
 * </p>
 *
 * @author geekidea
 * @since 2018-11-08
 */
public enum ApiCode {

    SUCCESS(200, "操作成功"),
    MSG_NOT_READABLE(400, "消息不能读取"),

    UNAUTHORIZED(401, "非法访问"),

    NOT_PERMISSION(403, "没有权限"),

    NOT_FOUND(404, "你请求的资源不存在"),

    METHOD_NOT_SUPPORTED(405, "不支持当前请求方法"),

    MEDIA_TYPE_NOT_SUPPORTED(415, "不支持当前媒体类型"),

    PARAM_MISS_EXCEPTION(416,"缺少必要的请求参数"),

    FAIL(500, "操作失败"),


    LOGIN_EXCEPTION(4000,"登陆失败"),

    USERNAME_PASSWORD_ERROR(4001, "用户名或密码错误"),

    SYSTEM_EXCEPTION(5000,"系统异常!"),

    PARAMETER_EXCEPTION(5001,"请求参数校验异常"),

    PARAMETER_PARSE_EXCEPTION(5002,"请求参数解析异常"),

    HTTP_MEDIA_TYPE_EXCEPTION(5003,"HTTP Media 类型异常")


    ;

    private final int code;
    private final String msg;

    ApiCode(final int code, final String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ApiCode getApiCode(int code) {
        ApiCode[] ecs = ApiCode.values();
        for (ApiCode ec : ecs) {
            if (ec.getCode() == code) {
                return ec;
            }
        }
        return SUCCESS;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
