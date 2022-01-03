package com.chickenfarms.chickenfarms.unitTests.service;


import com.chickenfarms.chickenfarms.enums.TicketStatus;
import com.chickenfarms.chickenfarms.exception.DBException;
import com.chickenfarms.chickenfarms.exception.InvalidRequestException;
import com.chickenfarms.chickenfarms.exception.RecordNotFoundException;
import com.chickenfarms.chickenfarms.model.Customer;
import com.chickenfarms.chickenfarms.model.DTO.CreateTicketDetailsDTO;
import com.chickenfarms.chickenfarms.model.entities.*;
import com.chickenfarms.chickenfarms.repository.CustomersInTicketRepository;
import com.chickenfarms.chickenfarms.repository.RootCauseRepository;
import com.chickenfarms.chickenfarms.repository.TicketRepository;
import com.chickenfarms.chickenfarms.service.DBValidation;
import com.chickenfarms.chickenfarms.service.impl.TicketLifecycleStateServiceImpl;
import com.chickenfarms.chickenfarms.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;



@ExtendWith({MockitoExtension.class})
public class TicketLifecycleStateServiceTest {
    
    @InjectMocks
    private TicketLifecycleStateServiceImpl ticketLifecycleStateService;
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private CustomersInTicketRepository customersInTicketRepository;
    @Mock
    private RootCauseRepository rootCauseRepository;
    @Mock
    private DBValidation dbValidation;
    
    @Test
    public void createNewTicketSuccessfullyTest() {
        CreateTicketDetailsDTO createTicketDetailsDTO = new CreateTicketDetailsDTO("A user name issue", 101, new ArrayList<Long>(Arrays.asList(111L)), 1, 3);
        Customer customerInDB = TestUtils.getCustomer(111L, "scohen");
        Ticket ticket = TestUtils.getTicketFromCreatedTicketDTO(createTicketDetailsDTO, TicketStatus.CREATED);
//        Ticket ticket = getTicket(createTicketDetailsDTO, TicketStatus.CREATED);
        try {
            when(dbValidation.getProblem(101)).thenReturn(ticket.getProblem());
            when(dbValidation.getUser(1L)).thenReturn(ticket.getUser());
            when(dbValidation.getCustomer(111L)).thenReturn(customerInDB);
            Ticket createdTicket = ticketLifecycleStateService.createNewTicket(createTicketDetailsDTO);
            Mockito.verify(ticketRepository).save(Mockito.any(Ticket.class));
            Mockito.verify(customersInTicketRepository).save(Mockito.any(CustomersInTicket.class));
        } catch (RecordNotFoundException | DBException e) {
            fail("Should not throw error: " + e.getMessage());
        }
    }
    
    @Test
    public void closeTicketSuccessfullyTest() {
        try {
            CreateTicketDetailsDTO createTicketDetailsDTO = new CreateTicketDetailsDTO("A user name issue", 101, new ArrayList<Long>(Arrays.asList(111L)), 1, 3);
            TestUtils.dbValidationMockTicket(createTicketDetailsDTO,TicketStatus.READY,dbValidation);
//            dbValidationMockTicket(createTicketDetailsDTO, TicketStatus.READY);
            ticketLifecycleStateService.moveTicketToCloseStatus(1, true);
            Mockito.verify(ticketRepository).save(Mockito.any(Ticket.class));
        } catch (RecordNotFoundException | InvalidRequestException e) {
            fail("Should not throw error: " + e.getMessage());
        }
    }
    
    @Test
    public void closeTicketFailedWithCreatedStatusTest() {
        try {
            CreateTicketDetailsDTO createTicketDetailsDTO = new CreateTicketDetailsDTO("A user name issue", 101, new ArrayList<Long>(Arrays.asList(111L)), 1, 3);
//            dbValidationMockTicket(createTicketDetailsDTO, TicketStatus.CREATED);
            TestUtils.dbValidationMockTicket(createTicketDetailsDTO,TicketStatus.CREATED,dbValidation);

            ticketLifecycleStateService.moveTicketToCloseStatus(1, true);
        } catch (InvalidRequestException e) {
            assertThatExceptionOfType(InvalidRequestException.class);
        } catch (RecordNotFoundException e) {
            fail("Should not throw error: " + e.getMessage());
        }
    }
    
    @Test
    public void moveTicketToReadyWithNewRootCause(){
        try {
            when(rootCauseRepository.getByRootCauseName(Mockito.anyString())).thenReturn(null);
            CreateTicketDetailsDTO createTicketDetailsDTO = new CreateTicketDetailsDTO("A user name issue", 101, new ArrayList<Long>(Arrays.asList(111L)), 1, 3);
//            dbValidationMockTicket(createTicketDetailsDTO,TicketStatus.CREATED);
            TestUtils.dbValidationMockTicket(createTicketDetailsDTO,TicketStatus.CREATED,dbValidation);

            Ticket returnedTicket = ticketLifecycleStateService.moveTicketToStatusReady(1, "Bad credentials");
            assertTicketMoveToReadyWithNewRootCause(returnedTicket);
    
        } catch (RecordNotFoundException |InvalidRequestException e) {
            fail("Should not throw error: " + e.getMessage());
        }
    }
    
