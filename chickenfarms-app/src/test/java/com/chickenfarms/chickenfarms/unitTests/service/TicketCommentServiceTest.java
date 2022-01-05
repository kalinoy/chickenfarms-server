package com.chickenfarms.chickenfarms.unitTests.service;

import com.chickenfarms.chickenfarms.enums.TicketStatus;
import com.chickenfarms.chickenfarms.exception.InvalidRequestException;
import com.chickenfarms.chickenfarms.exception.RecordNotFoundException;
import com.chickenfarms.chickenfarms.model.entities.Comment;
import com.chickenfarms.chickenfarms.model.entities.Ticket;
import com.chickenfarms.chickenfarms.repository.CommentRepository;
import com.chickenfarms.chickenfarms.repository.TicketRepository;
import com.chickenfarms.chickenfarms.repository.UserRepository;
import com.chickenfarms.chickenfarms.service.impl.TicketCommentServiceImp;
import com.chickenfarms.chickenfarms.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
public class TicketCommentServiceTest {

    @InjectMocks
    private TicketCommentServiceImp ticketCommentService;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private UserRepository userRepository;


    @Test
    public void addCommentToTicketSuccessfullyTest(){
        try {
            Ticket mockedTicket= TestUtils.getMockedTicket(ticketRepository,TicketStatus.CREATED);
            when(userRepository.findById(1L)).thenReturn(Optional.of(mockedTicket.getUser()));
            ticketCommentService.addCommentToTicket(mockedTicket.getTicketId(),mockedTicket.getUser().getUserId(),"Comment added");
            Mockito.verify(commentRepository).save(Mockito.any(Comment.class));
        } catch (RecordNotFoundException | InvalidRequestException e) {
            fail("Should not throw error: " + e.getMessage());
        }
    }

    @Test
    public void getTicketCommentsSuccessfullyTest(){
        try {
            Ticket mockedTicket= TestUtils.getMockedTicket(ticketRepository,TicketStatus.CREATED);
            ticketCommentService.getTicketComments(mockedTicket.getTicketId(),1);
            Mockito.verify(commentRepository).getSortedCommentsByPage(mockedTicket.getTicketId(),0,5);
        } catch (RecordNotFoundException | InvalidRequestException e) {
            e.printStackTrace();
        }
    }
}
