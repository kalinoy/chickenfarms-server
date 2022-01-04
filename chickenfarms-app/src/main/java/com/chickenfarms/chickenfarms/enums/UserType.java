package com.chickenfarms.chickenfarms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserType {
    
    AGENT("Agent"),
    ENGINEER("Engineer"),
    ADMIN("Admin");
    
    private String userType;
}
