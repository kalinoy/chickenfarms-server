package com.chickenfarms.chickenfarms.service;

import com.chickenfarms.chickenfarms.exception.DBException;
import com.chickenfarms.chickenfarms.exception.InvalidRequestException;
import com.chickenfarms.chickenfarms.exception.RecordNotFoundException;
import com.chickenfarms.chickenfarms.model.DTO.CreateTicketDetailsDTO;
import com.chickenfarms.chickenfarms.model.entities.Ticket;

public interface TicketLifecycleStateService {

    Ticket createNewTicket(CreateTicketDetailsDTO createTicketDetailsDTO) throws RecordNotFoundException, DBException;
    
    Ticket moveTicketToCloseStatus(long ticketId,boolean isResolved) throws RecordNotFoundException, InvalidRequestException;
    
    Ticket moveTicketToStatusReady(long ticketId,String rootCause) throws RecordNotFoundException, InvalidRequestException;
    

}
