package com.chickenfarms.chickenfarms.service.impl;

import com.chickenfarms.chickenfarms.exception.InvalidRequestException;
import com.chickenfarms.chickenfarms.exception.RecordNotFoundException;
import com.chickenfarms.chickenfarms.model.entities.Comment;
import com.chickenfarms.chickenfarms.model.entities.Ticket;
import com.chickenfarms.chickenfarms.model.entities.User;
import com.chickenfarms.chickenfarms.repository.CommentRepository;
import com.chickenfarms.chickenfarms.service.DBValidation;
import com.chickenfarms.chickenfarms.service.TicketCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.PageLimitUtils;

import java.util.Date;
import java.util.List;

@Service
public class TicketCommentServiceImp implements TicketCommentService {
    
    private DBValidation dbValidation;
    
    private CommentRepository commentRepository;
    
    @Autowired
    public TicketCommentServiceImp(DBValidation dbValidation,CommentRepository commentRepository){
        this.dbValidation=dbValidation;
        this.commentRepository=commentRepository;
    }
    
    @Override
    public Comment addCommentToTicket(long ticketId,long userId, String textMessage) throws RecordNotFoundException, InvalidRequestException {
        Ticket ticket=dbValidation.getTicket(ticketId);
        User user=dbValidation.getUser(userId);
        Comment comment=Comment.builder().ticket(ticket).user(user).commentText(textMessage).createdDate(new Date(System.currentTimeMillis())).build();
        return commentRepository.save(comment);
    }
    
    @Override
    public List<Comment> getTicketComments(long ticketId,int pageNumber) throws RecordNotFoundException, InvalidRequestException {
        Ticket ticket=dbValidation.getTicket(ticketId);
        int startIndex= PageLimitUtils.getStartPageLimit(pageNumber);
        int endIndex=PageLimitUtils.getEndPageLimit(pageNumber);
        return commentRepository.getSortedCommentsByPage(ticket.getTicketId(),startIndex,endIndex);
    }
    
    
}
