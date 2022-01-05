package com.chickenfarms.chickenfarms.model.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "UserTable")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id",insertable = false, updatable = false)
    private long userId;
    
    @Column(name = "user_name")
    private String userName;
    
    @Column(name = "user_type")
    private String typeName;
    
    @ToString.Exclude
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Ticket> tickets;
    
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Comment> comments;
}
