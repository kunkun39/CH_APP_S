package com.changhong.common.exception;

/**
 * User: Jack Wang
 * Date: 14-4-10
 * Time: 下午3:19
 */
public class CHAppExistException extends CHApplicationException {

    public CHAppExistException(String message) {
        super(message);
    }

    public CHAppExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public CHAppExistException(Throwable cause) {
        super(cause);
    }
}
