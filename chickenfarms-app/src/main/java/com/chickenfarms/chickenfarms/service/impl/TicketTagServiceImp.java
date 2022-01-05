package com.chickenfarms.chickenfarms.service.impl;

import com.chickenfarms.chickenfarms.exception.InnerServiceException;
import com.chickenfarms.chickenfarms.exception.RecordNotFoundException;
import com.chickenfarms.chickenfarms.model.entities.Tag;
import com.chickenfarms.chickenfarms.model.entities.Ticket;
import com.chickenfarms.chickenfarms.repository.TagRepository;
import com.chickenfarms.chickenfarms.repository.TicketRepository;
import com.chickenfarms.chickenfarms.utils.DbValidationUtils;
import com.chickenfarms.chickenfarms.service.TicketTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class TicketTagServiceImp implements TicketTagService {

    private TagRepository tagRepository;
    private TicketRepository ticketRepository;
    
    @Autowired
    public TicketTagServiceImp( TagRepository tagRepository,TicketRepository ticketRepository){
        this.tagRepository=tagRepository;
        this.ticketRepository=ticketRepository;
    }
    
    @Override
    public List<Tag> addTags(long ticketId, List<String> tags) throws RecordNotFoundException, InnerServiceException {
        List<Tag> savedTagsInDb=new ArrayList<>();
        Ticket ticket= DbValidationUtils.getTicket(ticketRepository,ticketId);
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
    
    private void setTicketUpdatedDate(Ticket ticket) {
        ticket.setLastUpdatedDate(new Date(System.currentTimeMillis()));
        ticketRepository.save(ticket);
    }
    
    private void checkValidTag(Tag tag) throws InnerServiceException {
        if(tag ==null){
            throw new InnerServiceException("Error while creating or updating tag.");
        }
    }
}
