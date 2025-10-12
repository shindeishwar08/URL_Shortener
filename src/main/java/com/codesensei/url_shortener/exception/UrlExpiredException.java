package com.codesensei.url_shortener.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.GONE, reason="The link has expired")
public class UrlExpiredException extends RuntimeException {
    public UrlExpiredException(String message){
        super(message);
    }
}
