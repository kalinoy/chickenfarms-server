package com.chickenfarms.chickenfarms.controller;

import com.chickenfarms.chickenfarms.exception.DBException;
import com.chickenfarms.chickenfarms.exception.InvalidRequestException;
import com.chickenfarms.chickenfarms.exception.RecordNotFoundException;
import com.chickenfarms.chickenfarms.model.CreateTicketDetailsDTO;
import com.chickenfarms.chickenfarms.model.entities.Ticket;
import com.chickenfarms.chickenfarms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    public long createNewTicket(@RequestBody @Valid CreateTicketDetailsDTO createTicketDetailsDTO) throws RecordNotFoundException, DBException {
        Ticket ticket= ticketLifecycleStateService.createNewTicket(createTicketDetailsDTO);
        return ticket.getTicketId();
    }
    
    @PostMapping(value = "/{ticket_id}/close")
    public Ticket moveTicketToCloseStatus(@PathVariable("ticket_id") @Valid long ticketId, @RequestParam("is_resolved") @Valid boolean isResolved) throws RecordNotFoundException, InvalidRequestException {
        return ticketLifecycleStateService.moveTicketToCloseStatus(ticketId,isResolved);
    }
    
    @PostMapping(value = "/{ticket_id}/ready")
    public Ticket moveTicketToReadyStatus(@PathVariable("ticket_id")  @Valid long ticketId,@RequestParam("root_cause") @Valid @NotBlank String rootCause) throws RecordNotFoundException, InvalidRequestException {
        return ticketLifecycleStateService.moveTicketToStatusReady(ticketId,rootCause);
    }
}
