package com.chickenfarms.chickenfarms.unitTests.service;

import com.chickenfarms.chickenfarms.enums.TicketStatus;
import com.chickenfarms.chickenfarms.exception.InnerServiceException;
import com.chickenfarms.chickenfarms.exception.RecordNotFoundException;
import com.chickenfarms.chickenfarms.model.entity.Tag;
import com.chickenfarms.chickenfarms.model.entity.Ticket;
import com.chickenfarms.chickenfarms.repository.TagRepository;
import com.chickenfarms.chickenfarms.repository.TicketRepository;
import com.chickenfarms.chickenfarms.service.impl.TicketTagServiceImp;
import com.chickenfarms.chickenfarms.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})

public class TicketTagServiceTest {
    
    @InjectMocks
    private TicketTagServiceImp ticketTagServiceImp;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private TicketRepository ticketRepository;
    
    
    @Test
    public void addNewTagsTest(){
        try {
            Ticket mockedTicket=TestUtils.getMockedTicket(ticketRepository,TicketStatus.READY);
            when(tagRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
            List<Tag> savesTags=getSavedTags(mockedTicket.getTicketId());
            assertThat(savesTags.size()).isEqualTo(2);
            savesTags.stream().forEach(tag -> {
                Mockito.verify(tagRepository).save(tag);
                assertThat(tag.getTickets().size()).isEqualTo(1);
                assertThat(tag.getTagName()).containsAnyOf("check again","invalid issue");
            });
        } catch (RecordNotFoundException | InnerServiceException e) {
            fail("Should not throw error: " + e.getMessage());
        }
    }
    
    @Test
    public void updateExistingTagTest(){
        try {
            Ticket mockTicket=TestUtils.getMockedTicket(ticketRepository,TicketStatus.READY);
            mockTagByRepository("check again",1);
            mockTagByRepository("invalid issue",2);
            List<Tag> savesTags = getSavedTags(mockTicket.getTicketId());
            assertThat(savesTags.size()).isEqualTo(2);
            savesTags.stream().forEach(tag -> {
                Mockito.verify(tagRepository).save(tag);
                assertThat(tag.getTickets().size()).isEqualTo(2);
                assertThat(tag.getTagName()).containsAnyOf("check again","invalid issue");
            });
        } catch (RecordNotFoundException |InnerServiceException e) {
            fail("Should not throw error: " + e.getMessage());
        }
    }
    
    @Test
    public void updateAndAddNewTagsTest(){
        try {
            Ticket mockTicket=TestUtils.getMockedTicket(ticketRepository,TicketStatus.READY);
            mockTagByRepository("check again",1);
            when(tagRepository.findById("invalid issue")).thenReturn(Optional.empty());
            List<Tag> savesTags = getSavedTags(mockTicket.getTicketId());
            savesTags.stream().forEach(tag -> {
                Mockito.verify(tagRepository).save(tag);
                assertThat(tag.getTagName()).containsAnyOf("check again","invalid issue");
                if(tag.getTagName().equals("check again")){
                    assertThat(tag.getTickets().size()).isEqualTo(2);
                }
                else {
                    assertThat(tag.getTickets().size()).isEqualTo(1);
                }
            });
        } catch (RecordNotFoundException |InnerServiceException e) {
            fail("Should not throw error: " + e.getMessage());
        }
    }
    
    private List<Tag> getSavedTags(long ticketId) throws RecordNotFoundException, InnerServiceException {
        List<String> tags=new ArrayList<>(Arrays.asList("Check again","Invalid issue"));
        return ticketTagServiceImp.addTags(ticketId,tags);
    }
    
    private void mockTagByRepository(String tagName,long ticketId) {
        Tag tag=TestUtils.getTag(tagName,ticketId);
        when(tagRepository.findById(tagName)).thenReturn(Optional.of(tag));
    }
}
