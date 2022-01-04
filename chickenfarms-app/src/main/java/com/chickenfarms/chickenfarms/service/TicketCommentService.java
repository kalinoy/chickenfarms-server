package com.chickenfarms.chickenfarms.service;

import com.chickenfarms.chickenfarms.exception.InvalidRequestException;
import com.chickenfarms.chickenfarms.exception.RecordNotFoundException;
import com.chickenfarms.chickenfarms.model.entities.Comment;

import java.util.List;

public interface TicketCommentService {
    
    Comment addCommentToTicket(long ticketId,long userId,String textMessage) throws RecordNotFoundException, InvalidRequestException;
    
    List<Comment> getTicketComments(long ticketId,int pageNumber) throws RecordNotFoundException, InvalidRequestException;
    
}
