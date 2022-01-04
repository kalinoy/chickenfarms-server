package com.chickenfarms.chickenfarms.service.impl;

import com.chickenfarms.chickenfarms.enums.TicketStatus;
import com.chickenfarms.chickenfarms.exception.InvalidRequestException;
import com.chickenfarms.chickenfarms.exception.RecordNotFoundException;
import com.chickenfarms.chickenfarms.model.entities.Problem;
import com.chickenfarms.chickenfarms.model.entities.RootCause;
import com.chickenfarms.chickenfarms.model.entities.Tag;
import com.chickenfarms.chickenfarms.model.entities.Ticket;
import com.chickenfarms.chickenfarms.repository.TagRepository;
import com.chickenfarms.chickenfarms.repository.TicketRepository;
import com.chickenfarms.chickenfarms.service.DBValidation;
import com.chickenfarms.chickenfarms.service.TicketViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.PageLimitUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TicketViewServiceImp implements TicketViewService {

    TicketRepository ticketRepository;
    DBValidation dbValidation;
    
    
    @Autowired
    public TicketViewServiceImp(DBValidation dbValidation,TicketRepository ticketRepository){
        this.ticketRepository=ticketRepository;
        this.dbValidation=dbValidation;
    }
    
    @Override
    public Set<Ticket> getTicketsByStatus( String status, int pageNumber) throws InvalidRequestException {
        if(!isValidStatus(status)){
            throw new InvalidRequestException("The user status doesn't exist.");
        }
        int startIndex= PageLimitUtils.getStartPageLimit(pageNumber);
        int endIndex=PageLimitUtils.getEndPageLimit(pageNumber);
        return ticketRepository.getTicketsByStatus(status,startIndex,endIndex);
    }
    
    @Override
    public Set<Ticket> getTicketsByTag(String tagName, int pageNumber) throws RecordNotFoundException {
        Tag tag=dbValidation.getTag(tagName);
        Set<Ticket> ticketsInTag=tag.getTickets();
        return getReturnedTicketsToUser(pageNumber, ticketsInTag);
    }
    
    @Override
    public Set<Ticket> getTicketsByFarm(int farmId, int pageNumber) {
        int startIndex= PageLimitUtils.getStartPageLimit(pageNumber);
        int endIndex=PageLimitUtils.getEndPageLimit(pageNumber);
        return ticketRepository.getTicketsByFarmId(farmId,startIndex,endIndex);
    }
    
    @Override
    public Set<Ticket> getTicketsByProblem(int problemId, int pageNumber) throws RecordNotFoundException {
        Problem problem=dbValidation.getProblem(problemId);
        Set<Ticket> ticketsInProblem=problem.getTickets();
        return getReturnedTicketsToUser(pageNumber,ticketsInProblem);
    }
    
    @Override
    public Set<Ticket> getTicketsByRootCause(String rootCauseName, int pageNumber) throws RecordNotFoundException {
        RootCause rootCause=dbValidation.getRootCause(rootCauseName);
        Set<Ticket> ticketsInRootCause=rootCause.getTickets();
        return getReturnedTicketsToUser(pageNumber,ticketsInRootCause);
    }
    
    private Set<Ticket> getReturnedTicketsToUser(int pageNumber, Set<Ticket> ticketsInTag) {
        Comparator<Ticket> comparator = getTicketComparator();
        int startIndex= PageLimitUtils.getStartPageLimit(pageNumber);
        int endIndex=PageLimitUtils.getEndPageLimit(pageNumber);
        return ticketsInTag.stream().sorted(comparator).skip(startIndex).limit(endIndex).collect(Collectors.toCollection(LinkedHashSet::new));
    }
    
    private Comparator<Ticket> getTicketComparator() {
        Comparator<Ticket> comparator=(c1,c2) ->{
            return Long.valueOf(c2.getCreatedDate().getTime()).compareTo(c1.getCreatedDate().getTime());
        };
        return comparator;
    }
    
    private boolean isValidStatus(String status){ 
        Optional<TicketStatus> ticketStatus=Arrays.stream(TicketStatus.values()).filter(ticketStatusValue -> ticketStatusValue.getTicketStatus().equals(status)).findAny();
        return ticketStatus.isPresent();
    }
}
