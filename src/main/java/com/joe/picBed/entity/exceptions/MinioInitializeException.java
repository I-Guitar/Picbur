package com.joe.picBed.entity.exceptions;

/**
 * Created by joe on 2020/3/20
 * <p>
 * 客户端初始化异常
 */
public class MinioInitializeException extends Exception {
    public MinioInitializeException() {
    }

    public MinioInitializeException(String message) {
        super(message);
    }

    public MinioInitializeException(String message, Throwable cause) {
        super(message, cause);
    }

    public MinioInitializeException(Throwable cause) {
        super(cause);
    }

    public MinioInitializeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
