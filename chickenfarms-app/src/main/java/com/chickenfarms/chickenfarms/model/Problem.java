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
@Table(name="ProblemTable")
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="problemId")
    private int problemId;

    @Column(name="problemName")
    private String problemName;

    @OneToMany(mappedBy = "problem")
    private Set<Ticket> tickets;


}
