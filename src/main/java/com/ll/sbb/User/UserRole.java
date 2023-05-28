package com.ll.sbb.User;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    //admin과 user 권한
    UserRole(String value) {
        this.value = value;
    }

    private String value;
}