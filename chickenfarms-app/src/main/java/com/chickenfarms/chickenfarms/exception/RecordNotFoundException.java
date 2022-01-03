package com.chickenfarms.chickenfarms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,reason = "The record doesn't exist in DB")
public class RecordNotFoundException extends Exception{
    public RecordNotFoundException(String message){
        super(message);
    }
}
