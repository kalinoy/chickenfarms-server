package com.chickenfarms.chickenfarms.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ApiResponse {
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();
    @JsonIgnore
    private HttpStatus httpStatus;
    private int status;
    private String error;
    private Object businessDetails;
    
    public ApiResponse(Object ticketBusinessDetails) {
        this.httpStatus = HttpStatus.OK;
        this.status = HttpStatus.OK.value();
        this.error = HttpStatus.OK.getReasonPhrase();
        this.businessDetails = ticketBusinessDetails;
    }
    
    
}
