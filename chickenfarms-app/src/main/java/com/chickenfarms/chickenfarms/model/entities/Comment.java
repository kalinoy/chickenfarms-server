package com.chickenfarms.chickenfarms.model.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="CommentTable")
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "commentId")
    private long commentId;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @Column(name = "comment_Text")
    private String commentText;

    @Column(name = "created_Date")
    private Date createdDate;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
}
