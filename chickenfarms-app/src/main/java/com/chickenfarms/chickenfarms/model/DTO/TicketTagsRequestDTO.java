package com.chickenfarms.chickenfarms.model.DTO;

import lombok.Getter;

import java.util.List;

@Getter
public class TicketTagsRequestDTO {
    
    long ticketId;
    List<String> tags;
}
