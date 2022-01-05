package com.chickenfarms.chickenfarms.service;

import com.chickenfarms.chickenfarms.exception.InvalidRequestException;
import com.chickenfarms.chickenfarms.exception.RecordNotFoundException;
import com.chickenfarms.chickenfarms.model.TicketBusinessDetails;
import com.chickenfarms.chickenfarms.model.entities.Ticket;

import java.util.Set;

public interface TicketViewService {
    
    Set<TicketBusinessDetails> getTicketsByPage(int pageNumber);

    Set<Ticket> getTicketsByStatus(String status,int pageNumber) throws InvalidRequestException;
    
    Set<Ticket> getTicketsByTag(String tagName, int pageNumber) throws RecordNotFoundException;
    
    Set<Ticket> getTicketsByFarm(int farmId,int pageNumber);
    
    Set<Ticket> getTicketsByProblem(int problemId,int pageNumber) throws RecordNotFoundException;
    
    Set<Ticket> getTicketsByRootCause(String rootCause,int pageNumber) throws RecordNotFoundException;
    
    
    
}
