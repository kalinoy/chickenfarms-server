package com.chickenfarms.chickenfarms.service.impl;

import com.chickenfarms.chickenfarms.exception.InvalidRequestException;
import com.chickenfarms.chickenfarms.exception.RecordNotFoundException;
import com.chickenfarms.chickenfarms.model.entity.Comment;
import com.chickenfarms.chickenfarms.model.entity.Ticket;
import com.chickenfarms.chickenfarms.model.entity.User;
import com.chickenfarms.chickenfarms.repository.CommentRepository;
import com.chickenfarms.chickenfarms.repository.TicketRepository;
import com.chickenfarms.chickenfarms.repository.UserRepository;
import com.chickenfarms.chickenfarms.utils.DbValidationUtils;
import com.chickenfarms.chickenfarms.service.TicketCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chickenfarms.chickenfarms.utils.PageLimitUtils;

import java.util.*;

@Service
public class TicketCommentServiceImp implements TicketCommentService {
    
    
    private CommentRepository commentRepository;
    private TicketRepository ticketRepository;
    private UserRepository userRepository;
    
    
    @Autowired
    public TicketCommentServiceImp(TicketRepository ticketRepository,CommentRepository commentRepository,UserRepository userRepository){
        this.ticketRepository=ticketRepository;
        this.commentRepository=commentRepository;
        this.userRepository=userRepository;
    }
    
    @Override
    public Comment addCommentToTicket(long ticketId,long userId, String textMessage) throws RecordNotFoundException, InvalidRequestException {
        Ticket ticket= DbValidationUtils.getTicket(ticketRepository,ticketId);
        User user= DbValidationUtils.getUser(userRepository,userId);
        Comment comment=Comment.builder().ticket(ticket).user(user).commentText(textMessage).createdDate(new Date(System.currentTimeMillis())).build();
        return commentRepository.save(comment);
    }
    
    @Override
    public List<Comment> getTicketComments(long ticketId, int pageNumber) throws RecordNotFoundException, InvalidRequestException {
        Ticket ticket= DbValidationUtils.getTicket(ticketRepository,ticketId);
        int startIndex= PageLimitUtils.getStartPageLimit(pageNumber);
        int endIndex=PageLimitUtils.getEndPageLimit(pageNumber);
        return commentRepository.getSortedCommentsByPage(ticket.getTicketId(),startIndex,endIndex);
    }
    
}
