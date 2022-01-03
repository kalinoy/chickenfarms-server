package com.chickenfarms.chickenfarms.service;

import com.chickenfarms.chickenfarms.exception.InnerServiceException;
import com.chickenfarms.chickenfarms.exception.RecordNotFoundException;
import com.chickenfarms.chickenfarms.model.entities.Tag;

import java.util.List;

public interface TicketEditService {

    List<Tag> addTags(long ticketId, List<String> tags) throws RecordNotFoundException, InnerServiceException;
}
