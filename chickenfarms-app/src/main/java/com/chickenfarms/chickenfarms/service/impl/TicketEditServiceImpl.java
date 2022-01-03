package com.chickenfarms.chickenfarms.service.impl;

import com.chickenfarms.chickenfarms.exception.InnerServiceException;
import com.chickenfarms.chickenfarms.exception.RecordNotFoundException;
import com.chickenfarms.chickenfarms.model.entities.Tag;
import com.chickenfarms.chickenfarms.model.entities.Ticket;
import com.chickenfarms.chickenfarms.repository.TagRepository;
import com.chickenfarms.chickenfarms.service.DBValidation;
import com.chickenfarms.chickenfarms.service.TicketEditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TicketEditServiceImpl implements TicketEditService {
    
    private TagRepository tagRepository;
    private DBValidation dbValidation;
    
    
    @Autowired
    public TicketEditServiceImpl(TagRepository tagRepository,DBValidation dbValidation){
        this.tagRepository = tagRepository;
        this.dbValidation=dbValidation;
    }
    
    @Override
    public List<Tag> addTags(long ticketId, List<String> tags) throws RecordNotFoundException, InnerServiceException {
        List<Tag> savedTagsInDb=new ArrayList<>();
        Ticket ticket= dbValidation.getTicket(ticketId);
        for (String tagName: tags) {
            tagName = tagName.toLowerCase();
            Optional<Tag> dbTag = tagRepository.findById(tagName);
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
