package com.chickenfarms.chickenfarms.controller;

import com.chickenfarms.chickenfarms.exception.InnerServiceException;
import com.chickenfarms.chickenfarms.exception.InvalidRequestException;
import com.chickenfarms.chickenfarms.exception.RecordNotFoundException;
import com.chickenfarms.chickenfarms.model.ApiResponse;
import com.chickenfarms.chickenfarms.model.entity.Comment;
import com.chickenfarms.chickenfarms.model.entity.Tag;
import com.chickenfarms.chickenfarms.model.entity.Ticket;
import com.chickenfarms.chickenfarms.service.TicketCommentService;
import com.chickenfarms.chickenfarms.service.TicketEditorDetailsService;
import com.chickenfarms.chickenfarms.service.TicketTagService;
import com.chickenfarms.chickenfarms.service.TicketViewService;
import com.chickenfarms.chickenfarms.utils.ApiResponseUtils;
import com.chickenfarms.chickenfarms.utils.BusinessDetailsConverterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse> addTagsToTicket(@PathVariable("ticket_id") @Valid long ticketId, @RequestParam("tags") @Valid @NotEmpty List<String> tags) throws RecordNotFoundException, InnerServiceException {
        List<Tag> tagsList= ticketTagService.addTags(ticketId,tags);
        return ApiResponseUtils.getApiResponse(BusinessDetailsConverterUtils.getTagBusinessDetailsList(tagsList));
    }
    
    
    @PutMapping(value = "/editDescription")
    public ResponseEntity<ApiResponse> editTicketDescription(@PathVariable("ticket_id") @Valid long ticketId, @RequestParam("description") @Valid @NotBlank String description) throws RecordNotFoundException {
        Ticket ticket= ticketEditorDetailsService.editTicketDescription(ticketId,description);
        return ApiResponseUtils.getApiResponse(BusinessDetailsConverterUtils.getTicketBusinessDetails(ticket));
    }
    
    @PutMapping(value = "/editProblem")
    public ResponseEntity<ApiResponse> editTicketProblem(@PathVariable("ticket_id")  @Valid long ticketId, @RequestParam("problem_id") @Valid int problemId) throws InvalidRequestException, RecordNotFoundException {
        Ticket ticket= ticketEditorDetailsService.editTicketProblem(ticketId,problemId);
        return ApiResponseUtils.getApiResponse(BusinessDetailsConverterUtils.getTicketBusinessDetails(ticket));
    }
    
    @PostMapping(value = "/addComment")
    public ResponseEntity<ApiResponse> addTicketComment(@PathVariable("ticket_id")  @Valid long ticketId, @RequestParam("user_id") @Valid long userId, @RequestParam("text_message") @Valid @NotBlank String textMessage) throws InvalidRequestException, RecordNotFoundException {
        Comment comment= ticketCommentService.addCommentToTicket(ticketId,userId,textMessage);
        return ApiResponseUtils.getApiResponse(BusinessDetailsConverterUtils.getCommentBusinessDetails(comment));
    }
    
}
