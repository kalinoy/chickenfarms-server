package com.chickenfarms.chickenfarms.acceptance;

import com.chickenfarms.chickenfarms.model.ApiResponse;
import com.chickenfarms.chickenfarms.model.CreatedTicketDetailsPayload;
import com.chickenfarms.chickenfarms.model.TicketBusinessDetails;
import com.chickenfarms.chickenfarms.utils.TestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LifeCycleTest {
    
    @LocalServerPort
    int randomServerPort;
    
    private RestTemplate restTemplate;
    
    private static final String LOCAL_HOST="http://localhost:";
    private static final String LIFECYCLE_ENDPOINT="/chickenFarms/lifecycle/ticket/";
    
    ObjectMapper mapper=new ObjectMapper();
    
    
    @BeforeEach
    void setUp() {
        restTemplate = new RestTemplate();
    }
    
    @Test
    public void getCreatedNewTicketTest(){
        ResponseEntity<ApiResponse> createdTicketResponse = TestUtils.getApiResponseForCreatedTicket(restTemplate,randomServerPort);
        TicketBusinessDetails ticketBusinessDetails=mapper.convertValue(createdTicketResponse.getBody().getBusinessDetails(),TicketBusinessDetails.class);
        assertThat(createdTicketResponse.getStatusCode().value()).isEqualTo(200);
        TicketBusinessDetailsResponse ticketBusinessDetailsResponse=new TicketBusinessDetailsResponse("lkalachman",101,"New description","Ticket created",2,null);
        assertTicketResponse( ticketBusinessDetails,ticketBusinessDetailsResponse);
    }
    
    @Test
    public void getReadyTicketTest(){
        ResponseEntity<ApiResponse> readyTicketResponse = getReadyTicketResponse();
        TicketBusinessDetails ticketBusinessDetails=mapper.convertValue(readyTicketResponse.getBody().getBusinessDetails(),TicketBusinessDetails.class);
        assertThat(readyTicketResponse.getStatusCode().value()).isEqualTo(200);
        TicketBusinessDetailsResponse ticketBusinessDetailsResponse=new TicketBusinessDetailsResponse("lkalachman",101,"New description","Ticket ready",2,"Bad username");
        assertTicketResponse(ticketBusinessDetails,ticketBusinessDetailsResponse);
    }
    

    
    @Test
    public void getClosedTicketTest(){
        String closedRequestUrl=getClosedRequestUrl();
        ResponseEntity<ApiResponse> closedTicketResponse=restTemplate.getForEntity(closedRequestUrl, ApiResponse.class);
        TicketBusinessDetails ticketBusinessDetails=mapper.convertValue(closedTicketResponse.getBody().getBusinessDetails(),TicketBusinessDetails.class);
        assertThat(closedTicketResponse.getStatusCode().value()).isEqualTo(200);
        TicketBusinessDetailsResponse ticketBusinessDetailsResponse=new TicketBusinessDetailsResponse("lkalachman",101,"New description","Ticket closed",2,"Bad username");
        assertTicketResponse(ticketBusinessDetails,ticketBusinessDetailsResponse);
        
    }
    
    private String getClosedRequestUrl() {
        ResponseEntity<ApiResponse> readyTicketResponse = getReadyTicketResponse();
        TicketBusinessDetails ticketBusinessDetails=mapper.convertValue(readyTicketResponse.getBody().getBusinessDetails(),TicketBusinessDetails.class);
        long ticketId=ticketBusinessDetails.getTicketId();
        return new StringBuilder(LOCAL_HOST).append(randomServerPort).append(LIFECYCLE_ENDPOINT).append(ticketBusinessDetails.getTicketId()).append("/close/resloved/").append(true).toString();
    }
    
    private String getReadyRequestUrl() {
        ResponseEntity<ApiResponse> createdTicketResponse = TestUtils.getApiResponseForCreatedTicket(restTemplate,randomServerPort);
        TicketBusinessDetails ticketBusinessDetails=mapper.convertValue(createdTicketResponse.getBody().getBusinessDetails(),TicketBusinessDetails.class);
        return new StringBuilder(LOCAL_HOST).append(randomServerPort).append(LIFECYCLE_ENDPOINT).append(ticketBusinessDetails.getTicketId()).append("/ready/root_cause/").append("Bad username").toString();
    }
    
    private ResponseEntity<ApiResponse> getReadyTicketResponse() {
        String readyUrl=getReadyRequestUrl();
        ResponseEntity<ApiResponse> readyTicketResponse=restTemplate.getForEntity(readyUrl, ApiResponse.class);
        return readyTicketResponse;
    }
    
    
    private void assertTicketResponse(TicketBusinessDetails ticketBusinessDetails,TicketBusinessDetailsResponse ticketBusinessDetailsResponse) {
        assertThat(ticketBusinessDetails.getUserName()).isEqualTo(ticketBusinessDetailsResponse.getUsername());
        assertThat(ticketBusinessDetails.getProblemId()).isEqualTo(ticketBusinessDetailsResponse.getProblemId());
        assertThat(ticketBusinessDetails.getDescription()).isEqualTo(ticketBusinessDetailsResponse.getDescription());
        assertThat(ticketBusinessDetails.getStatus()).isEqualTo(ticketBusinessDetailsResponse.getStatus());
        assertThat(ticketBusinessDetails.getFarmId()).isEqualTo(ticketBusinessDetailsResponse.getFarmId());
        assertThat(ticketBusinessDetails.getRootCauseName()).isEqualTo(ticketBusinessDetailsResponse.getRootCause());
    }
    
}
