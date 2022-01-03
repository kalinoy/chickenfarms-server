package com.chickenfarms.chickenfarms.service;

import com.chickenfarms.chickenfarms.exception.RecordNotFoundException;
import com.chickenfarms.chickenfarms.model.DTO.TicketTagsRequestDTO;

public interface TicketEditService {

    boolean addTags(TicketTagsRequestDTO ticketTagsRequestDTO) throws RecordNotFoundException;
}
