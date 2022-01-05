package com.chickenfarms.chickenfarms.controller;

import com.chickenfarms.chickenfarms.exception.InvalidRequestException;
import com.chickenfarms.chickenfarms.exception.RecordNotFoundException;
import com.chickenfarms.chickenfarms.model.TicketBusinessDetails;
import com.chickenfarms.chickenfarms.model.entities.Comment;
import com.chickenfarms.chickenfarms.model.entities.Ticket;
import com.chickenfarms.chickenfarms.service.TicketCommentService;
import com.chickenfarms.chickenfarms.service.TicketViewService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Set<TicketBusinessDetails> getTickets(@PathVariable("page_number") @Valid int pageNumber){
        return ticketViewService.getTicketsByPage(pageNumber);
    }
    
    @GetMapping(value = "/ticket/{ticket_id}/comments/page/{page_number}")
    public List<Comment> getTicketComments(@PathVariable("ticket_id")  @Valid long ticketId, @PathVariable("page_number") @Valid int pageNumber) throws InvalidRequestException, RecordNotFoundException {
        return ticketCommentService.getTicketComments(ticketId,pageNumber);
    }
    
    @GetMapping(value = "/status/{status}/page/{page_number}")
    public Set<Ticket> getTicketsByStatus(@PathVariable("status") @Valid String status, @PathVariable("page_number") @Valid int pageNumber) throws InvalidRequestException {
        return ticketViewService.getTicketsByStatus(status,pageNumber);
    }
    
    @GetMapping(value = "/tag/{tag_name}/page/{page_number}")
    public Set<Ticket> getTicketsByTag(@PathVariable("tag_name") @Valid String tag, @PathVariable("page_number") @Valid int pageNumber) throws InvalidRequestException, RecordNotFoundException {
        return ticketViewService.getTicketsByTag(tag,pageNumber);
    }
    
    @GetMapping(value = "/farmId/{farm_id}/page/{page_number}")
    public Set<Ticket> getTicketsByFarmId(@PathVariable("farm_id") @Valid int farmId, @PathVariable("page_number") @Valid int pageNumber) throws InvalidRequestException {
        return  ticketViewService.getTicketsByFarm(farmId,pageNumber);
    }
    
    @GetMapping(value = "/problem/{problem_id}/page/{page_number}")
    public Set<Ticket> getTicketsByProblemId(@PathVariable("problem_id") @Valid int problemId, @PathVariable("page_number") @Valid int pageNumber) throws InvalidRequestException, RecordNotFoundException {
        return ticketViewService.getTicketsByProblem(problemId,pageNumber);
    }
    
    @GetMapping(value = "/rootCause/{root_cause}/page/{page_number}")
    public Set<Ticket> getTicketsByRootCause(@PathVariable("root_cause") @Valid String rootCause, @PathVariable("page_number") @Valid int pageNumber) throws InvalidRequestException, RecordNotFoundException {
        return ticketViewService.getTicketsByRootCause(rootCause,pageNumber);
    }

}
