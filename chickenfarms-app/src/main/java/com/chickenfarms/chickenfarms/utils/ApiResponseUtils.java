package com.chickenfarms.chickenfarms.utils;

import com.chickenfarms.chickenfarms.model.ApiResponse;
import org.springframework.http.ResponseEntity;

public class ApiResponseUtils {
    
    public static ResponseEntity<ApiResponse> getApiResponse(Object businessDetails) {
        ApiResponse apiResponse = new ApiResponse(businessDetails);
        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
    }
    
}
