package com.chickenfarms.chickenfarms.controller;


import com.chickenfarms.chickenfarms.exception.DBException;
import com.chickenfarms.chickenfarms.exception.InnerServiceException;
import com.chickenfarms.chickenfarms.exception.InvalidRequestException;
import com.chickenfarms.chickenfarms.exception.RecordNotFoundException;
import com.chickenfarms.chickenfarms.model.DTO.CreateTicketDetailsDTO;
import com.chickenfarms.chickenfarms.model.entities.Comment;
import com.chickenfarms.chickenfarms.model.entities.Tag;
import com.chickenfarms.chickenfarms.model.entities.Ticket;
import com.chickenfarms.chickenfarms.service.TicketCommentService;
import com.chickenfarms.chickenfarms.service.TicketEditService;
import com.chickenfarms.chickenfarms.service.TicketLifecycleStateService;
import com.chickenfarms.chickenfarms.service.TicketViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/chickenFarms")
//@EnableTransactionManagement
public class ChickenFarmsController {

    private TicketLifecycleStateService ticketLifecycleStateService;
    
    private TicketEditService ticketEditService;
    
    private TicketCommentService ticketCommentService;
    
    private TicketViewService ticketViewService;
    
    //TODO: check valid input in all endpoints
    @Autowired
    public ChickenFarmsController(TicketLifecycleStateService ticketLifecycleStateService, TicketEditService ticketEditService,TicketCommentService ticketCommentService,TicketViewService ticketViewService) {
        this.ticketLifecycleStateService = ticketLifecycleStateService;
        this.ticketEditService=ticketEditService;
        this.ticketCommentService=ticketCommentService;
        this.ticketViewService=ticketViewService;
    }
    
    @PostMapping(value = "/ticket/createNewT",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public long createNewTicket(@RequestBody @Valid CreateTicketDetailsDTO createTicketDetailsDTO) throws RecordNotFoundException, DBException {
        Ticket ticket= ticketLifecycleStateService.createNewTicket(createTicketDetailsDTO);
        return ticket.getTicketId();
    }
    
    @PostMapping(value = "/ticket/{ticket_id}/moveToClose")
    public Ticket moveTicketToCloseStatus(@PathVariable("ticket_id") @Valid long ticketId,@RequestParam("is_resolved") @Valid boolean isResolved) throws RecordNotFoundException, InvalidRequestException {
        return ticketLifecycleStateService.moveTicketToCloseStatus(ticketId,isResolved);
    }
    
    //make it request paharam
    @PutMapping(value = "/ticket/{ticket_id}/addTags")
    public List<Tag> addTagsToTicket(@PathVariable("ticket_id") @Valid long ticket, @RequestParam("tags") @Valid @NotEmpty List<String> tags) throws RecordNotFoundException, InnerServiceException {
        return ticketEditService.addTags(ticket,tags);
    }
    
    @PostMapping(value = "/ticket/{ticket_id}/moveToReady")
    public Ticket moveTicketToReadyStatus(@PathVariable("ticket_id")  @Valid long ticketId,@RequestParam("root_cause") @Valid @NotBlank String rootCause) throws RecordNotFoundException, InvalidRequestException {
        return ticketLifecycleStateService.moveTicketToStatusReady(ticketId,rootCause);
    }
    
    @PutMapping(value = "/ticket/{ticket_id}/editDescription")
    public Ticket editTicketDescription(@PathVariable("ticket_id") @Valid long ticketId,@RequestParam("description") @Valid @NotBlank String description) throws RecordNotFoundException {
        return ticketEditService.editTicketDescription(ticketId,description);
    }
    
    @PutMapping(value = "/ticket/{ticket_id}/editProblem")
    public Ticket editTicketpProblem(@PathVariable("ticket_id")  @Valid long ticketId,@RequestParam("problem_id") @Valid int problemId) throws InvalidRequestException, RecordNotFoundException {
        return ticketEditService.editTicketProblem(ticketId,problemId);
    }
    
    @PostMapping(value = "/ticket/{ticket_id}/addComment")
    public Comment addTicketComment(@PathVariable("ticket_id")  @Valid long ticketId, @RequestParam("user_id") @Valid long userId,@RequestParam("text_message") @Valid @NotBlank String textMessage) throws InvalidRequestException, RecordNotFoundException {
        return ticketCommentService.addCommentToTicket(ticketId,userId,textMessage);
    }
    
    @GetMapping(value = "/ticket/{ticket_id}/viewComments/page/{page_number}")
    public List<Comment> viewTicketComments(@PathVariable("ticket_id")  @Valid long ticketId,@PathVariable("page_number") @Valid int pageNumber) throws InvalidRequestException, RecordNotFoundException {
        return ticketCommentService.getTicketComments(ticketId,pageNumber);
    }
    
    @GetMapping(value = "/ticket/status/{status}/page/{page_number}")
    public Set<Ticket> viewTicketByStatus(@PathVariable("status") @Valid String status,@PathVariable("page_number") @Valid int pageNumber) throws InvalidRequestException {
        return ticketViewService.getTicketsByStatus(status,pageNumber);
    }
    
    @GetMapping(value = "/ticket/tag/{tag_name}/page/{page_number}")
    public Set<Ticket> viewTicketByTag(@PathVariable("tag_name") @Valid String tag, @PathVariable("page_number") @Valid int pageNumber) throws InvalidRequestException, RecordNotFoundException {
        return ticketViewService.getTicketsByTag(tag,pageNumber);
    }
    
    @GetMapping(value = "/ticket/farmId/{farm_id}/page/{page_number}")
    public Set<Ticket> viewTicketByFarmId(@PathVariable("farm_id") @Valid int farmId, @PathVariable("page_number") @Valid int pageNumber) throws InvalidRequestException {
        return  ticketViewService.getTicketsByFarm(farmId,pageNumber);
    }
    
    @GetMapping(value = "/ticket/problem/{problem_id}/page/{page_number}")
    public Set<Ticket> viewTicketByProblemId(@PathVariable("problem_id") @Valid int problemId, @PathVariable("page_number") @Valid int pageNumber) throws InvalidRequestException, RecordNotFoundException {
        return ticketViewService.getTicketsByProblem(problemId,pageNumber);
    }
    
    @GetMapping(value = "/ticket/rootCause/{root_cause}/page/{page_number}")
    public Set<Ticket> viewTicketByRootCause(@PathVariable("root_cause") @Valid String rootCause, @PathVariable("page_number") @Valid int pageNumber) throws InvalidRequestException, RecordNotFoundException {
        return ticketViewService.getTicketsByRootCause(rootCause,pageNumber);
    }
}
