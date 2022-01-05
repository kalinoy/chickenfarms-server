package com.chickenfarms.chickenfarms.service;

import com.chickenfarms.chickenfarms.exception.InvalidRequestException;
import com.chickenfarms.chickenfarms.exception.RecordNotFoundException;
import com.chickenfarms.chickenfarms.model.entities.Ticket;

public interface TicketEditorDetailsService {
    
    Ticket editTicketDescription(long ticketId, String description) throws RecordNotFoundException;
    
    Ticket editTicketProblem(long ticketId, int description) throws RecordNotFoundException, InvalidRequestException;
    
}
