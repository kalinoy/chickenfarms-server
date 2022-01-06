package com.chickenfarms.chickenfarms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CommentBusinessDetails {
    
    private long ticketId;
    private String commentText;
    private Date createdDate;
    private String userName;
}
