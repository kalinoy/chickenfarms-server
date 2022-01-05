package com.chickenfarms.chickenfarms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TagBusinessDetails {

    String tagName;
    List<Long> ticketId;
}
