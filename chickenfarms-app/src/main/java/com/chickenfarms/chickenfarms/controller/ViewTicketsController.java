package com.chickenfarms.chickenfarms.controller;

import com.chickenfarms.chickenfarms.exception.InvalidRequestException;
import com.chickenfarms.chickenfarms.exception.RecordNotFoundException;
import com.chickenfarms.chickenfarms.model.ApiResponse;
import com.chickenfarms.chickenfarms.model.entity.Comment;
import com.chickenfarms.chickenfarms.model.entity.Ticket;
import com.chickenfarms.chickenfarms.service.TicketCommentService;
import com.chickenfarms.chickenfarms.service.TicketViewService;
import com.chickenfarms.chickenfarms.utils.ApiResponseUtils;
import com.chickenfarms.chickenfarms.utils.BusinessDetailsConverterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/chickenFarms/view")

public class ViewTicketsController {
    
    private TicketViewService ticketViewService;
    
    private TicketCommentService ticketCommentService;
    
    //TODO: check valid input in all endpoints
    @Autowired
    public ViewTicketsController(TicketViewService ticketViewService,TicketCommentService ticketCommentService) {
        this.ticketViewService=ticketViewService;
        this.ticketCommentService=ticketCommentService;
    }
    
    @GetMapping(value="/page/{page_number}")
    public ResponseEntity<ApiResponse> getTickets(@PathVariable("page_number") @Valid int pageNumber){
        Set<Ticket> tickets= ticketViewService.getTicketsByPage(pageNumber);
        return ApiResponseUtils.getApiResponse(BusinessDetailsConverterUtils.getTicketsBusinessList(tickets));
    
    }
    
    @GetMapping(value = "/status/{status}/page/{page_number}")
    public ResponseEntity<ApiResponse> getTicketsByStatus(@PathVariable("status") @Valid String status, @PathVariable("page_number") @Valid int pageNumber) throws InvalidRequestException {
        Set<Ticket> tickets= ticketViewService.getTicketsByStatus(status,pageNumber);
        return ApiResponseUtils.getApiResponse(BusinessDetailsConverterUtils.getTicketsBusinessList(tickets));
    
    }
    
    @GetMapping(value = "/tag/{tag_name}/page/{page_number}")
    public ResponseEntity<ApiResponse> getTicketsByTag(@PathVariable("tag_name") @Valid String tag, @PathVariable("page_number") @Valid int pageNumber) throws InvalidRequestException, RecordNotFoundException {
        Set<Ticket> tickets=  ticketViewService.getTicketsByTag(tag,pageNumber);
        return ApiResponseUtils.getApiResponse(BusinessDetailsConverterUtils.getTicketsBusinessList(tickets));
    
    }
    
    @GetMapping(value = "/farmId/{farm_id}/page/{page_number}")
    public ResponseEntity<ApiResponse> getTicketsByFarmId(@PathVariable("farm_id") @Valid int farmId, @PathVariable("page_number") @Valid int pageNumber) throws InvalidRequestException {
        Set<Ticket> tickets=  ticketViewService.getTicketsByFarm(farmId,pageNumber);
        return ApiResponseUtils.getApiResponse(BusinessDetailsConverterUtils.getTicketsBusinessList(tickets));
    
    }
    
    @GetMapping(value = "/problem/{problem_id}/page/{page_number}")
    public ResponseEntity<ApiResponse> getTicketsByProblemId(@PathVariable("problem_id") @Valid int problemId, @PathVariable("page_number") @Valid int pageNumber) throws InvalidRequestException, RecordNotFoundException {
        Set<Ticket> tickets= ticketViewService.getTicketsByProblem(problemId,pageNumber);
        return ApiResponseUtils.getApiResponse(BusinessDetailsConverterUtils.getTicketsBusinessList(tickets));
    
    }
    
    @GetMapping(value = "/rootCause/{root_cause}/page/{page_number}")
    public ResponseEntity<ApiResponse> getTicketsByRootCause(@PathVariable("root_cause") @Valid String rootCause, @PathVariable("page_number") @Valid int pageNumber) throws InvalidRequestException, RecordNotFoundException {
        Set<Ticket> tickets= ticketViewService.getTicketsByRootCause(rootCause,pageNumber);
        return ApiResponseUtils.getApiResponse(BusinessDetailsConverterUtils.getTicketsBusinessList(tickets));
    
    } 
       
    @GetMapping(value = "/ticket/{ticket_id}/comments/page/{page_number}")
    public ResponseEntity<ApiResponse> getTicketComments(@PathVariable("ticket_id")  @Valid long ticketId, @PathVariable("page_number") @Valid int pageNumber) throws InvalidRequestException, RecordNotFoundException {
        List<Comment> comments= ticketCommentService.getTicketComments(ticketId,pageNumber);
        return ApiResponseUtils.getApiResponse(BusinessDetailsConverterUtils.getCommentsBusinessList(comments));
    
    }
}
