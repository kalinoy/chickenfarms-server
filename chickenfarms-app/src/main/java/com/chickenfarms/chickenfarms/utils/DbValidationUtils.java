package com.chickenfarms.chickenfarms.utils;

import com.chickenfarms.chickenfarms.exception.DBException;
import com.chickenfarms.chickenfarms.exception.RecordNotFoundException;
import com.chickenfarms.chickenfarms.model.Customer;
import com.chickenfarms.chickenfarms.model.entities.*;
import com.chickenfarms.chickenfarms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DbValidationUtils {
    
    public static User getUser(UserRepository userRepository, long userId) throws RecordNotFoundException {
        return userRepository.findById(userId).orElseThrow(() ->new RecordNotFoundException("User id doesn't exist in User table."));
    }
    
    public static Problem getProblem(ProblemRepository problemRepository, int problemId) throws RecordNotFoundException {
        return problemRepository.findById(problemId).orElseThrow(() ->new RecordNotFoundException("Problem id doesn't exist in Problem table."));
    }
    
    public static Customer getCustomer(CustomerRepository customerRepository, long customerId) throws RecordNotFoundException {
        return customerRepository.findById(customerId).orElseThrow(() ->new RecordNotFoundException("Customer id doesn't exist in Customer table."));
    }
    
    public static Ticket getTicket(TicketRepository ticketRepository, long ticketId) throws RecordNotFoundException {
        return ticketRepository.findById(ticketId).orElseThrow(() ->new RecordNotFoundException("Ticket id doesn't exist in Ticket table."));
    }
    
    public static Tag getTag(TagRepository tagRepository, String tagName) throws RecordNotFoundException {
        return tagRepository.findById(tagName).orElseThrow(()->new RecordNotFoundException("Tag doesn't exist in Tag table."));
    }
    
    public static RootCause getRootCause(RootCauseRepository rootCauseRepository, String rootCause) throws RecordNotFoundException {
        return rootCauseRepository.findByRootCauseName(rootCause).orElseThrow(()->new RecordNotFoundException("Root cause doesn't exist in root cause table"));
    }
    
    public static boolean isInsertAllCustomersInTicket(int createTicketDetailsCustomersSize, int customersInTicketsSize) throws DBException {
        if(createTicketDetailsCustomersSize!=customersInTicketsSize){
            throw new DBException("Failed to insert all customers in ticket to DB.");
        }
        return true;
    }
}
