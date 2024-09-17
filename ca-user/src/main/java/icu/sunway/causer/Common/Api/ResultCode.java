package icu.sunway.causer.Common.Api;

import lombok.Getter;

/**
 * 封装服务器响应请求的响应码
 *
 * @author gushen
 * @since 15 April 2024
 * @version 0.0.1
 */
@Getter
public enum ResultCode {
    SUCCESS(200, "请求响应成功"),
    FAILED(500, "请求响应失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限");

    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
