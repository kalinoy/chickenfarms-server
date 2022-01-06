package com.chickenfarms.chickenfarms.controller;

import com.chickenfarms.chickenfarms.exception.DBException;
import com.chickenfarms.chickenfarms.exception.InvalidRequestException;
import com.chickenfarms.chickenfarms.exception.RecordNotFoundException;
import com.chickenfarms.chickenfarms.model.ApiResponse;
import com.chickenfarms.chickenfarms.model.CreateTicketDetailsDTO;
import com.chickenfarms.chickenfarms.model.entity.Ticket;
import com.chickenfarms.chickenfarms.service.TicketLifecycleStateService;
import com.chickenfarms.chickenfarms.utils.ApiResponseUtils;
import com.chickenfarms.chickenfarms.utils.BusinessDetailsConverterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    
    @PostMapping(value = "/create",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> createNewTicket(@RequestBody @Valid CreateTicketDetailsDTO createTicketDetailsDTO) throws RecordNotFoundException, DBException {
        Ticket ticket= ticketLifecycleStateService.createNewTicket(createTicketDetailsDTO);
        return ApiResponseUtils.getApiResponse(BusinessDetailsConverterUtils.getTicketBusinessDetails(ticket));
    }
    
    @PostMapping(value = "/{ticket_id}/close")
    public ResponseEntity<ApiResponse> moveTicketToCloseStatus(@PathVariable("ticket_id") @Valid long ticketId, @RequestParam("is_resolved") @Valid boolean isResolved) throws RecordNotFoundException, InvalidRequestException {
        Ticket ticket= ticketLifecycleStateService.moveTicketToCloseStatus(ticketId,isResolved);
        return ApiResponseUtils.getApiResponse(BusinessDetailsConverterUtils.getTicketBusinessDetails(ticket));
    }
    
    @PostMapping(value = "/{ticket_id}/ready")
    public ResponseEntity<ApiResponse> moveTicketToReadyStatus(@PathVariable("ticket_id")  @Valid long ticketId,@RequestParam("root_cause") @Valid @NotBlank String rootCause) throws RecordNotFoundException, InvalidRequestException {
        Ticket ticket= ticketLifecycleStateService.moveTicketToStatusReady(ticketId,rootCause);
        return ApiResponseUtils.getApiResponse(BusinessDetailsConverterUtils.getTicketBusinessDetails(ticket));
    }
}
