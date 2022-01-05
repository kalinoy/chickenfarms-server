package com.chickenfarms.chickenfarms.unitTests.service;

import com.chickenfarms.chickenfarms.exception.InvalidRequestException;
import com.chickenfarms.chickenfarms.exception.RecordNotFoundException;
import com.chickenfarms.chickenfarms.model.entities.Problem;
import com.chickenfarms.chickenfarms.model.entities.RootCause;
import com.chickenfarms.chickenfarms.model.entities.Tag;
import com.chickenfarms.chickenfarms.model.entities.Ticket;
import com.chickenfarms.chickenfarms.repository.ProblemRepository;
import com.chickenfarms.chickenfarms.repository.RootCauseRepository;
import com.chickenfarms.chickenfarms.repository.TagRepository;
import com.chickenfarms.chickenfarms.repository.TicketRepository;
import com.chickenfarms.chickenfarms.service.impl.TicketViewServiceImp;
import com.chickenfarms.chickenfarms.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
public class TicketViewServiceTest {

    @InjectMocks
    TicketViewServiceImp ticketViewService;

    @Mock
    TicketRepository ticketRepository;
    @Mock
    TagRepository tagRepository;
    @Mock
    ProblemRepository problemRepository;
    @Mock
    RootCauseRepository rootCauseRepository;


    @Test
    public void getTicketsByPageOneTest(){
        ticketViewService.getTicketsByPage(1);
        Mockito.verify(ticketRepository).getTicketsByPage(0,5);
    }

    @Test
    public void getTicketsByPageTwoTest(){
        ticketViewService.getTicketsByPage(2);
        Mockito.verify(ticketRepository).getTicketsByPage(5,10);
    }

    @Test
    public void getTicketByStatusPageOneTest(){
        try {
            ticketViewService.getTicketsByStatus("Ticket created",1);
            Mockito.verify(ticketRepository).getTicketsByStatus("Ticket created",0,5);
        } catch (InvalidRequestException e) {
            fail("Should not throw error: " + e.getMessage());
        }
    }

    @Test
    public void getTicketByStatusPageTwoTest(){
        try {
            ticketViewService.getTicketsByStatus("Ticket created",2);
            Mockito.verify(ticketRepository).getTicketsByStatus("Ticket created",5,10);
        } catch (InvalidRequestException e) {
            fail("Should not throw error: " + e.getMessage());
        }
    }

    @Test
    public void getTicketByInvalidStatusTest(){
        try {
            ticketViewService.getTicketsByStatus("created",2);
            Mockito.verify(ticketRepository).getTicketsByStatus("created",5,10);
        } catch (InvalidRequestException e) {
            assertThatExceptionOfType(InvalidRequestException.class);
        }
    }

    @Test
    public void getTicketByFarmPageOneTest(){
        ticketViewService.getTicketsByFarm(1,1);
        Mockito.verify(ticketRepository).getTicketsByFarmId(1,0,5);
    }

    @Test
    public void getTicketByFarmPageTwoTest(){
        ticketViewService.getTicketsByFarm(1,2);
        Mockito.verify(ticketRepository).getTicketsByFarmId(1,5,10);
    }

    @Test
    public void getTicketsByProblemSuccessfullyTest(){
        try {
            Problem mockedProblem= TestUtils.getProblem(101,"General issue");
            when(problemRepository.findById(101)).thenReturn(Optional.of(mockedProblem));
            Set<Ticket> mockedTicketSet=getMockedTicketSet();
            mockedProblem.setTickets(mockedTicketSet);
            Set<Ticket> returnedTickets=ticketViewService.getTicketsByProblem(101,1);
            assetReturnedTicketsView(returnedTickets);
        } catch (RecordNotFoundException e) {
            fail("Should not throw error: " + e.getMessage());
        }
    }

    @Test
    public void getTicketByRootCauseSuccessfullyTest(){
        try {
            RootCause mockedRootCause=TestUtils.getRootCause(1,3,"Bad user name");
            when(rootCauseRepository.findByRootCauseName("Bad user name")).thenReturn(Optional.of(mockedRootCause));
            Set<Ticket> mockedTicketSet=getMockedTicketSet();
            mockedRootCause.setTickets(mockedTicketSet);
            Set<Ticket> returnedTickets=ticketViewService.getTicketsByRootCause("Bad user name",1);
            assetReturnedTicketsView(returnedTickets);
        } catch (RecordNotFoundException e) {
            fail("Should not throw error: " + e.getMessage());
        }
    }

    @Test
    public void getTicketByTagSuccessfullyTest(){
        try {
            Tag mockedTag=TestUtils.getTag("tag",1);
            when(tagRepository.findById("tag")).thenReturn(Optional.of(mockedTag));
            Set<Ticket> mockedTicketSet=getMockedTicketSet();
            mockedTag.setTickets(mockedTicketSet);
            Set<Ticket> returnedTickets=ticketViewService.getTicketsByTag("tag",1);
            assetReturnedTicketsView(returnedTickets);
        } catch (RecordNotFoundException e) {
            fail("Should not throw error: " + e.getMessage());
        }
    }



    private Optional<Ticket> getOlderMockedTicket(Set<Ticket> mockedTicketSet) {
        return mockedTicketSet.stream().filter(mockedTicket -> mockedTicket.getTicketId() == 0).findAny();
    }

    private  Set<Ticket> getMockedTicketSet() {
        Set<Ticket> mockedTicketSet=new HashSet<>();
        for(int i=0;i<6;i++){
            Ticket mockedTicket=TestUtils.getTicket(i,i);
            String mockedDate=new StringBuilder().append(i+1).append("/12/2021").toString();
            try {
                Date date = new SimpleDateFormat("dd/MM/yyyy").parse(mockedDate);
                mockedTicket.setCreatedDate(date);
                mockedTicketSet.add(mockedTicket);
            } catch (ParseException e) {
                fail("Should not throw error: " + e.getMessage());
            }
        }
        return mockedTicketSet;
    }

    private void assetReturnedTicketsView(Set<Ticket> returnedTickets) {
        assertThat(returnedTickets.size()).isEqualTo(5);
        Optional<Ticket> ticket= getOlderMockedTicket(returnedTickets);
        assertThat(ticket.isPresent()).isFalse();
    }
}
