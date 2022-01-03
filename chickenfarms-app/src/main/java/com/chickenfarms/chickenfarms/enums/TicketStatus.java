package com.chickenfarms.chickenfarms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TicketStatus {

    CREATED("Ticket created"),
    READY("Ticket ready"),
    RECONCILED("Ticket reconciled"),
    CLOSED("Ticket closed");
    
    private String ticketStatus;
    
}
