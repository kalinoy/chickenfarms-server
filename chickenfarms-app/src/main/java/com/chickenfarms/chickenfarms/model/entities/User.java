package com.chickenfarms.chickenfarms.model.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "UserTable")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userId",insertable = false, updatable = false)
    private long userId;
    
    @Column(name = "userName")
    private String userName;
    
    @Column(name = "userType")
    private String typeName;
    
    @ToString.Exclude
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Ticket> tickets;
    
    //Lombok @Data generates toString for you and you are probably using bidirectiona
    @ToString.Exclude
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Comment> comments;
}
