package com.changhong.common.exception;

/**
 * User: Jack Wang
 * Date: 14-4-10
 * Time: 下午3:19
 */
public class CHDocumentOperationException extends CHApplicationException {

    public CHDocumentOperationException(String message) {
        super(message);
    }

    public CHDocumentOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CHDocumentOperationException(Throwable cause) {
        super(cause);
    }
}
