package com.chickenfarms.chickenfarms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TagBusinessDetails {

    String tagName;
    List<Long> ticketId;
}
