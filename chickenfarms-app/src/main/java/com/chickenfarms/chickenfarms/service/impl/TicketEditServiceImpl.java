package com.chickenfarms.chickenfarms.service.impl;

import com.chickenfarms.chickenfarms.exception.InnerServiceException;
import com.chickenfarms.chickenfarms.exception.InvalidRequestException;
import com.chickenfarms.chickenfarms.exception.RecordNotFoundException;
import com.chickenfarms.chickenfarms.model.entities.Problem;
import com.chickenfarms.chickenfarms.model.entities.Tag;
import com.chickenfarms.chickenfarms.model.entities.Ticket;
import com.chickenfarms.chickenfarms.repository.TagRepository;
import com.chickenfarms.chickenfarms.repository.TicketRepository;
import com.chickenfarms.chickenfarms.service.DBValidation;
import com.chickenfarms.chickenfarms.service.TicketEditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TicketEditServiceImpl implements TicketEditService {
    
    private TagRepository tagRepository;
    private TicketRepository ticketRepository;
    private DBValidation dbValidation;
    
    
    @Autowired
    public TicketEditServiceImpl(TagRepository tagRepository,DBValidation dbValidation,TicketRepository ticketRepository){
        this.tagRepository = tagRepository;
        this.dbValidation=dbValidation;
        this.ticketRepository=ticketRepository;
    }
    
    //Create tag service?
    @Override
    public List<Tag> addTags(long ticketId, List<String> tags) throws RecordNotFoundException, InnerServiceException {
        List<Tag> savedTagsInDb=new ArrayList<>();
        Ticket ticket= dbValidation.getTicket(ticketId);
        for (String tagName: tags) {
            tagName = tagName.toLowerCase();
            Optional<Tag> dbTag = tagRepository.findById(tagName);
            setTicketUpdatedDate(ticket);
            Tag tag=null;
            if (dbTag.isEmpty()) {
                tag= getNewTag(ticket, tagName);
            } else {
                tag=getUpdatedTag(ticket, dbTag.get());
            }
            checkValidTag(tag);
            tagRepository.save(tag);
            savedTagsInDb.add(tag);
        }
        return savedTagsInDb;
    }
    

    
    @Override
    public Ticket editTicketDescription(long ticketId, String description) throws RecordNotFoundException {
        Ticket ticket=dbValidation.getTicket(ticketId);
        ticket.setDecription(description);
        ticket.setLastUpdatedDate(new Date(System.currentTimeMillis()));
        return ticketRepository.save(ticket);
    }
    
    @Override
    public Ticket editTicketProblem(long ticketId, int problemId) throws RecordNotFoundException, InvalidRequestException {
        Ticket ticket=dbValidation.getTicket(ticketId);
        if(isSameProblemId(ticket,problemId)){
            throw new InvalidRequestException("The user problem exist already defined");
        }
        Problem problem=dbValidation.getProblem(problemId);
        ticket.setProblem(problem);
        ticket.setLastUpdatedDate(new Date(System.currentTimeMillis()));
        return ticketRepository.save(ticket);
    }
    
    private void setTicketUpdatedDate(Ticket ticket) {
        ticket.setLastUpdatedDate(new Date(System.currentTimeMillis()));
        ticketRepository.save(ticket);
    }
    
    private boolean isSameProblemId(Ticket ticket,int userProblemId){
        return ticket.getProblem().getProblemId()==userProblemId;
    }
    
    private void checkValidTag(Tag tag) throws InnerServiceException {
        if(tag ==null){
            throw new InnerServiceException("Error while creating or updating tag.");
        }
    }
    
    private Tag getUpdatedTag(Ticket ticket, Tag dbTag) {
        Tag updatedTag=dbTag;
        Set<Ticket> tickets= updatedTag.getTickets();
        tickets.add(ticket);
        return updatedTag;
    
    }
    
    private Tag getNewTag(Ticket ticket, String tagName) {
        Set<Ticket> tickets = Stream.of(ticket).collect(Collectors.toSet());
        return Tag.builder().tagName(tagName).tickets(tickets).build();
    
    }
}
