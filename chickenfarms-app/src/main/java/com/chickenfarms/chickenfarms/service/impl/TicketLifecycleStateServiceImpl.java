package com.chickenfarms.chickenfarms.service.impl;

import com.chickenfarms.chickenfarms.enums.TicketStatus;
import com.chickenfarms.chickenfarms.exception.DBException;
import com.chickenfarms.chickenfarms.exception.InvalidRequestException;
import com.chickenfarms.chickenfarms.exception.RecordNotFoundException;
import com.chickenfarms.chickenfarms.model.Customer;
import com.chickenfarms.chickenfarms.model.CreatedTicketDetailsPayload;
import com.chickenfarms.chickenfarms.model.entity.*;
import com.chickenfarms.chickenfarms.repository.*;
import com.chickenfarms.chickenfarms.utils.DbValidationUtils;
import com.chickenfarms.chickenfarms.service.TicketLifecycleStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TicketLifecycleStateServiceImpl implements TicketLifecycleStateService {
    
    private TicketRepository ticketRepository;
    private CustomersInTicketRepository customersInTicketRepository;
    private RootCauseRepository rootCauseRepository;
    private ProblemRepository problemRepository;
    private UserRepository userRepository;
    private CustomerRepository customerRepository;

    
    @Autowired
    public TicketLifecycleStateServiceImpl(TicketRepository ticketRepository, CustomersInTicketRepository customersInTicketRepository, RootCauseRepository rootCauseRepository, ProblemRepository problemRepository,UserRepository userRepository,CustomerRepository customerRepository){
        this.ticketRepository=ticketRepository;
        this.customersInTicketRepository=customersInTicketRepository;
        this.rootCauseRepository=rootCauseRepository;
        this.userRepository=userRepository;
        this.customerRepository=customerRepository;
        this.problemRepository=problemRepository;
    }
    
    @Override
    public Ticket createNewTicket(CreatedTicketDetailsPayload createdTicketDetailsPayload) throws RecordNotFoundException, DBException {
        Problem problem = DbValidationUtils.getProblem(problemRepository, createdTicketDetailsPayload.getProblemId());
        User user = DbValidationUtils.getUser(userRepository, createdTicketDetailsPayload.getUserId());
        Ticket ticket=Ticket.builder().farmId(createdTicketDetailsPayload.getFarmId()).description(createdTicketDetailsPayload.getDescription()).problem(problem).user(user).status(TicketStatus.CREATED.getTicketStatus()).createdDate(new Date(System.currentTimeMillis())).lastUpdatedDate(new Date(System.currentTimeMillis())).isResolved(false).build();
        return saveCreatedTicketInDB(createdTicketDetailsPayload, ticket);
    }
    
    @Override
    public Ticket moveTicketToCloseStatus(long ticketId,boolean isResolved) throws RecordNotFoundException, InvalidRequestException {
        Ticket ticket= DbValidationUtils.getTicket(ticketRepository,ticketId);
        validTicketStatus(ticket,TicketStatus.READY);
        setCloseTicketValue(isResolved, ticket);
        return ticketRepository.save(ticket);
    }
    
    @Override
    //move akso the tags
    public Ticket moveTicketToStatusReady(long ticketId,String rootCauseName) throws RecordNotFoundException, InvalidRequestException {
        RootCause rootCause= rootCauseRepository.getByRootCauseName(rootCauseName.toLowerCase());
        Ticket requestTicket= DbValidationUtils.getTicket(ticketRepository,ticketId);
        validTicketStatus(requestTicket,TicketStatus.CREATED);
        if(rootCause==null){
            updateNewRootCause(rootCauseName, requestTicket);
            return requestTicket;
        }
        else{
            return updateExistRootCause(rootCause, requestTicket);
        }
    }
    
    
    
    private Ticket updateExistRootCause(RootCause rootCause, Ticket requestTicket) {
        Set<Ticket> ticketsInExistRootCause= rootCause.getTickets();
        Optional<Ticket> ticketInFarmAndRootCause=ticketsInExistRootCause.stream().filter(ticket->Objects.equals(ticket.getFarmId(), requestTicket.getFarmId()) && isTicketStatus(ticket, TicketStatus.READY)).findAny();
        if(!isFarmAndRootCauseExist(ticketInFarmAndRootCause)){
            updateNewRootCauseForFarm(rootCause, requestTicket, ticketsInExistRootCause);
            return requestTicket;
        }
        else {
            reconciledTicket(requestTicket, ticketInFarmAndRootCause.get());
            return ticketInFarmAndRootCause.get();
        }
    }
    
    private void updateNewRootCauseForFarm(RootCause rootCause, Ticket requestTicket, Set<Ticket> ticketsInExistRootCause) {
        updateTicketsInRootCause(rootCause, requestTicket, ticketsInExistRootCause);
        updateReadyTicketStatus(rootCause, requestTicket);
    }
    
    private void reconciledTicket(Ticket requestTicket, Ticket existTicket) {
        Set<CustomersInTicket> customersInTicketCreated=customersInTicketRepository.getAllByTicket(requestTicket.getTicketId());
        customersInTicketRepository.deleteAll(customersInTicketCreated);
        saveReconciledCustomers(existTicket, customersInTicketCreated);
        updateReconciledTicketStatus(requestTicket);
    }
    
    private void saveReconciledCustomers(Ticket existTicket, Set<CustomersInTicket> customersInTicketCreated) {
            customersInTicketCreated.stream().forEach(customersInTicket -> {
                customersInTicket.setTicket(existTicket);
                customersInTicket.setPk(new CustomerInTicketPKId(existTicket.getTicketId(),customersInTicket.getCustomer().getCustomerId()));
            });
            Set<CustomersInTicket> customersInTicketReady=customersInTicketRepository.getAllByTicket(existTicket.getTicketId());
            customersInTicketReady.addAll(customersInTicketCreated);
            customersInTicketRepository.saveAll(customersInTicketReady);
    }
    
    private void updateTicketsInRootCause(RootCause rootCause, Ticket requestTicket, Set<Ticket> ticketsInExistRootCause) {
        ticketsInExistRootCause.add(requestTicket);
        rootCause.setTickets(ticketsInExistRootCause);
        rootCauseRepository.save(rootCause);
    }
    
    private boolean isFarmAndRootCauseExist(Optional<Ticket> ticketInFarmAndRootCause) {
        return ticketInFarmAndRootCause.isPresent();
    }
    
    private void updateNewRootCause(String rootCauseName, Ticket ticket) {
        RootCause rootCause=RootCause.builder().rootCauseName(rootCauseName).tickets(new HashSet<>(Arrays.asList(ticket))).build();
        rootCauseRepository.save(rootCause);
        updateReadyTicketStatus(rootCause, ticket);
    }
    
    private void validTicketStatus(Ticket ticket,TicketStatus ticketStatus) throws InvalidRequestException {
        if(!isTicketStatus(ticket, ticketStatus)){
            throw new InvalidRequestException("The ticket is not in appropriate status.");
        }
    }
    
    private boolean isTicketStatus(Ticket ticket, TicketStatus status) {
        return ticket.getStatus().equals(status.getTicketStatus());
    }
    
    private void updateReconciledTicketStatus(Ticket ticket) {
        ticket.setLastUpdatedDate(new Date(System.currentTimeMillis()));
        ticket.setStatus(TicketStatus.RECONCILED.getTicketStatus());
        ticketRepository.save(ticket);
    }
    
    private void updateReadyTicketStatus(RootCause rootCause, Ticket ticket) {
        ticket.setStatus(TicketStatus.READY.getTicketStatus());
        ticket.setLastUpdatedDate(new Date(System.currentTimeMillis()));
        ticket.setSla(3);
        ticket.setRootCause(rootCause);
        ticketRepository.save(ticket);
    
    }
    
    //TODO: make it transactionsl
    ////    @Transactional
////    @Transactional(readOnly = false,isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
////    @Transactional(propagation=Propagation.REQUIRED,rollbackFor =Exception.class)
    private Ticket saveCreatedTicketInDB(CreatedTicketDetailsPayload createdTicketDetailsPayload, Ticket ticket) throws RecordNotFoundException, DBException {
        Ticket createdTicket=ticketRepository.save(ticket);
        List<CustomersInTicket> customersInTickets=saveCustomersInTicket(createdTicketDetailsPayload, ticket);
        DbValidationUtils.isInsertAllCustomersInTicket(createdTicketDetailsPayload.getCustomersId().size(),customersInTickets.size());
        return createdTicket;
    }
    
    private List<CustomersInTicket> saveCustomersInTicket(CreatedTicketDetailsPayload createdTicketDetailsPayload, Ticket ticket) throws RecordNotFoundException {
        List<CustomersInTicket> customersInTickets=new ArrayList<>();
        for (long customerId: createdTicketDetailsPayload.getCustomersId()) {
            Customer customer= DbValidationUtils.getCustomer(customerRepository,customerId);
            CustomerInTicketPKId customerInTicketPKId=CustomerInTicketPKId.builder().ticketId(ticket.getTicketId()).customerId(customerId).build();
            CustomersInTicket customerInTicket= CustomersInTicket.builder().pk(customerInTicketPKId).addedDate(new Date(System.currentTimeMillis())).customer(customer).ticket(ticket).build();
            customersInTickets.add(customersInTicketRepository.save(customerInTicket));
        }
        return customersInTickets;
    }
    
    private void setCloseTicketValue(boolean isResolved, Ticket ticket) {
        ticket.setStatus(TicketStatus.CLOSED.getTicketStatus());
        ticket.setResolved(isResolved);
        ticket.setLastUpdatedDate(new Date(System.currentTimeMillis()));
    }

}
