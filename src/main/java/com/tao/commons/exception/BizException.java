package com.tao.commons.exception;

import com.tao.commons.result.ResponseCodeEnum;

/**
 * @Author：cboss
 * @Date：2023/3/30
 */
public class BizException extends RuntimeException {
    private ResponseCodeEnum code;

    public BizException(String message) {
        super(message, null, false, false);
    }

    public BizException(ResponseCodeEnum code) {
        super(code.getMessage(), null, false, false);
        this.code = code;
    }

    public ResponseCodeEnum getCode() {
        return this.code;
    }

    public BizException() {
        super("业务执行失败", null, false, false);
    }
}
