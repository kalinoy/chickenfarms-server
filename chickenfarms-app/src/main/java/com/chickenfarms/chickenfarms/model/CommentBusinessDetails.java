package com.chickenfarms.chickenfarms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Setter
@Getter
public class CommentBusinessDetails {
    
    private long ticketId;
    private String commentText;
    private Date createdDate;
    private String userName;
}
