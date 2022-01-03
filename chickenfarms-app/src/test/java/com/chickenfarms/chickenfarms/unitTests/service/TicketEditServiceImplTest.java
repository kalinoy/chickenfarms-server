package com.chickenfarms.chickenfarms.unitTests.service;

import com.chickenfarms.chickenfarms.enums.TicketStatus;
import com.chickenfarms.chickenfarms.exception.InnerServiceException;
import com.chickenfarms.chickenfarms.exception.RecordNotFoundException;
import com.chickenfarms.chickenfarms.model.DTO.CreateTicketDetailsDTO;
import com.chickenfarms.chickenfarms.model.entities.Tag;
import com.chickenfarms.chickenfarms.repository.TagRepository;
import com.chickenfarms.chickenfarms.service.DBValidation;
import com.chickenfarms.chickenfarms.service.impl.TicketEditServiceImpl;
import com.chickenfarms.chickenfarms.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
public class TicketEditServiceImplTest {
    
    @InjectMocks
    private TicketEditServiceImpl ticketEditService;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private DBValidation dbValidation;
    
    @Test
    public void addNewTagsTest(){
        try {
            mockTicketByRepository();
            when(tagRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
            List<Tag> savesTags=getSavedTags();
            savesTags.stream().forEach(tag -> {
                Mockito.verify(tagRepository).save(tag);
                assertThat(tag.getTickets().size()).isEqualTo(1);
                assertThat(tag.getTagName()).containsAnyOf("check again","invalid issue");
            });
        } catch (RecordNotFoundException |InnerServiceException e) {
            fail("Should not throw error: " + e.getMessage());
        } 
    }
    
    @Test
    public void updateTagsTest(){
        try {
            mockTicketByRepository();
            mockTagByRepository("check again",1);
            mockTagByRepository("invalid issue",2);
            List<Tag> savesTags = getSavedTags();
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
            mockTicketByRepository();
            mockTagByRepository("check again",1);
            when(tagRepository.findById("invalid issue")).thenReturn(Optional.empty());
            List<Tag> savesTags = getSavedTags();
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
    
    
    
    private void mockTicketByRepository() throws RecordNotFoundException {
        CreateTicketDetailsDTO createTicketDetailsDTO = new CreateTicketDetailsDTO("A user name issue", 101, new ArrayList<Long>(Arrays.asList(111L)), 1, 3);
        TestUtils.dbValidationMockTicket(createTicketDetailsDTO, TicketStatus.CREATED, dbValidation);
    }
    
    private List<Tag> getSavedTags() throws RecordNotFoundException, InnerServiceException {
        List<String> tags=new ArrayList<>(Arrays.asList("Check again","Invalid issue"));
        List<Tag> savesTags=ticketEditService.addTags(3,tags);
        return savesTags;
    }
    
    private void mockTagByRepository(String tagName,long ticketId) {
        Tag tag=TestUtils.getTag(tagName,ticketId);
        when(tagRepository.findById(tagName)).thenReturn(Optional.of(tag));
    }
}
