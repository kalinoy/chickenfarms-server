package com.chickenfarms.chickenfarms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR,reason = "An issue occurred with DB actions.")
public class DBException extends Exception{
    public DBException(String message){
        super(message);
    }
}
