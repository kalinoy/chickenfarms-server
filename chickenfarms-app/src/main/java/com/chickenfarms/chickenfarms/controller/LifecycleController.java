package com.chickenfarms.chickenfarms.controller;

import com.chickenfarms.chickenfarms.exception.DBException;
import com.chickenfarms.chickenfarms.exception.InvalidRequestException;
import com.chickenfarms.chickenfarms.exception.RecordNotFoundException;
import com.chickenfarms.chickenfarms.model.ApiResponse;
import com.chickenfarms.chickenfarms.model.CreatedTicketDetailsPayload;
import com.chickenfarms.chickenfarms.model.entity.Ticket;
import com.chickenfarms.chickenfarms.service.TicketLifecycleStateService;
import com.chickenfarms.chickenfarms.utils.ApiResponseUtils;
import com.chickenfarms.chickenfarms.utils.BusinessDetailsConverterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/chickenFarms/lifecycle/ticket")
public class LifecycleController {
    
    private TicketLifecycleStateService ticketLifecycleStateService;
    
    @Autowired
    public LifecycleController(TicketLifecycleStateService ticketLifecycleStateService) {
        this.ticketLifecycleStateService = ticketLifecycleStateService;
    }
    
    @PostMapping(value = "/create")
    public ResponseEntity<ApiResponse> createNewTicket(@RequestBody @Valid CreatedTicketDetailsPayload createdTicketDetailsPayload) throws RecordNotFoundException, DBException {
        Ticket ticket= ticketLifecycleStateService.createNewTicket(createdTicketDetailsPayload);
        return ApiResponseUtils.getApiResponse(BusinessDetailsConverterUtils.getTicketBusinessDetails(ticket));
    }
    
    @GetMapping(value = "/{ticket_id}/ready/root_cause/{root_cause}")
    public ResponseEntity<ApiResponse> moveTicketToReadyStatus(@PathVariable("ticket_id")  @Valid long ticketId,@PathVariable("root_cause") @Valid @NotBlank String rootCause) throws RecordNotFoundException, InvalidRequestException {
        Ticket ticket= ticketLifecycleStateService.moveTicketToStatusReady(ticketId,rootCause);
        return ApiResponseUtils.getApiResponse(BusinessDetailsConverterUtils.getTicketBusinessDetails(ticket));
    }
    
    @GetMapping(value = "/{ticket_id}/close/resloved/{is_resolved}")
    public ResponseEntity<ApiResponse> moveTicketToCloseStatus(@PathVariable("ticket_id") @Valid long ticketId, @PathVariable("is_resolved") @Valid boolean isResolved) throws RecordNotFoundException, InvalidRequestException {
        Ticket ticket= ticketLifecycleStateService.moveTicketToCloseStatus(ticketId,isResolved);
        return ApiResponseUtils.getApiResponse(BusinessDetailsConverterUtils.getTicketBusinessDetails(ticket));
    }
    

}
