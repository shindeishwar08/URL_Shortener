package com.codesensei.url_shortener.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.CONFLICT, reason="This alias is already in use")
public class AliasAlreadyExistsException extends RuntimeException{
    public AliasAlreadyExistsException(String message){
        super(message);
    }
}
