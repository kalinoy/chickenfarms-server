package com.chickenfarms.chickenfarms.utils;

import com.chickenfarms.chickenfarms.enums.TicketStatus;
import com.chickenfarms.chickenfarms.exception.RecordNotFoundException;
import com.chickenfarms.chickenfarms.model.Customer;
import com.chickenfarms.chickenfarms.model.DTO.CreateTicketDetailsDTO;
import com.chickenfarms.chickenfarms.model.entities.*;
import com.chickenfarms.chickenfarms.service.DBValidation;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import static org.mockito.Mockito.when;

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
    
    public static Ticket  getTicket(long ticketId, int farmId){
        return Ticket.builder().ticketId(ticketId).farmId(farmId).status(TicketStatus.READY.getTicketStatus()).createdDate(new Date(System.currentTimeMillis())).build();
    }
    
    public static RootCause getRootCause(int rootCauseId,String rootCauseName){
        Ticket ticket= getTicket(2,1);
        return RootCause.builder().rootCauseId(rootCauseId).rootCauseName(rootCauseName).tickets(new HashSet<Ticket>(Arrays.asList(ticket))).build();
    }
    
    public static Tag getTag(String tagName,long ticketId){
        Ticket ticket=getTicket(ticketId,2);
        return Tag.builder().tagName(tagName).tickets(new HashSet<>(Arrays.asList(ticket))).build();
    }
    
    public static Comment getComment(Ticket ticket){
        return Comment.builder().commentId(0).commentText("Test comment").ticket(ticket).build();
    }
    
    public static CustomersInTicket getCustomerInTicket(long ticketId,long customerId){
        CustomerInTicketPKId customerInTicketPKId=new CustomerInTicketPKId(ticketId,customerId);
        Customer customer=getCustomer(customerId,"scohen");
        return CustomersInTicket.builder().pk(customerInTicketPKId).customer(customer).build();
    }
    
    public static Ticket getMockedTicket(CreateTicketDetailsDTO createTicketDetailsDTO, TicketStatus ticketStatus, DBValidation dbValidation) throws RecordNotFoundException {
        Ticket ticket = getTicketFromCreatedTicketDTO(createTicketDetailsDTO, ticketStatus);
        when(dbValidation.getTicket(Mockito.anyLong())).thenReturn(ticket);
        return ticket;
    }
    
    public static Ticket getTicketFromCreatedTicketDTO(CreateTicketDetailsDTO createTicketDetailsDTO, TicketStatus ticketStatus) {
        Problem problemInDB = TestUtils.getProblem(101, "General issue");
        User userInDB = TestUtils.getUser(1L, "lkalachman", "Enginner");
        return Ticket.builder().farmId(createTicketDetailsDTO.getFarmId()).decription(createTicketDetailsDTO.getDescription()).problem(problemInDB).user(userInDB).status(ticketStatus.getTicketStatus()).createdDate(new Date(System.currentTimeMillis())).lastUpdatedDate(new Date(System.currentTimeMillis())).isResolved(false).build();
    }
    
//    public static CreateTicketDetailsDTO getCreateTicketDetailsDTO(List<Long> customersId){
//        return new CreateTicketDetailsDTO("A user name issue",101,customersId,1,3);
//    }
}