    @Test
    public void moveTicketToReadyWithNewRootCauseForFarmTest() {
        try {
            RootCause rootCause = TestUtils.getRootCause(1, "Bad credentials");
            when(rootCauseRepository.getByRootCauseName("bad credentials")).thenReturn(rootCause);
            CreateTicketDetailsDTO createTicketDetailsDTO = new CreateTicketDetailsDTO("A user name issue", 101, new ArrayList<Long>(Arrays.asList(111L)), 1, 2);
            TestUtils.dbValidationMockTicket(createTicketDetailsDTO,TicketStatus.CREATED,dbValidation);
//            dbValidationMockTicket(createTicketDetailsDTO, TicketStatus.CREATED);
            Ticket returnedTicket = ticketLifecycleStateService.moveTicketToStatusReady(1, "Bad credentials");
            assertTicketMoveToReadyWithNewRootCauseForFarm(rootCause, returnedTicket);
        } catch (RecordNotFoundException | InvalidRequestException e) {
            fail("Should not throw error: " + e.getMessage());
        }
    }
    
    @Test
    public void moveTicketToReadyWithExistRootCauseForFarmTest() {
        try {
            RootCause rootCause = TestUtils.getRootCause(1, "Bad credentials");
            when(rootCauseRepository.getByRootCauseName("bad credentials")).thenReturn(rootCause);
            CreateTicketDetailsDTO createTicketDetailsDTO = new CreateTicketDetailsDTO("A user name issue", 101, new ArrayList<Long>(Arrays.asList(111L)), 1, 1);
            TestUtils.dbValidationMockTicket(createTicketDetailsDTO,TicketStatus.CREATED,dbValidation);
//            dbValidationMockTicket(createTicketDetailsDTO, TicketStatus.CREATED);
            Set<CustomersInTicket> createdCustomersInTicket=new HashSet<>(Arrays.asList(TestUtils.getCustomerInTicket(0,111L)));
            when(customersInTicketRepository.getAllByTicket(0)).thenReturn(createdCustomersInTicket);
            Set<CustomersInTicket> existCustomersInTicket=new HashSet<>(Arrays.asList(TestUtils.getCustomerInTicket(2,123L)));
            when(customersInTicketRepository.getAllByTicket(2)).thenReturn(existCustomersInTicket);
            Ticket returnedTicket = ticketLifecycleStateService.moveTicketToStatusReady(1, "Bad credentials");
            assertTicketMoveToReadyWithExistRootCauseForFarm(createdCustomersInTicket, existCustomersInTicket, returnedTicket);
    
        } catch (RecordNotFoundException |InvalidRequestException e) {
            fail("Should not throw error: " + e.getMessage());
        } 
    }
    
    @Test
    public void moveTicketToReadyWithStatusReconcieled(){
        try {
            when(rootCauseRepository.getByRootCauseName(Mockito.anyString())).thenReturn(null);
            CreateTicketDetailsDTO createTicketDetailsDTO = new CreateTicketDetailsDTO("A user name issue", 101, new ArrayList<Long>(Arrays.asList(111L)), 1, 1);
            TestUtils.dbValidationMockTicket(createTicketDetailsDTO,TicketStatus.RECONCILED,dbValidation);

//            dbValidationMockTicket(createTicketDetailsDTO,TicketStatus.RECONCILED);
            ticketLifecycleStateService.moveTicketToStatusReady(1,"Bad credentials");
        } catch (RecordNotFoundException e) {
            fail("Should not throw error: " + e.getMessage());
        } catch (InvalidRequestException e) {
            assertThatExceptionOfType(InvalidRequestException.class);
        }
    }
    
    private void assertTicketMoveToReadyWithExistRootCauseForFarm(Set<CustomersInTicket> createdCustomersInTicket, Set<CustomersInTicket> existCustomersInTicket, Ticket returnedTicket) {
        Mockito.verify(customersInTicketRepository).deleteAll(createdCustomersInTicket);
        Mockito.verify(customersInTicketRepository).saveAll(existCustomersInTicket);
        assertThat(returnedTicket.getTicketId()).isEqualTo(2);
        assertThat(returnedTicket.getFarmId()).isEqualTo(1);
    }
    
    private void assertTicketMoveToReadyWithNewRootCauseForFarm(RootCause rootCause, Ticket returnedTicket) {
        Mockito.verify(rootCauseRepository).save(rootCause);
        assertThat(returnedTicket.getRootCause().getRootCauseId()).isEqualTo(1);
        assertThat(returnedTicket.getRootCause().getRootCauseName()).isEqualTo("Bad credentials");
        assertThat(returnedTicket.getRootCause().getTickets().size()).isEqualTo(2);
    }
    
    private void assertTicketMoveToReadyWithNewRootCause(Ticket returnedTicket) {
        Mockito.verify(rootCauseRepository).save(returnedTicket.getRootCause());
        Mockito.verify(ticketRepository).save(returnedTicket);
        assertThat(returnedTicket.getStatus()).isEqualTo(TicketStatus.READY.getTicketStatus());
        assertThat(returnedTicket.getSla()).isEqualTo(3);
    }
    
    
//    private Ticket getTicket(CreateTicketDetailsDTO createTicketDetailsDTO, TicketStatus ticketStatus) {
//        Problem problemInDB = TestUtils.getProblem(101, "General issue");
//        User userInDB = TestUtils.getUser(1L, "lkalachman", "Enginner");
//        return Ticket.builder().farmId(createTicketDetailsDTO.getFarmId()).decription(createTicketDetailsDTO.getDescription()).problem(problemInDB).user(userInDB).status(ticketStatus.getTicketStatus()).createdDate(new Date(System.currentTimeMillis())).lastUpdatedDate(new Date(System.currentTimeMillis())).isResolved(false).build();
//    }
    

}
