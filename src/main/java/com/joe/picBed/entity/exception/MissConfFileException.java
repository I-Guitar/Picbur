package com.joe.picBed.entity.exception;

/**
 * Created by hu-jinwen on 2020/8/23
 */
public class MissConfFileException extends RuntimeException {

    public MissConfFileException() {
        super();
    }

    public MissConfFileException(String message) {
        super(message);
    }

    public MissConfFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissConfFileException(Throwable cause) {
        super(cause);
    }

    protected MissConfFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
