package com.chickenfarms.chickenfarms.utils;

import com.chickenfarms.chickenfarms.enums.TicketStatus;
import com.chickenfarms.chickenfarms.model.Customer;
import com.chickenfarms.chickenfarms.model.DTO.CreateTicketDetailsDTO;
import com.chickenfarms.chickenfarms.model.entities.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class TestUtils {

    public static User getUser(long userId,String username,String typeName){
        return User.builder().userId(userId).userName(username).typeName(typeName).build();
    }
    
    public static Problem getProblem(int problemId,String problemName){
        return Problem.builder().problemId(problemId).problemName(problemName).build();
    }
    
    public static Customer getCustomer(long customerId,String username){
        return Customer.builder().customerId(customerId).customerUsername(username).build();
    }
    
    public static Ticket getTicket(long ticketId,int farmId){
        return Ticket.builder().ticketId(ticketId).farmId(farmId).status(TicketStatus.READY.getTicketStatus()).build();
    }
    
    public static RootCause getRootCause(int rootCauseId,String rootCauseName){
        Ticket ticket=getTicket(2,1);
        return RootCause.builder().rootCauseId(rootCauseId).rootCauseName(rootCauseName).tickets(new HashSet<Ticket>(Arrays.asList(ticket))).build();
    }
    
    public static CustomersInTicket getCustomerInTicket(long ticketId,long customerId){
        CustomerInTicketPKId customerInTicketPKId=new CustomerInTicketPKId(ticketId,customerId);
        Customer customer=getCustomer(customerId,"scohen");
        return CustomersInTicket.builder().pk(customerInTicketPKId).customer(customer).build();
    }
    
    public static CreateTicketDetailsDTO getCreateTicketDetailsDTO(List<Long> customersId){
        return new CreateTicketDetailsDTO("A user name issue",101,customersId,1,3);
    }
}
