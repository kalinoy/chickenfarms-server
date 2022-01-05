package com.chickenfarms.chickenfarms.model.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="ProblemTable")
public class Problem {

    @Id
    @Column(name="problem_Id")
    private int problemId;

    @Column(name="problem_Name")
    private String problemName;
    
    @OneToMany(mappedBy = "problem")
    private Set<Ticket> tickets;


}
