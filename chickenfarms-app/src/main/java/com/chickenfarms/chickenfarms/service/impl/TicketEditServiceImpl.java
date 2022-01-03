package com.chickenfarms.chickenfarms.service.impl;

import com.chickenfarms.chickenfarms.exception.RecordNotFoundException;
import com.chickenfarms.chickenfarms.model.DTO.TicketTagsRequestDTO;
import com.chickenfarms.chickenfarms.model.entities.Tag;
import com.chickenfarms.chickenfarms.model.entities.Ticket;
import com.chickenfarms.chickenfarms.repository.TagRepository;
import com.chickenfarms.chickenfarms.service.DBValidation;
import com.chickenfarms.chickenfarms.service.TicketEditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public boolean addTags(TicketTagsRequestDTO ticketTagsRequestDTO) throws RecordNotFoundException {
        Ticket ticket= dbValidation.getTicket(ticketTagsRequestDTO.getTicketId());
        for (String tagName: ticketTagsRequestDTO.getTags()) {
            tagName=tagName.toLowerCase();
            Tag tag;
            if(tagRepository.findById(tagName).isEmpty()){
                Set<Ticket> tickets = Stream.of(ticket).collect(Collectors.toSet());
                tag= Tag.builder().tagName(tagName).tickets(tickets).build();
            }
            else{
                tag=tagRepository.getById(tagName);
                Set<Ticket> tickets=tag.getTickets();
                tickets.add(ticket);
            }
            tagRepository.save(tag);
            return true;
        }
        return false;
    }
}
