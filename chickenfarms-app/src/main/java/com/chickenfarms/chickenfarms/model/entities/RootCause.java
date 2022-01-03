package com.chickenfarms.chickenfarms.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
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
