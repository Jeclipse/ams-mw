package com.byblosbank.ams.exception;

public class AMSWebClientException extends RuntimeException {

    public AMSWebClientException() {
        super();
    }

    public AMSWebClientException(String message) {
        super(message);
    }

    public AMSWebClientException(String message, Throwable t) {
        super(message, t);
    }

}
