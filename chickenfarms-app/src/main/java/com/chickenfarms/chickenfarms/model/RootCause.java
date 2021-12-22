package com.chickenfarms.chickenfarms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="RootCauseTable")
public class RootCause {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="rootCauseId",insertable = false, updatable = false)
    private int rootCauseId;
    
    @Column(name = "rootCauseName")
    private String rootCauseName;
    
    @OneToMany(mappedBy = "rootCause", fetch = FetchType.LAZY)
    private Set<Ticket> tickets;
    
    


}
