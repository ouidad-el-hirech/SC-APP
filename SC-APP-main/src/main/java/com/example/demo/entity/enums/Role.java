package com.example.demo.entity.enums;

public enum Role {
    CLIENT,
    EMPLOYEE,
    ADMIN;
    
    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
