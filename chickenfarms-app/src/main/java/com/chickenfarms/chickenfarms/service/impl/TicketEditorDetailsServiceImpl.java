package com.chickenfarms.chickenfarms.service.impl;

import com.chickenfarms.chickenfarms.exception.InvalidRequestException;
import com.chickenfarms.chickenfarms.exception.RecordNotFoundException;
import com.chickenfarms.chickenfarms.model.entities.Problem;
import com.chickenfarms.chickenfarms.model.entities.Ticket;
import com.chickenfarms.chickenfarms.repository.ProblemRepository;
import com.chickenfarms.chickenfarms.repository.TicketRepository;
import com.chickenfarms.chickenfarms.service.TicketEditorDetailsService;
import com.chickenfarms.chickenfarms.utils.DbValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TicketEditorDetailsServiceImpl implements TicketEditorDetailsService {
    
    private TicketRepository ticketRepository;
    private ProblemRepository problemRepository;
    
    @Autowired
    public TicketEditorDetailsServiceImpl(TicketRepository ticketRepository,ProblemRepository problemRepository){
        this.ticketRepository=ticketRepository;
        this.problemRepository=problemRepository;
    }
    
    @Override
    public Ticket editTicketDescription(long ticketId, String description) throws RecordNotFoundException {
        Ticket ticket= DbValidationUtils.getTicket(ticketRepository,ticketId);
        ticket.setDescription(description);
        ticket.setLastUpdatedDate(new Date(System.currentTimeMillis()));
        return ticketRepository.save(ticket);
    }
    
    @Override
    public Ticket editTicketProblem(long ticketId, int problemId) throws RecordNotFoundException, InvalidRequestException {
        Ticket ticket= DbValidationUtils.getTicket(ticketRepository,ticketId);
        if(isSameProblemId(ticket,problemId)){
            throw new InvalidRequestException("The user problem exist already defined");
        }
        Problem problem= DbValidationUtils.getProblem(problemRepository,problemId);
        ticket.setProblem(problem);
        ticket.setLastUpdatedDate(new Date(System.currentTimeMillis()));
        return ticketRepository.save(ticket);
    }
    
    private boolean isSameProblemId(Ticket ticket,int userProblemId){
        return ticket.getProblem().getProblemId()==userProblemId;
    }

}
