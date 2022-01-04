package com.chickenfarms.chickenfarms.controller;


import com.chickenfarms.chickenfarms.exception.DBException;
import com.chickenfarms.chickenfarms.exception.InnerServiceException;
import com.chickenfarms.chickenfarms.exception.InvalidRequestException;
import com.chickenfarms.chickenfarms.exception.RecordNotFoundException;
import com.chickenfarms.chickenfarms.model.DTO.CreateTicketDetailsDTO;
import com.chickenfarms.chickenfarms.model.entities.Comment;
import com.chickenfarms.chickenfarms.model.entities.Tag;
import com.chickenfarms.chickenfarms.model.entities.Ticket;
import com.chickenfarms.chickenfarms.model.entities.User;
import com.chickenfarms.chickenfarms.service.TicketCommentService;
import com.chickenfarms.chickenfarms.service.TicketEditService;
import com.chickenfarms.chickenfarms.service.TicketLifecycleStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequestMapping("/chickenFarms")
//@EnableTransactionManagement
public class ChickenFarmsController {

    private TicketLifecycleStateService ticketLifecycleStateService;
    
    private TicketEditService ticketEditService;
    
    private TicketCommentService ticketCommentService;
    
    //TODO: check valid input in all endpoints
    @Autowired
    public ChickenFarmsController(TicketLifecycleStateService ticketLifecycleStateService, TicketEditService ticketEditService,TicketCommentService ticketCommentService) {
        this.ticketLifecycleStateService = ticketLifecycleStateService;
        this.ticketEditService=ticketEditService;
        this.ticketCommentService=ticketCommentService;
    }
    
    @PostMapping(value = "/createNewTicket",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public long createNewTicket(@RequestBody @Valid CreateTicketDetailsDTO createTicketDetailsDTO) throws RecordNotFoundException, DBException {
        Ticket ticket= ticketLifecycleStateService.createNewTicket(createTicketDetailsDTO);
        return ticket.getTicketId();
    }
    
    @PostMapping(value = "/moveToClose")
    public Ticket moveTicketToCloseStatus(@RequestParam("ticket_id")  @Valid long ticketId,@RequestParam("is_resolved") @Valid boolean isResolved) throws RecordNotFoundException, InvalidRequestException {
        return ticketLifecycleStateService.moveTicketToCloseStatus(ticketId,isResolved);
    }
    
    //make it request paharam
    @PostMapping(value = "/addTagsToTicket")
    public List<Tag> addTagsToTicket(@RequestParam("ticket_id") @Valid long ticket, @RequestParam("tags") @Valid @NotEmpty List<String> tags) throws RecordNotFoundException, InnerServiceException {
        return ticketEditService.addTags(ticket,tags);
    }
    
    @PostMapping(value = "/moveToReady")
    public Ticket moveTicketToReadyStatus(@RequestParam("ticket_id")  @Valid long ticketId,@RequestParam("root_cause") @Valid @NotBlank String rootCause) throws RecordNotFoundException, InvalidRequestException {
        return ticketLifecycleStateService.moveTicketToStatusReady(ticketId,rootCause);
    }
    
    @PostMapping(value = "/editTicketDescription")
    public Ticket editTicketDescription(@RequestParam("ticket_id") @Valid long ticketId,@RequestParam("description") @Valid @NotBlank String description) throws RecordNotFoundException {
        return ticketEditService.editTicketDescription(ticketId,description);
    }
    
    @PostMapping(value = "/editTicketProblem")
    public Ticket editTicketpProblem(@RequestParam("ticket_id")  @Valid long ticketId,@RequestParam("problem_id") @Valid int problemId) throws InvalidRequestException, RecordNotFoundException {
        return ticketEditService.editTicketProblem(ticketId,problemId);
    }
    
    public Comment addTicketComment(@RequestParam("ticket_id")  @Valid long ticketId, @RequestParam("user_id") @Valid String userId,@RequestParam("comment_message") @Valid String commentMessage){
        return 
    }
    

    
    

}
