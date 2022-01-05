package com.chickenfarms.chickenfarms.controller;

import com.chickenfarms.chickenfarms.exception.InnerServiceException;
import com.chickenfarms.chickenfarms.exception.InvalidRequestException;
import com.chickenfarms.chickenfarms.exception.RecordNotFoundException;
import com.chickenfarms.chickenfarms.model.entities.Comment;
import com.chickenfarms.chickenfarms.model.entities.Tag;
import com.chickenfarms.chickenfarms.model.entities.Ticket;
import com.chickenfarms.chickenfarms.service.TicketCommentService;
import com.chickenfarms.chickenfarms.service.TicketEditorDetailsService;
import com.chickenfarms.chickenfarms.service.TicketTagService;
import com.chickenfarms.chickenfarms.service.TicketViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequestMapping("/chickenFarms/ticket/{ticket_id}")
public class EditorTicketDetailsController {
    
    private TicketEditorDetailsService ticketEditorDetailsService;
    
    private TicketCommentService ticketCommentService;
    
    private TicketTagService ticketTagService;
    
    @Autowired
    public EditorTicketDetailsController(TicketEditorDetailsService ticketEditorDetailsService, TicketCommentService ticketCommentService, TicketViewService ticketViewService, TicketTagService ticketTagService) {
        this.ticketEditorDetailsService = ticketEditorDetailsService;
        this.ticketCommentService=ticketCommentService;
        this.ticketTagService=ticketTagService;
    }
    
    @PutMapping(value = "/addTags")
    public List<Tag> addTagsToTicket(@PathVariable("ticket_id") @Valid long ticket, @RequestParam("tags") @Valid @NotEmpty List<String> tags) throws RecordNotFoundException, InnerServiceException {
        return ticketTagService.addTags(ticket,tags);
    }
    
    
    @PutMapping(value = "/editDescription")
    public Ticket editTicketDescription(@PathVariable("ticket_id") @Valid long ticketId, @RequestParam("description") @Valid @NotBlank String description) throws RecordNotFoundException {
        return ticketEditorDetailsService.editTicketDescription(ticketId,description);
    }
    
    @PutMapping(value = "/editProblem")
    public Ticket editTicketpProblem(@PathVariable("ticket_id")  @Valid long ticketId,@RequestParam("problem_id") @Valid int problemId) throws InvalidRequestException, RecordNotFoundException {
        return ticketEditorDetailsService.editTicketProblem(ticketId,problemId);
    }
    
    @PostMapping(value = "/addComment")
    public Comment addTicketComment(@PathVariable("ticket_id")  @Valid long ticketId, @RequestParam("user_id") @Valid long userId, @RequestParam("text_message") @Valid @NotBlank String textMessage) throws InvalidRequestException, RecordNotFoundException {
        return ticketCommentService.addCommentToTicket(ticketId,userId,textMessage);
    }
}
