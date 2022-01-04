package com.chickenfarms.chickenfarms.unitTests.service;

import com.chickenfarms.chickenfarms.enums.TicketStatus;
import com.chickenfarms.chickenfarms.exception.InvalidRequestException;
import com.chickenfarms.chickenfarms.exception.RecordNotFoundException;
import com.chickenfarms.chickenfarms.model.DTO.CreateTicketDetailsDTO;
import com.chickenfarms.chickenfarms.model.entities.Comment;
import com.chickenfarms.chickenfarms.model.entities.Ticket;
import com.chickenfarms.chickenfarms.repository.CommentRepository;
import com.chickenfarms.chickenfarms.service.DBValidation;
import com.chickenfarms.chickenfarms.service.impl.TicketCommentServiceImp;
import com.chickenfarms.chickenfarms.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
public class TicketCommentServiceTest {
    
    @InjectMocks
    private TicketCommentServiceImp ticketCommentService;
    @Mock
    private DBValidation dbValidation;
    @Mock
    private CommentRepository commentRepository;
    
    @Test
    public void addCommentToTicketSuccessfullyTest(){
        try {
            CreateTicketDetailsDTO createTicketDetailsDTO = new CreateTicketDetailsDTO("A user name issue", 101, new ArrayList<Long>(Arrays.asList(111L)), 1, 3);
            Ticket mockedTicket= TestUtils.getMockedTicket(createTicketDetailsDTO, TicketStatus.CREATED,dbValidation);
            when(dbValidation.getUser(1L)).thenReturn(mockedTicket.getUser());
            ticketCommentService.addCommentToTicket(mockedTicket.getTicketId(),mockedTicket.getUser().getUserId(),"Comment added");
            Mockito.verify(commentRepository).save(Mockito.any(Comment.class));
        } catch (RecordNotFoundException | InvalidRequestException e) {
            fail("Should not throw error: " + e.getMessage());
        }
    }
    
    @Test
    public void getTicketCommentsSuccessfullyTest(){
        try {
            CreateTicketDetailsDTO createTicketDetailsDTO = new CreateTicketDetailsDTO("A user name issue", 101, new ArrayList<Long>(Arrays.asList(111L)), 1, 3);
            Ticket mockedTicket= TestUtils.getMockedTicket(createTicketDetailsDTO, TicketStatus.CREATED,dbValidation);
            Comment comment=TestUtils.getComment(mockedTicket);
            ticketCommentService.getTicketComments(mockedTicket.getTicketId(),1);
            Mockito.verify(commentRepository).getSortedCommentsByPage(mockedTicket.getTicketId(),0,5);
        } catch (RecordNotFoundException | InvalidRequestException e) {
            e.printStackTrace();
        }
    }
}
