package com.chickenfarms.chickenfarms.controller;


import com.chickenfarms.chickenfarms.exception.DBException;
import com.chickenfarms.chickenfarms.exception.InnerServiceException;
import com.chickenfarms.chickenfarms.exception.InvalidRequestException;
import com.chickenfarms.chickenfarms.exception.RecordNotFoundException;
import com.chickenfarms.chickenfarms.model.DTO.CreateTicketDetailsDTO;
import com.chickenfarms.chickenfarms.model.entities.Tag;
import com.chickenfarms.chickenfarms.model.entities.Ticket;
import com.chickenfarms.chickenfarms.service.TicketEditService;
import com.chickenfarms.chickenfarms.service.TicketLifecycleStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/chickenFarms")
//@EnableTransactionManagement
public class ChickenFarmsController {

    private TicketLifecycleStateService ticketLifecycleStateService;
    
    private TicketEditService ticketEditService;
    
    //TODO: check valid input in all endpoints
    @Autowired
    public ChickenFarmsController(TicketLifecycleStateService ticketLifecycleStateService, TicketEditService ticketEditService) {
        this.ticketLifecycleStateService = ticketLifecycleStateService;
        this.ticketEditService=ticketEditService;
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
    public List<Tag> addTagsToTicket(@RequestParam("ticket_id") @Valid long ticket, @RequestParam("tags") @Valid List<String> tags) throws RecordNotFoundException, InnerServiceException {
        return ticketEditService.addTags(ticket,tags);
    }
    
    @PostMapping(value = "/moveToReady")
    public Ticket moveTicketToReadyStatus(@RequestParam("ticket_id")  @Valid long ticketId,@RequestParam("root_cause") @Valid String rootCause) throws RecordNotFoundException, InvalidRequestException {
        return ticketLifecycleStateService.moveTicketToStatusReady(ticketId,rootCause);
    }
    

    
    

}
