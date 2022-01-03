package com.chickenfarms.chickenfarms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR,reason = "Service exception")
public class InnerServiceException extends Exception{
    public InnerServiceException(String message){
        super(message);
    }
    
}
