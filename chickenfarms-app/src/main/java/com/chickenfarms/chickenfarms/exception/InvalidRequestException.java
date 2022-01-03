package com.chickenfarms.chickenfarms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST,reason = "Invalid user request")
public class InvalidRequestException extends Exception{
    public InvalidRequestException(String message){
        super(message);
    }
}
