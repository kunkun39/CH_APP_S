package com.changhong.common.exception;

/**
 * User: Jack Wang
 * Date: 14-4-10
 * Time: 下午3:19
 */
public class CHApplicationException extends RuntimeException {

    public CHApplicationException() {
    }

    public CHApplicationException(String message) {
        super(message);
    }

    public CHApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CHApplicationException(Throwable cause) {
        super(cause);
    }
}
