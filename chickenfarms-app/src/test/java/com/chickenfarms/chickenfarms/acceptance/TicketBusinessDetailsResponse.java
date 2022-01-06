package com.chickenfarms.chickenfarms.acceptance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TicketBusinessDetailsResponse {
    
    private String username;
    private int problemId;
    private String description;
    private String status;
    private int farmId;
    private String rootCause;
    
}
