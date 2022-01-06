package com.chickenfarms.chickenfarms.acceptance;

import com.chickenfarms.chickenfarms.model.ApiResponse;
import com.chickenfarms.chickenfarms.model.CommentBusinessDetails;
import com.chickenfarms.chickenfarms.model.TagBusinessDetails;
import com.chickenfarms.chickenfarms.model.TicketBusinessDetails;
import com.chickenfarms.chickenfarms.utils.TestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class EditorTicketTest {
    @LocalServerPort
    int randomServerPort;
    
    private RestTemplate restTemplate;
    
    private static final String LOCAL_HOST="http://localhost:";
    private static final String EDITOR_ENDPOINT="/chickenFarms/ticket/";
    
    ObjectMapper mapper=new ObjectMapper();
    
    
    @BeforeEach
    void setUp() {
        restTemplate = new RestTemplate();
    }
    

    
    @Test
    public void addTagsToTicketTest() {
        ResponseEntity<ApiResponse> createdTicketResponse = TestUtils.getApiResponseForCreatedTicket(restTemplate,randomServerPort);
        TicketBusinessDetails ticketBusinessDetails=mapper.convertValue(createdTicketResponse.getBody().getBusinessDetails(),TicketBusinessDetails.class);
        String requestUrl = new StringBuilder(LOCAL_HOST).append(randomServerPort).append(EDITOR_ENDPOINT).append(ticketBusinessDetails.getTicketId()).append("/addTags").toString();
        HttpEntity requestEntity=new HttpEntity(new ArrayList<>(Arrays.asList("check again","unknown issue")));
        ResponseEntity<ApiResponse> responseEntity=restTemplate.exchange(requestUrl, HttpMethod.PUT,requestEntity, ApiResponse.class);
        List<TagBusinessDetails> tagsBusinessDetails=mapper.convertValue(responseEntity.getBody().getBusinessDetails(), List.class);
        for (Object tag:tagsBusinessDetails) {
            TagBusinessDetails tagBusinessDetails=mapper.convertValue(tag, TagBusinessDetails.class);
            assertThat(tagBusinessDetails.getTagName()).containsAnyOf("check again","unknown issue");
        }
    }
    
    @Test
    public void editDescriptionInTicketTest() {
        ResponseEntity<ApiResponse> createdTicketResponse = TestUtils.getApiResponseForCreatedTicket(restTemplate,randomServerPort);
        TicketBusinessDetails ticketBusinessDetails=mapper.convertValue(createdTicketResponse.getBody().getBusinessDetails(),TicketBusinessDetails.class);
        String requestUrl = new StringBuilder(LOCAL_HOST).append(randomServerPort).append(EDITOR_ENDPOINT).append(ticketBusinessDetails.getTicketId()).append("/editDescription").toString();
        HttpEntity requestEntity=new HttpEntity("New description");
        ResponseEntity<ApiResponse> responseEntity=restTemplate.exchange(requestUrl, HttpMethod.PUT,requestEntity, ApiResponse.class);
        TicketBusinessDetails ticketBusinessDetail=mapper.convertValue(responseEntity.getBody().getBusinessDetails(), TicketBusinessDetails.class);
        assertThat(ticketBusinessDetail.getDescription()).isEqualTo("New description");
    }
    
    @Test
    public void editProblemInTicketTest() {
        ResponseEntity<ApiResponse> createdTicketResponse = TestUtils.getApiResponseForCreatedTicket(restTemplate,randomServerPort);
        TicketBusinessDetails ticketBusinessDetails=mapper.convertValue(createdTicketResponse.getBody().getBusinessDetails(),TicketBusinessDetails.class);
        String requestUrl = new StringBuilder(LOCAL_HOST).append(randomServerPort).append(EDITOR_ENDPOINT).append(ticketBusinessDetails.getTicketId()).append("/editProblem/").append(102).toString();
        HttpEntity requestEntity=new HttpEntity("New description");
        ResponseEntity<ApiResponse> responseEntity=restTemplate.exchange(requestUrl, HttpMethod.PUT,requestEntity, ApiResponse.class);
        TicketBusinessDetails ticketBusinessDetail=mapper.convertValue(responseEntity.getBody().getBusinessDetails(), TicketBusinessDetails.class);
        assertThat(ticketBusinessDetail.getProblemId()).isEqualTo(102);
    }
    
    @Test
    public void editCommentInTicketTest() {
        ResponseEntity<ApiResponse> createdTicketResponse = TestUtils.getApiResponseForCreatedTicket(restTemplate,randomServerPort);
        TicketBusinessDetails ticketBusinessDetails=mapper.convertValue(createdTicketResponse.getBody().getBusinessDetails(),TicketBusinessDetails.class);
        String requestUrl = new StringBuilder(LOCAL_HOST).append(randomServerPort).append(EDITOR_ENDPOINT).append(ticketBusinessDetails.getTicketId()).append("/addComment/user/").append(1).toString();
        HttpEntity requestEntity=new HttpEntity("New Comment");
        ResponseEntity<ApiResponse> responseEntity=restTemplate.postForEntity(requestUrl,requestEntity, ApiResponse.class);
        CommentBusinessDetails commentBusinessDetails=mapper.convertValue(responseEntity.getBody().getBusinessDetails(), CommentBusinessDetails.class);
        assertThat(commentBusinessDetails.getCommentText()).isEqualTo("New Comment");
        assertThat(commentBusinessDetails.getUserName()).isEqualTo("lkalachman");
    
    }
    
    
}
