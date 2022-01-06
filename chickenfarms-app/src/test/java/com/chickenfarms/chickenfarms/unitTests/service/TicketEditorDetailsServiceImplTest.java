package com.chickenfarms.chickenfarms.unitTests.service;

import com.chickenfarms.chickenfarms.enums.TicketStatus;
import com.chickenfarms.chickenfarms.exception.InvalidRequestException;
import com.chickenfarms.chickenfarms.exception.RecordNotFoundException;
import com.chickenfarms.chickenfarms.model.entity.Problem;
import com.chickenfarms.chickenfarms.model.entity.Ticket;
import com.chickenfarms.chickenfarms.repository.ProblemRepository;
import com.chickenfarms.chickenfarms.repository.TicketRepository;
import com.chickenfarms.chickenfarms.service.impl.TicketEditorDetailsServiceImpl;
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
public class TicketEditorDetailsServiceImplTest {

    @InjectMocks
    private TicketEditorDetailsServiceImpl ticketEditService;
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private ProblemRepository problemRepository;

    @Test
    public void editTicketDescriptionSuccessfullyTest(){
        try {
            Ticket mockedTicket=TestUtils.getMockedTicket(ticketRepository,TicketStatus.READY);
            ticketEditService.editTicketDescription(mockedTicket.getTicketId(),"Change description");
            Mockito.verify(ticketRepository).save(mockedTicket);
            assertThat(mockedTicket.getDescription()).isEqualTo("Change description");
        } catch (RecordNotFoundException e) {
            fail("Should not throw error: " + e.getMessage());
        }
    }

    @Test
    public void editTicketProblemSuccessfullyTest(){
        try {
            Ticket mockedTicket=TestUtils.getMockedTicket(ticketRepository,TicketStatus.READY);
            Problem mockedProblem=TestUtils.getProblem(102,"Temporary issue");
            when(problemRepository.findById(102)).thenReturn(Optional.of(mockedProblem));
            ticketEditService.editTicketProblem(mockedTicket.getTicketId(),102);
            Mockito.verify(ticketRepository).save(mockedTicket);
            assertThat(mockedTicket.getProblem()).isEqualTo(mockedProblem);
        } catch (RecordNotFoundException |InvalidRequestException e) {
            fail("Should not throw error: " + e.getMessage());
        }
    }

    @Test
    public void editTicketProblemWithSameProblemTest(){
        try {
            Ticket mockedTicket=TestUtils.getMockedTicket(ticketRepository,TicketStatus.READY);
            ticketEditService.editTicketProblem(mockedTicket.getTicketId(),101);
        } catch (InvalidRequestException e) {
            assertThatExceptionOfType(InvalidRequestException.class);
        }catch  (RecordNotFoundException e){
            fail("Should not throw error: " + e.getMessage());
        }
    }
}
