package com.chickenfarms.chickenfarms.unitTests.service;

import com.chickenfarms.chickenfarms.exception.DBException;
import com.chickenfarms.chickenfarms.exception.RecordNotFoundException;
import com.chickenfarms.chickenfarms.model.Customer;
import com.chickenfarms.chickenfarms.model.entities.*;
import com.chickenfarms.chickenfarms.repository.*;
import com.chickenfarms.chickenfarms.service.DBValidation;
import com.chickenfarms.chickenfarms.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
public class DBValidationTest {
    
    @InjectMocks
    private DBValidation dbValidation;
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private ProblemRepository problemRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private RootCauseRepository rootCauseRepository;
    
    
    @Test
    public void successfulGetUserTest(){
        User userInDB=TestUtils.getUser(1L,"lkalachman","Enginner");
//        User userInDB=User.builder().userId(1L).userName("lkalachman").typeName("Enginner").build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(userInDB));
        try {
            User user=dbValidation.getUser(1L);
            assertThat(user).isNotNull();
        } catch (Exception e) {
            fail("Failed to get user:"+e.getMessage());
        }
    }
    
    @Test
    public void failedGetUserTest(){
        when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        try{
            User user=dbValidation.getUser(1L);
        }
        catch (Exception e) {
            assertThatExceptionOfType(RecordNotFoundException.class);
        }
    }
    
    @Test
    public void successfulGetProblemTest(){
        Problem problemInDB= TestUtils.getProblem(101,"General error");
//        Problem problemInDB=Problem.builder().problemId(101).problemName("General error").build();
        when(problemRepository.findById(101)).thenReturn(Optional.of(problemInDB));
        try {
            Problem problem=dbValidation.getProblem(101);
            assertThat(problem).isNotNull();
        } catch (Exception e) {
            fail("Failed to get problem from DB: "+e.getMessage());
        }
    }
    
    @Test
    public void failedGetProblemTest(){
        when(problemRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        try{
            Problem user=dbValidation.getProblem(101);
        }
        catch (Exception e) {
            assertThatExceptionOfType(RecordNotFoundException.class);
        }
    }
    
    @Test
    public void successfulGetCustomerTest(){
        Customer customerInDB=TestUtils.getCustomer(123L,"schoen");
//        Customer customerInDB=Customer.builder().customerId(123).customerFirstName("Shir").customerSurname("Cohen").customerUsername("schoen").build();
        when(customerRepository.findById(123L)).thenReturn(Optional.of(customerInDB));
        try {
            Customer customer=dbValidation.getCustomer(123);
            assertThat(customer).isNotNull();
        } catch (Exception e) {
            fail("Failed to get customer from DB: "+e.getMessage());
        }
    }
    
    @Test
    public void failedGetCustomerTest(){
        when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        try{
            Customer customer=dbValidation.getCustomer(123);
        }
        catch (Exception e) {
            assertThatExceptionOfType(RecordNotFoundException.class);
        }
    }
    
    
    @Test
    public void successfulGetTicketTest(){
        Ticket ticketInDB=TestUtils.getTicket(5487L,1);
//        Ticket ticketInDB=Ticket.builder().ticketId(5487L).farmId(1).grade(123).build();
        when(ticketRepository.findById(5487L)).thenReturn(Optional.of(ticketInDB));
        try {
            Ticket ticket=dbValidation.getTicket(5487L);
            assertThat(ticket).isNotNull();
        } catch (Exception e) {
            fail("Failed to get ticket from DB: "+e.getMessage());
        }
    }
    
    @Test
    public void failedGetTicketTest(){
        when(ticketRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        try{
            Ticket ticket=dbValidation.getTicket(5487L);
        }
        catch (Exception e) {
            assertThatExceptionOfType(RecordNotFoundException.class);
        }
    }
    
    @Test
    public void successfulGetTagTest(){
        Tag tagInDB=TestUtils.getTag("Tag",1);
        when(tagRepository.findById("Tag")).thenReturn(Optional.of(tagInDB));
        try {
            Tag tag=dbValidation.getTag("Tag");
            assertThat(tag).isNotNull();
        } catch (Exception e) {
            fail("Failed to get tag from DB: "+e.getMessage());
        }
    }
    
    @Test
    public void failedGetTagTest(){
        when(tagRepository.findById("Tag")).thenReturn(Optional.empty());
        try{
            Tag tag=dbValidation.getTag("Tag");
        }
        catch (Exception e) {
            assertThatExceptionOfType(RecordNotFoundException.class);
        }
    }
    
    @Test
    public void successfulGetRootCauseTest(){
        RootCause rootCauseInDB=TestUtils.getRootCause(1,"Bad user name");
        when(rootCauseRepository.findByRootCauseName("Bad user name")).thenReturn(Optional.of(rootCauseInDB));
        try {
            RootCause rootCause=dbValidation.getRootCause("Bad user name");
            assertThat(rootCause).isNotNull();
        } catch (Exception e) {
            fail("Failed to get root cause from DB: "+e.getMessage());
        }
    }
    
    @Test
    public void failedGetRootCauseTest(){
        when(rootCauseRepository.findByRootCauseName("Bad user name")).thenReturn(Optional.empty());
        try{
            RootCause rootCause=dbValidation.getRootCause("Bad user name");
        }
        catch (Exception e) {
            assertThatExceptionOfType(RecordNotFoundException.class);
        }
    }
    
    @Test
    public void successfulInsertCustomersInTicketTest(){
        try {
            boolean isInsertAllCustomersInTicket=dbValidation.isInsertAllCustomersInTicket(1,1);
            assertThat(isInsertAllCustomersInTicket).isTrue();
        } catch (DBException e) {
            fail("Failed to insert all customers in ticket: "+e.getMessage());
        }
    }
    
    @Test
    public void failedInsertCustomersInTicketTest(){
        try {
            boolean isInsertAllCustomersInTicket=dbValidation.isInsertAllCustomersInTicket(2,1);
            assertThat(isInsertAllCustomersInTicket).isTrue();
        } catch (DBException e) {
            assertThatExceptionOfType(DBException.class);
        }
    }
    
    
    

}
