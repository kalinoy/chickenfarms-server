package com.chickenfarms.chickenfarms.service;

import com.chickenfarms.chickenfarms.exception.InnerServiceException;
import com.chickenfarms.chickenfarms.exception.RecordNotFoundException;
import com.chickenfarms.chickenfarms.model.entity.Tag;

import java.util.List;

public interface TicketTagService {
    
    List<Tag> addTags(long ticketId, List<String> tags) throws RecordNotFoundException, InnerServiceException;
}
