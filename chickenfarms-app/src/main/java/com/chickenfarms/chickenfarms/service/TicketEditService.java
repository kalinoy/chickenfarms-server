package com.chickenfarms.chickenfarms.service;

import com.chickenfarms.chickenfarms.exception.InnerServiceException;
import com.chickenfarms.chickenfarms.exception.InvalidRequestException;
import com.chickenfarms.chickenfarms.exception.RecordNotFoundException;
import com.chickenfarms.chickenfarms.model.entities.Tag;
import com.chickenfarms.chickenfarms.model.entities.Ticket;

import java.util.List;

public interface TicketEditService {
    
    List<Tag> addTags(long ticketId, List<String> tags) throws RecordNotFoundException, InnerServiceException;
    
    Ticket editTicketDescription(long ticketId, String description) throws RecordNotFoundException;
    
    Ticket editTicketProblem(long ticketId, int description) throws RecordNotFoundException, InvalidRequestException;
    
}
