package com.chickenfarms.chickenfarms.acceptance;

import com.chickenfarms.chickenfarms.model.ApiResponse;
import com.chickenfarms.chickenfarms.model.TicketBusinessDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ViewTicketsTest {
    
    @LocalServerPort
    int randomServerPort;
    
    private RestTemplate restTemplate;
    
    private static final String LOCAL_HOST="http://localhost:";
    private static final String VIEW_ENDPOINT="/chickenFarms/view/";
    
    ObjectMapper mapper=new ObjectMapper();
    
    
    @BeforeEach
    void setUp() {
        restTemplate = new RestTemplate();
    }
    
    @Test
    public void getAllTicketsByPageTest(){
        ResponseEntity<ApiResponse> responseEntity = getApiResponseResponseEntity("/page/1");
        List<TicketBusinessDetails> ticketBusinessDetails=mapper.convertValue(responseEntity.getBody().getBusinessDetails(),List.class);
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
        assertThat(ticketBusinessDetails.size()).isLessThan(6);
    }
    

    
    @Test
    public void getAllTicketsByStatusTest(){
        ResponseEntity<ApiResponse> responseEntity = getApiResponseResponseEntity("/status/Ticket created/page/1");
        List<TicketBusinessDetails> ticketsBusinessDetails=mapper.convertValue(responseEntity.getBody().getBusinessDetails(), ArrayList.class);
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
        assertThat(ticketsBusinessDetails.size()).isLessThan(6);
        for (Object ticket:ticketsBusinessDetails) {
            TicketBusinessDetails ticketBusinessDetails=mapper.convertValue(ticket, TicketBusinessDetails.class);
            assertThat(ticketBusinessDetails.getStatus()).isEqualTo("Ticket created");
        }
    }
    
    @Test
    public void getAllTicketsByTagTest(){
        ResponseEntity<ApiResponse> responseEntity = getApiResponseResponseEntity("/tag/check again/page/1");
        List<TicketBusinessDetails> ticketsBusinessDetails=mapper.convertValue(responseEntity.getBody().getBusinessDetails(), ArrayList.class);
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
        assertThat(ticketsBusinessDetails.size()).isLessThan(6);
        for (Object ticket:ticketsBusinessDetails) {
            TicketBusinessDetails ticketBusinessDetails=mapper.convertValue(ticket, TicketBusinessDetails.class);
            assertThat(ticketBusinessDetails.getTagsName()).contains("check again");
        }
    }
    
    @Test
    public void getAllTicketsByFarmTest(){
        ResponseEntity<ApiResponse> responseEntity = getApiResponseResponseEntity("/farmId/1/page/1");
        List<TicketBusinessDetails> ticketsBusinessDetails=mapper.convertValue(responseEntity.getBody().getBusinessDetails(), ArrayList.class);
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
        assertThat(ticketsBusinessDetails.size()).isLessThan(6);
        for (Object ticket:ticketsBusinessDetails) {
            TicketBusinessDetails ticketBusinessDetails=mapper.convertValue(ticket, TicketBusinessDetails.class);
            assertThat(ticketBusinessDetails.getFarmId()).isEqualTo(1);
        }
    }
    
    @Test
    public void getAllTicketsByProblemTest(){
        ResponseEntity<ApiResponse> responseEntity = getApiResponseResponseEntity("/problem/101/page/1");
        List<TicketBusinessDetails> ticketsBusinessDetails=mapper.convertValue(responseEntity.getBody().getBusinessDetails(), ArrayList.class);
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
        assertThat(ticketsBusinessDetails.size()).isLessThan(6);
        for (Object ticket:ticketsBusinessDetails) {
            TicketBusinessDetails ticketBusinessDetails=mapper.convertValue(ticket, TicketBusinessDetails.class);
            assertThat(ticketBusinessDetails.getProblemId()).isEqualTo(101);
        }
    }
    
    @Test
    public void getAllTicketsByRootCauseTest(){
        ResponseEntity<ApiResponse> responseEntity = getApiResponseResponseEntity("/rootCause/Bad username/page/1");
        List<TicketBusinessDetails> ticketsBusinessDetails=mapper.convertValue(responseEntity.getBody().getBusinessDetails(), ArrayList.class);
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
        assertThat(ticketsBusinessDetails.size()).isLessThan(6);
        for (Object ticket:ticketsBusinessDetails) {
            TicketBusinessDetails ticketBusinessDetails=mapper.convertValue(ticket, TicketBusinessDetails.class);
            assertThat(ticketBusinessDetails.getRootCauseName()).isEqualTo("Bad username");
        }
    }
    
    private ResponseEntity<ApiResponse> getApiResponseResponseEntity(String urlPath) {
        String requestUrl = new StringBuilder(LOCAL_HOST).append(randomServerPort).append(VIEW_ENDPOINT).append(urlPath).toString();
        return restTemplate.getForEntity(requestUrl, ApiResponse.class);
    }
}
