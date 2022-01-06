package com.chickenfarms.chickenfarms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@AllArgsConstructor
public class CreatedTicketDetailsPayload {
    
    @NotBlank
    private String description;
    @NotNull
    private int problemId;
    @NotEmpty
    private List<Long> customersId;
    @NotNull
    private long userId;
    @NotNull
    private int farmId;
}
