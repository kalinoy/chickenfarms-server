package com.chickenfarms.chickenfarms.unitTests.service;


import com.chickenfarms.chickenfarms.enums.TicketStatus;
import com.chickenfarms.chickenfarms.exception.DBException;
import com.chickenfarms.chickenfarms.exception.InvalidRequestException;
import com.chickenfarms.chickenfarms.exception.RecordNotFoundException;
import com.chickenfarms.chickenfarms.model.CreatedTicketDetailsPayload;
import com.chickenfarms.chickenfarms.model.Customer;
import com.chickenfarms.chickenfarms.model.entity.CustomersInTicket;
import com.chickenfarms.chickenfarms.model.entity.RootCause;
import com.chickenfarms.chickenfarms.model.entity.Ticket;
import com.chickenfarms.chickenfarms.repository.*;
import com.chickenfarms.chickenfarms.service.impl.TicketLifecycleStateServiceImpl;
import com.chickenfarms.chickenfarms.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
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
    private ProblemRepository problemRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CustomerRepository customerRepository;

    @Test
    public void createNewTicketSuccessfullyTest() {
        CreatedTicketDetailsPayload createdTicketDetailsPayload = new CreatedTicketDetailsPayload("A user name issue", 101, new ArrayList<Long>(Arrays.asList(111L)), 1, 3);
        Customer customerInDB = TestUtils.getCustomer(111L, "scohen");
        Ticket ticket = TestUtils.getTicketFromCreatedTicketDTO(createdTicketDetailsPayload, TicketStatus.CREATED);
        try {
            when(problemRepository.findById(101)).thenReturn(Optional.of(ticket.getProblem()));
            when(userRepository.findById(1L)).thenReturn(Optional.of(ticket.getUser()));
            when(customerRepository.findById(111L)).thenReturn(Optional.of(customerInDB));
            Ticket createdTicket = ticketLifecycleStateService.createNewTicket(createdTicketDetailsPayload);
            Mockito.verify(ticketRepository).save(Mockito.any(Ticket.class));
            Mockito.verify(customersInTicketRepository).save(Mockito.any(CustomersInTicket.class));
        } catch (RecordNotFoundException | DBException e) {
            fail("Should not throw error: " + e.getMessage());
        }
    }

    @Test
    public void closeTicketSuccessfullyTest() {
        try {
            Ticket ticket=TestUtils.getMockedTicket(ticketRepository,TicketStatus.READY);
            ticketLifecycleStateService.moveTicketToCloseStatus(ticket.getTicketId(), true);
            Mockito.verify(ticketRepository).save(ticket);
        } catch (RecordNotFoundException | InvalidRequestException e) {
            fail("Should not throw error: " + e.getMessage());
        }
    }

    @Test
    public void closeTicketFailedWithCreatedStatusTest() {
        try {
            Ticket ticket=TestUtils.getMockedTicket(ticketRepository,TicketStatus.CREATED);
            ticketLifecycleStateService.moveTicketToCloseStatus(ticket.getTicketId(), true);
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
            CreatedTicketDetailsPayload createdTicketDetailsPayload = new CreatedTicketDetailsPayload("A user name issue", 101, new ArrayList<Long>(Arrays.asList(111L)), 1, 3);
            Ticket mockedTicket=TestUtils.getMockedTicket(ticketRepository,TicketStatus.CREATED);
            Ticket returnedTicket = ticketLifecycleStateService.moveTicketToStatusReady(mockedTicket.getTicketId(), "Bad credentials");
            assertTicketMoveToReadyWithNewRootCause(returnedTicket);
        } catch (RecordNotFoundException |InvalidRequestException e) {
            fail("Should not throw error: " + e.getMessage());
        }
    }

    @Test
    public void moveTicketToReadyWithNewRootCauseForFarmTest() {
        try {
            RootCause rootCause = TestUtils.getRootCause(1, 2,"Bad credentials");
            when(rootCauseRepository.getByRootCauseName("bad credentials")).thenReturn(rootCause);
            Ticket mockedTicket=TestUtils.getMockedTicket(ticketRepository,TicketStatus.CREATED);
            Ticket returnedTicket = ticketLifecycleStateService.moveTicketToStatusReady(mockedTicket.getTicketId(), "Bad credentials");

            assertTicketMoveToReadyWithNewRootCauseForFarm(rootCause, returnedTicket);
        } catch (RecordNotFoundException | InvalidRequestException e) {
            fail("Should not throw error: " + e.getMessage());
        }
    }

    @Test
    public void moveTicketToReadyWithExistRootCauseForFarmTest() {
        try {
            RootCause rootCause = TestUtils.getRootCause(1, 3,"Bad credentials");
            when(rootCauseRepository.getByRootCauseName("bad credentials")).thenReturn(rootCause);
            Ticket mockedTicket=TestUtils.getMockedTicket(ticketRepository,TicketStatus.CREATED);
            Set<CustomersInTicket> createdCustomersInTicket=new HashSet<>(Arrays.asList(TestUtils.getCustomerInTicket(mockedTicket.getTicketId(),111L)));
            when(customersInTicketRepository.getAllByTicket(mockedTicket.getTicketId())).thenReturn(createdCustomersInTicket);
            Set<CustomersInTicket> existCustomersInTicket=new HashSet<>(Arrays.asList(TestUtils.getCustomerInTicket(2,123L)));
            when(customersInTicketRepository.getAllByTicket(2)).thenReturn(existCustomersInTicket);
            Ticket returnedTicket = ticketLifecycleStateService.moveTicketToStatusReady(mockedTicket.getTicketId(), "Bad credentials");
            assertTicketMoveToReadyWithExistRootCauseForFarm(createdCustomersInTicket, existCustomersInTicket, returnedTicket);

        } catch (RecordNotFoundException |InvalidRequestException e) {
            fail("Should not throw error: " + e.getMessage());
        } 
    }

    @Test
    public void moveTicketToReadyWithStatusReconciled(){
        try {
            when(rootCauseRepository.getByRootCauseName(Mockito.anyString())).thenReturn(null);
            CreatedTicketDetailsPayload createdTicketDetailsPayload = new CreatedTicketDetailsPayload("A user name issue", 101, new ArrayList<Long>(Arrays.asList(111L)), 1, 1);
            Ticket mockedTicket=TestUtils.getMockedTicket(ticketRepository,TicketStatus.RECONCILED);
            ticketLifecycleStateService.moveTicketToStatusReady(mockedTicket.getTicketId(),"Bad credentials");

        } catch (RecordNotFoundException e) {
            fail("Should not throw error: " + e.getMessage());
        } catch (InvalidRequestException e) {
            assertThatExceptionOfType(InvalidRequestException.class);
        }
    }

    private void assertTicketMoveToReadyWithExistRootCauseForFarm(Set<CustomersInTicket> createdCustomersInTicket, Set<CustomersInTicket> existCustomersInTicket, Ticket returnedTicket) {
        Mockito.verify(customersInTicketRepository).saveAll(existCustomersInTicket);
        assertThat(returnedTicket.getFarmId()).isEqualTo(3);
        assertThat(createdCustomersInTicket.iterator().next().getTicket().getTicketId()).isEqualTo(2);
    
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

}
