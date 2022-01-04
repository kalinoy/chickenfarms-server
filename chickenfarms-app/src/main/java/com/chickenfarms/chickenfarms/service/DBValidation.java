package com.chickenfarms.chickenfarms.service;

import com.chickenfarms.chickenfarms.exception.DBException;
import com.chickenfarms.chickenfarms.exception.RecordNotFoundException;
import com.chickenfarms.chickenfarms.model.Customer;
import com.chickenfarms.chickenfarms.model.DTO.CreateTicketDetailsDTO;
import com.chickenfarms.chickenfarms.model.entities.*;
import com.chickenfarms.chickenfarms.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DBValidation {
    
    private TicketRepository ticketRepository;
    private ProblemRepository problemRepository;
    private UserRepository userRepository;
    private CustomerRepository customerRepository;
    private TagRepository tagRepository;
    private RootCauseRepository rootCauseRepository;
    @Autowired
    public DBValidation(TicketRepository ticketRepository, ProblemRepository problemRepository, UserRepository userRepository, CustomerRepository customerRepository,TagRepository tagRepository,RootCauseRepository rootCauseRepository){
        this.ticketRepository=ticketRepository;
        this.problemRepository=problemRepository;
        this.userRepository=userRepository;
        this.customerRepository=customerRepository;
        this.tagRepository=tagRepository;
        this.rootCauseRepository=rootCauseRepository;
    }
    //Maybe make the class util?
    
    public User getUser(long userId) throws RecordNotFoundException {
        return userRepository.findById(userId).orElseThrow(() ->new RecordNotFoundException("User id doesn't exist in User table."));
//        Optional<User> user=userRepository.findById(userId);
//        if(user.isEmpty()){
//            throw new RecordNotFoundException("User id doesn't exist in User table.");
//        }
//        return user.get();
    }
    
    public Problem getProblem(int problemId) throws RecordNotFoundException {
        return problemRepository.findById(problemId).orElseThrow(() ->new RecordNotFoundException("Problem id doesn't exist in Problem table."));
//        Optional<Problem> problem=problemRepository.findById(problemId);
//        if(problem.isEmpty()){
//            throw new RecordNotFoundException("Problem id doesn't exist in Problem table.");
//        }
//        return problem.get();
    }
    
    public Customer getCustomer(long customerId) throws RecordNotFoundException {
        return customerRepository.findById(customerId).orElseThrow(() ->new RecordNotFoundException("Customer id doesn't exist in Customer table."));
//        Optional<Customer> customer=customerRepository.findById(customerId);
//        if(customer.isEmpty()){
//            throw new RecordNotFoundException("Customer id doesn't exist in Customer table.");
//        }
//        return customer.get();
    }
    
    public Ticket getTicket(long ticketId) throws RecordNotFoundException {
        return ticketRepository.findById(ticketId).orElseThrow(() ->new RecordNotFoundException("Ticket id doesn't exist in Ticket table."));
//        Optional<Ticket> ticket=ticketRepository.findById(ticketId);
//        if(ticket.isEmpty()){
//            throw new RecordNotFoundException("Ticket id doesn't exist in Ticket table.");
//        }
//        return ticket.get();
    
    }
    
    public Tag getTag(String tagName) throws RecordNotFoundException {
        return tagRepository.findById(tagName).orElseThrow(()->new RecordNotFoundException("Tag doesn't exist in Tag table."));
    }
    
    public RootCause getRootCause(String rootCause) throws RecordNotFoundException {
        return rootCauseRepository.findByRootCauseName(rootCause).orElseThrow(()->new RecordNotFoundException("Root cause doesn't exist in root cause table"));
    }
    
    public boolean isInsertAllCustomersInTicket(int createTicketDetailsCustomersSize, int customersInTicketsSize) throws DBException {
        if(createTicketDetailsCustomersSize!=customersInTicketsSize){
            throw new DBException("Failed to insert all customers in ticket to DB.");
        }
        return true;
    }
}
