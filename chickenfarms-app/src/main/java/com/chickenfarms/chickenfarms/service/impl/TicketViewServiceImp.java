package com.chickenfarms.chickenfarms.service.impl;

import com.chickenfarms.chickenfarms.enums.TicketStatus;
import com.chickenfarms.chickenfarms.exception.InvalidRequestException;
import com.chickenfarms.chickenfarms.exception.RecordNotFoundException;
import com.chickenfarms.chickenfarms.model.TicketBusinessDetails;
import com.chickenfarms.chickenfarms.model.entities.Problem;
import com.chickenfarms.chickenfarms.model.entities.RootCause;
import com.chickenfarms.chickenfarms.model.entities.Tag;
import com.chickenfarms.chickenfarms.model.entities.Ticket;
import com.chickenfarms.chickenfarms.repository.ProblemRepository;
import com.chickenfarms.chickenfarms.repository.RootCauseRepository;
import com.chickenfarms.chickenfarms.repository.TagRepository;
import com.chickenfarms.chickenfarms.repository.TicketRepository;
import com.chickenfarms.chickenfarms.utils.DbValidationUtils;
import com.chickenfarms.chickenfarms.service.TicketViewService;
import com.chickenfarms.chickenfarms.utils.BusinessDetailsConverterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chickenfarms.chickenfarms.utils.PageLimitUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TicketViewServiceImp implements TicketViewService {

    TicketRepository ticketRepository;
    TagRepository tagRepository;
    ProblemRepository problemRepository;
    RootCauseRepository rootCauseRepository;
    
    @Autowired
    public TicketViewServiceImp(TicketRepository ticketRepository,TagRepository tagRepository,ProblemRepository problemRepository,RootCauseRepository rootCauseRepository){
        this.ticketRepository=ticketRepository;
        this.tagRepository=tagRepository;
        this.problemRepository=problemRepository;
        this.rootCauseRepository=rootCauseRepository;
    }
    
    @Override
    public Set<Ticket> getTicketsByPage(int pageNumber) {
        int startIndex= PageLimitUtils.getStartPageLimit(pageNumber);
        int endIndex=PageLimitUtils.getEndPageLimit(pageNumber);
        return ticketRepository.getTicketsByPage(startIndex,endIndex);
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
        Tag tag= DbValidationUtils.getTag(tagRepository,tagName);
        Set<Ticket> ticketsInTag=tag.getTickets();
        return getSortedTicketsInPage(pageNumber, ticketsInTag);
    }
    
    @Override
    public Set<Ticket> getTicketsByFarm(int farmId, int pageNumber) {
        int startIndex= PageLimitUtils.getStartPageLimit(pageNumber);
        int endIndex=PageLimitUtils.getEndPageLimit(pageNumber);
        return ticketRepository.getTicketsByFarmId(farmId,startIndex,endIndex);
    }
    
    @Override
    public Set<Ticket> getTicketsByProblem(int problemId, int pageNumber) throws RecordNotFoundException {
        Problem problem= DbValidationUtils.getProblem(problemRepository,problemId);
        Set<Ticket> ticketsInProblem=problem.getTickets();
        return getSortedTicketsInPage(pageNumber,ticketsInProblem);
    }
    
    @Override
    public Set<Ticket> getTicketsByRootCause(String rootCauseName, int pageNumber) throws RecordNotFoundException {
        RootCause rootCause= DbValidationUtils.getRootCause(rootCauseRepository,rootCauseName);
        Set<Ticket> ticketsInRootCause=rootCause.getTickets();
        return getSortedTicketsInPage(pageNumber,ticketsInRootCause);
    }
    
    private Set<Ticket> getSortedTicketsInPage(int pageNumber, Set<Ticket> ticketsInTag) {
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
    
//    private Set<TicketBusinessDetails> getTicketsBusinessList(Set<Ticket> ticketsByPage) {
//        Set<TicketBusinessDetails> ticketsBusinessDetails=new HashSet<>();
//        ticketsByPage.forEach(ticket -> {
//            List<String> ticketTagsName=ticket.getTags().stream().map(Tag::getTagName).collect(Collectors.toList());
//            TicketBusinessDetails ticketBusinessDetails=TicketBusinessDetails.builder().ticketId(ticket.getTicketId()).description(ticket.getDescription()).farmId(ticket.getFarmId()).status(ticket.getStatus()).tagsName(ticketTagsName).problemId(ticket.getProblem().getProblemId()).userName(ticket.getUser().getUserName()).build();
//            setAdditionalTicketBusinessDetails(ticket, ticketBusinessDetails);
//            ticketsBusinessDetails.add(ticketBusinessDetails);
//        });
//        return ticketsBusinessDetails;
//    }
//    
//    private void setAdditionalTicketBusinessDetails(Ticket ticket, TicketBusinessDetails ticketBusinessDetails) {
//        if(isClosedOrReadyStatus(ticket)){
//            ticketBusinessDetails.setSla(ticket.getSla());
//            ticketBusinessDetails.setGrade(ticket.getGrade());
//            ticketBusinessDetails.setRootCauseName(ticket.getRootCause().getRootCauseName());
//        }
//        if(isStatus(ticket, TicketStatus.CLOSED)){
//            ticketBusinessDetails.setResolved(ticket.isResolved());
//        }
//    }
//    
//    private boolean isClosedOrReadyStatus(Ticket ticket) {
//        return isStatus(ticket, TicketStatus.CLOSED) || isStatus(ticket, TicketStatus.READY);
//    }
//    
//    private boolean isStatus(Ticket ticket, TicketStatus ticketStatus) {
//        return ticket.getStatus().equals(ticketStatus.getTicketStatus());
//    }
}
