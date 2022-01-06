package com.chickenfarms.chickenfarms.model;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TicketBusinessDetails {

    private long ticketId;
    private String status;
    private int sla;
    private int grade;
    private String description;
    private int farmId;
    private boolean isResolved;
    private List<String> tagsName;
    private int problemId;
    private String userName;
    private String rootCauseName;
    
    
}
